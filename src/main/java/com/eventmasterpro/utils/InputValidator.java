package com.eventmasterpro.utils;

public class InputValidator {
    //Constructor to handle the class as an object
    public InputValidator() {

    }

    //Check if field is empty
    public boolean checkEmpty(String text){
        return text.isEmpty() || text.isBlank();
    }
    //Check if the input is a number
    public boolean checkNumber(String text){
        try{
            Double.parseDouble(text);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
