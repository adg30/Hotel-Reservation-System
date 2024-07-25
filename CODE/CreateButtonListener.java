import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class CreateButtonListener extends BaseButtonListener {
    public CreateButtonListener(ArrayList<Hotel> hotels, HotelView view){
        super(hotels,view);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
      String name = view.getHotelName();
      boolean isUniqueName = true;
      int numRooms = view.getNumRooms();
      double price = view.getRoomPrice();
      int type = view.getHotelType();

      for (Hotel hotel : hotels) {
        if (hotel.getName().equals(name)) {
            isUniqueName = false;
            break;
          }
      }

      if (name.isEmpty() || numRooms <= 0 || type < 1 || type > 2) {//TODO: maybe make these more specific? like a different response for each error
        view.setDisplayText("Invalid input. Please enter valid hotel details.");
        return;
      }

      if(!isUniqueName){
        view.setDisplayText("Hotel name must be unique. Try again.");
        return;
      }

      if (price < 100) {
        hotels.add(new Hotel(name, numRooms, 1299, type));
        view.setDisplayText("Invalid Price detected.\n A Type - " + type + " Hotel named \"" + name + "\" added successfully with " + numRooms + " rooms.\n A default price of 1299 per room has been applied.");
      } else {
        hotels.add(new Hotel(name, numRooms, price, type));
        view.setDisplayText("A Type - " + type + " Hotel named \"" + name + "\" added successfully with " + numRooms + " rooms.\n A custom price of "+ price + " added to each room");
      }
    }
  }
