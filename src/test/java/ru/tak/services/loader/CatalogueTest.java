package ru.tak.services.loader;


import org.junit.Assert;
import org.junit.Test;
import ru.tak.services.comparator.CompareByName;
import ru.tak.services.comparator.CompareByRegion;
import ru.tak.services.counter.Count;
import ru.tak.model.City;
import ru.tak.services.searcher.Search;

import java.util.ArrayList;
import java.util.List;

public class CatalogueTest {
    private CatalogueLoader loader = new CatalogueLoader();

    private List<City> cities = loader.readFromFileToList();

    @Test
    public void whenLoadFromFileSizeCorrect() {
        Assert.assertEquals(8, new CatalogueLoader().readFromFileToList().size());
    }


    @Test
    public void listAfterSortByNameNotEqualsListBerfore() {
        List<City> citiesSorted = new ArrayList<>(cities);
        citiesSorted.sort(new CompareByName());
        Assert.assertNotEquals(cities, citiesSorted);
    }

    @Test
    public void listAfterSortByRegionNotEqualsListBerfore() {
        List<City> citiesSorted = new ArrayList<>(cities);
        citiesSorted.sort(new CompareByRegion());
        Assert.assertNotEquals(cities, citiesSorted);
    }

    @Test
    public void listAfterSortByRegionAndNameNotEqualsListBerfore() {
        List<City> citiesSorted = new ArrayList<>(cities);
        citiesSorted.sort(new CompareByRegion().thenComparing(new CompareByName()));
        Assert.assertNotEquals(cities, citiesSorted);
    }

    @Test
    public void searchByPopulation() {
        String str = "[3] = 1150000";
        Assert.assertEquals(str, new Search().searchByPopulation(cities));
    }

    @Test
    public void countCityInRegion() {
        Assert.assertEquals(6, new Count().countCityByRegions(cities).size());
    }
}