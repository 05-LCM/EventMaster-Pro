package com.eventmasterpro.persistence;
import com.eventmasterpro.model.Event;
import java.io.*;
import java.util.*;

import com.eventmasterpro.model.Location;
import com.eventmasterpro.model.Artist;
public class EventStorageManager {
    //Saves a list of Event objects to a .txt file
    public static void saveEventList(List<Event> events, String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Event event : events) {
                writer.write(event.toFileString());
                writer.newLine();
            }
        }
    }

    // Loads Event objects from a .txt file using references to existing Locations and Artists
    public static List<Event> loadEventList(String filePath, Map<String, Location> locationsByName, List<Artist> allArtists) throws IOException {
        List<Event> events = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    Event event = Event.fromFileString(line, locationsByName, allArtists);
                    if (event != null) {
                        events.add(event);
                    }
                } catch (Exception e) {
                    System.err.println("Error parsing event line: " + line);
                    e.printStackTrace();
                }
            }
        }
        return events;
    }
}
