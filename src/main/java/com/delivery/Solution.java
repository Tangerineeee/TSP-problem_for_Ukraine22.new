package com.delivery;

import java.util.List;

public class Solution {
    private List<String> bestRoute;
    private double bestTourLength;
    private double fuelExpenses;
    private long algorithmExecutionTime;

    public List<String> getBestRoute() {
        return bestRoute;
    }

    public void setBestRoute(List<String> bestRoute) {
        this.bestRoute = bestRoute;
    }

    public double getBestTourLength() {
        return bestTourLength;
    }

    public void setBestTourLength(double bestTourLength) {
        this.bestTourLength = bestTourLength;
    }

    public double getFuelExpenses() {
        return fuelExpenses;
    }

    public void setFuelExpenses(double fuelExpenses) {
        this.fuelExpenses = fuelExpenses;
    }

    public double getAlgorithmExecutionTime() {
        return algorithmExecutionTime;
    }

    public void setAlgorithmExecutionTime(long algorithmExecutionTime) {
        this.algorithmExecutionTime = algorithmExecutionTime;
    }
}
