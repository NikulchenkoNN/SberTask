package ru.task.services.searcher;

import ru.task.model.City;

import java.util.List;

public class Search {
    public static String searchByPopulation(List<City> cities) {
        int maxPopulation = 0;
        int index = 0;
        for (int i =0; i < cities.size(); i++) {
            if (cities.get(i).getPopulation() > maxPopulation) {
                maxPopulation = cities.get(i).getPopulation();
                index = cities.get(i).getId();
            }
        }
        return "[" + index + "]" + " = " + maxPopulation;
    }
}
