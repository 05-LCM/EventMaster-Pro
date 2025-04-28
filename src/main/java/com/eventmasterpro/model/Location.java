package com.eventmasterpro.model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Location {
    //Class attributes
    private static double id = 0;
    private String name;
    private String address;
    private int capacity;
    private ArrayList<String> technicalFeatures;
    private ArrayList<LocalDate> availableDates;
    //Constructor of the class
    public Location(String name, String address, int capacity) {
        this.id = ++id;
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
    public void setName(String name) {
        this.name = name;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public int getCapacity() {
        return capacity;
    }
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    public double getId() {
        return id;
    }
    public ArrayList<String> getTechnicalFeatures() {
        return technicalFeatures;
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
    //Delete date of the available dates
    public void removeAvailableDate(LocalDate date) {
        availableDates.remove(date);
    }
    //Check if date is available
    public boolean isAvailableOnDate(LocalDate date) {
        return availableDates.contains(date);
    }
    @Override
    public String toString() {
        return "Location name: "+name+"\nLocation address: "+address+"\nLocation capacity: "+capacity;
    }
}
