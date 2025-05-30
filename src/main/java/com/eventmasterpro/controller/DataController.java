package com.eventmasterpro.controller;
import com.eventmasterpro.model.*;
import com.eventmasterpro.persistence.*;
import java.io.IOException;
import java.util.*;

public class DataController {
    //Load data when the program starts
    public void loadAllData() {
        try {
            // Load Locations
            List<Location> locations = LocationStorageManager.loadLocationList("data/locations.txt");
            HashMap<String, Location> locationsByName = new HashMap<>();
            for (Location loc : locations) {
                locationsByName.put(loc.getName(), loc);
            }
            LocationController.getLocationController().setLocations(new ArrayList<>(locations));
            // Load Artists
            List<Artist> artists = ArtistStorageManager.loadArtistList("data/artists.txt");
            Map<String, Artist> artistsByName = new HashMap<>();
            for (Artist artist : artists) {
                artistsByName.put(artist.getName(), artist);
            }
            ArtistController.getArtistController().setArtists(new ArrayList<>(artists));
            // Load Events (category mapping no longer needed)
            List<Event> events = EventStorageManager.loadEventList(
                    "data/events.txt",
                    locationsByName,
                    artists
            );
            Map<String, Event> eventsByName = new HashMap<>();
            for (Event e : events) {
                eventsByName.put(e.getName(), e);
            }
            EventController.getEventController().setEvents(new ArrayList<>(events));
            // Load Tickets
            List<Ticket> tickets = TicketStorageManager.loadTicketList("data/tickets.txt", eventsByName);
            for (Ticket t : tickets) {
                t.getEvent().addTicket(t);
            }
            TicketController.getTicketController().setTickets(new ArrayList<>(tickets));
            // Load Finances
            List<Finance> finances = FinanceStorageManager.loadFinanceList("data/finances.txt", eventsByName);
            FinanceController.getFinanceController().setFinances(new ArrayList<>(finances));
            System.out.println("All data successfully loaded.");
        } catch (IOException e) {
            System.err.println("Error loading data: " + e.getMessage());
        }
    }
    //Save all data from objects arrayLists
    public void saveAllData() {
        try {
            // Save Locations
            ArrayList<Location> locations = LocationController.getLocationController().getAllLocations();
            LocationStorageManager.saveLocationList(locations, "data/locations.txt");
            // Save Artists
            ArrayList<Artist> artists = ArtistController.getArtistController().getArtists();
            ArtistStorageManager.saveArtistList(artists, "data/artists.txt");
            // Save Events
            ArrayList<Event> events = EventController.getEventController().getEvents();
            EventStorageManager.saveEventList(events, "data/events.txt");
            // Save Tickets
            ArrayList<Ticket> tickets = TicketController.getTicketController().getTickets();
            TicketStorageManager.saveTicketList(tickets, "data/tickets.txt");
            // Save Finances
            ArrayList<Finance> finances = FinanceController.getFinanceController().getFinances();
            FinanceStorageManager.saveFinanceList(finances, "data/finances.txt");
            System.out.println("All data successfully saved.");
        } catch (IOException e) {
            System.err.println("Error saving data: " + e.getMessage());
        }
    }
}
