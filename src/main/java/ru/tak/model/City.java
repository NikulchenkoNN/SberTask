package ru.tak.model;

import lombok.Data;

@Data
public class City {

    private int id;
    private String name;
    private String region;
    private String district;
    private int population;
    private int foundation;

}
