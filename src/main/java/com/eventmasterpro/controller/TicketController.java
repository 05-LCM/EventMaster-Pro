package com.eventmasterpro.controller;

import com.eventmasterpro.model.Event;
import com.eventmasterpro.model.Ticket;
import com.eventmasterpro.model.enums.TicketCategory;

public class TicketController {
    //A singleton is made to handle only one instance of the class in the whole execution of the program
    private TicketController ticketController;
    public TicketController getTicketController() {
        if(ticketController==null){
            ticketController = new TicketController();
        }
        return ticketController;
    }
    //Create new ticket
    public Ticket createTicket(Event event, TicketCategory type, double price) {
        if(event == null||type == null||price <= 0){
            return null;
        }else{
            return new Ticket(event, type, price);
        }
    }
    //Sell a ticket for the event
    public String soldTicket(Event event, Ticket ticket) {
        if(event == null||ticket == null){
            return "Some fields are null when they should not be";
        }else {
            if(!event.getTickets().contains(ticket)){
                return "The ticket is not part of the selected event";
            }else{
                if(ticket.isSold()){
                    return "Ticket is already sold";
                }else{
                    ticket.soldTicket();
                    return "Ticket successfully sold ";
                }
            }
        }
    }
    //Change ticket price
    public Ticket updateTicketPrice(Ticket ticket, double newPrice) {
        if(ticket == null){
            return null;
        }else{
            ticket.setPrice(newPrice);
            return ticket;
        }
    }
}
