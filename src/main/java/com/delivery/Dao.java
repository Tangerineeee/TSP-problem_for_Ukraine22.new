package com.delivery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Dao {

    private Dao() {

    }

    public static List<String> getCitiesList() {
        String query = "SELECT DISTINCT city1 FROM cities_distances UNION SELECT DISTINCT city2 FROM cities_distances";
        List<String> citiesList = new ArrayList<>();
        try (Connection connection = DatabaseUtil.getDataSource().getConnection();
             PreparedStatement statement1 = connection.prepareStatement(query)) {
            ResultSet resultSet = statement1.executeQuery();
            while (resultSet.next()) {
                citiesList.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return citiesList;
    }

    public static Map<Set<String>, Double> getCitiesDistances(List<String> cities) {
        String query = "SELECT * FROM cities_distances WHERE city1=? AND city2=?";
        Map<Set<String>, Double> citiesDistances = new HashMap<>();
        try (Connection connection = DatabaseUtil.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            for (var city1 : cities) {
                for (var city2 : cities) {
                    if (city1.equals(city2)) {
                        continue;
                    }
                    statement.setString(1, city1);
                    statement.setString(2, city2);
                    ResultSet resultSet = statement.executeQuery();
                    while (resultSet.next()) {
                        Set<String> citiesPair = new HashSet<>();
                        citiesPair.add(resultSet.getString(1));
                        citiesPair.add(resultSet.getString(2));
                        citiesDistances.put(citiesPair, resultSet.getDouble(3));
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
//        System.out.println(citiesDistances);

        return citiesDistances;
    }
}
