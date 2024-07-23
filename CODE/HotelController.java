import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class HotelController {
  private ArrayList<Hotel> hotels;
  private HotelView view;

  public HotelController(ArrayList<Hotel> hotels, HotelView view) {
    this.hotels = hotels;
    this.view = view;

    this.view.addAddButtonListener(new AddButtonListener());
    this.view.addManageButtonListener(new ManageButtonListener());
    this.view.addViewButtonListener(new ViewButtonListener());
    this.view.addBookButtonListener(new BookButtonListener());
    this.view.addSearchButtonListener(new SearchButtonListener());
    this.view.addExitButtonListener(new ExitButtonListener());
  }

  class AddButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      String name = view.getHotelName();
      int numRooms = view.getNumRooms();
      double price = view.getRoomPrice();

      if (name.isEmpty() || numRooms <= 0 || price <= 0) {
        view.setDisplayText("Invalid input. Please enter valid hotel details.");
        return;
      }

      hotels.add(new Hotel(name, numRooms, price));
      view.setDisplayText("Hotel added successfully.");
    }
  }

  class ManageButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      if (hotels.isEmpty()) {
        view.setDisplayText("No hotels available to manage.");
        return;
      }

      StringBuilder builder = new StringBuilder("Available hotels:\n");
      for (int i = 0; i < hotels.size(); i++) {
        builder.append((i + 1)).append(". ").append(hotels.get(i).getName()).append("\n");
      }
      view.setDisplayText(builder.toString());

      String input = JOptionPane.showInputDialog(view, "Enter the number of the hotel to manage:");
      int index;
      try {
        index = Integer.parseInt(input) - 1;
      } catch (NumberFormatException ex) {
        view.setDisplayText("Invalid input.");
        return;
      }

      if (index < 0 || index >= hotels.size()) {
        view.setDisplayText("Invalid hotel number.");
        return;
      }

      manageHotel(index);
      
    }

  }

  public void manageHotel(int index) {
    Hotel hotel = hotels.get(index);

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
            removeHotel(index);
            break;
        case 6:
            modifyDatePrice(hotel);
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

        String[] roomTypes = {"Standard Room", "Deluxe Room", "Executive Room"};
        int roomType = JOptionPane.showOptionDialog(view, "Select room type:", "Room Type",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, roomTypes, roomTypes[0]);

        if (roomType >= 0 && roomType < 3) {
            hotel.addRoom(roomID, roomType);
            view.setDisplayText("Room added successfully.");
        } else {
            view.setDisplayText("Invalid room type.");
        }

    } catch (NumberFormatException e) {
        view.setDisplayText("Invalid room ID.");
    }
}

private void removeRooms(Hotel hotel) {
    if (hotel.getRooms().size() <= 1) {
        view.setDisplayText("There are no rooms left to remove. Please add more rooms before removing.");
        return;
    }
    hotel.removeRoom();
    view.setDisplayText("Room removed successfully.");
}

private void updateBasePrice(Hotel hotel) {
    String priceStr = JOptionPane.showInputDialog(view, "Enter new base price:");
    try {
        double price = Double.parseDouble(priceStr);
        if (price > 0) {
            hotel.updatePrice(price);
            view.setDisplayText("Base price updated successfully.");
        } else {
            view.setDisplayText("Invalid input. Base price not updated.");
        }
    } catch (NumberFormatException e) {
        view.setDisplayText("Invalid input. Base price not updated.");
    }
}

private void removeReservation(Hotel hotel) {
    hotel.removeReservation();
    view.setDisplayText("Reservation removed successfully.");
}

private void removeHotel(int index) {
    int confirm = JOptionPane.showConfirmDialog(view, "Are you sure you want to remove hotel \"" + hotels.get(index).getName() + "\"?", "Confirm Removal", JOptionPane.YES_NO_OPTION);
    if (confirm == JOptionPane.YES_OPTION) {
        hotels.remove(index);
        view.setDisplayText("Hotel removed successfully.");
    } else {
        view.setDisplayText("Hotel removal cancelled.");
    }
}

private void modifyDatePrice(Hotel hotel) {
    // Implement date price modification logic
    view.setDisplayText("Date price modification is not yet implemented.");
}


  class ViewButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      if (hotels.isEmpty()) {
        view.setDisplayText("No hotels available to view.");
        return;
      }

      StringBuilder builder = new StringBuilder("Available hotels:\n");
      for (int i = 0; i < hotels.size(); i++) {
        builder.append((i + 1)).append(". ").append(hotels.get(i).getName()).append("\n");
      }
      view.setDisplayText(builder.toString());
      // TODO: COMPLETE viewHotel
    }
  }

  class BookButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      if (hotels.isEmpty()) {
        view.setDisplayText("No hotels available to book.");
        return;
      }

      StringBuilder builder = new StringBuilder("Available hotels:\n");
      for (int i = 0; i < hotels.size(); i++) {
        builder.append((i + 1)).append(". ").append(hotels.get(i).getName()).append("\n");
      }
      view.setDisplayText(builder.toString());
      // TODO: COMPLETE simulateBooking
    }
  }
  //TODO: go over the initial hotel finder for choosing
  class SearchButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      String query = view.getSearchQuery().toLowerCase();

      if (query.isEmpty()) {
        view.setDisplayText("Please enter a hotel name to search.");
        return;
      }

      StringBuilder builder = new StringBuilder("Search results:\n");
      boolean found = false;
      for (Hotel hotel : hotels) {
        if (hotel.getName().toLowerCase().contains(query)) {
          builder.append("Hotel Name: ").append(hotel.getName())
                 .append(", Rooms: ").append(hotel.getNumRooms())
                 .append(", Price: $").append(hotel.getRoomPrice()).append("\n");
          found = true;
        }
      }

      if (!found) {
        builder.append("No hotels found with the name '").append(query).append("'.");
      }

      view.setDisplayText(builder.toString());
    }
  }

  class ExitButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      System.exit(0);
    }
  }
}
