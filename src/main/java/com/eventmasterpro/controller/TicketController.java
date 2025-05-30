package com.eventmasterpro.controller;

import com.eventmasterpro.model.Event;
import com.eventmasterpro.model.Ticket;
import com.eventmasterpro.model.enums.TicketCategory;

import java.util.ArrayList;

public class TicketController {
    //A singleton is made to handle only one instance of the class in the whole execution of the program
    private static TicketController ticketController;
    //Centralized list of tickets for persistence and global access
    private ArrayList<Ticket> tickets;
    //Private constructor
    private TicketController() {
        tickets = new ArrayList<>();
    }
    //Get instance
    public static TicketController getTicketController() {
        if (ticketController == null) {
            ticketController = new TicketController();
        }
        return ticketController;
    }
    //Set tickets from external source (used by DataController)
    public void setTickets(ArrayList<Ticket> tickets) {
        this.tickets = tickets;
    }
    //Get all tickets
    public ArrayList<Ticket> getTickets() {
        return tickets;
    }
    //Create new ticket
    public Ticket createTicket(Event event, TicketCategory type, double price) {
        if (event == null || type == null || price <= 0) {
            return null;
        } else {
            Ticket ticket = new Ticket(event, type, price);
            tickets.add(ticket); // Add to centralized list
            event.addTicket(ticket); // Also link it to the event
            return ticket;
        }
    }
    //Sell a ticket for the event
    public String soldTicket(Event event, Ticket ticket) {
        if (event == null || ticket == null) {
            return "Some fields are null when they should not be";
        } else {
            if (!event.getTickets().contains(ticket)) {
                return "The ticket is not part of the selected event";
            } else {
                if (ticket.isSold()) {
                    return "Ticket is already sold";
                } else {
                    ticket.soldTicket();
                    return "Ticket successfully sold ";
                }
            }
        }
    }
    //Print an array of tickets
    public void printTickets(ArrayList<Ticket> tickets) {
        if (tickets.isEmpty()) {
            System.out.println("Tickets list is empty or null");
        } else {
            int count = 1;
            for (Ticket ticket : tickets) {
                System.out.println("Number " + count + " ID: " + ticket.getId() + " Name concert: " + ticket.getEvent().getName());
                count ++;
                System.out.println("---------------next ticket--------------");
            }
        }
    }
}
