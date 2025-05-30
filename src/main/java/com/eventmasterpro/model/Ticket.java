package com.eventmasterpro.model;

import com.eventmasterpro.model.enums.TicketCategory;

public class Ticket {
    //Attributes of the class
    private final int id;
    private static int countID = 0;
    private final Event event;
    private final TicketCategory type;
    private final double price;
    private boolean sold;
    //Constructor
    public Ticket(Event event, TicketCategory type, double price) {
        this.id = ++countID;
        this.event = event;
        this.type = type;
        this.price = price;
        this.sold = false;
    }
    public double getId() {
        return id;
    }
    public Event getEvent() {
        return event;
    }
    //Methods of the class
    //Sell ticket
    public void soldTicket() {
        if(sold) {
        }else{
            sold = true;
        }
    }
    //The ticket is sold?
    public boolean isSold() {
        return sold;
    }
    //Convert this object in string for save
    public String toFileString() {
        return id + ";" + event.getName() + ";" + type.name() + ";" + price + ";" + sold;
    }
    //To string method
    @Override
    public String toString() {
        return "Ticket ID: "+id+"\nEvent: "+event.toString()+"\nPrice: "+price+"\nType: "+type.toString()+"\nSold: "+sold;
    }
}
