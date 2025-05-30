package com.eventmasterpro;
import com.eventmasterpro.controller.*;
import com.eventmasterpro.model.*;
import com.eventmasterpro.model.enums.EventCategory;
import com.eventmasterpro.model.enums.TicketCategory;
import java.time.LocalDate;
import java.util.ArrayList;
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
    //Ticket controller
    private final static TicketController ticketController = TicketController.getTicketController();
    public static void main(String[] args) {
        DataController dataController = new DataController();
        dataController.loadAllData();
        //Start autosave
        AutoSaveScheduler autoSave = new AutoSaveScheduler(dataController);
        autoSave.startAutoSave();
        //Save all data when the program is ended
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Saving data before exit...");
            dataController.saveAllData();
        }));
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
        if(actualEvent == null){
            System.out.println("No event selected yet");
        }else{
            System.out.println("Active event selected: " + actualEvent.getName());
        }
        System.out.println("""
                0. Show All events
                1. Create Event
                2. Delete Event
                3. Show event information
                4. Edit event
                5. Assign location for event
                6. Select event
                7. Assign a date to the event
                8. Create tickets for the event
                9. Get maximum number of tickets that can be created
                10. Show tickets available for sale
                11. Show tickets sold
                12. Sold ticket for the event
                13. Gets events by category
                14. Return to main menu""");
        Scanner sc = new Scanner(System.in);
        try{
            int choice = sc.nextInt();
            if(choice <0 || choice >16){
                System.out.println("This number is not included in the available options");
                showEventsOptions();
            }else{
                switch (choice) {
                    case 0:
                        showAllEvents();
                        break;
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
                        assignLocationEvent();
                        break;
                    case 6:
                        selectEvent();
                        break;
                    case 7:
                        assignDateToEvent();
                        break;
                    case 8:
                        createTickets();
                        break;
                    case 9:
                        getNumAvailableTicketsToCreate();
                        break;
                    case 10:
                        getTicketsAvailable();
                        break;
                    case 11:
                        showTicketsSold();
                        break;
                    case 12:
                        soldTicket();
                        break;
                    case 13:
                        getEventsByCategory();
                        break;
                    case 14:
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
    //Show all events
    private static void showAllEvents(){
        if(eventController.getEvents().isEmpty()){
            System.out.println("No events yet");
            showEventsOptions();
        }else{
            System.out.println("----------------Active events-----------------");
            eventController.printEvents(eventController.getEvents());
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
                    LocalDate date = LocalDate.parse(sc.nextLine());
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
            System.out.println("Please enter the date of the event, with the following format: YYYY-MM-DD");
            LocalDate date = LocalDate.parse(sc.nextLine());
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
    //Assign  location for event
    private static void assignLocationEvent(){
        if(actualEvent==null){
            System.out.println("No event selected yet");
            showEventsOptions();
        }else{
            if(locationController.getAllLocations().isEmpty()){
                System.out.println("No locations added yet");
                showEventsOptions();
            }else{
                locationController.showLocationsDetails(locationController.getAllLocations());
                System.out.println("Please enter a ID of location to assign to the event");
                try{
                    Scanner sc = new Scanner(System.in);
                    int id = sc.nextInt();
                    Location location = locationController.getLocationById(id);
                    if(location == null){
                        System.out.println("Not location found");
                        showEventsOptions();
                    }else{
                        eventController.assignLocationEvent(actualEvent,location);
                        System.out.println("Assigned location event successfully");
                        showEventsOptions();
                    }
                }catch (Exception e){
                    System.out.println("Please enter a valid ID");
                    System.out.println("-------------back to events menu----------");
                    showEventsOptions();
                }
            }
        }
    }
    //Select event
    private static void selectEvent(){
        if(actualEvent != null){
            actualEvent = null;
        }
        eventController.printEvents(eventController.getEvents());
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the ID of the event to select: ");
        try{
            int id = sc.nextInt();
            actualEvent = eventController.getEventById(id);
            if(actualEvent == null){
                System.out.println("There is no event with the selected id.");
                selectEvent();
            }else{
                System.out.println("Event selected successfully\n"+actualEvent);
                showEventsOptions();
            }
        }catch (Exception e){
            System.out.println("Please enter a valid ID");
            System.out.println("-------------------------------------------------------");
            selectEvent();
        }
    }
    //Assign date to actual event selected
    private static void assignDateToEvent(){
        if(actualEvent == null){
            System.out.println("No event selected yet");
            showEventsOptions();
        }else{
            System.out.println("Please enter the date of the event");
            try{
                Scanner sc = new Scanner(System.in);
                LocalDate date = LocalDate.parse(sc.next());
                System.out.println(eventController.assignDateEvent(actualEvent,date));
                showEventsOptions();
            }catch (Exception e){
                System.out.println("Please enter a valid date");
                System.out.println("-------------back to events menu----------");
                showEventsOptions();
            }
        }
    }
    //Create a tickets for the event
    private static void createTickets(){
        if(actualEvent == null){
            System.out.println("No event selected yet");
            showEventsOptions();
        }else{
            System.out.println("Please enter the number of the type of ticket you are going to create");
            System.out.println("""
                    1. Vip
                    2. General
                    3. Backstage
                    4. Early access
                    5. other
                    """);
            try{
                Scanner sc = new Scanner(System.in);
                TicketCategory category;
                int choice = sc.nextInt();
                switch (choice) {
                    case 1 -> category = TicketCategory.VIP;
                    case 2 -> category = TicketCategory.GENERAL;
                    case 3 -> category = TicketCategory.BACKSTAGE;
                    case 4 -> category = TicketCategory.EARLY_ACCESS;
                    case 5 -> category = TicketCategory.OTHER;
                    default -> {
                        category = null;
                        System.out.println("Please enter a valid option!");
                        createTickets();
                    }
                }
                System.out.println("Please enter the price of the ticket");
                double price = sc.nextDouble();
                System.out.println("Please enter the number of tickets you are going to generate");
                int numTickets = sc.nextInt();
                System.out.println(eventController.createTicketsEvent(numTickets,actualEvent,category,price));
                showEventsOptions();
            }catch (Exception e){
                System.out.println("Please enter a valid number of the type of ticket or a valid amount for the price or or number of tickets");
                System.out.println("-------------back to events menu----------");
                showEventsOptions();
            }
        }
    }
    //Get a number of tickets available to create
    private static void getNumAvailableTicketsToCreate(){
        if(actualEvent == null){
            System.out.println("No event selected yet");
            showEventsOptions();
        }else{
            System.out.println(eventController.getNumAvailableTicketsToCreate(actualEvent));
            showEventsOptions();
        }
    }
    //Show tickets available for sale
    private static void getTicketsAvailable(){
        if(actualEvent == null){
            System.out.println("No event selected yet");
            showEventsOptions();
        }else{
            ArrayList<Ticket> ticketsAvailable = eventController.getTicketsAvailable(actualEvent);
            ticketController.printTickets(ticketsAvailable);
            showEventsOptions();
        }
    }
    //Show all tickets sold
    private static void showTicketsSold(){
        if(actualEvent == null){
            System.out.println("No event selected yet");
            showEventsOptions();
        }else{
            ArrayList<Ticket> ticketsSold = eventController.getTicketsSold(actualEvent);
            ticketController.printTickets(ticketsSold);
            showEventsOptions();
        }
    }
    //Sold ticket for actual event selected
    private static void  soldTicket(){
        if(actualEvent == null){
            System.out.println("No event selected yet");
            showEventsOptions();
        }else{
            ArrayList<Ticket> ticketsAvailable = eventController.getTicketsAvailable(actualEvent);
            ticketController.printTickets(ticketsAvailable);
            System.out.println("Please enter the id of the ticket to sell");
            try{
                Scanner sc = new Scanner(System.in);
                int ticketId = sc.nextInt();
                Ticket ticket = actualEvent.getTickets().get(ticketId - 1);
                if(ticket == null){
                    System.out.println("Ticket does not exist");
                    showEventsOptions();
                }else{
                    System.out.println(eventController.soldTicket(actualEvent,ticket));
                    showEventsOptions();
                }
            }catch (Exception e){
                System.out.println("Please enter a valid id");
                System.out.println("------------back to events menu----------");
                showEventsOptions();
            }
        }
    }
    //Get all events given a category
    private static void getEventsByCategory(){
        System.out.println("""
                Please select the number of the category
                1. Concert
                2. Festival
                3. Conference
                4. WorkShop
                5. Other
                """);
        try{
            Scanner sc = new Scanner(System.in);
            int choice = sc.nextInt();
            EventCategory category;
            switch (choice) {
                case 1 -> category = EventCategory.CONCERT;
                case 2 -> category = EventCategory.FESTIVAL;
                case 3 -> category = EventCategory.CONFERENCE;
                case 4 -> category = EventCategory.WORKSHOP;
                case 5 -> category = EventCategory.OTHER;
                default -> {
                    category = null;
                    System.out.println("Please enter a valid option!");
                    showEventsOptions();
                }
            }
            eventController.printEvents(eventController.getEventsByCategory(category));
            showEventsOptions();
        }catch (Exception e){
            System.out.println("Please enter a valid option!");
            System.out.println("------------back to events menu----------");
            showEventsOptions();
        }
    }
    //If user select 2
    private static void showArtistOptions(){
        if(actualArtist == null){
            System.out.println("No artist selected yet");
        }else{
            System.out.println("Actual artist selected: "+actualArtist);
        }
        Scanner sc = new Scanner(System.in);
        System.out.println("Please select an option: ");
        System.out.println("""
                0. Show all artists
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
            if(choice <0 || choice >9){
                System.out.println("This number is not included in the available options");
                showArtistOptions();
            }else{
                switch (choice) {
                    case 0:
                        showAllArtists();
                        break;
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
    //Show all artist
    private static void showAllArtists(){
        artistController.showArtists(artistController.getArtists());
        showArtistOptions();
    }
    //Create artis
    private static void createArtis(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the artist's name: ");
        String name = sc.nextLine();
        System.out.println("Enter the artist's contact information: ");
        String contactInfo = sc.nextLine();
        Artist artist = artistController.createArtists(name,contactInfo);
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
        if(actualLocation == null){
            System.out.println("No location selected yet");
        }else{
            System.out.println("Actual location selected: "+actualLocation);
        }
        Scanner sc = new Scanner(System.in);
        System.out.println("Please select an option: ");
        System.out.println("""
                0. Show all Locations
                1. Create Location
                2. Select Location
                3. Set Location in event\
                
                4. Record date available
                5. Add Technical Feature
                6. Remove Technical Feature\
                
                7. Change Location Capacity
                8. Remove available date for location
                9. Get available locations by date\
                
                10. Search for unavailable locations by date
                11. Return to main menu""");
        try{
            int choice = sc.nextInt();
            if(choice <0 || choice > 11){
                System.out.println("The option you entered is invalid");
                showLocationOptions();
            }else{
                switch (choice){
                    case 0:
                        showAllLocations();
                        break;
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
                        deleteAvailableDate();
                        break;
                    case 9:
                        getAvailableLocationsByDate();
                        break;
                    case 10:
                        getUnavailableLocationsByDate();
                        break;
                    case 11:
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
    //Show all locations
    private static void showAllLocations(){
        locationController.showLocationsDetails(locationController.getAllLocations());
        showLocationOptions();
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
    //Delete available date for a location
    private static void deleteAvailableDate(){
        Scanner sc = new Scanner(System.in);
        if(actualLocation == null){
            System.out.println("No location selected yet");
            showLocationOptions();
        }else{
            try{
                System.out.println("Enter the date you wish to delete: ");
                LocalDate date = LocalDate.parse(sc.nextLine());
                System.out.println(locationController.deleteDateAvailable(date, actualLocation));
                showLocationOptions();
            }catch (Exception e){
                System.out.println("Please enter a valid date");
                System.out.println("-------------back to location menu----------");
                showLocationOptions();
            }
        }
    }
    //Get all available locations with a date
    private static void getAvailableLocationsByDate(){
        Scanner sc = new Scanner(System.in);
        try{
            System.out.println("Please enter the date you would like to check availability of locations");
            LocalDate date = LocalDate.parse(sc.nextLine());
            ArrayList<Location> locations = locationController.searchLocationsWithDate(date);
            if(locations.isEmpty()){
                System.out.println("No locations found");
                showLocationOptions();
            }else{
                locationController.showLocationsDetails(locations);
                showLocationOptions();
            }
        }catch (Exception e){
            System.out.println("Please enter a valid date");
            System.out.println("-------------back to location menu----------");
            showLocationOptions();
        }
    }
    //Get unavailable locations with date
    private static void getUnavailableLocationsByDate(){
        Scanner sc = new Scanner(System.in);
        try{
            System.out.println("Please enter the date you would like to check availability of locations");
            LocalDate date = LocalDate.parse(sc.nextLine());
            ArrayList<Location> locations = locationController.searchNotAvailableLocationsWithDate(date);
            if(locations.isEmpty()){
                System.out.println("No locations found");
                showLocationOptions();
            }else{
                locationController.showLocationsDetails(locations);
                showLocationOptions();
            }
        }catch (Exception e){
            System.out.println("Please enter a valid date");
            System.out.println("-------------back to location menu----------");
            showLocationOptions();
        }
    }
    //If user select 4
    private static void showFinancesOptions(){
        if(actualFinance == null){
            System.out.println("No finance selected yet");
        }else{
            System.out.println("Actual finance selected: "+actualFinance);
        }
        Scanner sc = new Scanner(System.in);
        System.out.println("Please select an option: ");
        System.out.println("""
                0. Show all finances
                1. Create Finance for an event
                2. Select Finance
                3. Add Income\
                
                4. Add Expense
                5. Get balance
                6. Add budget\
                
                7. Return to main menu""");
        try{
            int choice = sc.nextInt();
            if(choice <0 || choice > 7){
                System.out.println("This is not a valid option");
                showFinancesOptions();
            }else{
                switch (choice){
                    case 0:
                        showAllFinances();
                        break;
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
                        break;
                }
            }
        }catch (Exception e){
            System.out.println("Please enter a valid option");
            System.out.println("-------------back to finance menu----------");
            showFinancesOptions();
        }
    }
    //Show all finances
    private static void showAllFinances(){
        financeController.showFinances(financeController.getFinances());
        showFinancesOptions();
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
            System.out.println("Event: "+actualFinance.getEvent().getName()+" currently has a balance of: "+financeController.getBalance(actualFinance));
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