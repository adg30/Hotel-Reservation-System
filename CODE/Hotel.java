
import java.util.ArrayList;

/**
 * Represents a hotel with multiple rooms.
 */
public class Hotel {
  /**
  *the name of the hotel
  */
  private String name;
  /**
  *list of rooms in the hotel
  */
  private ArrayList<Room> rooms = new ArrayList<Room>();
  private ArrayList<Double> DatePrice;
  
  public Hotel(String name, int numrooms, int type) {
    initializeHotel(name, numrooms, type, 1299.0);
  }

  public Hotel(String name, int numrooms, double price, int type) {
    initializeHotel(name, numrooms, type, price);
  }

  public Hotel() {
    this.rooms = new ArrayList<>();
  }

  private void initializeHotel(String name, int numrooms, int type, double price) {
    this.name = name;
    this.rooms = new ArrayList<>();
    this.DatePrice = new ArrayList<>();
    for (int i = 0; i < 31; i++) {
      this.DatePrice.add(1.00);
    }
    int amountEach = numrooms / 3;
    switch (type) {
      case 1:
        for (int i = 0; i < numrooms; i++) {
          this.rooms.add(new standardRoom(i + 100, price));
        }
        break;
      case 2:
        for (int i = 0; i < amountEach; i++) {
          this.rooms.add(new standardRoom(i + 100, price));
        }
        for (int i = amountEach; i < amountEach * 2; i++) {
          this.rooms.add(new executiveRoom(i + 100, price));
        }
        for (int i = amountEach * 2; i < numrooms; i++) {
          this.rooms.add(new deluxeRoom(i + 100, price));
        }
        break;
    }
  }



  /**
   * Gets the name of the hotel.
   *
   * @return the name of the hotel
   */
  public String getName() {
    return this.name;
  }

  /**
   * Checks if a room ID is unique within the hotel.
   *
   * @param ID the room ID to check
   * @return true if the room ID is unique, false otherwise
   */
  public boolean isUniqueRoom(int ID) {
    for (int i = 0; i < rooms.size(); i++) {
      if (rooms.get(i).getID() == ID) {
        return false;
      }
    }
    return true;
  }

  /**
   * Adds a new room to the hotel with a specified ID.
   *
   * @param ID the ID of the new room
   * @param scan the scanner passed from main
   */
  public void addRoom(int ID, int model) {
        switch(model){
          case 1:
            rooms.add(new standardRoom(ID, rooms.get(0).getPrice()));
            break;
          case 2:
            rooms.add(new deluxeRoom(ID, rooms.get(0).getPrice()));
            break;
          case 3:
            rooms.add(new executiveRoom(ID, rooms.get(0).getPrice()));
            break;
        }
  }

  public ArrayList<Room> getRooms(){
    return rooms;
  }


  /**
   * Calculates the total revenue of the hotel.
   *
   * @return the total revenue
   */
  public double sumTotal() {
    int i;
    double sum = 0;
    for (i = 0; i < rooms.size(); i++) {
      sum += rooms.get(i).getTotalPrice(); // adds all the rooms totalprice to sum
    }
    return sum;
  }

  public String updatePrice(double price) {
    if (price < 100) {
        return "Price must be greater than or equal to 100.";
    }

    for (Room room : rooms) {
        if (!room.getReservations().isEmpty()) {
            return "There are still reservations in the hotel, please remove them first.";
        }
    }

    for (Room room : rooms) {
        room.setPrice(price);
    }

    return "Price updated successfully.";
}

  /**
   * Method for searching up a specific room in the hotel using its ID.
   *
   * @param key the the room ID to search for
   * @return the index of the room if found, -1 if not found
   */
  public int searchRoom(int key) {
    for (int i = 0; i < rooms.size(); i++) {
      if (key == rooms.get(i).getID()) {
        return i;
      }
    }
    return -1;
  }

  /**
   * Method for searching up a specific guest in the hotel using their name and
   * room index.
   *
   * @param roomIndex the index of the room to search index
   * @param key       for searching index of guest name in a room
   * @return the index of the guest if found, -1 if not found
   */
  public int searchGuest(int roomIndex, String key)// this checks if the guest is in the hotel, key is their name
  {
    int i;
    for (i = 0; i < rooms.get(roomIndex).getReservations().size(); i++) {
      if (key.equals(rooms.get(roomIndex).getReservations().get(i).getGuestName()))
        return i;
    }
    return -1;
  }

  public String removeReservation(int roomName, String guestName) {
    int roomIndex = searchRoom(roomName);
    if (roomIndex == -1) {
        return "Invalid room name.";
    }

    int reservationIndex = searchGuest(roomIndex, guestName);
    if (reservationIndex == -1) {
        return "Invalid guest name.";
    }

    rooms.get(roomIndex).getReservations().remove(reservationIndex);
    return "Reservation removed successfully.";
}

  /**
   * Method for adding a name to the hotel.
   *
   * @param name the name of the hotel
   */
  public void setName(String name) {
    this.name = name;
  }


  public void setDatePrice(int date, double percentage){
    this.DatePrice.set(date, percentage);
  }

  public ArrayList<Double> getDatePrice(){
    return this.DatePrice;
  }

  

  public void addPrice(double price, int index){
    rooms.get(index).addTotalPrice(price);
  }

  public int findRoomIndexByGuest(String guestName) {
    for (int i = 0; i < rooms.size(); i++) {
        for (Reservation reservation : rooms.get(i).getReservations()) {
            if (reservation.getGuestName().equals(guestName)) {
                return i;
            }
        }
    }
    return -1;
}


}