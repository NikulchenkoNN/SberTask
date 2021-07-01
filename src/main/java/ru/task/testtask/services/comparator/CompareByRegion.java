package ru.task.testtask.services.comparator;

import ru.task.testtask.model.City;

import java.util.Comparator;

public class CompareByRegion implements Comparator<City> {
    @Override
    public int compare(City o1, City o2) {
        return o1.getDistrict().compareTo(o2.getDistrict());
    }
}