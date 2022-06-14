package com.delivery;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class AntColonyOptimization {

    private final double c = 1.0;
    private final double alpha = 1;
    private final double beta = 5;
    private final double evaporation = 0.5;
    private final double Q = 500;
    private final double antFactor = 8;
    private final double randomFactor = 0.1;

    private int maxIterations = 1000;

    private int numberOfCities;
    private int numberOfAnts;
    private double[][] graph;
    private double[][] trails;
    private List<Ant> ants = new ArrayList<>();
    private Random random = new Random();
    private double[] probabilities;

    private int currentIndex;

    private int[] bestTourOrder;
    private double bestTourLength;

    private final List<String> citiesList;
    private double fuelCost;
    private double fuelConsumption;

    private long algorithmExecutionTime;
    private double fuelExpenses;

    public AntColonyOptimization(RequestParameters requestParameters) { //передаємо список міст
        citiesList = requestParameters.getCitiesList();
        fuelCost = requestParameters.getFuelCost();
        fuelConsumption = requestParameters.getFuelConsumption();
        graph = getDistancesMatrix(citiesList);
        numberOfCities = graph.length;
        numberOfAnts = (int) (numberOfCities * antFactor);

        trails = new double[numberOfCities][numberOfCities];
        probabilities = new double[numberOfCities];
        IntStream.range(0, numberOfAnts)
                .forEach(i -> ants.add(new Ant(numberOfCities)));
    }

    /**
     * Generate initial solution
     */
    public double[][] getDistancesMatrix(List<String> citiesList) {
        Map<Set<String>, Double> citiesDistancesMap = Dao.getCitiesDistances(citiesList);
        int size = citiesList.size();
        double[][] distancesMatrix = new double[size][size];
        int i = 0;
        int j = 0;
        for (var city1 : citiesList) {
            for (var city2 : citiesList) {
                if (city1.equals(city2)) {
                    distancesMatrix[i][j] = 0;
                } else {
                    Set<String> cityPair = new HashSet<>();
                    cityPair.add(city1);
                    cityPair.add(city2);
                    double distance = citiesDistancesMap.get(cityPair);
                    distancesMatrix[i][j] = distance;
                }
                j++;
            }
            j = 0;
            i++;
        }
        return distancesMatrix;
    }

    /**
     * Use this method to run the main logic
     */
    public Solution solve() {
        Instant startTime = Instant.now();
        setupAnts();
        clearTrails();
        IntStream.range(0, maxIterations)
                .forEach(i -> {
                    moveAnts();
                    updateTrails();
                    updateBest();
                });
        calculateFuelExpenses();
        Instant endTime = Instant.now();
        algorithmExecutionTime = Duration.between(startTime, endTime).toMillis();
        return getSolution();
    }

    /**
     * Prepare ants for the simulation
     */
    private void setupAnts() {
        IntStream.range(0, numberOfAnts)
                .forEach(i -> ants.forEach(ant -> {
                    ant.clear();
                    ant.visitCity(-1, random.nextInt(numberOfCities));
                }));
        currentIndex = 0;
    }

    /**
     * At each iteration, move ants
     */
    private void moveAnts() {
        IntStream.range(currentIndex, numberOfCities - 1)
                .forEach(i -> {
                    ants.forEach(ant -> ant.visitCity(currentIndex, selectNextCity(ant)));
                    currentIndex++;
                });
    }

    /**
     * Select next city for each ant
     */
    private int selectNextCity(Ant ant) {
        int t = random.nextInt(numberOfCities - currentIndex);
        if (random.nextDouble() < randomFactor) {
            OptionalInt cityIndex = IntStream.range(0, numberOfCities)
                    .filter(i -> i == t && !ant.visited(i))
                    .findFirst();
            if (cityIndex.isPresent()) {
                return cityIndex.getAsInt();
            }
        }
        calculateProbabilities(ant);
        double r = random.nextDouble();
        double total = 0;
        for (int i = 0; i < numberOfCities; i++) {
            total += probabilities[i];
            if (total >= r) {
                return i;
            }
        }

        throw new RuntimeException("There are no other cities");
    }

    /**
     * Calculate the next city picks probabilites
     */
    public void calculateProbabilities(Ant ant) {
        int i = ant.trail[currentIndex];
        double pheromone = 0.0;
        for (int l = 0; l < numberOfCities; l++) {
            if (!ant.visited(l)) {
                pheromone += Math.pow(trails[i][l], alpha) * Math.pow(1.0 / graph[i][l], beta);
            }
        }
        for (int j = 0; j < numberOfCities; j++) {
            if (ant.visited(j)) {
                probabilities[j] = 0.0;
            } else {
                double numerator = Math.pow(trails[i][j], alpha) * Math.pow(1.0 / graph[i][j], beta);
                probabilities[j] = numerator / pheromone;
            }
        }
    }

    /**
     * Update trails that ants used
     */
    private void updateTrails() {
        for (int i = 0; i < numberOfCities; i++) {
            for (int j = 0; j < numberOfCities; j++) {
                trails[i][j] *= evaporation;
            }
        }
        for (Ant a : ants) {
            double contribution = Q / a.trailLength(graph);
            for (int i = 0; i < numberOfCities - 1; i++) {
                trails[a.trail[i]][a.trail[i + 1]] += contribution;
            }
            trails[a.trail[numberOfCities - 1]][a.trail[0]] += contribution;
        }
    }

    /**
     * Update the best solution
     */
    private void updateBest() {
        if (bestTourOrder == null) {
            bestTourOrder = ants.get(0).trail;
            bestTourLength = ants.get(0)
                    .trailLength(graph);
        }
        for (Ant a : ants) {
            if (a.trailLength(graph) < bestTourLength) {
                bestTourLength = a.trailLength(graph);
                bestTourOrder = a.trail.clone();
            }
        }
    }

    /**
     * Clear trails after simulation
     */
    private void clearTrails() {
        IntStream.range(0, numberOfCities)
                .forEach(i -> IntStream.range(0, numberOfCities)
                        .forEach(j -> trails[i][j] = c));
    }

    private void calculateFuelExpenses() {
        fuelExpenses = bestTourLength * fuelConsumption / 100 * fuelCost;
    }

    private Solution getSolution() {
        var solution = new Solution();
        List<String> bestRoute = new LinkedList<>();
        for (int j : bestTourOrder) {
            bestRoute.add(citiesList.get(j));
        }
        if (!bestRoute.get(0).equals(citiesList.get(0))) {
            int firstCityIndex = bestRoute.indexOf(citiesList.get(0));
            bestRoute = Stream.concat(bestRoute.subList(firstCityIndex, bestRoute.size()).stream(),
                            bestRoute.subList(0, firstCityIndex).stream())
                    .collect(Collectors.toList());
        }
        bestRoute.add(citiesList.get(0));
        solution.setBestRoute(bestRoute);
        solution.setBestTourLength(bestTourLength);
        solution.setFuelExpenses(fuelExpenses);
        solution.setAlgorithmExecutionTime(algorithmExecutionTime);
        return solution;
    }
}