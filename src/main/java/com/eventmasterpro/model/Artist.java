package com.eventmasterpro.model;

import java.util.ArrayList;

public class Artist {
    //Class attributes
    private static double idCount = 0;
    private final double id ;
    private final String name;
    private String contactInfo;
    private final ArrayList<String> technicalRequirements;
    private final ArrayList<Event> pastEvents;
    //Constructor
    public Artist( String name, String contactInfo) {
        this.id = ++idCount;
        this.name = name;
        this.contactInfo = contactInfo;
        this.technicalRequirements = new ArrayList<>();
        this.pastEvents = new ArrayList<>();
    }
    //Getters and setters
    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }
    public String getName() {
        return name;
    }
    public ArrayList<String> getTechnicalRequirements() {
        return technicalRequirements;
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
    //To save object
    public String toFileString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(";").append(contactInfo).append(";");
        for (String req : technicalRequirements) {
            sb.append(req).append("|");
        }
        return sb.toString();
    }
    //To load Artis
    public static Artist fromFileString(String line) {
        String[] parts = line.split(";");
        String name = parts[0];
        String contact = parts[1];
        Artist artist = new Artist(name, contact);
        if (parts.length > 2 && !parts[2].isEmpty()) {
            String[] reqs = parts[2].split("\\|");
            for (String r : reqs) {
                artist.addTechnicalRequirement(r);
            }
        }
        return artist;
    }
    //To string of the class
    @Override
    public String toString() {
        return "Name artist: "+name+"\nArtist ID: "+id+"\nContact Information: "+contactInfo;
    }
}
