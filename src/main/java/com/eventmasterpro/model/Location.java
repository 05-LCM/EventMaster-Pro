package com.eventmasterpro.model;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class Location {
    //Class attributes
    private static double countId=0;
    private final double id;
    private final String name;
    private final String address;
    private int capacity;
    private final ArrayList<String> technicalFeatures;
    private final ArrayList<LocalDate> availableDates;
    //Constructor of the class
    public Location(String name, String address, int capacity) {
        this.id = ++countId;
        this.name = name;
        this.address = address;
        this.capacity = capacity;
        this.technicalFeatures = new ArrayList<>();
        this.availableDates = new ArrayList<>();
    }
    //Getters and setters
    public String getName() {
        return name;
    }
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    public ArrayList<LocalDate> getAvailableDates() {
        return availableDates;
    }
    //Methods of the class
    //Manage technical characteristics of the site
    public void addTechnicalFeature(String feature) {
        if (!technicalFeatures.contains(feature)) {
            technicalFeatures.add(feature);
        }
    }
    public void removeTechnicalFeature(String feature) {
        technicalFeatures.remove(feature);
    }
    //Manage site availability
    //Add new date available
    public void addAvailableDate(LocalDate date) {
        if (!availableDates.contains(date)) {
            availableDates.add(date);
        }
    }
    //Convert object to string to save it
    public String toFileString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(";")
                .append(address).append(";")
                .append(capacity).append(";");
        for (String feature : technicalFeatures) {
            sb.append(feature).append("|");
        }
        sb.append(";");
        for (LocalDate date : availableDates) {
            sb.append(date.toString()).append("|");
        }
        return sb.toString();
    }
    //Convert string in object location
    public static Location fromFileString(String line) {
        String[] parts = line.split(";");
        String name = parts.length > 0 ? parts[0] : "Unnamed location";
        String address = parts.length > 1 ? parts[1] : "No address";
        int capacity = 0;
        if (parts.length > 2 && !parts[2].isEmpty()) {
            try {
                capacity = Integer.parseInt(parts[2]);
            } catch (NumberFormatException e) {
                System.err.println("Invalid capacity, defaulting to 0 for line: " + line);
            }
        }
        Location location = new Location(name, address, capacity);
        if (parts.length > 3 && !parts[3].isEmpty()) {
            String[] features = parts[3].split("\\|");
            for (String feature : features) {
                location.addTechnicalFeature(feature);
            }
        }
        if (parts.length > 4 && !parts[4].isEmpty()) {
            String[] dates = parts[4].split("\\|");
            for (String dateStr : dates) {
                try {
                    location.addAvailableDate(LocalDate.parse(dateStr));
                } catch (DateTimeParseException e) {
                    System.err.println("Invalid date format: " + dateStr);
                }
            }
        }
        return location;
    }
    @Override
    public String toString() {
        return "Id: "+id+"\nLocation name: "+name+"\nLocation address: "+address+"\nLocation capacity: "+capacity;
    }
}
