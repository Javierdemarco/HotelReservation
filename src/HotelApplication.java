package src;

import java.text.SimpleDateFormat;
import java.util.*;

import src.api.AdminResource;
import src.api.HotelResource;
import src.model.Customer;
import src.model.IRoom;
import src.ui.AdminMenu;
import src.ui.MainMenu;

import static java.lang.Integer.compare;
import static java.lang.Integer.rotateLeft;

public class HotelApplication {
    public static void main(String[] args) {
        MainMenu mainMenu = new MainMenu();
        String userInput = null;
        boolean running = true;
        Scanner scMain = new Scanner(System.in);
        Date checkInDate;
        Date checkOutDate;
        String email = null;
        String firstName;
        String emailAdress;
        String lastName;
        boolean flagReservation = true;

        System.out.println("Hello and welcome to the Hotel Reservation Application");
        while (running) {
            try {
                System.out.println(mainMenu);
                userInput = scMain.nextLine();
                switch (Objects.requireNonNull(userInput)) {
                    case "1" -> {
                        if (HotelResource.getAllCustomers().isEmpty()) {
                            System.out.println("You cannot get a reservation if there are no customers registered.");
                            break;
                        }
                        // find reservation
                        System.out.println("These are the accounts registered: ");
                        for (Customer c : HotelResource.getAllCustomers()) {
                            System.out.println(c.toString());
                        }
                        System.out.println("Enter email address (example@domain.com) to book a room with an existing account: ");
                        emailAdress = scMain.nextLine();
                        if (!HotelResource.checkEmailFormat(emailAdress)) {
                            System.out.println("The email format is not correct, please check it (example@domain.com)");
                            break;
                        }
                        if (!HotelResource.emailExistInCustomers(emailAdress)) {
                            System.out.println("The email does not exist within the registered users.");
                            break;
                        }
                        System.out.println("Enter check in date (MM/dd/yyyy): ");
                        checkInDate = new SimpleDateFormat("MM/dd/yyyy").parse(scMain.nextLine());
                        System.out.println("Enter check out date (MM/dd/yyyy): ");
                        checkOutDate = new SimpleDateFormat("MM/dd/yyyy").parse(scMain.nextLine());
                        if (!checkValidDates(checkInDate, checkOutDate)){
                            break;
                        }
                        if (HotelResource.findARoom(checkInDate, checkOutDate, emailAdress)){
                            System.out.println("Room booked");
                        } else{
                            System.out.println("Nothing booked");
                        }

                    }
                    case "2" -> {
                        // see reservations
                        System.out.println("These are the accounts registered: ");
                        for (Customer c : HotelResource.getAllCustomers()) {
                            System.out.println(c.toString());
                        }
                        System.out.println("Enter email address (example@domain.com) to see reservations: ");
                        emailAdress = scMain.nextLine();
                        if (!HotelResource.checkEmailFormat(emailAdress)) {
                            System.out.println("The email format is not correct, please check it (example@domain.com)");
                            break;
                        }
                        if (!HotelResource.emailExistInCustomers(emailAdress)) {
                            System.out.println("The email does not exist within the registered users.");
                            break;
                        }
                        System.out.println(HotelResource.getCustomersReservations(emailAdress));
                    }
                    case "3" -> {
                        // create account
                        System.out.println("Enter Email: ");
                        email = scMain.nextLine();
                        System.out.println("Enter First Name: ");
                        firstName = scMain.nextLine();
                        System.out.println("Enter Last Name: ");
                        lastName = scMain.nextLine();
                        try {
                            HotelResource.createACustomer(email, firstName, lastName);
                        } catch (Exception e) {
                            System.out.println(e.getLocalizedMessage());
                        }
                    }
                    case "4" ->
                        // Admin menu
                            adminMenuMethod(scMain);
                    case "5" -> {
                        System.out.println("Exiting program");
                        scMain.close();
                        running = false;
                    }
                    default -> System.out.println("Please enter a valid option");
                }
            } catch (Exception ex) {
                System.out.println(ex.getLocalizedMessage());
            }
        }
    }

    public static void adminMenuMethod(Scanner scAdmin) {
        AdminMenu adminMenu = new AdminMenu();
        String userInput = null;
        boolean inAdminMenu = true;

        while (inAdminMenu) {
            System.out.println(adminMenu);
            try {
                userInput = scAdmin.nextLine();
                switch (userInput) {
                    case "1" ->
                        // see all customers
                            System.out.println(AdminResource.getAllCustomers().toString());
                    case "2" ->
                        // see all rooms
                            System.out.println(AdminResource.getAllRooms().toString());
                    case "3" ->
                        // see all reservation
                            AdminResource.displayAllReservations();
                    case "4" ->
                        // add a room
                            addRooms(scAdmin);
                    case "5" -> {
                        inAdminMenu = false;
                        System.out.println("Going back to Main Menu");
                        scAdmin.reset();
                    }
                    default -> System.out.println("Please enter a valid option");
                }
            } catch (Exception ex) {
                System.out.println(ex.getLocalizedMessage());
            }
        }
    }

    public static void addRooms(Scanner scAdmin) {
        boolean addingRooms = true;
        boolean flagAnswer = true;
        List<IRoom> newRooms = new ArrayList<>();
        do {
            System.out.println("Enter Room Number ");
            Integer roomNumber = Integer.parseInt(scAdmin.nextLine());
            System.out.println("Enter Room Price ");
            double roomPrice = Double.parseDouble(scAdmin.nextLine());
            System.out.println("Enter Room Type 1-Single 2-Double ");
            int roomEnumeration = Integer.parseInt(scAdmin.nextLine()) - 1;

            newRooms.add(AdminResource.createRoom(roomNumber, roomPrice, roomEnumeration));

            flagAnswer = true;
            while (flagAnswer) {
                System.out.println("Do you want to add more rooms? Y or N");
                String addMore = scAdmin.nextLine();

                if (addMore.compareTo("y") == 0 || addMore.compareTo("Y") == 0) {
                    flagAnswer = false;
                } else if (addMore.compareTo("n") == 0 || addMore.compareTo("N") == 0) {
                    flagAnswer = false;
                    addingRooms = false;
                } else {
                    System.out.println("Answer Y or N");
                }
            }
        } while (addingRooms);
        try {
            AdminResource.addRoom(newRooms);
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
        }
    }

    public static boolean checkValidDates(Date checkIn, Date checkOut){
        Date now = new Date();
        if (now.compareTo(checkIn) > 0 || now.compareTo(checkOut) > 0){
            System.out.println("No date can be earlier than now");
            return false;
        }
        if (checkOut.compareTo(checkIn) < 0){
            System.out.println("Check out date is earlier than check in");
            return false;
        }
        return true;
    }
}
