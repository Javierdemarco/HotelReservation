package src.model;

import java.util.Date;

public class Reservation {

    private Customer customer;
    private IRoom room;
    private Date checkInDate;
    private Date checkOutDate;

    /**
     * @param customer
     * @param room
     * @param checkInDate
     * @param checkOutDate
     */
    public Reservation(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    /**
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * @param customer the customer to set
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * @return the room
     */
    public IRoom getRoom() {
        return room;
    }

    /**
     * @param room the room to set
     */
    public void setRoom(IRoom room) {
        this.room = room;
    }

    /**
     * @return the checkInDate
     */
    public Date getCheckInDate() {
        return checkInDate;
    }

    /**
     * @param checkInDate the checkInDate to set
     */
    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    /**
     * @return the checkOutDate
     */
    public Date getCheckOutDate() {
        return checkOutDate;
    }

    /**
     * @param checkOutDate the checkOutDate to set
     */
    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public boolean checkDates(Date checkInDate, Date checkOutDate){
            if (this.checkInDate.equals(checkInDate) && this.checkOutDate.equals(checkOutDate)) {
                return false;
            }
            if (this.checkInDate.before(checkInDate) && this.checkOutDate.after(checkInDate)) {
                return false;
            }
            if (this.checkInDate.before(checkOutDate) && this.checkOutDate.after(checkOutDate)) {
                return false;
            }
        return true;
    }

    @Override
    public String toString() {
        return customer + " has a reservation on room " + room + " from " + checkInDate + " to " + checkOutDate;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((checkInDate == null) ? 0 : checkInDate.hashCode());
	result = prime * result + ((checkOutDate == null) ? 0 : checkOutDate.hashCode());
	result = prime * result + ((room == null) ? 0 : room.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (getClass() != obj.getClass())
	    return false;
	Reservation other = (Reservation) obj;
	if (this.room.equals(other.room)){
	    if(this.checkInDate.equals(other.checkInDate)
	       && this.checkOutDate.equals(other.checkOutDate)){
		return true;
	    }
	}
	return false;
    }
}
