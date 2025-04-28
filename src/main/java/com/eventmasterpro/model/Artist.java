package com.eventmasterpro.model;

import java.util.ArrayList;

public class Artist {
    //Class attributes
    private static  double id = 0;
    private String name;
    private String contactInfo;
    private ArrayList<String> technicalRequirements;
    private ArrayList<Event> pastEvents;
    //Constructor
    public Artist( String name, String contactInfo) {
        this.id = ++id;
        this.name = name;
        this.contactInfo = contactInfo;
        this.technicalRequirements = new ArrayList<>();
        this.pastEvents = new ArrayList<>();
    }
    //Getters and setters
    //Only modify de contact info
    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }
    //Only the rest of the information can be got
    public double getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public ArrayList<String> getTechnicalRequirements() {
        return technicalRequirements;
    }
    public ArrayList<Event> getPastEvents() {
        return pastEvents;
    }
    //Class methods
    //Add new technical requirement
    public void addTechnicalRequirement(String technicalRequirement) {
        if(!technicalRequirements.contains(technicalRequirement)) {
            technicalRequirements.add(technicalRequirement);
        }
    }
    //Delete technical requirement
    public void deleteTechnicalRequirement(String technicalRequirement) {
        technicalRequirements.remove(technicalRequirement);
    }
    //Add past event
    public void addPastEvent(Event event) {
        if(!pastEvents.contains(event)) {
            pastEvents.add(event);
        }
    }
    //To string of the class
    @Override
    public String toString() {
        return "Name artist: "+name+"\nArtist ID: "+id+"\nContact Information: "+contactInfo;
    }
}
