
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
  /**
   * dateprice array of the hotel
   */
  private ArrayList<Double> DatePrice;
  
  /**
   * Contructor for hotel with no custom price
   * 
   * @param name name of hotel
   * @param numrooms number of rooms of hotel
   * @param type type of hotel
   */
  public Hotel(String name, int numrooms, int type) {
    initializeHotel(name, numrooms, type, 1299.0);
  }
  /**
   * constructor for hotel with custom price included
   * 
   * @param name name of hotel
   * @param numrooms number of rooms
   * @param price custom price of hotel
   * @param type type of hotel
   */
  public Hotel(String name, int numrooms, double price, int type) {
    initializeHotel(name, numrooms, type, price);
  }
  /**
   * constructor for an empty hotel
   */
  public Hotel() {
    this.rooms = new ArrayList<>();
  }

  /**
   * Initializes a hotel
   * 
   * @param name name of hotel
   * @param numrooms number of rooms of hotel
   * @param type type of hotel(standard or divided)
   * @param price base price of rooms for the hotel
   */
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
   * @param model the type of the room
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
  /**
   * gets the rooms array for the hotel
   * 
   * @return the rooms array of the hotel
   */
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
  /**
   * Updates the price of all rooms in the hotel into a new price
   * 
   * @param price the new price of the rooms
   * @return a string that is displayed in the controller's managebuttonlistener if it doesnt satisfy the requirements
   */
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
   * @param room the room to search index
   * @param key       for searching index of guest name in a room
   * @return the index of the guest if found, -1 if not found
   */
  public int searchGuest(Room room, String key)// this checks if the guest is in the hotel, key is their name
  {
    int i;
    for (i = 0; i < room.getReservations().size(); i++) {
      if (key.equals(room.getReservations().get(i).getGuestName()))
        return i;
    }
    return -1;
  }
  /**
   * Removes a reservation from this hotel
   * 
   * @param room the room to remove the reservation from
   * @param guestName the guest who's reservation is to be removed
   * @return message that tells user the outcome of the process
   */
  public String removeReservation(Room room, String guestName) {

    int reservationIndex = searchGuest(room, guestName);
    if (reservationIndex == -1) {
        return "Invalid guest name.";
    }

    room.getReservations().remove(reservationIndex);
    return "Reservation removed successfully.";
}

  /**
   * Method for setting a new name for the hotel.
   *
   * @param name the name of the hotel
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Method for setting a DatePrice for the hotel
   * 
   * @param date the date to set a dateprice
   * @param percentage the percentage the dateprice is set to
   */
  public void setDatePrice(int date, double percentage){
    this.DatePrice.set(date, percentage);
  }
  /**
   * Method for getting the dateprice array of the hotel
   * 
   * @return the array of doubles containing the dateprices of the hotel
   */
  public ArrayList<Double> getDatePrice(){
    return this.DatePrice;
  }

  
  /**
   * adds price to a room at a certain index
   * 
   * @param price the price to be added
   * @param index the index of the room the price is added to
   */
  public void addPrice(double price, int index){
    rooms.get(index).addTotalPrice(price);
  }
  /**
   * finds the room index reserved by searching using guest name
   * 
   * @param guestName the guest to be searched
   * @return the index of the room, if not found, return -1
   */
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