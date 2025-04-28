package com.eventmasterpro.controller;

import com.eventmasterpro.model.Artist;
import com.eventmasterpro.model.Event;
import com.eventmasterpro.utils.InputValidator;

import java.util.ArrayList;

public class ArtistController {
    //List of artist
    private final ArrayList<Artist> artists = new ArrayList<>();
    //Import data validator
    private final InputValidator  validator = new InputValidator();
    //A singleton is made to handle only one instance of the class in the whole execution of the program
    private static ArtistController artistController;
    private ArtistController(){

    }
    //Function to get instance
    public static ArtistController getArtistController() {
        if(artistController==null){
            artistController = new ArtistController();
        }
        return artistController;
    }
    //Create a new artist
    public Artist createArtists(String name, String contactInfo) {
        if(validator.checkEmpty(name)||validator.checkEmpty(contactInfo)){
            return null;
        }else{
            return new Artist(name, contactInfo);
        }
    }
    //Set previous events
    public String setPastEvents(Artist artist, Event event) {
        if(artist==null || event==null){
            return "Missing information to be filled in";
        }else{
            artist.addPastEvent(event);
            return "Past event added";
        }
    }
    //Add technical requirement
    public String addTechnicalRequirement(String technicalRequirement, Artist artist) {
        //Check if the technical requirement is not empty
        if(validator.checkEmpty(technicalRequirement)){
            return "You must fill in all required fields";
        }else{
            artist.addTechnicalRequirement(technicalRequirement);
            return "Technical requirement added";
        }
    }
    //Remove technical requirement
    public String deleteTechnicalRequirement(String technicalRequirement, Artist artist) {
        if(validator.checkEmpty(technicalRequirement)){
            return "You must fill in all required fields";
        }else{
            if(!artist.getTechnicalRequirements().contains(technicalRequirement)){
                return "The technical requirement to be eliminated is not within the requirements of the requested artist";
            }else{
                artist.deleteTechnicalRequirement(technicalRequirement);
                return "Technical requirement deleted";
            }
        }
    }
    //Change contact information
    public String changeContactInfo(String contactInfo, Artist artist) {
        if(artist==null || validator.checkEmpty(contactInfo)){
            return "You must fill in all required fields";
        }else{
            artist.setContactInfo(contactInfo);
            return "Contact info changed";
        }
    }
    //Get all artis
    public ArrayList<Artist> getArtists() {
        return artists;
    }
    //Add artist to list
    public void addArtist(Artist artist) {
        artists.add(artist);
    }
    //Show  artist form list
    public void showArtists(ArrayList<Artist> artists) {
        if(artists.isEmpty()){
            System.out.println("Nothing to show");
        }else{
            System.out.println("---------------artist to show-------------------");
            for(Artist artist : artists){
                System.out.println(artist.toString());
                System.out.println("-------------------------------------------------");
            }
        }
    }
    //Get artist by index
    public Artist getArtistById(int id) {
        return artists.get(id-1);
    }
    //Show all technicals requirements form an artist
    public void showAllTechnicalRequirements(Artist artist) {
        for(String technicalRequirement : artist.getTechnicalRequirements()){
            System.out.println(technicalRequirement);
        }
    }
}
