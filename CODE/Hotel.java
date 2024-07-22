
import java.util.ArrayList;
import java.util.Scanner;

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
   * Constructs a hotel with a specified name and number of rooms.
   *
   * @param name     the name of the hotel
   * @param numrooms the number of rooms in the hotel
   */
  public Hotel(String name, int numrooms) {// use numrooms to construct all rooms using a for loop
    this.name = name;
    this.rooms = new ArrayList<Room>();
    for (int i = 0; i < numrooms; i++)
      this.rooms.add(new Room(i + 100, 1299.0));

  }

  /**
   * Constructs a hotel with a specified name, number of rooms, and room price.
   *
   * @param name     the name of the hotel
   * @param numrooms the number of rooms in the hotel
   * @param price    the price of each room
   */
  public Hotel(String name, int numrooms, double price) {// other variation of hotel to include price

    this.name = name;
    this.rooms = new ArrayList<Room>();
    for (int i = 0; i < numrooms; i++)
      this.rooms.add(new Room(i + 100, price));

  }

  /**
   * Default constructor for creating an empty hotel.
   */
  public Hotel() {
    this.rooms = new ArrayList<Room>();

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
  public void addRoom(int ID, Scanner scan) {
    if (isUniqueRoom(ID)) {
        System.out.print("Are you sure you want to add room: " + ID + "? (Y/N) ");
        char choice = scan.next().charAt(0);
        if (choice == 'y' || choice == 'Y') {
            rooms.add(new Room(ID, rooms.get(0).getPrice()));
            System.out.println("\n>>  Room added successfully.  <<\n");
        } else {
            System.out.println("\n>>  Modification discarded.  <<\n");
        }
    } else {
        System.out.println("\n>>  Room name already exists.  <<\n");
    }
}


  /**
   * Calculates the total revenue of the hotel.
   *
   * @return the total revenue
   */
  public double sumTotal() {
    int i, j;
    double sum = 0;
    for (i = 0; i < rooms.size(); i++) {
      for (j = 0; j < rooms.get(i).getReservations().size(); j++) {
        sum += rooms.get(i).getReservations().get(j).getTotalPrice(); // adds all the reservations totalprice to sum
      }
    }
    return sum;
  }

  /**
   * Displays information about the hotel in two levels, then prints outputs
   * depending on the user's choices.
   * 
   * @param scan the scanner passed from main
   */
  public void viewHotel(Scanner scan) {
    int countavail = 0;//counter for available rooms
    int countbooked = 0;//counter for booked rooms
    int i, j, lowchoice;
    int flag = 0;//flag for error messages
    System.out.println("\n<<1. High-level info>>");
    System.out.println("<<2. Low-level info>>");
    System.out.print("Which level of info would you like to view?: ");
    int choice = scan.nextInt();
    scan.nextLine();

    while (choice != 1 && choice != 2) // for invalid choices
    {
      System.out.println("\nInvalid input");
      System.out.println("<<1. High-level info>>");
      System.out.println("<<2. Low-level info>>");
      System.out.print("Which level of info would you like to view?: ");
      choice = scan.nextInt();
      scan.nextLine();

    }
    if (choice == 1) // High-level info choice
      System.out.println("\nName: " + this.name + "\nNumber of rooms: " + this.rooms.size() + "\nTotal revenue: " + sumTotal());

    else if (choice == 2) // low-level info choice
    {
      System.out.println("<<1. Total number of booked & available rooms for a selected date>>");
      System.out.println("<<2. Information about the selected room>>");
      System.out.println("<<3. Information about a selected reservation>>\n");
      System.out.print("Information choice: ");
      lowchoice = scan.nextInt();
      scan.nextLine();

      while (lowchoice > 3 || lowchoice < 1) // for invalid choices
      {
        System.out.println("Invalid input, Please try again");
        System.out.println("<<1. Total number of booked & available rooms for a selected date>>");
        System.out.println("<<2. Information about the selected room>>");
        System.out.println("<<3. Information about a selected reservation>>");
        System.out.print("Information choice: ");
        lowchoice = scan.nextInt();
        scan.nextLine();
      }
      if (lowchoice == 1) // low-info for total no. of booked & avail rooms for a selected date
      {

        System.out.print("Enter date: ");
        int date = scan.nextInt();
        scan.nextLine();

        for (i = 0; i < rooms.size(); i++) {
          if (rooms.get(i).getAvailability(date)) // counts all true values of availability
            countavail++;
          else countbooked++;
        }
        System.out.println(date + "\nAvailable rooms: " + countavail + "\nBooked rooms: " + countbooked);
      }

      else if (lowchoice == 2) // low-info for Info about selected room
      {
        System.out.println("\nRooms:");
        for (i = 0; i < rooms.size(); i++) {
          System.out.println("Room ID: " + rooms.get(i).getID()); // prints all room id
        }

        System.out.print("Enter room ID: ");
        int roomID = scan.nextInt();
        scan.nextLine();

        for (i = 0; i < rooms.size(); i++) {
          if (rooms.get(i).getID() == roomID) // finds matching id as input
          {
            System.out.println("\nRoom ID: " + rooms.get(i).getID() + "\nPrice: " + rooms.get(i).getPrice()
                + "\nNumber of reservations: " + rooms.get(i).getReservations().size()); // displays room information
            flag++;
          }
        }
        if (flag == 0)// if room is not found
        {
          System.out.println(">>        Room cannot be found.       <<");
        }
      } else if (lowchoice == 3) // low-info for info about selected reservation
      {
        boolean hasReservations = false;
        for (i = 0; i< rooms.size(); i++){
            if (!rooms.get(i).getReservations().isEmpty()){//check every room and if one is not empty, there are reservations
                hasReservations = true;
                break;
            }
        }
        if (!hasReservations){
            System.out.println(">>      No reservations found.      <<");
            return;//return if there are no reservations
        } 

        System.out.println("\nReservations:"); // prints all reservation guest name
        for (i = 0; i < rooms.size(); i++) {
            for (j = 0; j < rooms.get(i).getReservations().size(); j++)
            System.out.println("Reservation by: \"" + rooms.get(i).getReservations().get(j).getGuestName() + "\"");
        }
        System.out.print("Enter a guest name to view more information: ");// asks for guest name
        String guestName = scan.nextLine();

        flag = 0;
        for (i = 0; i < rooms.size(); i++) {
            for (j = 0; j < rooms.get(i).getReservations().size(); j++) {
                if (rooms.get(i).getReservations().get(j).getGuestName().equals(guestName)) // finds matching guest name from room reservations
                {
                    // displays reservation information
                    System.out.print("Guest name: " + rooms.get(i).getReservations().get(j).getGuestName() + "\nRoom ID: "
                        + rooms.get(i).getID() + "\nCheck-in: " + rooms.get(i).getReservations().get(j).getCheckin()
                        + "\nCheck-out: " + rooms.get(i).getReservations().get(j).getCheckout() + "\nPrice per day: "
                        + rooms.get(i).getPrice() + "\nTotal price: "
                        + rooms.get(i).getReservations().get(j).getTotalPrice());
                    flag++;
                }
            }   
        }
        if (flag == 0){
            System.out.println(">>      Reservation cannot be found.        <<");
        }

      }
    }
  }

  /**
   * Manages the hotel with various options such as changing the name,
   * adding/removing rooms, updating prices, and removing reservations.
   * 
   * @param hotel for checking other hotel names and determining if name is unique
   * @param scan the scanner passed from main
   */
  public void Manage(ArrayList<Hotel> hotel, Scanner scan) {
    boolean isUniqueName = false;//used for loop
    int i;
    String rename = "";
    System.out.println("<<1. Change Hotel name>>");
    System.out.println("<<2. Add Rooms>>");
    System.out.println("<<3. Remove Rooms>>");
    System.out.println("<<4. Update base price of rooms>>");
    System.out.println("<<5. Remove Reservation>>");
    System.out.println("<<6. Remove hotel>>");
    System.out.println("<<7. Back to Menu>>");
    System.out.print("Please select an option: ");

    int choice = scan.nextInt();
    scan.nextLine();

    switch (choice) {
      case 1:
        System.out.print("Enter new hotel name: ");

        // Loop until a unique hotel name is provided
        while (!isUniqueName) {
          rename = scan.nextLine();

          isUniqueName = true;
          for (i = 0; i < hotel.size(); i++) {
            if (hotel.get(i).getName().equals(rename)) {
              System.out.println(">>      Hotel name must be unique. Try again.       <<");
              System.out.print("Enter new hotel name:");
              isUniqueName = false;
              break;
            }
          }
        }

        setName(rename);
        System.out.println("Hotel name changed to " + rename + " successfully.");
        break;
      case 2:
        int newRoomID = 0;
        if (rooms.size() < 50) {
          while (newRoomID < 100) {
            System.out.print("Enter new room ID: ");
            newRoomID = scan.nextInt();
            if (newRoomID < 100 || newRoomID > 199) {
              System.out.println(">>        Please enter an ID fit to our standard (100, 101, 102 ... 199)      <<");
            } else {
              scan.nextLine();
              addRoom(newRoomID, scan);
            }

          }

        } else {
          System.out.println("\n>>      Maximum number of rooms reached. Remove a room to continue.         <<\n");
        }
        break;
      case 3:
        if (rooms.size() > 1)
          removeRoom(scan);
        else
          System.out.println(">>        There is 0 rooms left to remove. Please add more rooms before removing.     <<");
        break;
      case 4:
        updatePrice(scan);
        break;
      case 5:
        removeReservation(scan);
        break;
      case 6:
        System.out.print("Are you sure you want to remove hotel \"" + getName() + "\" ? (Y/N): ");
        char confirm = scan.next().charAt(0);
        if (confirm == 'Y' || confirm == 'y') {
          hotel.remove(this);
          System.out.println("Hotel removed successfully.");
        } else {
          System.out.println("Discarding modification.");
        }
        break;
      case 7:
        return;
      default:
        System.out.println(">>       Invalid choice, please try again.      <<");
        break;
    }
  }

  /**
   * Removes a room with a specified ID from the hotel.
   *
   * @param scan the scanner passed from main
   */
  public void removeRoom(Scanner scan) {
    if (rooms.isEmpty()){
        System.out.println(">>      No rooms available to remove.       <<");
        return;
    }


    int roomindex;
    int column = 5;
    ArrayList<Integer> removable = new ArrayList<Integer>();
    for (int i = 0; i < rooms.size(); i++) {
      if (rooms.get(i).getReservations().isEmpty())
        removable.add(rooms.get(i).getID());//store all IDs into the removable arraylist, which can then be used to make prettier
    }

    if (removable.isEmpty()) {
      System.out.println(">>      No rooms without reservations available to remove.       <<");
      return;
    }

    System.out.println("\nRoom Selector: \n");
    for (int i = 0; i < removable.size(); i++) {
        System.out.printf("%-10d", removable.get(i));//print with spacing using printf
        if ((i + 1) % column == 0 || i == removable.size() - 1) {//if i is a multiple of the columns(set to 5) or the last index
            System.out.println();//print new line
        }
    }

    System.out.print("\n\nPlease select a room ID to remove: ");
    int room = scan.nextInt();
    scan.nextLine();

    roomindex = searchRoom(room);

    while (roomindex == -1){
        System.out.print("Room not found. Please enter another room ID:");
        room = scan.nextInt();
        scan.nextLine();
        roomindex = searchRoom(room);
    }

    if (rooms.get(roomindex).getReservations().size() > 0)// for room with reservations
    {
      System.out.println(">>        Room selected has active reservations, please remove them first     <<");
    } else// for actual room removal
    {
      System.out.print("Are you sure you want to remove room " + rooms.get(roomindex).getID() + "? (Y/N): ");
      char choice = scan.next().charAt(0);
      if (choice == 'y' || choice == 'Y') {
        rooms.remove(roomindex);
        System.out.println("Room removed successfully.");
      } else
        System.out.println("Discarding Modification");

    }

  }

  /**
   * Method for updating base price of a room. can only work if there are no
   * reservations in the hotel.
   *
   * @param scan the scanner passed from main
   */

  public void updatePrice(Scanner scan) {
    int i;
    double price = 0;

    boolean hasReservations = false;
    for (i = 0; i< rooms.size(); i++){
        if (!rooms.get(i).getReservations().isEmpty()){//check every room and if one is not empty, there are reservations
            hasReservations = true;
            break;
        }
    }
    if (hasReservations){
        System.out.println(">>      There are still reservations in the hotel, please remove them first.        <<");
        return;
    } 


    System.out.println("Current Price: " + rooms.get(0).getPrice());
    System.out.print("Insert new room price(This will apply to all rooms!): ");
    price = scan.nextDouble();
    scan.nextLine();

    while (price < 100){
        System.out.println("\nPlease input a price greater than or equal to 100.");
        System.out.println("Current Price: " + rooms.get(0).getPrice());
        System.out.print("Insert new room price: ");
        price = scan.nextDouble();
        scan.nextLine();
    }

    System.out.print("Are you sure you want to change the price to " + price + "? (Y/N) ");
    char choice = scan.next().charAt(0);
    if (choice == 'y' || choice == 'Y') {
        for (i = 0; i < rooms.size(); i++) {
        rooms.get(i).setPrice(price);
        }
        System.out.println("Price updated successfully.");
    } else
        System.out.println("Discarding Modification.");
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

  /**
   * Removes a reservation with a specified guest name.
   *
   * @param scan the scanner passed from main
   */
  public void removeReservation(Scanner scan) {
    int size;
    int roomName;
    String guestName;
    int roomIndex;
    int reservationIndex;

    System.out.println("Reservations List:");
    for (int i = 0; i < rooms.size(); i++)// displays room id
    {
      size = rooms.get(i).getReservations().size();
      System.out.println(rooms.get(i).getID() + ":");
      for (int j = 0; j < size; j++)// displays reservation details of room
      {
        System.out.println(rooms.get(i).getReservations().get(j).getGuestName() + ": "
            + rooms.get(i).getReservations().get(j).getCheckin() + " - "
            + rooms.get(i).getReservations().get(j).getCheckout());
      }
    }


    System.out.print("Please enter a room name to remove a reservation from: ");
    roomName = scan.nextInt();
    scan.nextLine(); // consume new line

    roomIndex = searchRoom(roomName);
    while (roomIndex == -1) // for invalid inputs
    {
      System.out.println(">>        Invalid room name       <<");
      System.out.print("Please enter a room name to remove a reservation from: ");
      roomName = scan.nextInt();
      scan.nextLine(); // consume new line
      roomIndex = searchRoom(roomName);
    }

    System.out.print("Please enter a guest name to remove a reservation from: ");
    guestName = scan.nextLine();
    reservationIndex = searchGuest(roomIndex, guestName);
    while (reservationIndex == -1) {
      System.out.println(">>        Invalid guest name      <<");
      System.out.print("Please enter a guest name to remove a reservation from: ");
      guestName = scan.nextLine();
      roomIndex = searchRoom(roomName);
    }
    System.out.print("Are you sure you want to remove this reservation? (Y/N): ");
    char choice = scan.next().charAt(0);
    if (choice == 'Y' || choice == 'y') {

      rooms.get(roomIndex).getReservations().remove(reservationIndex);
      System.out.println("Reservation removed successfully");
    } else
      System.out.println("Modification discarded");
  }

  /**
   * Method for adding a name to the hotel.
   *
   * @param name the name of the hotel
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Simulates booking for the specified Hotel.
   *
   * @param scan the scanner passed from main
   */
  public void simulateBooking(Scanner scan) {
    int checkin = 0;
    int checkout = 0;
    System.out.print("Enter Check-in date (1-30):");
    checkin = scan.nextInt();
    scan.nextLine(); // Consume newline
    System.out.print("Enter Check-out date:");
    checkout = scan.nextInt();
    scan.nextLine(); // Consume newline

    while (checkin < 1 || checkin > 31 || checkout < 1 || checkout > 31 || checkin >= checkout) {
      System.out.println(">>        Invalid dates. Please ensure dates are within 1-31 and check-in is before check-out.        <<");
      System.out.print("Enter Check-in date:");
      checkin = scan.nextInt();
      scan.nextLine(); // Consume newline
      System.out.print("Enter Check-out date:");
      checkout = scan.nextInt();
      scan.nextLine(); // Consume newline
    }

    // Choose selection mechanism
    System.out.println("Select room selection mechanism:");
    System.out.println("(1.) Automated");
    System.out.println("(2.) Manual");
    System.out.print("Your Choice: ");
    int mechanismChoice = scan.nextInt();
    scan.nextLine(); // Consume newline

    while (mechanismChoice != 1 && mechanismChoice != 2) {
      System.out.println("\nInvalid input (choose the number on the side). please try again");
      System.out.println("(1.) Automated");
      System.out.println("(2.) Manual");
      System.out.print("Insert your choice: ");
      mechanismChoice = scan.nextInt();
      scan.nextLine();
    }

    Room availableRoom = null; // Initialize to null, will later store a reference to an available room

    if (mechanismChoice == 1) {
      // Automated mechanism
      System.out.println("Automated mechanism selected...");
      availableRoom = selectAutomatically(checkin, checkout);

      if (availableRoom != null) {
        System.out.println("Booked room " + availableRoom.getID() + " successfully!");
      } else {
        System.out.println(">>      No rooms available for the selected dates.      <<");
      }

    } else if (mechanismChoice == 2) {
      // Manual mechanism
      System.out.println("Manual mechanism selected...");
      availableRoom = selectManually(checkin, checkout, scan);

      if (availableRoom != null) {
        System.out.println("Selected room " + availableRoom.getID() + " successfully!");
      } else {
        System.out.println(">>      No rooms available for the selected dates.      <<");
      }
    } else {
      System.out.println(">>        Invalid selection mechanism choice.     <<");
    }

    if (availableRoom != null) {
      System.out.print("Enter guest name: ");
      String guestName = scan.nextLine();
      availableRoom.addReservation(guestName, checkin, checkout);
      int numNights = checkout - checkin;
      double price = numNights * availableRoom.getPrice();
      
      System.out.println("\nBooking successful for guest " + guestName + " in room " + availableRoom.getID());
      System.out.printf("Total price after booking %d nights: $%.2f\n", numNights, price);

    }
  }

  /**
   * Method for finding an available room for a given check-in and check-out
   * dates.
   *
   * @param checkin  the check-in date
   * @param checkout the check-out date
   * @return the available room, or null if no rooms are available
   */
  private Room selectAutomatically(int checkin, int checkout) {
    for (int i = 0; i < rooms.size(); i++) {
      boolean available = true;
      for (int day = checkin - 1; day < checkout - 1; day++) {
        if (!rooms.get(i).getAvailability(day)) {
          available = false;
          break;
        }
      }
      if (available) {
        return rooms.get(i);
      }
    }
    return null;
  }

  /**
   * Method for selecting an available room manually.
   *
   * @param checkin  the check-in date
   * @param checkout the check-out date
   * @param scan the scanner passed from main
   * @return the selected room, or null if no rooms are available
   */
  private Room selectManually(int checkin, int checkout, Scanner scan) {
    ArrayList<Room> availableRooms = new ArrayList<Room>();
    for (int i = 0; i < rooms.size(); i++) {
      boolean available = true;
      for (int day = checkin - 1; day < checkout - 1; day++) {
        if (!rooms.get(i).getAvailability(day)) {
          available = false;
          break;
        }
      }
      if (available) {
        availableRooms.add(rooms.get(i));
        System.out.println("(" + (availableRooms.size()) + ".) Room " + rooms.get(i).getID());
      }
    }

    if (availableRooms.size() > 0) {
      boolean validChoice = false;
      while (!validChoice) {
        System.out.print("Select a room(number on the left side): ");
        int roomChoice = scan.nextInt();
        scan.nextLine(); // Consume newline

        if (roomChoice > 0 && roomChoice <= availableRooms.size()) {
          return availableRooms.get(roomChoice - 1);
        } else {
          System.out.println("Invalid choice, try again.");
        }
      }
    }
    return null;
  }

}
