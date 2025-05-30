package com.eventmasterpro.controller;

import com.eventmasterpro.model.Artist;
import com.eventmasterpro.model.Event;
import com.eventmasterpro.utils.InputValidator;

import java.util.ArrayList;

public class ArtistController {
    //List of artist
    private ArrayList<Artist> artists = new ArrayList<>();
    //Import data validator
    private final InputValidator validator = new InputValidator();
    //A singleton is made to handle only one instance of the class in the whole execution of the program
    private static ArtistController artistController;
    private ArtistController() {}
    //Function to get instance
    public static ArtistController getArtistController() {
        if (artistController == null) {
            artistController = new ArtistController();
        }
        return artistController;
    }
    //Set artists from external source (used by DataController)
    public void setArtists(ArrayList<Artist> artists) {
        this.artists = artists;
    }
    //Create a new artist
    public Artist createArtists(String name, String contactInfo) {
        if (validator.checkEmpty(name) || validator.checkEmpty(contactInfo)) {
            return null;
        } else {
            Artist artist = new Artist(name, contactInfo);
            if(!artists.contains(artist)) {
                artists.add(artist);
            }
            return artist;
        }
    }
    //Set previous events
    public void setPastEvents(Artist artist, Event event) {
        artist.addPastEvent(event);
    }
    //Add technical requirement
    public void addTechnicalRequirement(String technicalRequirement, Artist artist) {
        artist.addTechnicalRequirement(technicalRequirement);
    }
    //Remove technical requirement
    public String deleteTechnicalRequirement(String technicalRequirement, Artist artist) {
        if (validator.checkEmpty(technicalRequirement)) {
            return "You must fill in all required fields";
        } else {
            if (!artist.getTechnicalRequirements().contains(technicalRequirement)) {
                return "The technical requirement to be eliminated is not within the requirements of the requested artist";
            } else {
                artist.deleteTechnicalRequirement(technicalRequirement);
                return "Technical requirement deleted";
            }
        }
    }
    //Change contact information
    public String changeContactInfo(String contactInfo, Artist artist) {
        if (artist == null || validator.checkEmpty(contactInfo)) {
            return "You must fill in all required fields";
        } else {
            artist.setContactInfo(contactInfo);
            return "Contact info changed";
        }
    }
    //Get all artists
    public ArrayList<Artist> getArtists() {
        return artists;
    }
    //Show artist from list
    public void showArtists(ArrayList<Artist> artists) {
        if (artists.isEmpty()) {
            System.out.println("Nothing to show");
        } else {
            System.out.println("---------------artist to show-------------------");
            for (Artist artist : artists) {
                System.out.println(artist.toString());
                System.out.println("-------------------------------------------------");
            }
        }
    }
    //Get artist by index
    public Artist getArtistById(int id) {
        return artists.get(id - 1);
    }
    //Show all technical requirements from an artist
    public void showAllTechnicalRequirements(Artist artist) {
        for (String technicalRequirement : artist.getTechnicalRequirements()) {
            System.out.println(technicalRequirement);
        }
    }
}
