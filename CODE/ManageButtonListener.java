import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;


public class ManageButtonListener extends BaseButtonListener {
    public ManageButtonListener(ArrayList<Hotel> hotels, HotelView view){
        super(hotels,view);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        Hotel selectedHotel = selectHotel(hotels, view, "Manage Hotel", "Select a hotel to manage:");
        if (selectedHotel != null) {
            manageHotel(selectedHotel);
        }
      
    }

  

  public void manageHotel(Hotel hotel) {

    String[] options = {
            "Change Hotel Name",
            "Add Rooms",
            "Remove Rooms",
            "Update Base Price of Rooms",
            "Remove Reservation",
            "Remove Hotel",
            "Modify Date Price",
            "Back to Menu"
    };

    int choice = JOptionPane.showOptionDialog(view, "Manage Hotel: " + hotel.getName(), "Manage Hotel",
            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

    switch (choice) {
        case 0:
            changeHotelName(hotel);
            break;
        case 1:
            addRooms(hotel);
            break;
        case 2:
            removeRooms(hotel);
            break;
        case 3:
            updateBasePrice(hotel);
            break;
        case 4:
            removeReservation(hotel);
            break;
        case 5:
            removeHotel(hotel);
            break;
        case 6:
            DatePriceModify(hotel);
            break;
        case 7:
            // Back to Menu
            return;
        default:
            view.setDisplayText("Invalid choice, please try again.");
            break;
    }
  }

  private void changeHotelName(Hotel hotel) {
      boolean isUniqueName = false;
      String newName = "";
      while (!isUniqueName) {
          newName = JOptionPane.showInputDialog(view, "Enter new hotel name:");
          if (newName == null || newName.isEmpty()) {
              view.setDisplayText("Invalid input. Hotel name not updated.");
              return;
          }
          isUniqueName = true;
          for (Hotel h : hotels) {
              if (h.getName().equals(newName)) {
                  JOptionPane.showMessageDialog(view, "Hotel name must be unique. Try again.");
                  isUniqueName = false;
                  break;
              }
          }
      }
      hotel.setName(newName);
      view.setDisplayText("Hotel name changed to " + newName + " successfully.");
  }

  private void addRooms(Hotel hotel) {
      if (hotel.getRooms().size() >= 50) {
          view.setDisplayText("Maximum number of rooms reached. Remove a room to continue.");
          return;
      }

      String roomIDStr = JOptionPane.showInputDialog(view, "Enter new room ID (100-199):");
      try {
          int roomID = Integer.parseInt(roomIDStr);
          if (roomID < 100 || roomID > 199) {
              view.setDisplayText("Please enter an ID fit to our standard (100, 101, 102 ... 199).");
              return;
          }

          String[] roomTypes = {"(0)Standard Room", "(1)Deluxe Room", "(2)Executive Room"};
          int roomType = JOptionPane.showOptionDialog(view, "Select room type:", "Room Type",
                  JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, roomTypes, roomTypes[0]);

          if (roomType >= 0 && roomType < 3) {//TODO:ADD CONFIRMATION MESSAGE FOR ADDING ROOM, AND CHECKING THAT THE ROOMID IS UNIQUE
              hotel.addRoom(roomID, roomType + 1);//+1 since room types are 1,2,3
              view.setDisplayText("Room added successfully.");
          } else {
              view.setDisplayText("Invalid room type.");
          }

      } catch (NumberFormatException e) {
          view.setDisplayText("Invalid room ID.");
      }
  }

  private void removeRooms(Hotel hotel) {//TODO:SEPERATE INTO MVC STUFF
    if (hotel.getRooms().size() <= 1) {
        view.setDisplayText("There are no rooms left to remove. Please add more rooms before removing.");
        return;
    }

    ArrayList<Integer> removable = new ArrayList<>();
    for (Room room : hotel.getRooms()) {
        if (room.getReservations().isEmpty()) {
            removable.add(room.getID());
        }
    }

    if (removable.isEmpty()) {
        view.setDisplayText("No rooms without reservations available to remove.");
        return;
    }

    Object[] roomIDs = removable.toArray();//TODO: go over why it did this
    Integer roomID = (Integer) JOptionPane.showInputDialog(view, "Select a room to remove:", "Remove Room",
            JOptionPane.QUESTION_MESSAGE, null, roomIDs, roomIDs[0]);

    if (roomID != null) {
        int roomIndex = hotel.searchRoom(roomID);

        if (!hotel.getRooms().get(roomIndex).getReservations().isEmpty()) {
            view.setDisplayText("Room selected has active reservations, please remove them first.");
        } else {
            int choice = JOptionPane.showConfirmDialog(view, "Are you sure you want to remove room " + roomID + "?", "Confirm Remove",
                    JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                hotel.getRooms().remove(roomID);//ignore squiggly since id == room name
                view.setDisplayText("Room removed successfully.");
            } else {
                view.setDisplayText("Modification discarded.");
            }
        }
    }
  }

  private void updateBasePrice(Hotel hotel) {
    double price = 0;
    boolean hasReservations = false;
    for (Room room : hotel.getRooms()) {//maybe turn this into a seperate method?
        if (!room.getReservations().isEmpty()) {
            hasReservations = true;
            break;
        }
    }
    if (hasReservations) {
        view.setDisplayText("There are still reservations in the hotel, please remove them first.");
        return;
    }

    String priceStr = JOptionPane.showInputDialog(view, "Enter new base price:");
    try {
        price = Double.parseDouble(priceStr);
        if (price > 100) {
            int confirm = JOptionPane.showConfirmDialog(view, "Are you sure you want to change the price to " + price + "?", "Confirm Price Change", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                String result = hotel.updatePrice(price);
                view.setDisplayText(result);
            } else {
                view.setDisplayText("Modification Discarded.");
            }
        } else if(price < 100) {
            view.setDisplayText("Input must be greater than or equal to 100. Base price not updated.");
        } else {
            view.setDisplayText("Invalid input. Base price not updated.");
        }
    } catch (NumberFormatException e) {
        view.setDisplayText("Invalid input. Base price not updated.");
    }
  }

  private void removeReservation(Hotel hotel) {
    String roomNameStr = JOptionPane.showInputDialog(view, "Enter room name:");
    try {
        int roomName = Integer.parseInt(roomNameStr);
        String guestName = JOptionPane.showInputDialog(view, "Enter guest name:");
        int choice = JOptionPane.showConfirmDialog(view, "Are you sure you want to remove this reservation?", "Confirm Remove", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            String result = hotel.removeReservation(roomName, guestName);
            view.setDisplayText(result);
        } else {
            view.setDisplayText("Modification discarded.");
        }

    } catch (NumberFormatException e) {
        view.setDisplayText("Invalid input. Reservation not removed.");
    }
  }


  private void removeHotel(Hotel hotel) {
      int confirm = JOptionPane.showConfirmDialog(view, "Are you sure you want to remove hotel \"" + hotel.getName() + "\"?", "Confirm Removal", JOptionPane.YES_NO_OPTION);
      if (confirm == JOptionPane.YES_OPTION) {
          hotels.remove(hotel);
          view.setDisplayText("Hotel removed successfully.");
      } else {
          view.setDisplayText("Hotel removal cancelled.");
      }
  }

  public void DatePriceModify(Hotel hotel) {
    // Prompt the user for the date to modify
    String dateStr = JOptionPane.showInputDialog(view, "Which date would you like to modify (1-31):");//string so that later on we can maybe display a calendar and if they type like july x it only takes x
    int date;
    try {
        date = Integer.parseInt(dateStr);
        if (date < 1 || date > 31) {
            view.setDisplayText("Invalid input. Date must be between 1 and 31.");
            return;
        }
    } catch (NumberFormatException e) {
        view.setDisplayText("Invalid input. Please enter a valid date.");
        return;
    }

    // Prompt the user for the new percentage
    String percentageStr = JOptionPane.showInputDialog(view, "Enter new percentage for date (50-150):");
    double percentage;
    try {
        percentage = Double.parseDouble(percentageStr);
        if (percentage < 50 || percentage > 150) {
            view.setDisplayText("Invalid input. Percentage must be between 50 and 150.");
            return;
        }
    } catch (NumberFormatException e) {
        view.setDisplayText("Invalid input. Please enter a valid percentage.");
        return;
    }

    // Update the date price in the hotel
    hotel.setDatePrice(date - 1, percentage / 100);
    view.setDisplayText("Price for date " + date + " has been changed to " + percentage + "%.");
  }
}
