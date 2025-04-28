package com.eventmasterpro;

import com.eventmasterpro.controller.ArtistController;
import com.eventmasterpro.controller.EventController;
import com.eventmasterpro.controller.FinanceController;
import com.eventmasterpro.controller.LocationController;
import com.eventmasterpro.model.Artist;
import com.eventmasterpro.model.Event;
import com.eventmasterpro.model.Finance;
import com.eventmasterpro.model.Location;
import com.eventmasterpro.model.enums.EventCategory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {
    //Actual event selected
    private static Event actualEvent;
    //Actual artist
    private static Artist actualArtist;
    //Actual location selected
    private static Location actualLocation;
    //Actual finance
    private static Finance actualFinance;
    //Finance controller
    private final static FinanceController financeController = FinanceController.getFinanceController();
    //Location controller
    private final static LocationController locationController = LocationController.getLocationController();
    //Artist controller
    private final static ArtistController artistController = ArtistController.getArtistController();
    //Event controller
    private final static EventController eventController = EventController.getEventController();
    public static void main(String[] args) {
        displayMenu(args);
    }
    private static void displayMenu(String[] args){
        System.out.println("Welcome to EventMaster Pro");
        System.out.println("Please enter the number of the action you wish to perform.");
        System.out.println("1. Manage Events");
        System.out.println("2. Manage Artist");
        System.out.println("3. Manage Locations");
        System.out.println("4. Manage Finances of the event");
        System.out.println("5. Exit");
        Scanner sc = new Scanner(System.in);
        try{
            int choice = sc.nextInt();
            showOptionsChoice(choice, args);
        }catch (Exception e){
            System.out.println("Please enter a valid option!");
            System.out.println(e.getMessage());
            //If the user selects an invalid option, the main is restarted.
            main(args);
        }
    }
    private static void showOptionsChoice(int choice, String[] args){
        switch (choice) {
            case 1:
                showEventsOptions();
                break;
            case 2:
                showArtistOptions();
                break;
            case 3:
                showLocationOptions();
                break;
            case 4:
                showFinancesOptions();
                break;
            case 5:
                exit();
                break;
            default:
                System.out.println("This number is not included in the available options");
                main(args);
                break;

        }
    }
    //If user select 1
    private static void showEventsOptions(){
        if(eventController.getEvents().isEmpty()){
            System.out.println("No events yet");
        }else{
            System.out.println("----------------Active events-----------------");
            eventController.printEvents(eventController.getEvents());
        }
        System.out.println("1. Create Event\n2. Delete Event\n3. Show event information\n4. Edit event\n5. Return to main menu");
        Scanner sc = new Scanner(System.in);
        try{
            int choice = sc.nextInt();
            if(choice <0 || choice >5){
                System.out.println("This number is not included in the available options");
                showEventsOptions();
            }else{
                switch (choice) {
                    case 1:
                        creatEvent();
                        break;
                    case 2:
                        deleteEvent();
                        break;
                    case 3:
                        showEventInfo();
                        break;
                    case 4:
                        editEvent();
                        break;
                    case 5:
                        displayMenu(null);
                        break;
                    default:
                        System.out.println("This number is not included in the available options");
                        showEventsOptions();
                        break;
                }
            }

        }catch (Exception e){
            System.out.println("Please enter a valid option!");
            showEventsOptions();
        }
    }
    //Edit event information
    private static void editEvent(){
        System.out.println("Please enter the ID of the event to edit information");
        try{
            Scanner sc = new Scanner(System.in);
            int id = sc.nextInt();
            Event event = eventController.getEventById(id);
            if(event == null){
                System.out.println("This event does not exist");
                showEventsOptions();
            }else{
                try {
                    System.out.println("enter the new event description: ");
                    sc.nextLine();
                    String description = sc.nextLine();
                    System.out.println("enter the new event date: ");
                    LocalDateTime date = LocalDateTime.parse(sc.nextLine());
                    System.out.println("enter the new event capacity: ");
                    int capacity = sc.nextInt();
                    System.out.println("enter the new event price: ");
                    double price = sc.nextDouble();
                    System.out.println(eventController.editEvent(event,description,date,capacity,price));
                    showEventsOptions();
                }catch (Exception e){
                    System.out.println("Please enter a valid value!");
                    showEventsOptions();
                }

            }
        }catch (Exception e){
            System.out.println("Please enter a valid ID");
            showEventsOptions();
        }
    }
    //Show event information
    private static void showEventInfo(){
        Scanner sc = new Scanner(System.in);
        try{
            System.out.println("Please enter the ID of the event to show information: ");
            int id = sc.nextInt();
            Event event = eventController.getEventById(id);
            if(event == null){
                System.out.println("There is no event with the selected id.");
                showEventInfo();
            }else{
                System.out.println(event);
                System.out.println("Press 1 and enter to continue");
                try{
                    if(sc.nextInt() == 1){
                        showEventsOptions();
                    }
                }catch (Exception e){
                    System.out.println("Back to menu");
                    showEventsOptions();
                }
                showEventsOptions();
            }
        }catch (Exception e){
            System.out.println("Please enter a valid ID");
            showEventInfo();
        }
    }
    //Create a new event catch data
    private static void creatEvent(){
        try{
            Scanner sc = new Scanner(System.in);
            System.out.println("Please enter the name of the event");
            String name = sc.nextLine();
            System.out.println("Please enter the date of the event, with the following format: YYYY-MM-DDTHH:MM");
            LocalDateTime date = LocalDateTime.parse(sc.nextLine());
            System.out.println("Please enter the description of the event");
            String description = sc.nextLine();
            EventCategory category = selectEventCategory();
            System.out.println("Please enter the capacity of the event: ");
            int capacity;
            capacity = sc.nextInt();
            System.out.println("Please enter the price of the event: ");
            double price = sc.nextDouble();
            Event event = eventController.createEevent(name,description,null,date,category,capacity,price);
            System.out.println("Event created successfully\n"+event.toString());
            //System.out.println("Event created\nEvent information: \n"+event.toString());
            showEventsOptions();
        }catch(Exception e){
            System.out.println("Please enter a valid value!");
            creatEvent();
        }
    }
    private static EventCategory selectEventCategory(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the event category number according to the following options: ");
        System.out.println("1. Concert\n2. Festival\n3. Conference\n4. Workshop\n5. Other");
        try {
            int choice = sc.nextInt();
            if(choice <1 || choice >5){
                System.out.println("This number is not included in the available options");
                return  selectEventCategory();
            }else{
                return switch (choice) {
                    case 1 -> EventCategory.CONCERT;
                    case 2 -> EventCategory.FESTIVAL;
                    case 3 -> EventCategory.CONFERENCE;
                    case 4 -> EventCategory.WORKSHOP;
                    case 5 -> EventCategory.OTHER;
                    default -> {
                        System.out.println("This number is not included in the available options");
                        yield selectEventCategory();
                    }
                };
            }
        }catch (Exception e){
            System.out.println("Please enter a valid option!");
            return selectEventCategory();
        }
    }
    //Delete event
    private static void deleteEvent(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the ID of the event to Delete");
        try{
            int id = sc.nextInt();
            System.out.println(eventController.deleteEventById(id));
            showEventsOptions();
        }catch (Exception e){
            System.out.println("Please enter a valid ID");
            System.out.println("-------------back to events menu----------");
            showEventsOptions();
        }
    }
    //If user select 2
    private static void showArtistOptions(){
        artistController.showArtists(artistController.getArtists());
        if(actualArtist == null){
            System.out.println("No artist selected yet");
        }else{
            System.out.println("Actual artist selected: "+actualArtist);
        }
        Scanner sc = new Scanner(System.in);
        System.out.println("Please select an option: ");
        System.out.println("""
                1. Create artist
                2. Select artis
                3. Set artis in event\
                
                4. Set past events
                5. Add technical requirement
                6. Delete technical requirement\
                
                7. Show all technical requirements
                8. Change contact information
                9. Return to main menu""");
        try{
            int choice = sc.nextInt();
            if(choice <1 || choice >9){
                System.out.println("This number is not included in the available options");
                showArtistOptions();
            }else{
                switch (choice) {
                    case 1:
                        createArtis();
                        break;
                    case 2:
                        selectArtist();
                        break;
                    case 3:
                        putArtistInEvent();
                        break;
                    case 4:
                        setArtistInPastEvent();
                        break;
                    case 5:
                        addTechnicalRequirement();
                        break;
                    case 6:
                        deleteTechnicalRequirement();
                        break;
                    case 7:
                        showTechnicalRequirements();
                        break;
                    case 8:
                        changeArtistInfo();
                        break;
                    case 9:
                        displayMenu(null);
                        break;
                    default:
                        System.out.println("This is a not valid option");
                        showArtistOptions();
                        break;
                }
            }
        }catch (Exception e){
            System.out.println("Please enter a valid option!");
            showArtistOptions();
        }
    }
    //Create artis
    private static void createArtis(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the artist's name: ");
        String name = sc.nextLine();
        System.out.println("Enter the artist's contact information: ");
        String contactInfo = sc.nextLine();
        Artist artist = artistController.createArtists(name,contactInfo);
        artistController.addArtist(artist);
        System.out.println("Artis created");
        System.out.println(artist.toString());
        showArtistOptions();
    }
    //Select artist
    private static void selectArtist(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the artist id of the artist to select: ");
        try{
            int id = sc.nextInt();
            actualArtist = artistController.getArtistById(id);
            showArtistOptions();
        }catch (Exception e){
            System.out.println("Please enter a valid ID");
            System.out.println("-------------back to artist menu----------");
            showArtistOptions();
        }
    }
    //Set artist in event
    private static void putArtistInEvent(){
        Scanner sc = new Scanner(System.in);
        if(actualArtist == null){
            System.out.println("No artist selected yet");
            showArtistOptions();
        }else{
            if(eventController.getEvents().isEmpty()){
                System.out.println("No events added yet");
                showArtistOptions();
            }
            try{
                eventController.printEvents(eventController.getEvents());
                System.out.println("Please enter the ID of the event in which the artist is going to participate.  ");
                int id = sc.nextInt();
                actualEvent = eventController.getEventById(id);
                if(actualEvent == null){
                    System.out.println("No event with the requested id was found");
                    showArtistOptions();
                }else{
                    actualEvent.addNewArtist(actualArtist);
                    System.out.println("Artist added to the event");
                    showArtistOptions();
                }
            }catch (Exception e){
                System.out.println("Please enter a valid ID for the event");
                System.out.println("-------------back to artist menu----------");
                showArtistOptions();
            }
        }
    }
    //Set artis in past event
    private static void setArtistInPastEvent(){
        if(actualArtist == null){
            System.out.println("No artist selected yet");
            showArtistOptions();
        }else{
            Scanner sc = new Scanner(System.in);
            System.out.println("Please enter the ID of the event in which the artist has participated. ");
            try{
                int id = sc.nextInt();
                actualEvent = eventController.getEventById(id);
                if(actualEvent == null){
                    System.out.println("No event with the requested id was found");
                    showArtistOptions();
                }else{
                    artistController.setPastEvents(actualArtist,actualEvent);
                    System.out.println("Past event added to the artist");
                    showArtistOptions();
                }
            }catch (Exception e){
                System.out.println("Please enter a valid ID");
                System.out.println("-------------back to artist menu----------");
                showArtistOptions();
            }
        }
    }
    //Add technical requirement
    private static void addTechnicalRequirement(){
        if(actualArtist == null){
            System.out.println("No artist selected yet");
            showArtistOptions();
        }else{
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter the artist's technical requirements");
            String technicalRequirements = sc.nextLine();
            artistController.addTechnicalRequirement(technicalRequirements,actualArtist);
            System.out.println("Technical requirements added to the artist");
            showArtistOptions();
        }
    }
    //Delete technical requirement
    private static void deleteTechnicalRequirement(){
        if(actualArtist == null){
            System.out.println("No artist selected yet");
            showArtistOptions();
        }else{
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter the artist's technical requirements to delete");
            String technicalRequirements = sc.nextLine();
            System.out.println(artistController.deleteTechnicalRequirement(technicalRequirements,actualArtist));
            showArtistOptions();
        }
    }
    //Show technical requirements
    private static void showTechnicalRequirements(){
        if(actualArtist == null){
            System.out.println("No artist selected yet");
            showArtistOptions();
        }else{
            artistController.showAllTechnicalRequirements(actualArtist);
            showArtistOptions();
        }
    }
    //Change artist contact information
    private static void changeArtistInfo(){
        if(actualArtist == null){
            System.out.println("No artist selected yet");
            showArtistOptions();
        }else{
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter the artist's new contact information: ");
            String newContactInfo = sc.nextLine();
            System.out.println(artistController.changeContactInfo(newContactInfo,actualArtist));
            showArtistOptions();
        }
    }
    //If user select 3
    private static void showLocationOptions(){
        locationController.showLocationsDetails(locationController.getAllLocations());
        if(actualLocation == null){
            System.out.println("No location selected yet");
        }else{
            System.out.println("Actual location selected: "+actualLocation);
        }
        Scanner sc = new Scanner(System.in);
        System.out.println("Please select an option: ");
        System.out.println("""
                1. Create Location
                2. Select Location
                3. Set Location in event\
                
                4. Record date available
                5. Add Technical Feature
                6. Remove Technical Feature\
                
                7. Change Location Capacity
                8. Return to main menu""");
        try{
            int choice = sc.nextInt();
            if(choice <0 || choice > 8){
                System.out.println("The option you entered is invalid");
                showLocationOptions();
            }else{
                switch (choice){
                    case 1:
                        createLocation();
                        break;
                    case 2:
                        selectLocation();
                        break;
                    case 3:
                        setEventLocation();
                        break;
                    case 4:
                        addAvailableDate();
                        break;
                    case 5:
                        addTechnicalFeature();
                        break;
                    case 6:
                        deleteTechnicalFeature();
                        break;
                    case 7:
                        changedCapacityLocation();
                        break;
                    case 8:
                        displayMenu(null);
                        break;
                    default:
                        System.out.println("Please enter a valid option");
                        showLocationOptions();
                }
            }
        }catch (Exception e){
            System.out.println("Please enter a valid option");
            System.out.println("-------------back to location menu----------");
            showLocationOptions();
        }
    }
    //Create a new location
    private static void createLocation(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the location's name: ");
        String name = sc.nextLine();
        System.out.println("Enter the location's address: ");
        String address = sc.nextLine();
        try{
            System.out.println("Enter the capacity of the location: ");
            int capacity = sc.nextInt();
            if(capacity <= 0){
                System.out.println("Capacity must be greater than 0");
                System.out.println("-------------back to location menu----------");
                showLocationOptions();
            }else{
                Location location = locationController.createLocation(name,address,capacity);
                System.out.println("Location created successfully:\n "+location.toString());
                showLocationOptions();
            }
        }catch (Exception e){
            System.out.println("Please enter a valid capacity");
            System.out.println("-------------back to location menu----------");
            showLocationOptions();
        }
    }
    //Select a location
    private static void selectLocation(){
        if(locationController.getAllLocations().isEmpty()){
            System.out.println("No location added yet");
        }else{
            System.out.println("Please enter the id of the location you want to select");
            try{
                Scanner sc = new Scanner(System.in);
                int id = sc.nextInt();
                Location location = locationController.getLocationById(id);
                if(location == null){
                    System.out.println("The entered id does not correspond to any available location.");
                }else{
                    actualLocation = location;
                    System.out.println("Location successfully selected");
                    showLocationOptions();
                }
            }catch (Exception e){
                System.out.println("Please enter a valid location");
                System.out.println("-------------back to location menu----------");
                showLocationOptions();
            }
        }
    }
    //Set location in event
    private static void setEventLocation(){
        Scanner sc = new Scanner(System.in);
        if(actualLocation == null){
            System.out.println("No location selected yet");
            showLocationOptions();
        }else{
            if(eventController.getEvents().isEmpty()){
                System.out.println("No events added yet");
                showLocationOptions();
            }
            try{
                eventController.printEvents(eventController.getEvents());
                System.out.println("Please enter the ID of the event in which the artist is going to participate.  ");
                int id = sc.nextInt();
                actualEvent = eventController.getEventById(id);
                if(actualEvent == null){
                    System.out.println("No event with the requested id was found");
                    showLocationOptions();
                }else{
                    actualEvent.setLocation(actualLocation);
                    System.out.println("Location added to the event");
                    showLocationOptions();
                }
            }catch (Exception e){
                System.out.println("Please enter a valid ID for the event");
                System.out.println("-------------back to artist menu----------");
                showLocationOptions();
            }
        }
    }
    //Add an available date to the location
    private static void addAvailableDate(){
        Scanner sc = new Scanner(System.in);
        if(actualLocation == null){
            System.out.println("No location selected yet");
            showLocationOptions();
        }else{
            try{
                System.out.println("Enter the date you wish to add with the following format: YYYY-MM-DD");
                LocalDate date = LocalDate.parse(sc.nextLine());
                locationController.recordDateAvailable(date,actualLocation);
                System.out.println("Available date location successfully added");
                showLocationOptions();
            }catch (Exception e){
                System.out.println("The date entered does not have a valid format.");
                System.out.println("-------------back to location menu----------");
                showLocationOptions();
            }
        }
    }
    //Adding a technical feature to the location
    private static void addTechnicalFeature(){
        Scanner sc = new Scanner(System.in);
        if(actualLocation == null){
            System.out.println("No location selected yet");
            showLocationOptions();
        }else{
            System.out.println("Enter the technical feature you want to add");
            String technicalFeature = sc.nextLine();
            locationController.addTechnicalFeature(technicalFeature,actualLocation);
            System.out.println("Technical feature successfully added");
            showLocationOptions();
        }
    }
    //Remove a technical feature of the location
    private static void deleteTechnicalFeature(){
        Scanner sc = new Scanner(System.in);
        if(actualLocation == null){
            System.out.println("No location selected yet");
            showLocationOptions();
        }else{
            System.out.println("Enter the technical feature you want Delete: ");
            String technicalFeature = sc.nextLine();
            locationController.removeTechnicalFeature(technicalFeature,actualLocation);
            System.out.println("Technical feature successfully deleted");
            showLocationOptions();
        }
    }
    //Change the capacity of a location
    private static void changedCapacityLocation(){
        Scanner sc = new Scanner(System.in);
        if(actualLocation == null){
            System.out.println("No location selected yet");
            showLocationOptions();
        }else{
            try{
                System.out.println("Enter the capacity you wish to change: ");
                int newCapacity = sc.nextInt();
                locationController.changeCapacity(newCapacity,actualLocation);
                System.out.println("Capacity successfully changed");
                showLocationOptions();

            }catch (Exception e){
                System.out.println("Please enter a valid capacity");
                System.out.println("-------------back to location menu----------");
                showLocationOptions();
            }
        }
    }
    //If user select 4
    private static void showFinancesOptions(){
        financeController.showFinances(financeController.getFinances());
        if(actualFinance == null){
            System.out.println("No finance selected yet");
        }else{
            System.out.println("Actual finance selected: "+actualFinance);
        }
        Scanner sc = new Scanner(System.in);
        System.out.println("Please select an option: ");
        System.out.println("""
                1. Create Finance for an event
                2. Select Finance
                3. Add Income\
                
                4. Add Expense
                5. Get balance
                6. Add budget\
                
                7. Return to main menu""");
        try{
            int choice = sc.nextInt();
            if(choice <1 || choice > 7){
                System.out.println("This is not a valid option");
                showFinancesOptions();
            }else{
                switch (choice){
                    case 1:
                        createFinance();
                        break;
                    case 2:
                        selectFinance();
                        break;
                    case 3:
                        addFinanceIncome();
                        break;
                    case 4:
                        addExpense();
                        break;
                    case 5:
                        getBalance();
                        break;
                    case 6:
                        injectBudget();
                        break;
                    case 7:
                        displayMenu(null);
                        break;
                    default:
                        System.out.println("Please enter a valid option");
                        showFinancesOptions();
                }
            }
        }catch (Exception e){
            System.out.println("Please enter a valid option");
            System.out.println("-------------back to finance menu----------");
            showFinancesOptions();
        }
    }
    //Create finance
    private static void createFinance(){
        actualEvent = null;
        Scanner sc = new Scanner(System.in);
        if(eventController.getEvents().isEmpty()){
            System.out.println("No events added yet, Please add an event first: ");
            displayMenu(null);
        }
        try{
            eventController.printEvents(eventController.getEvents());
            System.out.println("Select the ID of the event for which you want to manage the financials");
            int id = sc.nextInt();
            Event event = eventController.getEventById(id);
            if(event== null){
                System.out.println("No event has been found with the requested event ID");
            }else{
                System.out.println("Enter the initial event budget: "+event.getName());
                try{
                    double budget = sc.nextDouble();
                    financeController.createFinance(event,budget);
                    System.out.println("Finance successfully added");
                    showFinancesOptions();
                }catch (Exception e){
                    System.out.println("Please enter a valid budget");
                    System.out.println("-------------back to finance menu----------");
                    showFinancesOptions();
                }
            }
        }catch (Exception e){
            System.out.println("Please enter a valid ID event");
            System.out.println("-------------back to finance menu----------");
            showFinancesOptions();
        }
    }
    //Select an actual finance
    private static void selectFinance(){
        if(financeController.getFinances().isEmpty()){
            System.out.println("No finance available, please create a new finance");
            showFinancesOptions();
        }else{
            try{
                System.out.println("Enter the id of the fund you want to select: ");
                Scanner sc = new Scanner(System.in);
                int id = sc.nextInt();
                Finance finance = financeController.searchFinanceByID(id);
                if(finance == null){
                    System.out.println("No finance found with the id "+id);
                    showFinancesOptions();
                }else{
                    actualFinance = finance;
                    System.out.println("Finance successfully selected");
                    showFinancesOptions();
                }
            }catch (Exception e){
                System.out.println("Please enter a valid ID finance");
                System.out.println("-------------back to finance menu----------");
                showFinancesOptions();
            }
        }
    }
    //Add income
    private static void addFinanceIncome(){
        Scanner sc = new Scanner(System.in);
        if(actualFinance == null){
            System.out.println("No finance selected yet");
            showFinancesOptions();
        }else{
            try{
                System.out.println("Enter the amount of income: ");
                double amount = sc.nextDouble();
                if(amount <0){
                    System.out.println("Please enter a valid amount of income");
                    showFinancesOptions();
                }else{
                    System.out.println("Add income details: ");
                    sc.nextLine();
                    String details = sc.nextLine();
                    System.out.println(financeController.addIncome(actualFinance,amount,details));
                    showFinancesOptions();
                }
            }catch (Exception e){
                System.out.println("Please enter a valid income");
                System.out.println("-------------back to finance menu----------");
                showFinancesOptions();
            }
        }
    }
    //Add expenses to the event
    private static void addExpense(){
        Scanner sc = new Scanner(System.in);
        if(actualFinance == null){
            System.out.println("No finance selected yet");
            showFinancesOptions();
        }else{
            try{
                System.out.println("Enter the amount of expense: ");
                double amount = sc.nextDouble();
                if(amount <0){
                    System.out.println("Please enter a valid amount of expense");
                    showFinancesOptions();
                }else{
                    if(amount > actualFinance.getBudget()){
                        System.out.println("Spending far exceeds budget");
                        showFinancesOptions();
                    }
                    System.out.println("Add expense details: ");
                    sc.nextLine();
                    String details = sc.nextLine();
                    System.out.println(financeController.addExpense(actualFinance,amount,details));
                    showFinancesOptions();
                }
            }catch (Exception e){
                System.out.println("Please enter a valid expense");
                System.out.println("-------------back to finance menu----------");
                showFinancesOptions();
            }
        }
    }
    //Get balance of the event
    private static void getBalance(){
        Scanner sc = new Scanner(System.in);
        if(actualFinance == null){
            System.out.println("No finance selected yet");
            showFinancesOptions();
        }else{
            System.out.println("Event: "+actualFinance.getEvent().getName()+" currently has a balance of: "+actualFinance.getBalance());
            System.out.println("Press enter to continue");
            sc.nextLine();
            showFinancesOptions();
        }
    }
    //Add budget
    private static void injectBudget(){
        Scanner sc = new Scanner(System.in);
        if(actualFinance == null){
            System.out.println("No finance selected yet");
            showFinancesOptions();
        }else{
            try{
                System.out.println("Enter the amount to be injected to the budget: ");
                double amount = sc.nextDouble();
                if(amount <0){
                    System.out.println("The budget must be greater than 0");
                    showLocationOptions();
                }else{
                    System.out.println(financeController.addBudget(actualFinance,amount));
                    showFinancesOptions();
                }
            }catch (Exception e){
                System.out.println("Please enter a valid budget");
                System.out.println("-------------back to finance menu----------");
                showFinancesOptions();
            }
        }
    }
    //If user select 5
    private static void exit(){
        System.out.println("Goodbye!");
        System.exit(1);
    }
}