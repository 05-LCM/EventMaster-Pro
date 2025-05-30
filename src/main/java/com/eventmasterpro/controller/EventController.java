package com.eventmasterpro.controller;

import com.eventmasterpro.model.Event;
import com.eventmasterpro.model.Location;
import com.eventmasterpro.model.Ticket;
import com.eventmasterpro.model.enums.EventCategory;
import com.eventmasterpro.model.enums.TicketCategory;
import com.eventmasterpro.utils.InputValidator;

import java.time.LocalDate;
import java.util.ArrayList;

public class EventController {
    //Import data validator
    private final InputValidator validator = new InputValidator();
    //Import ticket controller
    private final TicketController ticketController = TicketController.getTicketController();
    //List of the all events
    private ArrayList<Event> events;
    //A singleton is made to handle only one instance of the class in the whole execution of the program
    private static EventController eventController;
    //Get the instance
    public static EventController getEventController() {
        if (eventController == null) {
            eventController = new EventController();
        }
        return eventController;
    }
    //Private constructor
    private EventController() {
        events = new ArrayList<>();
    }
    //Set events from external source (used by DataController)
    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }
    //Create new event
    public Event createEevent(String name, String description, Location location,
                              LocalDate date, EventCategory category, int capacity, double price) {
        if (validator.checkEmpty(name) || category == null || capacity <= 0) {
            return null;
        } else {
            //Create a new event with builder
            Event event = new Event.Builder(name, category)
                    .price(price)
                    .description(description)
                    .capacity(capacity)
                    .location(location)
                    .date(date)
                    .build();
            addEvent(event);
            return event;
        }
    }
    //Add event to events list
    private void addEvent(Event event) {
        events.add(event);
    }
    //Get all events
    public ArrayList<Event> getEvents() {
        return events;
    }
    //Assign date of the event
    public String assignDateEvent(Event event, LocalDate date) {
        if (event == null || date == null) {
            return "Event or date is null";
        } else {
            event.setDate(date);
            return "Event date successfully assigned";
        }
    }
    //Assign location of the event
    public void assignLocationEvent(Event event, Location location) {
        event.setLocation(location);
    }
    //Create tickets
    public String createTicketsEvent(int numTickets, Event event, TicketCategory category, double price) {
        if (event == null || category == null) {
            return "Event or category is null";
        } else if (numTickets <= 0) {
            return "Number of tickets must be greater than 0";
        } else if (event.getTickets().size() + numTickets > event.getCapacity()) {
            return "Number of tickets exceeds capacity";
        } else {
            for (int i = 0; i < numTickets; i++) {
                event.addTicket(ticketController.createTicket(event, category, price));
            }
            return "Tickets successfully created";
        }
    }
    //Get maximum number of tickets available to create
    public String getNumAvailableTicketsToCreate(Event event) {
        if (event == null) {
            return "Event is null";
        } else {
            int maxCapacity = event.getCapacity() - event.getTickets().size();
            if(maxCapacity <0){
                maxCapacity =0;
            }
            return "A maximum of the following can be created: " + maxCapacity + " tickets";
        }
    }
    //Get all tickets available
    public ArrayList<Ticket> getTicketsAvailable(Event event) {
        ArrayList<Ticket> ticketsAvailable = new ArrayList<>();
        for (Ticket ticket : event.getTickets()) {
            if (!ticket.isSold()) {
                ticketsAvailable.add(ticket);
            }
        }
        return ticketsAvailable;
    }
    //Get all tickets sold
    public ArrayList<Ticket> getTicketsSold(Event event) {
        ArrayList<Ticket> ticketsSold = new ArrayList<>();
        for (Ticket ticket : event.getTickets()) {
            if (ticket.isSold()) {
                ticketsSold.add(ticket);
            }
        }
        return ticketsSold;
    }
    //Sold a ticket
    public String soldTicket(Event event, Ticket ticket) {
        if (event == null || ticket == null) {
            return "Event or ticket is null";
        } else {
            return ticketController.soldTicket(event, ticket);
        }
    }
    //Get all events of one category
    public ArrayList<Event> getEventsByCategory(EventCategory category) {
        ArrayList<Event> eventsByCategory = new ArrayList<>();
        for (Event event : events) {
            if (event.getCategory().equals(category)) {
                eventsByCategory.add(event);
            }
        }
        return eventsByCategory;
    }
    //Delete event by id
    public String deleteEventById(int id) {
        if (id < 0) {
            return "Event id is not valid";
        } else {
            events.remove(id - 1);
            return "Event successfully deleted";
        }
    }
    //Select an event by id
    public Event getEventById(int id) {
        return events.get(id - 1);
    }
    //Edit event information
    public String editEvent(Event event, String description, LocalDate date, int capacity, double price) {
        if (event == null) {
            return "Event is null";
        } else {
            event.setDescription(description);
            event.setDate(date);
            event.setCapacity(capacity);
            event.setPrice(price);
            return "Event successfully updated";
        }
    }
    //Print events
    public void printEvents(ArrayList<Event> events) {
        for (Event event : events) {
            System.out.println(event.toString());
            System.out.println("-----------------------------------------------------------------------------------");
        }
    }
}
