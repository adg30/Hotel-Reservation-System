import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
      // TODO: COMPLETE managehotel
    }
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
