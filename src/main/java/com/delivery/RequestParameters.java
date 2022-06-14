package com.delivery;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class RequestParameters {
    private final String cities;
    private final double fuelCost;
    private final double fuelConsumption;

    public RequestParameters(HttpServletRequest request) {
        cities = request.getParameter("citiesList");
        fuelCost = Double.parseDouble(request.getParameter("fuel-cost"));
        fuelConsumption = Double.parseDouble(request.getParameter("fuel-consumption"));
    }

    public String getCities() {
        return cities;
    }

    public double getFuelCost() {
        return fuelCost;
    }

    public double getFuelConsumption() {
        return fuelConsumption;
    }

    public List<String> getCitiesList() {
        return List.of(cities.split(" "));
    }
}
