package ru.task.services.loader;


import org.junit.Assert;
import org.junit.Test;
import ru.task.services.comparator.CompareByName;
import ru.task.services.comparator.CompareByRegion;
import ru.task.services.counter.Count;
import ru.task.model.City;
import ru.task.services.searcher.Search;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class CatalogueTest {
    private CatalogueLoader loader = new CatalogueLoader();

    private List<City> cities = loader.readFromFileToList();
    private List<City> citiesSorted = new ArrayList<>(cities);

    public CatalogueTest() throws FileNotFoundException {
    }

    @Test
    public void whenLoadFromFileSizeCorrect() throws FileNotFoundException {
        new CatalogueLoader();
        Assert.assertEquals(8, CatalogueLoader.readFromFileToList().size());
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
        String str = "[3] = 1150000";
        Assert.assertEquals(str, Search.searchByPopulation(cities));
    }

    @Test
    public void countCityInRegion() {
        new Count();
        Assert.assertEquals(6, Count.countCityByRegions(cities).size());
    }
}