import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class CreateButtonListener extends BaseButtonListener {//TODO: make this loop until a valid input is entered
    public CreateButtonListener(ArrayList<Hotel> hotels, HotelView view){
        super(hotels,view);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
      String name = view.getHotelName();
      boolean isUniqueName = false;
      int numRooms = view.getNumRooms();
      double price = view.getRoomPrice();
      int type = view.getHotelType();

      if (name.isEmpty() || numRooms <= 0 || price <= 0) {
        view.setDisplayText("Invalid input. Please enter valid hotel details.");
        return;
      }

      hotels.add(new Hotel(name, numRooms, price, type));
      view.setDisplayText("Hotel added successfully.");
    }
  }
