import java.awt.event.ActionEvent;
import java.util.ArrayList;
/**
 * Listener class for the create button that handles hotel creation.
 */
public class CreateButtonListener extends BaseButtonListener {
    /**
     * Constructor for initializing hotels and view.
     * 
     * @param hotels The list of hotels.
     * @param view   The HotelView instance.
     */
    public CreateButtonListener(ArrayList<Hotel> hotels, HotelView view){
        super(hotels, view);
    }
    /**
     * Handles the action performed when the createhotel button is clicked.
     * 
     * @param e The ActionEvent triggered by the button click.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        createHotel();
    }
    /**
     * Validates inputs, then creates a hotel with the given information
     */
    private void createHotel(){
        String name = view.getHotelName();
        boolean isUniqueName = true;
        int numRooms = view.getNumRooms();
        int type = view.getSelectedHotelType();
        double price;

        // Check if the custom price checkbox is selected
        if (view.isCustomPriceSelected()) {
            price = view.getCustomPrice();
        } else {
            price = 1299; // Default price
        }

        // Check if the hotel name is unique
        for (Hotel hotel : hotels) {
            if (hotel.getName().equals(name)) {
                isUniqueName = false;
                break;
            }
        }

        // Validate inputs
        if (name.isEmpty() || numRooms <= 0) {
            view.setDisplayText("Invalid input. Please enter valid hotel details.");
            return;
        }

        if (numRooms > 50) {
            view.setDisplayText("Invalid input. Max amount of rooms is 50.");
            return;
        }

        if ( type < 1 || type > 2){
          view.setDisplayText("User must select a hotel type.");
          return;
        }

        if (!isUniqueName) {
            view.setDisplayText("Hotel name must be unique. Try again.");
            return;
        }

        // Add hotel to the list
        if (price < 100) {
            hotels.add(new Hotel(name, numRooms, 1299, type));
            view.setDisplayText("Invalid Price detected.\nA Type - " + type + " Hotel named \"" + name + "\" added successfully with " + numRooms + " rooms.\nA default price of 1299 per room has been applied.");
        } else {
            hotels.add(new Hotel(name, numRooms, price, type));
            view.setDisplayText("A Type - " + type + " Hotel named \"" + name + "\" added successfully with " + numRooms + " rooms.\nA price of " + price + " added to each room.");
        }

    }
}
