package com.eventmasterpro.model;

import com.eventmasterpro.model.enums.EventCategory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public class Event {
    //class attributes
    private final double id;
    private final String name;
    private String description;
    private Location location; //The class include an object location for the location of the event
    private LocalDateTime date;
    private EventCategory category;
    private ArrayList<Artist> artists; //List of the artist in the event
    private ArrayList<Ticket> tickets; //Tickets of the event
    private int capacity;
    private double price;
    //Getters and setters methods
    public double getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Location getLocation() {
        return location;
    }
    public void setLocation(Location location) {
        this.location = location;
    }
    public LocalDateTime getDate() {
        return date;
    }
    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    public EventCategory getCategory() {
        return category;
    }
    public void setCategory(EventCategory category) {
        this.category = category;
    }
    public ArrayList<Artist> getArtists() {
        return artists;
    }
    public ArrayList<Ticket> getTickets() {
        return tickets;
    }
    public int getCapacity() {
        return capacity;
    }
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    //Methods of the class
    //Constructor with the Builder object of the class
    private Event(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.description = builder.description;
        this.location = builder.location;
        this.date = builder.date;
        this.category = builder.category;
        this.artists = builder.artists;
        this.tickets = builder.tickets;
        this.capacity = builder.capacity;
        this.price = builder.price;
    }
    //Add a new artist in the event
    public String addNewArtist(Artist artist) {
        //verify that the input object is not null
        if(artists == null) {
            return "The artist must contain all mandatory attributes";
        }else{
            //verify that the input object does not exist in the list of artists
            if(checkArtist(artist)) {
                return "The artist already exists";
            }else{
                //If not exist, add to the list of artists
                artists.add(artist);
                return "The artist added in the event "+this.name;
            }
        }
    }
    //Delete an artist od the event
    public String removeArtist(Artist artist)
    {
        //verify that the input object is not null
        if(artists == null) {
            return "The artist to be eliminated cannot be null";
        }else{
            //verify that the input object exists in the list of artists
            if(checkArtist(artist)) {
                artists.remove(artist);
                return "The artist removed from the event "+this.name;
            }
            //If not exist send this message to the user
            return "The artist does not exist";
        }
    }
    //Check if the artist is in the list
    public boolean checkArtist(Artist artist) {
        //Return true or false if the artist is in the list of artists
        return artists.contains(artist);
    }
    //Function to reschedule an event
    public void rescheduleEvent(LocalDateTime newDate) {
        this.date = newDate;
    }
    //Change a location of the event
    public void changeLocation(Location location) {
        this.location = location;
    }
    //Change a capacity of the event
    public void changeCapacity(int capacity) {
        this.capacity = capacity;
    }
    //Changed a price of the event
    public void changePrice(double price) {
        this.price = price;
    }
    //Add ticket to the event
    public void addTicket(Ticket ticket) {
        tickets.add(ticket);
    }
    //Method to string of the class
    @Override
    public String toString() {
        return "Name event: "+name+"\nID event: "+id+"\nLocation event: "+location+"\nDate event: "+date+"\nCategory event: "+category+"\nDescription: "+description;
    }
    //We use the Builder pattern so as not to make the constructor method so long.
    public static class Builder {
        //This variable is for assigning the id automatically and autoincrement
        private static int countID = 0;
        private final double id;
        private final String name;
        private String description;
        private Location location; //The class include an object location for the location of the event
        private LocalDateTime date;
        private final EventCategory category;
        private final ArrayList<Artist> artists; //List of the artist in the event
        private final ArrayList<Ticket> tickets;
        private int capacity;
        private double price;
        public Builder (String name, EventCategory category){
            this.name = name;
            this.category = category;
            this.id = ++countID;
            this.artists = new ArrayList<>();
            this.tickets = new ArrayList<>();
        }
        //Set description
        public Builder description(String description){
            this.description = description;
            return this;
        }
        //Set location
        public Builder location(Location location){
            this.location = location;
            return this;
        }
        //Set time
        public Builder date(LocalDateTime date){
            this.date = date;
            return this;
        }
        //Set capacity
        public Builder capacity(int capacity){
            this.capacity = capacity;
            return this;
        }
        public Builder price(double price){
            this.price = price;
            return this;
        }
        //Set the countID if previously created events are obtained
        public static void setId(int id){
            countID = id;
        }
        // Create Event with builder
        public Event build(){
            return new Event(this);
        }
    }
}
