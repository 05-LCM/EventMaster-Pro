package com.eventmasterpro.persistence;

import com.eventmasterpro.model.Location;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LocationStorageManager {
    //Save a list of locations in txt file
    public static void saveLocationList(List<Location> locations, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Location loc : locations) {
                writer.write(loc.toFileString());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //Load a list of locations
    public static List<Location> loadLocationList(String filePath) throws IOException {
        List<Location> locations = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Location location = Location.fromFileString(line);
                locations.add(location);
            }
        }

        return locations;
    }
}
