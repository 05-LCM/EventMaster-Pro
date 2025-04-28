package com.eventmasterpro.model;

import java.util.ArrayList;

public class Finance {
    //Class attributes
    private final Event event;
    private double budget;
    private double income;
    private double expense;
    private ArrayList<String> expensesDetails;
    private ArrayList<String> incomeDetails;
    //Getters methods
    public Event getEvent() {
        return event;
    }
    public double getBudget(){
        return budget;
    }
    public double getIncome(){
        return income;
    }
    public double getExpenses(){
        return expense;
    }
    public ArrayList<String> getExpenseDetails(){
        return expensesDetails;
    }
    public ArrayList<String> getIncomeDetails(){
        return incomeDetails;
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
    //Method to string of the class
    @Override
    public String toString(){
        return "Event name: "+event.getName()+"\nBudget: "+budget+"\nIncome: "+income+"\nBalance: "+getBalance();
    }
    
}
