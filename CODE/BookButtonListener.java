import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
/**
 * Listener class for the book button that handles hotel booking simulation.
 */
public class BookButtonListener extends BaseButtonListener {
    /**
     * Constructor to initialize hotels and view.
     * 
     * @param hotels The list of hotels.
     * @param view   The HotelView instance.
     */
    public BookButtonListener(ArrayList<Hotel> hotels, HotelView view){
        super(hotels,view);
    }

    /**
     * Handles the action performed when the book button is clicked.
     * 
     * @param e The ActionEvent triggered by the button click.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Hotel selectedHotel = selectHotel(hotels, view, "Simulate Hotel Booking", "Select a hotel to simulate:");
        if (selectedHotel != null) {
            simulateBooking(selectedHotel);
        }
    }

    /**
     * Simulates the hotel booking process.
     * 
     * @param hotel The selected hotel.
     */
    public void simulateBooking(Hotel hotel) {
        int checkin = 0;
        int checkout = 0;
        double price;
        int numNights = 0;
        double percentage = 0;
        StringBuilder breakdown = new StringBuilder("Breakdown:\n");
    
        checkin = getIntInput("Enter Check-in date (1-30):", 1, 30);
        checkout = getIntInput("Enter Check-out date(" + (checkin+1) + "-30):", checkin + 1, 31);

        int mechanismChoice = getIntInput("Select room selection mechanism:\n(1.) Automated\n(2.) Manual\nYour Choice:", 1, 2);
    
        int roomType = getIntInput("Which tier of room would you like to book? (1-3):\n1. Standard Room\n2. Executive Room\n3. Deluxe Room", 1, 3);
    
        Room availableRoom = null;
    
        if (mechanismChoice == 1) {
            // Automated mechanism
            view.setDisplayText("Automated mechanism selected...");
            availableRoom = selectAutomatically(hotel, checkin, checkout, roomType);
    
            if (availableRoom != null) {
                view.setDisplayText("Booked room " + availableRoom.getID() + " successfully!");
            } else {
                view.setDisplayText("No rooms available for the selected dates.");
            }
    
        } else if (mechanismChoice == 2) {
            // Manual mechanism
            view.setDisplayText("Manual mechanism selected...");
            availableRoom = selectManually(hotel, checkin, checkout, roomType);
    
            if (availableRoom != null) {
                view.setDisplayText("Selected room " + availableRoom.getID() + " successfully!");
            } else {
                view.setDisplayText("No rooms available for the selected dates.");
            }
        }

        if (availableRoom != null) {
            String guestName = JOptionPane.showInputDialog("Enter guest name:");
            numNights = checkout - checkin;

            //calculate total percentage based on date prices
            for (int i = checkin; i < checkout; i++) {
                percentage += hotel.getDatePrice().get(i - 1);//get the dateprices for all days in between
                breakdown.append(i).append("th day -> ").append(hotel.getDatePrice().get(i - 1) * 100).append("%\n");
            }

            //check for discount code
            String discountCode = "";
            char discountChoice = getCharInput("Do you have a discount code? (Y/N):");
            
            if (discountChoice == 'Y' || discountChoice == 'y') {
                discountCode = JOptionPane.showInputDialog("Enter discount code(CASE SENSITIVE):");
                price = applyDiscount(hotel, discountCode, availableRoom, percentage, numNights, checkin, checkout);
            } else if (discountChoice == 'N' || discountChoice == 'n'){
                view.setDisplayText("Proceeding with normal pricing...");
                price = percentage * availableRoom.getPrice();
            } else {
                view.setDisplayText("Invalid choice. Proceeding with normal pricing.");
                price = percentage * availableRoom.getPrice();
            }
    
            availableRoom.addReservation(guestName, checkin, checkout, discountCode);
            view.setDisplayText("Booking successful for guest " + guestName + " in room " + availableRoom.getID() + "\n" + breakdown.toString() + "\nTotal price after booking " + numNights + " nights: $" + price);
            availableRoom.addTotalPrice(price);//DONT REMOVE
        }
      }

    /**
     * Automatically selects an available room based on the specified criteria.
     * 
     * @param hotel    The hotel instance.
     * @param checkin  The check-in date.
     * @param checkout The check-out date.
     * @param type The room type.
     * @return The selected Room instance or null if no room is available.
     */
    public Room selectAutomatically(Hotel hotel, int checkin, int checkout, int type) {
            for (Room room : hotel.getRooms()) {
                if (room.getAvailabilityRange(checkin, checkout) && room.getType() == type) {
                    return room;
                }
            }
        
        return null;
    }
    /**
     * Manually selects an available room based on the specified criteria.
     * 
     * @param hotel    The hotel instance.
     * @param checkin  The check-in date.
     * @param checkout The check-out date.
     * @param type The room type.
     * @return The selected Room instance or null if no room is available.
     */
    public Room selectManually(Hotel hotel, int checkin, int checkout, int type) {
        StringBuilder availableRoomsStr = new StringBuilder("Available rooms:\n");
        ArrayList<Room> availableRooms = new ArrayList<Room>();

        for (Room room : hotel.getRooms()) {
        if (room.getAvailabilityRange(checkin, checkout) && room.getType() == type) {
            availableRooms.add(room);
            availableRoomsStr.append(availableRooms.size()).append(". Room ").append(room.getID()).append("\n");
        }
    }

        if (!availableRooms.isEmpty()) {
            int roomChoice = getIntInput(availableRoomsStr.toString() + "Select a room (number on the left side):", 1, availableRooms.size());
            return availableRooms.get(roomChoice-1);
        } else {
            view.setDisplayText("No rooms available for the selected dates.");
            return null;
        }
    }

    
    /**
     * Applies a discount to the booking based on the provided discount code.
     * 
     * @param hotel    The hotel instance.
     * @param code     The discount code.
     * @param room     The selected room.
     * @param percentage The total percentage of date prices.
     * @param numNights The number of nights for the booking.
     * @param checkin  The check-in date.
     * @param checkout The check-out date.
     * @return The total price after applying the discount.
     */
    private double applyDiscount(Hotel hotel, String code, Room room, double percentage, int numNights, int checkin, int checkout) {
        double price = 0;
        boolean validCode = false;
        
        while (!validCode) {
            switch (code) {
                case "I_WORK_HERE":
                    price = room.getPrice() * percentage * 0.9;
                    view.setDisplayText("Applying 10% discount...");
                    validCode = true;
                    break;
                case "STAY4_GET1":
                    if (numNights > 4) {
                        price = room.getPrice() * (percentage - hotel.getDatePrice().get(checkin - 1));
                        view.setDisplayText("Removing price for first day...");
                    } else {
                        price = room.getPrice() * percentage;
                    }
                    validCode = true;
                    break;
                case "PAYDAY":
                    // Check if within range instead of just checking availability
                    if ((checkin <= 15 && checkout > 15) || (checkin <= 30 && checkout > 30)) {
                        price = room.getPrice() * percentage * 0.93;
                        view.setDisplayText("Applying 7% discount...");
                        validCode = true;
                    } else {
                        view.setDisplayText("Day 15 or 30 should be included as a check-in time. Default price will be applied with no discounts.");
                        price = room.getPrice() * percentage;
                        validCode = true;
                    }
                    break;
                default:
                    // Prompt user to re-enter the code if the discount code is invalid
                    code = JOptionPane.showInputDialog("Invalid discount code. Please enter a valid discount code (CASE SENSITIVE):");
                    if (code == null || code.isEmpty()) {
                        view.setDisplayText("Default price will be applied with no discounts.");
                        price = room.getPrice() * percentage;
                        validCode = true;
                    }
                    break;
            }
        }
        return price;
    }

}






  
