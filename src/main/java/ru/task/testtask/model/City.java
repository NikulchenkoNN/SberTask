package ru.task.testtask.model;

import lombok.Data;

@Data
public class City {
    private int id;
    private String name;
    private String region;
    private String district;
    private int population;
    private int foundation;

    public static City createCityFromString(String str) {
        String[] strSplit = str.split(";");
        City city = new City();
        city.setId(Integer.parseInt(strSplit[0]));
        city.setName(strSplit[1]);
        city.setRegion(strSplit[2]);
        city.setDistrict(strSplit[3]);
        city.setPopulation(Integer.parseInt(strSplit[4]));
        city.setFoundation(Integer.parseInt(strSplit[5]));
        return city;
    }
}