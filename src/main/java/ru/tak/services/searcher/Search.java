package ru.tak.services.searcher;

import ru.tak.model.City;

import java.util.List;

public class Search {
    public String searchByPopulation(List<City> cities) {
        int maxPopulation = 0;
        int index = 0;
        for (int i =0; i < cities.size(); i++) {
            if (cities.get(i).getPopulation() > maxPopulation) {
                maxPopulation = cities.get(i).getPopulation();
                index = i;
            }
        }
        return "[" + index + "]" + " = " + maxPopulation;
    }
}
