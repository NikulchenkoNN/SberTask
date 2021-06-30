package ru.tak.services.comparator;

import ru.tak.model.City;

import java.util.Comparator;

public class CompareByName implements Comparator<City> {
    @Override
    public int compare(City o1, City o2) {
        return o1.getName().compareTo(o2.getName());
    }
}
