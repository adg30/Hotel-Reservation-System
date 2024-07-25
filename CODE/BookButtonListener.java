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

    public void simulateBooking(Hotel hotel) {
        int checkin = 0;
        int checkout = 0;
        double price;
        int numNights = 0;
        double percentage = 0;
        int firstdate = 0;
    
        checkin = getIntInput("Enter Check-in date (1-30):", 1, 31);
        checkout = getIntInput("Enter Check-out date:", checkin + 1, 31);
    
        int mechanismChoice = getIntInput("Select room selection mechanism:\n(1.) Automated\n(2.) Manual\nYour Choice:", 1, 2);
    
        int roomType = getIntInput("Which tier of room would you like to book? (1-3):\n1. Standard Room\n2. Executive Room\n3. Deluxe Room", 1, 3);
    
        Room availableRoom = null;
    
        if (mechanismChoice == 1) {
            // Automated mechanism
            view.setDisplayText("Automated mechanism selected...");
            availableRoom = selectAutomatically(checkin, checkout, roomType);
    
            if (availableRoom != null) {
                view.setDisplayText("Booked room " + availableRoom.getID() + " successfully!");
            } else {
                view.setDisplayText("No rooms available for the selected dates.");
            }
    
        } else if (mechanismChoice == 2) {
            // Manual mechanism
            view.setDisplayText("Manual mechanism selected...");
            availableRoom = selectManually(checkin, checkout, roomType);
    
            if (availableRoom != null) {
                view.setDisplayText("Selected room " + availableRoom.getID() + " successfully!");
            } else {
                view.setDisplayText("No rooms available for the selected dates.");
            }
        }
    
        if (availableRoom != null) {
            String guestName = JOptionPane.showInputDialog("Enter guest name:");
            numNights = checkout - checkin;
            firstdate = checkin;
    
            while (checkin < checkout) {
                percentage += hotel.getDatePrice().get(checkin - 1);
                checkin++;
            }
    
            char discountChoice = getCharInput("Do you have a discount code? (Y/N):");
    
            if (discountChoice == 'Y' || discountChoice == 'y') {
                String code = JOptionPane.showInputDialog("Enter discount code:");
                price = applyDiscount(hotel, code, availableRoom, percentage, numNights, firstdate);
            } else {
                price = percentage * availableRoom.getPrice();
            }
    
            availableRoom.addReservation(guestName, checkin, checkout);
            view.setDisplayText("Booking successful for guest " + guestName + " in room " + availableRoom.getID() + "\nTotal price after booking " + numNights + " nights: $" + price);
            availableRoom.addTotalPrice(price);
        }
      }
    
      private Room selectAutomatically(int checkin, int checkout, int type) {
          for (Hotel hotel : hotels) {
              for (Room room : hotel.getRooms()) {
                  if (room.getAvailabilityRange(checkin, checkout) && room.getType() == type) {
                      return room;
                  }
              }
          }
          return null;
      }
    
      private Room selectManually(int checkin, int checkout, int type) {
          StringBuilder availableRoomsStr = new StringBuilder("Available rooms:\n");
          int roomCount = 0;
    
          for (Hotel hotel : hotels) {
              for (Room room : hotel.getRooms()) {
                  if (room.getAvailabilityRange(checkin, checkout) && room.getType() == type) {
                      roomCount++;
                      availableRoomsStr.append(roomCount).append(". Room ").append(room.getID()).append("\n");
                  }
              }
          }
    
          if (roomCount > 0) {
              int roomChoice = getIntInput(availableRoomsStr.toString() + "Select a room (number on the left side):", 1, roomCount);
              return hotels.get(0).getRooms().get(roomChoice - 1); // Simplified for the example, should map correctly to chosen room
          } else {
              return null;
          }
      }
    
      private int getIntInput(String message, int min, int max) {
          int input = 0;
          do {
              try {
                  String strInput = JOptionPane.showInputDialog(message);
                  input = Integer.parseInt(strInput);
              } catch (NumberFormatException e) {
                  view.setDisplayText("Invalid input. Please enter a number.");
              }
          } while (input < min || input > max);
          return input;
      }
    
      private char getCharInput(String message) {
          char input;
          String strInput;
          do {
              strInput = JOptionPane.showInputDialog(message);
              if (strInput == null || strInput.length() != 1) {
                  view.setDisplayText("Invalid input. Please enter a single character.");
                  input = 0;
              } else {
                  input = strInput.charAt(0);
              }
          } while (input == 0);
          return input;
      }
    
      private double getDoubleInput(String message, double min, double max) {
        double input = 0;
        do {
            try {
                String strInput = JOptionPane.showInputDialog(message);
                input = Double.parseDouble(strInput);
            } catch (NumberFormatException e) {
                view.setDisplayText("Invalid input. Please enter a valid percentage.");
            }
        } while (input < min || input > max);
        return input;
      }
    
      private double applyDiscount(Hotel hotel, String code, Room room, double percentage, int numNights, int firstdate) {
          double price;
          switch (code) {
              case "I_WORK_HERE":
                  price = room.getPrice() * percentage * 0.9;
                  view.setDisplayText("Applying 10% discount...");
                  break;
              case "STAY4_GET1":
                  if (numNights > 4) {
                      price = room.getPrice() * (percentage - hotel.getDatePrice().get(firstdate - 1));
                      view.setDisplayText("Removing price for first day...");
                  } else {
                      price = room.getPrice() * percentage;
                  }
                  break;
              case "PAYDAY":
                  if (room.getAvailability(15) || room.getAvailability(30)) {
                      price = room.getPrice() * percentage * 0.93;
                      view.setDisplayText("Applying 6% discount...");
                  } else {
                      view.setDisplayText("Day 15 or 30 should be included as a check-in time. Default price will be applied with no discounts.");
                      price = room.getPrice() * percentage;
                  }
                  break;
              default:
                  price = room.getPrice() * percentage;
                  break;
          }
          return price;
      }

  }






  
