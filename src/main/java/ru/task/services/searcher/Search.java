package ru.task.services.searcher;

import ru.task.model.City;

import java.util.List;

public class Search {
    public static String searchByPopulation(List<City> cities) {
        int maxPopulation = 0;
        int index = 0;
        for (City city : cities) {
            if (city.getPopulation() > maxPopulation) {
                maxPopulation = city.getPopulation();
                index = city.getId();
            }
        }
        return "[" + index + "]" + " = " + maxPopulation;
    }
}