package com.eventmasterpro.controller;

import com.eventmasterpro.model.Event;
import com.eventmasterpro.model.Finance;
import com.eventmasterpro.utils.InputValidator;

import java.util.ArrayList;

public class FinanceController {
    //Import data validator
    private InputValidator validator = new InputValidator();
    private ArrayList<Finance> finances;
    //A singleton is made to handle only one instance of the class in the whole execution of the program
    private static FinanceController financeController;
    private FinanceController(){
        finances = new ArrayList<>();
    }
    public static FinanceController getFinanceController() {
        if(financeController==null){
            financeController = new FinanceController();
        }
        return financeController;
    }
    //Create new finance object
    public Finance createFinance(Event event, double budget) {
        if(event==null){
            return null;
        }else{
            Finance finance = new Finance(event, budget);
            finances.add(finance);
            return finance;
        }
    }
    //Add income for an event
    public String addIncome(Finance finance, double amount, String details) {
        if(finance==null || validator.checkEmpty(details)) {
            return "All requested fields must be filled in";
        }else{
            if(amount <= 0) {
                return "the amount must be greater than 0";
            }else{
                finance.addIncome(amount, details);
                return "Amount added to the finance";
            }
        }
    }
    //Add expenses
    public String addExpense(Finance finance, double amount, String details) {
        if(finance==null || validator.checkEmpty(details)) {
            return "All requested fields must be filled in";
        }else{
            if(amount <= 0) {
                return "the amount must be greater than 0";
            }else{
                finance.addExpense(amount, details);
                return "Amount added to the finance";
            }
        }
    }
    //Get event balance
    public double getBalance(Finance finance) {
        if(finance==null) {
            return 0.0;
        }
        return finance.getBalance();
    }
    //Add budget
    public String addBudget(Finance finance, double amount) {
        if(finance==null) {
            return "All requested fields must be filled in";
        }else{
            if(amount <= 0) {
                return "the amount must be greater than 0";
            }else{
                finance.addBudget(amount);
                return "Amount added to the budget event";
            }
        }
    }
    //Show finance of the list
    public void showFinances(ArrayList<Finance> finances) {
        if(finances.isEmpty()) {
            System.out.println("No finances found");
        }else{
            System.out.println("---------Finance List---------");
            for (Finance finance : finances) {
                System.out.println(finance);
                System.out.println("---------------------------------------------");
            }
        }
    }
    //Get all reports
    public ArrayList<Finance> getFinances() {
        return finances;
    }
    //Search finance by id
    public Finance searchFinanceByID(int id) {
        return finances.get(id-1);
    }
}
