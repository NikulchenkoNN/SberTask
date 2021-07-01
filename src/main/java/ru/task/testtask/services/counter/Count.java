package ru.task.testtask.services.counter;

import ru.task.testtask.model.City;

import java.util.*;

public class Count {
    public static Map<String, Integer> countCityByRegions(List<City> cities) {
        Map<String, Integer> map = new TreeMap<>();
        for (City city : cities) {
            map.put(city.getRegion(), map.getOrDefault(city.getRegion(), 0) +1);
        }
        return map;
    }
}