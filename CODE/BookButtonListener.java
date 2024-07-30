import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

public class BookButtonListener extends BaseButtonListener {
    public BookButtonListener(ArrayList<Hotel> hotels, HotelView view){
        super(hotels,view);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Hotel selectedHotel = selectHotel(hotels, view, "Simulate Hotel Booking", "Select a hotel to simulate:");
        if (selectedHotel != null) {
            simulateBooking(selectedHotel);
        }
    }

    public void simulateBooking(Hotel hotel) {//TODO:implement breakdown display as shown in the specs,like a receipt
        int checkin = 0;
        int checkout = 0;
        double price;
        int numNights = 0;
        double percentage = 0;
        StringBuilder breakdown = new StringBuilder("Breakdown:\n");
    
        checkin = getIntInput("Enter Check-in date (1-30):", 1, 30);
        checkout = getIntInput("Enter Check-out date(" + (checkin+1) + "-30):", checkin + 1, 31);

        int mechanismChoice = getIntInput("Select room selection mechanism:\n(1.) Automated\n(2.) Manual\nYour Choice:", 1, 2);
    
        int roomType = getIntInput("Which tier of room would you like to book? (1-3):\n1. Standard Room\n2. Executive Room\n3. Deluxe Room", 1, 3);//TODO:make this check if there are executive/deluxe rooms available
    
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

    
            for (int i = checkin; i < checkout; i++) {//changed so no need for firstdate
                percentage += hotel.getDatePrice().get(i - 1);//get the dateprices for all days in between
                breakdown.append(i).append("th day -> ").append(hotel.getDatePrice().get(i - 1) * 100).append("%\n");
            }
            
            char discountChoice = getCharInput("Do you have a discount code? (Y/N):");
    
            if (discountChoice == 'Y' || discountChoice == 'y') {
                String code = JOptionPane.showInputDialog("Enter discount code(CASE SENSITIVE):");
                price = applyDiscount(hotel, code, availableRoom, percentage, numNights, checkin, checkout);
            } else if (discountChoice == 'N' || discountChoice == 'n'){
                view.setDisplayText("Proceeding with normal pricing...");
                price = percentage * availableRoom.getPrice();
            } else {
                view.setDisplayText("Invalid choice. Proceeding with normal pricing.");
                price = percentage * availableRoom.getPrice();
            }
    
            availableRoom.addReservation(guestName, checkin, checkout);
            view.setDisplayText("Booking successful for guest " + guestName + " in room " + availableRoom.getID() + "\n" + breakdown.toString() + "\nTotal price after booking " + numNights + " nights: $" + price);
            availableRoom.addTotalPrice(price);
        }
      }

    public Room selectAutomatically(Hotel hotel, int checkin, int checkout, int type) {
            for (Room room : hotel.getRooms()) {
                if (room.getAvailabilityRange(checkin, checkout) && room.getType() == type) {
                    return room;
                }
            }
        
        return null;
    }

    public Room selectManually(Hotel hotel, int checkin, int checkout, int type) {//TODO:go over this
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

    

private double applyDiscount(Hotel hotel, String code, Room room, double percentage, int numNights, int checkin, int checkout) {
    double price;
    switch (code) {
        case "I_WORK_HERE":
            price = room.getPrice() * percentage * 0.9;
            view.setDisplayText("Applying 10% discount...");
            break;
        case "STAY4_GET1":
            if (numNights > 4) {
                price = room.getPrice() * (percentage - hotel.getDatePrice().get(checkin - 1));
                view.setDisplayText("Removing price for first day...");
            } else {
                price = room.getPrice() * percentage;
            }
            break;
        case "PAYDAY"://check if within range instead of just checking availability
            if ((checkin <= 15 && checkout > 15) || (checkin <= 30 && checkout > 30)) {
                price = room.getPrice() * percentage * 0.93;
                view.setDisplayText("Applying 7% discount...");
            } else {
                view.setDisplayText("Day 15 or 30 should be included as a check-in time. Default price will be applied with no discounts.");
                price = room.getPrice() * percentage;
            }
            break;
        default://TODO: maybe make this ask for them to type the code again instead of just using default price
            view.setDisplayText("Invalid discount code detected. Default price will be applied with no discounts");
            price = room.getPrice() * percentage;
            break;
    }
    return price;
}

}






  
