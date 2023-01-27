package src.model;

public class Room implements IRoom {

  private Integer roomNumber;
  private Double price;
  private RoomType enumeration;

  public Room(Integer roomNumber, Double price, RoomType enumeration) {
    this.roomNumber = roomNumber;
    this.price = price;
    this.enumeration = enumeration;
  }

  public Integer getRoomNumber() { return roomNumber; }

  public void setRoomNumber(Integer roomNumber) { this.roomNumber = roomNumber; }

  public Double getRoomPrice() { return price; }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result =
        prime * result + ((enumeration == null) ? 0 : enumeration.hashCode());
    result = prime * result + ((price == null) ? 0 : price.hashCode());
    result =
        prime * result + ((roomNumber == null) ? 0 : roomNumber.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Room other = (Room)obj;
    if (this.roomNumber.compareTo(other.roomNumber) == 0) {
      return true;
    }
    return false;
  }

  public void setRoomPrice(Double price) { this.price = price; }

  public RoomType getRoomType() { return enumeration; }

  public void setRoomType(RoomType enumeration) {
    this.enumeration = enumeration;
  }

  public boolean isFree() {
    if (price == 0) {
      return true;
    }
    return false;
  }

  @Override
  public String toString() {
    return enumeration + " room number " + roomNumber + " with cost of " +
        price;
  }
}
