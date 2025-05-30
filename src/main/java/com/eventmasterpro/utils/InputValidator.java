package com.eventmasterpro.utils;

public class InputValidator {
    //Constructor to handle the class as an object
    public InputValidator() {

    }
    //Check if field is empty
    public boolean checkEmpty(String text){
        return text.isBlank();
    }
}
