package com.eventmasterpro.persistence;

import com.eventmasterpro.model.Event;
import com.eventmasterpro.model.Finance;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FinanceStorageManager {
     //Saves a list of Finance objects to a .txt file
    public static void saveFinanceList(List<Finance> finances, String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Finance finance : finances) {
                writer.write(finance.toFileString());
                writer.newLine();
            }
        }
    }
    //Loads a list of Finance objects from a .txt file
    public static List<Finance> loadFinanceList(String filePath, Map<String, Event> eventsByName) throws IOException {
        List<Finance> finances = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                String eventName = parts[0];

                Event event = eventsByName.get(eventName);
                if (event != null) {
                    Finance finance = Finance.fromFileString(line, event);
                    finances.add(finance);
                } else {
                    System.err.println("Event not found for finance line: " + line);
                }
            }
        }

        return finances;
    }
}
