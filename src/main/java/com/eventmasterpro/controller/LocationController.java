package com.eventmasterpro.controller;

import com.eventmasterpro.model.Location;
import com.eventmasterpro.utils.InputValidator;

import java.time.LocalDate;
import java.util.ArrayList;

public class LocationController {
    //An array is made to store all the locations added by the user
    private ArrayList<Location> locations;
    //Create an input validator
    private InputValidator validator = new InputValidator();
    //A singleton is made to handle only one instance of the class in the whole execution of the program
    private static LocationController locationController;
    private LocationController(){
        createLocations();
    }
    //Get instance of the controller
    public static LocationController getLocationController() {
        if(locationController==null){
            locationController = new LocationController();
        }
        return locationController;
    }
    //Create array of the locations
    private void createLocations(){
        locations = new ArrayList<>();
    }
    //Get all locations
    public ArrayList<Location> getAllLocations() {
        return locations;
    }
    //Get only Location by index
    public Location getLocationById(int index) {
        Location location = locations.get(index-1);
        return location;
    }
    //Create a new location
    public Location createLocation(String name, String address, int capacity) {
        //Validate fields
        if (validator.checkEmpty(name) || validator.checkEmpty(address)|| capacity<=0) {
            return null;
        } else {
            //Create and add locations
            Location location = new Location(name, address, capacity);
            locations.add(location);
            return location;

        }
    }
    //Add date available
    public void recordDateAvailable(LocalDate date, Location location) {
        location.addAvailableDate(date);
    }
    //Occupy available date
    public String deleteDateAvailable(LocalDate date, Location location) {
        if(!location.getAvailableDates().contains(date)){
            return "Location is not available on the requested date";
        }else{
            location.getAvailableDates().remove(date);
            return "Location has been successfully occupied";
        }
    }
    //Search for available locations given a date
    public ArrayList<Location> searchLocationsWithDate(LocalDate date) {
        ArrayList<Location> availableLocations = new ArrayList<>();
        for (Location location : locations) {
            if(location.getAvailableDates().contains(date)){
                availableLocations.add(location);
            }
        }
        return availableLocations;
    }
    //Search for places not available from a certain date
    public ArrayList<Location> searchNotAvailableLocationsWithDate(LocalDate date) {
        ArrayList<Location> notAvailableLocations = new ArrayList<>();
        for (Location location : locations) {
            if(!location.getAvailableDates().contains(date)){
                notAvailableLocations.add(location);
            }
        }
        return notAvailableLocations;
    }
    //Add technical feature
    public void addTechnicalFeature(String feature, Location location) {
        location.addTechnicalFeature(feature);
    }
    //Delete technical feature
    public void removeTechnicalFeature(String feature, Location location) {
        location.removeTechnicalFeature(feature);
    }
    //Change capacity of the locations
    public void changeCapacity(int newCapacity, Location location) {
        location.setCapacity(newCapacity);
    }
    //Show locations
    public void showLocationsDetails(ArrayList<Location> locations) {
        if(locations.isEmpty()){
            System.out.println("Nothing to show");
        }else{
            System.out.println("--------Locations Details--------");
            for(Location location : locations){
                System.out.println(location.toString());
                System.out.println("-----------------------------------------");
            }
        }
    }
}
