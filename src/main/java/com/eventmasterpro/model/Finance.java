package com.eventmasterpro.model;
import java.util.Collections;

import java.util.ArrayList;

public class Finance {
    //Class attributes
    private final Event event;
    private double budget;
    private double income;
    private double expense;
    private final ArrayList<String> expensesDetails;
    private final ArrayList<String> incomeDetails;
    //Getters methods
    public Event getEvent() {
        return event;
    }
    public double getBudget(){
        return budget;
    }
    //constructor
    public Finance(Event event, double budget) {
        this.event = event;
        this.budget = budget;
        this.income = 0;
        this.expense = 0;
        this.expensesDetails = new ArrayList<>();
        this.incomeDetails = new ArrayList<>();
    }
    //Methods of the class
    //Add new income
    public void addIncome(double amount, String details) {
        this.income += amount;
        this.incomeDetails.add(details);
    }
    //Expense record
    public void addExpense(double amount, String details) {
        this.expense += amount;
        this.expensesDetails.add(details);
    }
    //Obtain balance, income-expense ratio
    public double getBalance(){
        return budget - expense;
    }
    public void addBudget(double amount){
        this.budget += amount;
    }
    //Location to string to save it
    public String toFileString() {
        StringBuilder sb = new StringBuilder();
        sb.append(event.getName()).append(";")
                .append(budget).append(";")
                .append(income).append(";")
                .append(expense).append(";");
        for (String detail : expensesDetails) {
            sb.append(detail).append("|");
        }
        sb.append(";");
        for (String detail : incomeDetails) {
            sb.append(detail).append("|");
        }
        return sb.toString();
    }
    //String to location to load it
    public static Finance fromFileString(String line, Event event) {
        String[] parts = line.split(";");
        double budget = Double.parseDouble(parts[1]);
        double income = Double.parseDouble(parts[2]);
        double expense = Double.parseDouble(parts[3]);

        Finance finance = new Finance(event, budget);

        // Set income and expense directly (already updated)
        finance.income = income;
        finance.expense = expense;
        if (!parts[4].isEmpty()) {
            String[] expenseItems = parts[4].split("\\|");
            Collections.addAll(finance.expensesDetails, expenseItems);
        }
        if (parts.length > 5 && !parts[5].isEmpty()) {
            String[] incomeItems = parts[5].split("\\|");
            Collections.addAll(finance.incomeDetails, incomeItems);
        }
        return finance;
    }
    //Method to string of the class
    @Override
    public String toString(){
        return "Event name: "+event.getName()+"\nBudget: "+budget+"\nIncome: "+income+"\nBalance: "+getBalance();
    }
    
}
