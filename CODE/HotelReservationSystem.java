import java.util.Scanner;
import java.util.ArrayList;

/**
 * Hotel Reservation System class that handles main and contains the createHotel and getHotel methods
 */
public class HotelReservationSystem {
  /**
  *ArrayList to hold all of the hotels
  */
  private static ArrayList<Hotel> hotels = new ArrayList<Hotel>();
  /**
  *Scanner that will be used throughout the program
  */
  private static Scanner scan = new Scanner(System.in);
  /**
   * Main class for the Hotel Reservation System, mainly acts as a menu.
   * 
   * @param args command line arguments
   */
  public static void main(String[] args) {
    int hotelInd = 0;//checks if hotel index is valid
    int loop = 1;//loop to stop when exit hotel is chosen


    while (loop == 1){
      System.out.println("\n\nWelcome to the Hotel Reservation System!");
      System.out.println("Please select an option:");
      System.out.println("1. Create a new hotel");
      System.out.println("2. View Hotel Information");
      System.out.println("3. Manage your hotel");
      System.out.println("4. Simulate Booking");
      System.out.println("5. Exit");
      System.out.print("Your choice: ");

      int choice = scan.nextInt();
      scan.nextLine();

      switch (choice){
        case 1: 
          System.out.println("Creating Hotel...");
          createHotel(scan);//pass scanner into the methods
          break;

        case 2://viewHotel
          System.out.println("View Hotel selected...");
          hotelInd = getHotel(scan);
          if (hotelInd != -1)
            hotels.get(hotelInd).viewHotel(scan);

          break;

        case 3://manageHotel
          System.out.println("Manage Hotel selected...");
          hotelInd = getHotel(scan);
          if (hotelInd != -1)
            hotels.get(hotelInd).Manage(hotels, scan);
          break;

        case 4://simulateBooking
          System.out.println("Simulate Booking selected...");
          hotelInd = getHotel(scan);
          if (hotelInd != -1)
            hotels.get(hotelInd).simulateBooking(scan);
          break;

        case 5:
          System.out.println("Thank you for using the hotel reservation system!");
          System.out.println("Exiting Hotel Reservation System...");
          scan.close();//finally close scanner when program finishes
          loop = 0;
          break;

        default: System.out.println(">>    invalid choice, please try again.    <<");
      }

    }
  }
/**
*Creates a new Hotel
*
*@param scan the scanner passed from main
**/
public static void createHotel(Scanner scan) {

    String name = " ";//placeholder
    boolean isUniqueName = false;//set as false at first to set up a while loop
    int i = 0;//for the for loop
    Hotel hotel;//placeholder

    System.out.print("Enter hotel name: ");

    // Loop until a unique hotel name is provided
    while (!isUniqueName) {
        name = scan.nextLine();

        isUniqueName = true;
        for (i = 0;i<hotels.size();i++) {
            if (hotels.get(i).getName().equals(name)) {
                System.out.println("Hotel name must be unique. Try again.");
                System.out.print("Enter hotel name: ");
                isUniqueName = false;
                break;
            }
        }
    }

    System.out.println("Enter number of rooms (1-50):");
    int numRooms = scan.nextInt();
    scan.nextLine(); // consume newline

    // Validate the number of rooms
    while (numRooms < 1 || numRooms > 50) {
        System.out.println("Invalid number of rooms. Try again (1-50):");
        numRooms = scan.nextInt();
        scan.nextLine(); // consume newline
    }
  System.out.println("Do you wish to add a custom price for the rooms? (Y/N)");
  char choice = scan.next().charAt(0);
  scan.nextLine();

  while(choice != 'Y' && choice != 'N' && choice != 'y' && choice != 'n'){
    System.out.println("Invalid choice. Please enter Y or N: ");
    choice = scan.next().charAt(0);
    scan.nextLine();

  }

  switch (choice){
    case 'Y':
    case 'y':
      System.out.println("Enter the custom price for each room:");
      double customPrice = scan.nextDouble();
      scan.nextLine(); 

      //check if custom price is valid
      while (customPrice < 100){
        System.out.println("Invalid custom price. please enter a price greater than or equal to 100: ");
        customPrice = scan.nextDouble();
        scan.nextLine(); 
      }

      hotel = new Hotel(name, numRooms, customPrice);
      hotels.add(hotel);

      System.out.println("\nHotel named \"" + name + "\" created successfully with " + numRooms + " rooms.");
      System.out.println("Custom Price of " + customPrice + " added to each room.\n");

      break;
    case 'N':
    case 'n':
      System.out.println("Default price selected.");
      hotel = new Hotel(name, numRooms);
      hotels.add(hotel);

      System.out.println("\nHotel named \"" + name + "\" created successfully with " + numRooms + " rooms.");
      System.out.println("Default Price of " + 1299.0 + " added to each room.\n");

      break;

    default: System.out.println(">>    Invalid choice. please try again.    <<");
    }


}

  /**
   * Gets the index of a hotel by name.
   *
   * @param scan the scanner passed from main
   * @return the index of the hotel, or -1 if no hotels exist
   */
  public static int getHotel(Scanner scan) {
      // Check if there are any hotels
      if (hotels.isEmpty()) {
          System.out.println();
          System.out.println("==================================================================");
          System.out.println("||>>>>      You haven't created any hotels yet!             <<<<||");
          System.out.println("||>>>>      Please create a hotel before proceeding.        <<<<||");
          System.out.println("==================================================================\n\n");


          return -1; // Return -1 indicating no hotels available
      }

      // Display the list of hotel names
      System.out.println("Please select a hotel from the following list:");
      for (int i = 0; i< hotels.size(); i++) {
          System.out.println(hotels.get(i).getName());
      }

      while (true) {
          System.out.print("Enter hotel name:");
          String choice = scan.nextLine();

          // Find the hotel with the given name
          for (int i = 0; i < hotels.size(); i++) {
              if (choice.equals(hotels.get(i).getName())) {
                  return i;
              }
          }

          // If no match is found, prompt the user again
          System.out.println(">>    Invalid hotel name. Please try again.    <<");
      }
  }




}