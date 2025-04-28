package com.eventmasterpro.model;

import com.eventmasterpro.model.enums.TicketCategory;

public class Ticket {
    //Attributes of the class
    private static double id = 0;
    private final Event event;
    private TicketCategory type;
    private double price;
    private boolean sold;
    //Constructor
    public Ticket(Event event, TicketCategory type, double price) {
        this.id = ++id;
        this.event = event;
        this.type = type;
        this.price = price;
        this.sold = false;
    }
    //Getters and setters
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public double getId() {
        return id;
    }
    public Event getEvent() {
        return event;
    }
    public TicketCategory getType() {
        return type;
    }
    //Methods of the class
    //Sell ticket
    public String soldTicket() {
        if(sold) {
            return "The ticket has already been sold";
        }else{
            sold = true;
            return "Ticket successfully purchased";
        }
    }
    //The ticket is sold?
    public boolean isSold() {
        return sold;
    }
    //To string method
    @Override
    public String toString() {
        return "Ticket ID: "+id+"\nEvent: "+event.toString()+"\nPrice: "+price+"\nType: "+type.toString()+"\nSold: "+sold;
    }
}
