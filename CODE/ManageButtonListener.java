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
    showChoices(choice, hotel);
    }

    public void showChoices(int choice, Hotel hotel){
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
                datePriceModify(hotel);
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
        String newName = validateName(hotel);
        hotel.setName(newName);
        view.setDisplayText("Hotel name changed to " + newName + " successfully.");
    }

    private String validateName(Hotel hotel){
        boolean isUniqueName = false;
        String newName = "";
        while (!isUniqueName) {
            newName = JOptionPane.showInputDialog(view, "Enter new hotel name:");
            if (newName == null || newName.isEmpty()) {
                view.setDisplayText("Invalid input. Hotel name not updated.");
                break;
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
        return newName;
    }

  private void addRooms(Hotel hotel) {
      if (hotel.getRooms().size() >= 50) {
          view.setDisplayText("Maximum number of rooms reached. Remove a room to continue.");
          return;
      }

      int roomID = promptForRoomID();
      if (roomID == -1) return;

      if (!isRoomIDUnique(hotel, roomID)) {
        view.setDisplayText("Room ID must be unique. Try again.");
        return;
    }


     
        int roomType = promptForRoomType();
        if (roomType == -1) return;

        if (roomType >= 0 && roomType < 3) {
            int confirm = JOptionPane.showConfirmDialog(view, "Are you sure you want to add room " + roomID + " as " + getRoomTypeString(roomType) + "?", "Confirm Add Room", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                hotel.addRoom(roomID, roomType + 1);
                view.setDisplayText("Room added successfully.");
            } else {
                view.setDisplayText("Room addition cancelled.");
            }
        } else {
            view.setDisplayText("Invalid room type.");
        }
    }

    private String getRoomTypeString(int roomType) {
        String[] roomTypes = {"Standard Room", "Deluxe Room", "Executive Room"};
        return roomTypes[roomType];
    }
  

    private int promptForRoomType(){
        String[] roomTypes = {"Standard Room", "Deluxe Room", "Executive Room"};
        return JOptionPane.showOptionDialog(view, "Select room type:", "Room Type",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, roomTypes, roomTypes[0]);

    }

    private int promptForRoomID() {
        String roomIDStr = JOptionPane.showInputDialog(view, "Enter new room ID (100-199):");
        try {
            int roomID = Integer.parseInt(roomIDStr);
            if (roomID < 100 || roomID > 199) {
                view.setDisplayText("Please enter an ID fit to our standard (100, 101, 102 ... 199).");
                return -1;
            }
            return roomID;
        } catch (NumberFormatException e) {
            view.setDisplayText("Invalid room ID.");
            return -1;
        }
    }
    private boolean isRoomIDUnique(Hotel hotel, int roomID) {
    for (Room room : hotel.getRooms()) {
        if (room.getID() == roomID) {
            return false;
        }
    }
    return true;
}


    private void removeRooms(Hotel hotel) {
        if (hotel.getRooms().size() <= 1) {
            view.setDisplayText("There are no rooms left to remove. Please add more rooms before removing.");
            return;
        }

        ArrayList<Integer> removable = getRemovableRoomIDs(hotel);
        if (removable.isEmpty()) {
            view.setDisplayText("No rooms without reservations available to remove.");
            return;
        }

        Integer roomID = promptForRoomSelection(removable);
        if (roomID != null) {
            removeRoom(hotel, roomID);
        }
    }

    private ArrayList<Integer> getRemovableRoomIDs(Hotel hotel) {
        ArrayList<Integer> removable = new ArrayList<>();
        for (Room room : hotel.getRooms()) {
            if (room.getReservations().isEmpty()) {
                removable.add(room.getID());
            }
        }
        return removable;
    }

    private Integer promptForRoomSelection(ArrayList<Integer> removable) {
        Object[] roomIDs = removable.toArray();
        return (Integer) JOptionPane.showInputDialog(view, "Select a room to remove:", "Remove Room",
                JOptionPane.QUESTION_MESSAGE, null, roomIDs, roomIDs[0]);
    }

    private void removeRoom(Hotel hotel, int roomID) {
        int roomIndex = hotel.searchRoom(roomID);
        Room room = hotel.getRooms().get(roomIndex);

        if (!room.getReservations().isEmpty()) {
            view.setDisplayText("Room selected has active reservations, please remove them first.");
            return;
        }

        if (confirmAction("Are you sure you want to remove room " + roomID + "?")) {
            hotel.getRooms().remove(roomIndex);
            view.setDisplayText("Room removed successfully.");
        } else {
            view.setDisplayText("Modification discarded.");
        }
    }

    private boolean confirmAction(String message) {
        int confirm = JOptionPane.showConfirmDialog(view, message, "Confirm", JOptionPane.YES_NO_OPTION);
        return confirm == JOptionPane.YES_OPTION;
    }

  

  private void updateBasePrice(Hotel hotel) {
        checkReservations(hotel);
        double newPrice = promptForPrice("Enter new base price:", 100);
        if (newPrice >= 100 && confirmAction("Are you sure you want to change the price to " + newPrice + "?")) {
            String result = hotel.updatePrice(newPrice);
            view.setDisplayText(result);
        } else {
            view.setDisplayText("Modification discarded.");
        }
    }

    private double promptForPrice(String message, double minValue) {
        while (true) {
            String priceStr = JOptionPane.showInputDialog(view, message);
            try {
                double price = Double.parseDouble(priceStr);
                if (price >= minValue) return price;
                view.setDisplayText("Input must be greater than or equal to " + minValue + ". Try again.");
            } catch (NumberFormatException e) {
                view.setDisplayText("Invalid input. Try again.");
            }
        }
    }

    private void removeReservation(Hotel hotel) {
        try {
            int roomID = Integer.parseInt(JOptionPane.showInputDialog(view, "Enter room ID:"));
            String guestName = JOptionPane.showInputDialog(view, "Enter guest name:");
            if (confirmAction("Are you sure you want to remove this reservation?")) {
                String result = hotel.removeReservation(roomID, guestName);
                view.setDisplayText(result);
            } else {
                view.setDisplayText("Modification discarded.");
            }
        } catch (NumberFormatException e) {
            view.setDisplayText("Invalid input. Reservation not removed.");
        }
    }


  private void removeHotel(Hotel hotel) {
    checkReservations(hotel);

    int confirm = JOptionPane.showConfirmDialog(view, "Are you sure you want to remove hotel \"" + hotel.getName() + "\"?", "Confirm Removal", JOptionPane.YES_NO_OPTION);
    if (confirm == JOptionPane.YES_OPTION) {
        hotels.remove(hotel);
        view.setDisplayText("Hotel removed successfully.");
    } else {
        view.setDisplayText("Hotel removal cancelled.");
    }
  }

  public void datePriceModify(Hotel hotel) {
    checkReservations(hotel);
    // Prompt the user for the date to modify
    String dateStr = JOptionPane.showInputDialog(view, "Which date would you like to modify (1-31):");
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

  public void checkReservations(Hotel hotel){
    boolean hasReservations = false;
    for (Room room : hotel.getRooms()) {
        if (!room.getReservations().isEmpty()) {
            hasReservations = true;
            break;
        }
    }
    if (hasReservations) {
        view.setDisplayText("There are still reservations in the hotel, please remove them first.");
        return;
    }

  }
}
