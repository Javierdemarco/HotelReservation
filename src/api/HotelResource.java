package src.api;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.regex.Pattern;

import src.model.Customer;
import src.model.IRoom;
import src.model.RoomType;
import src.model.Reservation;
import src.services.CustomerService;
import src.services.ReservationService;

public class HotelResource {

    private static final CustomerService customerService = CustomerService.getInstance();
    static ReservationService reservationService = ReservationService.getInstance();
    
    public static Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }

    public static void createACustomer(String email, String firstName, String lastName) throws Exception {
        try {
            customerService.addCustomer(email, firstName, lastName);
        }catch (Exception e){
            throw new Exception(e.getLocalizedMessage());
        }
    }

    public static IRoom getRoom(String roomNumber) throws Exception {
        IRoom result = reservationService.getRoom(roomNumber);
        if (result.equals(null)) {
            throw new Exception("Room with number " + roomNumber + "does not exist");
        }
        return result;

    }

    public static Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate) {
        Reservation result;
        try {
            result = reservationService.reserveARoom(customerService.getCustomer(customerEmail), room, checkInDate,
						     checkOutDate);
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            result = null;
        }
        return result;
    }

    public static Collection<Reservation> getCustomersReservations(String customerEmail) {
        return reservationService.getCustomersReservation(customerService.getCustomer(customerEmail));
    }

    public static boolean findARoom(Date checkIn, Date checkOut, String emailAdress) {
        return reservationService.findRooms(checkIn, checkOut, emailAdress);
    }

    public static Collection<Customer> getAllCustomers(){
        return customerService.getAllCustomers();
    }

    public static boolean checkEmailFormat(String email){
        String emailRegex = "^(.+)@(.+).com";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    public static boolean emailExistInCustomers (String email){
        for (Customer c : getAllCustomers()){
            if (c.getEmail().compareTo(email) == 0){
                return true;
            }
        }
        return false;
    }

}
