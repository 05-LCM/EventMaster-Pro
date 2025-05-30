package com.eventmasterpro.model;

import com.eventmasterpro.model.enums.EventCategory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Event {
    //class attributes
    private final double id;
    private final String name;
    private String description;
    private Location location; //The class include an object location for the location of the event
    private LocalDate date;
    private final EventCategory category;
    private final ArrayList<Artist> artists; //List of the artist in the event
    private final ArrayList<Ticket> tickets; //Tickets of the event
    private int capacity;
    private double price;
    //Getters and setters methods
    public String getName() {
        return name;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setLocation(Location location) {
        this.location = location;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
    public EventCategory getCategory() {
        return category;
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
    public void setPrice(double price) {
        this.price = price;
    }
    //Methods of the class
    //To save object in to txt file
    public String toFileString() {
        StringBuilder sb = new StringBuilder();
        sb.append(id).append(";").append(name != null ? name : "Unnamed event").append(";")
                .append(description != null ? description : "No description").append(";").append(location != null ? location.getName() : "Location not assigned yet").append(";").append(date != null ? date.toString() : "Date not assigned yet").append(";")
                .append(category != null ? category.name() : "Category not assigned yet").append(";").append(capacity).append(";").append(price).append(";");
        // Artist names
        if (artists != null && !artists.isEmpty()) {
            for (Artist a : artists) {
                sb.append(a.getName()).append("|");
            }
        }
        sb.append(";");
        // Ticket IDs
        if (tickets != null && !tickets.isEmpty()) {
            for (Ticket t : tickets) {
                sb.append(t.getId()).append("|");
            }
        }
        return sb.toString();
    }
    //File to events object
    public static Event fromFileString(String line, Map<String, Location> locationsByName, List<Artist> allArtists) {
        try {
            String[] parts = line.split(";");
            if (parts.length < 8) {
                System.err.println("Invalid event line (too short): " + line);
                return null;
            }
            String name = parts[1];
            String description = parts[2];
            String locationName = parts[3];
            String dateStr = parts[4];
            String categoryStr = parts[5];
            int capacity = Integer.parseInt(parts[6]);
            double price = Double.parseDouble(parts[7]);
            // Get location if exist
            Location location = locationsByName.getOrDefault(locationName, null);
            // Try parse category
            EventCategory category;
            try {
                category = EventCategory.valueOf(categoryStr);
            } catch (IllegalArgumentException e) {
                category = null;
            }
            // Parse date
            LocalDate date = null;
            if (!dateStr.equals("Date not assigned yet")) {
                date = LocalDate.parse(dateStr);
            }
            Event.Builder builder = new Event.Builder(name, category)
                    .description(description)
                    .location(location)
                    .date(date)
                    .capacity(capacity)
                    .price(price);

            Event event = builder.build();
            // If artist exist, parse artist
            if (parts.length > 8 && !parts[8].isEmpty()) {
                String[] artistNames = parts[8].split("\\|");
                for (String artistName : artistNames) {
                    for (Artist a : allArtists) {
                        if (a.getName().equals(artistName)) {
                            event.addNewArtist(a);
                            break;
                        }
                    }
                }
            }
            return event;
        } catch (Exception ex) {
            System.err.println("Invalid location or category for event line: " + line);
            return null;
        }
    }
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
    public void addNewArtist(Artist artist) {
       artists.add(artist);
    }
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
        private LocalDate date;
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
        public Builder date(LocalDate date){
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
        // Create Event with builder
        public Event build(){
            return new Event(this);
        }
    }
}
