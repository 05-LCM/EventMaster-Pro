package com.eventmasterpro.controller;

import com.eventmasterpro.model.Event;
import com.eventmasterpro.model.Location;
import com.eventmasterpro.model.Ticket;
import com.eventmasterpro.model.enums.EventCategory;
import com.eventmasterpro.model.enums.TicketCategory;
import com.eventmasterpro.utils.InputValidator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class EventController {
    //Import data validator
    private final InputValidator validator = new InputValidator();
    //Import ticket controller
    private final TicketController  ticketController = new TicketController();
    //List of the all events
    private static ArrayList<Event> events;
    //A singleton is made to handle only one instance of the class in the whole execution of the program
    private static EventController eventController;
    //Get the instance
    public static EventController  getEventController(){
        if(eventController==null){
            eventController = new EventController();
            events = new ArrayList<>();
        }
        return eventController;
    }
    //Create new event
    public Event createEevent(String name, String description, Location location,
                              LocalDateTime date, EventCategory category, int capacity, double price) {
        if(validator.checkEmpty(name) || category == null || capacity <= 0){
            return null;
        }else{
            //Create a new event with builder
            Event event = new Event.Builder(name,category)
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
    //Get alls events
    public ArrayList<Event> getEvents(){
        return events;
    }
    //Modify event
    //Delete event
    public String deleteEvent(Event event){
        if(event==null){
            return "Event is null";
        }else{
            if(!events.contains(event)){
                return "Event does not exist";
            }else{
                events.remove(event);
                return "Event successfully deleted";
            }
        }
    }
    //Assign date of the event
    public String assignDateEvent(Event event, LocalDateTime date){
        if(event==null||date==null){
            return "Event or date is null";
        }else{
            event.setDate(date);
            return "Event date successfully assigned";
        }
    }
    //Assign location of the event
    public String assignLocationEvent(Event event, Location location){
        if(event==null||location==null){
            return "Event or location is null";
        }else{
            event.setLocation(location);
            return "Event location successfully assigned";
        }
    }
    //Categorize event
    public String assignCategoryEvent(Event event, EventCategory category){
        if(event==null||category==null){
            return "Event or category is null";
        }else{
            event.setCategory(category);
            return "Event category successfully assigned";
        }
    }
    //Create tickets
    public String createTicketsEvent(int numTickets, Event event, TicketCategory category, double price){
        if(event==null||category==null){
            return "Event or category is null";
        }else{
            if(numTickets <= 0){
                return "Number of tickets must be greater than 0";
            }else{
                if(event.getTickets().size()+numTickets>event.getCapacity()){
                    return "Number of tickets exceeds capacity";
                }else{
                    for(int i =0;i<numTickets;i++){
                        ticketController.createTicket(event,category,price);
                    }
                    return "Tickets successfully created";
                }
            }
        }
    }
    //Get maximum number of tickets available to create
    public String getNumAvailableTicketsToCreate(Event event){
        if(event==null){
            return "Event is null";
        }else{
            return "A maximum of the following can be created: "+(event.getCapacity()-event.getTickets().size())+" tickets";
        }
    }
    //Get all tickets available
    public ArrayList<Ticket> getTicketsAvailable(Event event){
        ArrayList<Ticket> ticketsAvailable = new ArrayList<>();
        for(Ticket ticket: event.getTickets()){
            if(!ticket.isSold()){
                ticketsAvailable.add(ticket);
            }
        }
        return ticketsAvailable;
    }
    //Get all tickets sold
    public ArrayList<Ticket> getTicketsSold(Event event){
        ArrayList<Ticket> ticketsSold = new ArrayList<>();
        for(Ticket ticket: event.getTickets()){
            if(ticket.isSold()){
                ticketsSold.add(ticket);
            }
        }
        return ticketsSold;
    }
    //Sold a ticket
    public String soldTicket(Event event, Ticket ticket){
        if(event==null||ticket==null){
            return "Event or ticket is null";
        }else{
            return ticketController.soldTicket(event, ticket);
        }
    }
    //Get all events of the one category
    public ArrayList<Event> getEventsByCategory(EventCategory category){
        ArrayList<Event> eventsByCategory = new ArrayList<>();
        for(Event event: events){
            if(event.getCategory().equals(category)){
                eventsByCategory.add(event);
            }
        }
        return eventsByCategory;
    }
    //Delete event by id
    public  String deleteEventById(int id){
        if(id<0){
            return "Event id is not valid";
        }else{
            events.remove(id-1);
            return "Event successfully deleted";
        }
    }
    //Select an event by id
    public Event getEventById(int id){
        return events.get(id-1);
    }
    //Edit event information
    public String editEvent(Event event, String description, LocalDateTime date, int capacity, double price){
        if(event==null){
            return "Event is null";
        }else{
            event.setDescription(description);
            event.setDate(date);
            event.setCapacity(capacity);
            event.setPrice(price);
            return "Event successfully updated";
        }
    }
    //Print events
    public void printEvents(ArrayList<Event> events){
        for(Event event: events){
            System.out.println(event.toString());
            System.out.println("-----------------------------------------------------------------------------------");
        }
    }
}
