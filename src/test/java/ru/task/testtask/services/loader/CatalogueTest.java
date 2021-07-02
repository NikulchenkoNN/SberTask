package ru.task.testtask.services.loader;


import org.junit.Assert;
import org.junit.Test;
import ru.task.testtask.model.City;
import ru.task.testtask.services.comparator.CompareByName;
import ru.task.testtask.services.comparator.CompareByRegion;
import ru.task.testtask.services.counter.Count;
import ru.task.testtask.services.searcher.Search;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class CatalogueTest {

    private final List<City> cities = CatalogueLoader.readFromFileToList();
    private final List<City> citiesSorted = new ArrayList<>(cities);

    public CatalogueTest() throws FileNotFoundException {
    }

    @Test
    public void whenLoadFromFileSizeCorrect() throws FileNotFoundException {
        Assert.assertEquals(8, CatalogueLoader.readFromFileToList().size());
    }

    @Test
    public void fileNotFound() {

    }


    @Test
    public void listAfterSortByNameNotEqualsListBerfore() {
        citiesSorted.sort(new CompareByName());
        Assert.assertNotEquals(cities, citiesSorted);
    }

    @Test
    public void listAfterSortByRegionNotEqualsListBerfore() {
        citiesSorted.sort(new CompareByRegion());
        Assert.assertNotEquals(cities, citiesSorted);
    }

    @Test
    public void listAfterSortByRegionAndNameNotEqualsListBerfore() {
        citiesSorted.sort(new CompareByRegion().thenComparing(new CompareByName()));
        Assert.assertNotEquals(cities, citiesSorted);
    }

    @Test
    public void searchByPopulation() {
        String str = "[4] = 1150000";
        Assert.assertEquals(str, Search.searchByPopulation(cities));
    }

    @Test
    public void countCityInRegion() {
        Assert.assertEquals(6, Count.countCityByRegions(cities).size());
    }
}