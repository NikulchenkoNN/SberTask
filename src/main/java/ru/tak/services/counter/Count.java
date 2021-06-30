package ru.tak.services.counter;

import ru.tak.model.City;

import java.util.*;

public class Count {
    public Map<String, Integer> countCityByRegions(List<City> cities) {
        Map<String, Integer> map = new TreeMap<>();
        for (City city :
                cities) {
            if (!map.containsKey(city.getRegion())) {
                map.put(city.getRegion(), 1);
            } else {
                map.put(city.getRegion(), map.get(city.getRegion()) + 1);
            }
        }

        return map;
    }
}

