package ru.task.testtask.services.loader;

import ru.task.testtask.model.City;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class CatalogueLoader {

    public static List<City> readFromFileToList() throws FileNotFoundException {
        List<City> cities = new ArrayList<>();

        File file = null;
        try {
            file = new File(Objects.requireNonNull(CatalogueLoader.class.getClassLoader().getResource("Cities.txt")).getFile());
        }catch (NullPointerException e) {
            System.out.println("File does not exist");
            e.printStackTrace();
        }
        validateFile(file);
        try (Scanner scanner = new Scanner(new File(String.valueOf(file)))){
            while (scanner.hasNext()) {
                String str = scanner.nextLine();
                validateString(str);
                cities.add(City.createCityFromString(str));
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found, check correct filepath");
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Data in file incorrect, wrong format");
            e.printStackTrace();
        }

        return cities;
    }

    public static void validateString(String str) {
        String[] strings = str.split(";");
        if (strings.length != 6) throw new IllegalStateException("Data in file is incorrect, not enough poles");
        for (String s : strings) {
            if (s.equals("") || s.equals(" ")) throw new IllegalStateException("Data in file is incorrect, empty poles");
        }
    }

    public static void validateFile(File file) throws FileNotFoundException {
        if (file.length() == 0) throw new FileNotFoundException("File not found");
    }
}