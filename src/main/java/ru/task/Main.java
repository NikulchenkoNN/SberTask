package ru.task;

import ru.task.model.City;
import ru.task.services.comparator.CompareByName;
import ru.task.services.comparator.CompareByRegion;
import ru.task.services.counter.Count;
import ru.task.services.loader.CatalogueLoader;
import ru.task.services.searcher.Search;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        List<City> cities = CatalogueLoader.readFromFileToList();
        cities.forEach(System.out::println);
        System.out.println("------");

        cities.stream().sorted(new CompareByName()).forEach(System.out::println);
        System.out.println("------");

        cities.stream().sorted(new CompareByRegion().thenComparing(new CompareByName())).forEach(System.out::println);
        System.out.println("------");

        System.out.println(Search.searchByPopulation(cities));
        System.out.println("------");

        Map<String, Integer> map = Count.countCityByRegions(cities);
        for (Map.Entry<String, Integer> m : map.entrySet()) {
            System.out.println(m.getKey() +" - " + m.getValue());
        }
    }
}
