package ru.tak.services.loader;

import ru.tak.model.City;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CatalogueLoader {

    public List<City> readFromFileToList() {
        List<City> cities = new ArrayList<>();
        File file = new File(getClass().getClassLoader().getResource("Cities.txt").getFile());
        try {
            Scanner scanner = new Scanner(new File(String.valueOf(file)));
            while (scanner.hasNext()) {
                String[] str = scanner.nextLine().split(";");
                City city = new City();
                city.setId(Integer.parseInt(str[0]));
                city.setName(str[1]);
                city.setRegion(str[2]);
                city.setDistrict(str[3]);
                city.setPopulation(Integer.parseInt(str[4]));
                city.setFoundation(Integer.parseInt(str[5]));
                cities.add(city);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found, check correct filepath");
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Data in file incorrect");
            e.printStackTrace();
        }
        return cities;
    }
}
