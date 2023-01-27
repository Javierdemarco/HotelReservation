package src.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import src.api.HotelResource;
import src.model.Customer;
import src.model.IRoom;
import src.model.Reservation;
import src.model.Room;
import src.model.RoomType;

import static java.lang.Integer.compare;

public class ReservationService {

    private static ReservationService INSTANCE;
    private static final Set<Reservation> reservations = new HashSet<Reservation>();
    private static final Set<IRoom> rooms = new HashSet<IRoom>();

    public static ReservationService getInstance() {
        if (INSTANCE == null) {
            return new ReservationService();
        }
        return INSTANCE;
    }

    public void addRoom(IRoom room) throws Exception {
        // Check if that room already exist
        for (IRoom each : rooms) {
            System.out.println("1. ROOM " + each.getClass());
            if (each.equals(room)) {
                throw new Exception("The room " + each.getRoomNumber() + " cannot be added");
            }
        }
        plusRoomToCollection(room);
    }

    void plusRoomToCollection(IRoom room) {
        rooms.add(room);
    }

    void plusReservationToCollection(Reservation reservation) {
        reservations.add(reservation);
    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate)
            throws Exception {
        Reservation tempR = new Reservation(customer, room, checkInDate, checkOutDate);
        for (Reservation each : reservations) {
            if (each.getRoom().equals(tempR.getRoom())) {
                if (!each.checkDates(tempR.getCheckInDate(), tempR.getCheckOutDate())) {
                    throw new Exception("This reservation is not possible. Check other dates.");
                }
            }
        }
        plusReservationToCollection(tempR);
        return tempR;
    }

    public boolean findRooms(Date checkInDate, Date checkOutDate, String emailAdress) {
        boolean flagReservation = true;
        Date inDate = checkInDate;
        Date outDate = checkOutDate;
        Collection<IRoom> resultR = new ArrayList<IRoom>(rooms);
        for (Reservation each : reservations) {
            if (each.checkDates(checkInDate, checkOutDate)) {
                if (checkInDate.before(each.getCheckInDate()) && checkOutDate.after(each.getCheckOutDate())) {
                    resultR.remove(each.getRoom());
                } else if (checkInDate.after(each.getCheckInDate()) && checkOutDate.before(each.getCheckOutDate())) {
                    resultR.remove(each.getRoom());
                } else if (checkInDate.before(each.getCheckInDate()) && checkOutDate.after(each.getCheckInDate())) {
                    resultR.remove(each.getRoom());
                } else if (checkInDate.before(each.getCheckOutDate()) && checkOutDate.after(each.getCheckOutDate())) {
                    resultR.remove(each.getRoom());
                } else if (checkInDate.compareTo(each.getCheckInDate()) == 0 || checkInDate.compareTo(each.getCheckOutDate()) == 0
                        || checkOutDate.compareTo(each.getCheckInDate()) == 0 || checkOutDate.compareTo(each.getCheckOutDate()) == 0) {
                    resultR.remove(each.getRoom());
                } else {
                    resultR.add(each.getRoom());
                }
            } else {
                resultR.remove(each.getRoom());
            }
        }
        if (resultR.isEmpty()){
            System.out.println("There are no rooms left on the dates you entered.");
            LocalDate newCheckInL = Instant.ofEpochMilli(checkInDate.getTime())
                    .atZone(ZoneId.of("America/New_York"))
                    .toLocalDate();
            newCheckInL = newCheckInL.plusDays(8);
            Date newCheckIn = Date.from(newCheckInL.atStartOfDay()
                    .atZone(ZoneId.of("America/New_York"))
                    .toInstant());
            // Add 7 days to Check out date
            LocalDate newCheckOutL = Instant.ofEpochMilli(checkOutDate.getTime())
                    .atZone(ZoneId.of("America/New_York"))
                    .toLocalDate();
            newCheckOutL = newCheckOutL.plusDays(8);
            Date newCheckOut = Date.from(newCheckOutL.atStartOfDay()
                    .atZone(ZoneId.of("America/New_York"))
                    .toInstant());
            resultR.addAll(rooms);
            for (Reservation each : reservations) {
                if (each.checkDates(newCheckIn, newCheckOut)) {
                    if (newCheckIn.before(each.getCheckInDate()) && newCheckOut.after(each.getCheckOutDate())) {
                        resultR.remove(each.getRoom());
                    } else if (newCheckIn.after(each.getCheckInDate()) && newCheckOut.before(each.getCheckOutDate())) {
                        resultR.remove(each.getRoom());
                    } else if (newCheckIn.before(each.getCheckInDate()) && newCheckOut.after(each.getCheckInDate())) {
                        resultR.remove(each.getRoom());
                    } else if (newCheckIn.before(each.getCheckOutDate()) && newCheckOut.after(each.getCheckOutDate())) {
                        resultR.remove(each.getRoom());
                    } else if (newCheckIn.compareTo(each.getCheckInDate()) == 0 || newCheckIn.compareTo(each.getCheckOutDate()) == 0
                            || newCheckOut.compareTo(each.getCheckInDate()) == 0 || newCheckOut.compareTo(each.getCheckOutDate()) == 0) {
                        resultR.remove(each.getRoom());
                    } else {
                        resultR.add(each.getRoom());
                    }
                } else {
                    resultR.remove(each.getRoom());
                }
            }
            if (!resultR.isEmpty()){
                System.out.println("We can recommend booking in this dates instead.");
                System.out.println("Check in:" + newCheckIn.toString());
                System.out.println("Check out:" + newCheckOut.toString());
                inDate = newCheckIn;
                outDate = newCheckOut;
            }
        }
        if (!resultR.isEmpty()) {
            List<Integer> roomNumbers = new ArrayList<>();
            for (IRoom each : resultR) {
                roomNumbers.add(each.getRoomNumber());
            }
            while (flagReservation) {
                System.out.println("Which room do you want to book? Answer N if you want to quit");
                System.out.println(resultR);
                Scanner scMain = new Scanner(System.in);;
                String rN = scMain.nextLine();
                if (roomNumbers.contains(Integer.parseInt(rN))) {
                    IRoom roomR = null;
                    for (IRoom each : resultR) {
                        if (compare(each.getRoomNumber(), Integer.parseInt(rN)) == 0) {
                            roomR = each;
                        }
                    }
                    return HotelResource.bookARoom(emailAdress, roomR, inDate, outDate) != null;
                } else if (rN.compareTo("n") == 0 || rN.compareTo("N") == 0) {
                    return true;
                } else {
                    System.out.println("Answer room number or N");
                }
            }
        } else {
            System.out.println("There are no rooms left now and seven days after");
            return false;
        }
        return false;
    }

    public Collection<Reservation> getCustomersReservation(Customer customer) {
        List<Reservation> returnReservations = new ArrayList<Reservation>();
        for (Reservation each : reservations) {
            if (each.getCustomer().equals(customer)) {
                returnReservations.add(each);
            }
        }
        return returnReservations;
    }

    public void printAllReservation() {
        String returnString = "";
        for (Reservation each : reservations) {
            returnString += each.toString() + "\n";
        }
        System.out.println(returnString);
    }

    public Collection<IRoom> getAllRooms() {
        return rooms;
    }

    public IRoom getRoom(String roomNumber) {
        for (IRoom each : rooms) {
            if (each.getRoomNumber().equals(roomNumber)) {
                return each;
            }
        }
        return null;
    }

    public IRoom createRoom(Integer number, double price, int type) {
        return new Room(number, price, RoomType.values()[type]);
    }
}
