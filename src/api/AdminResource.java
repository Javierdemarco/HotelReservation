package src.api;

import src.model.Customer;
import src.model.IRoom;
import src.services.CustomerService;
import src.services.ReservationService;

import java.util.Collection;
import java.util.List;

public class AdminResource {

    private static final CustomerService customerService = CustomerService.getInstance();
    private static final ReservationService reservationService = ReservationService.getInstance();

    public static Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }

    public static void addRoom(List<IRoom> rooms) throws Exception{
	try{
	for(IRoom each : rooms){
	    reservationService.addRoom(each);
	}
	}catch(Exception e){
		throw new Exception("Error in creating room");
	}
    }

    public static Collection<IRoom> getAllRooms() {
        return reservationService.getAllRooms();
    }

    public static Collection<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    public static void displayAllReservations() {
        reservationService.printAllReservation();
    }

    public static IRoom createRoom(Integer number, double price, int type){
	return reservationService.createRoom(number, price, type);
    }
}
