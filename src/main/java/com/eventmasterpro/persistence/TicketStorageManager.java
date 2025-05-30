package com.eventmasterpro.persistence;

import java.io.*;
import java.util.*;
import com.eventmasterpro.model.Event;
import com.eventmasterpro.model.Ticket;
import com.eventmasterpro.model.enums.TicketCategory;

public class TicketStorageManager {
    //Save a ticket list
    public static void saveTicketList(List<Ticket> tickets, String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Ticket ticket : tickets) {
                writer.write(ticket.toFileString());
                writer.newLine();
            }
        }
    }
    //Load a ticket list
    public static List<Ticket> loadTicketList(String filePath, Map<String, Event> eventsByName) throws IOException {
        List<Ticket> tickets = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                String eventName = parts[1];
                Event event = eventsByName.get(eventName);
                if (event != null) {
                    Ticket ticket = parseTicketFromLine(line, event);
                    tickets.add(ticket);
                } else {
                    System.err.println("Event not found for ticket line: " + line);
                }
            }
        }

        return tickets;
    }
    //Parses a single line from the file into a Ticket object
    private static Ticket parseTicketFromLine(String line, Event event) {
        String[] parts = line.split(";");
        TicketCategory type = TicketCategory.valueOf(parts[2]);
        double price = Double.parseDouble(parts[3]);
        boolean sold = Boolean.parseBoolean(parts[4]);
        Ticket ticket = new Ticket(event, type, price);
        if (sold) {
            ticket.soldTicket();
        }
        return ticket;
    }
}