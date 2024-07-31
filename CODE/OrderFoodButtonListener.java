import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
/**
 * Handles ordering of food for a guest
 */
public class OrderFoodButtonListener extends BaseButtonListener {
    /**
     * constructor for order food button listener
     * 
     * @param hotels the list of hotels
     * @param view the hotel view
     */
    public OrderFoodButtonListener(ArrayList<Hotel> hotels, HotelView view) {
        super(hotels, view);
    }

    /**
     * Handles the action performed when the order food button is clicked.
     * 
     * @param e The ActionEvent triggered by the button click.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Hotel selectedHotel = selectHotel(hotels, view, "Order Food", "Select a hotel:");
        if (selectedHotel != null) {
            orderFood(selectedHotel);
        } else {
            view.setDisplayText("Invalid Hotel.");
        }
    }
    /**
     * orders food in the selected hotel
     * 
     * @param hotel the hotel to order food in 
     */
    private void orderFood(Hotel hotel) {
        ArrayList<String> guestNames = getGuestNames(hotel);

        if (guestNames.isEmpty()) {
            view.setDisplayText("No guests found.");
            return;
        }

        String selectedGuest = promptForGuestSelection(guestNames);
        if (selectedGuest == null) {
            view.setDisplayText("No guest selected.");
            return;
        }

        int roomIndex = hotel.findRoomIndexByGuest(selectedGuest);
        if (roomIndex == -1) {
            view.setDisplayText("Guest not found.");
            return;
        }

        double price = promptForFoodOrder();
        if (price != -1) {
            hotel.addPrice(price, roomIndex);
            view.setDisplayText("Food has been ordered.");
        }
    }
    /**
     * returns the guest names of a hotel in the form of a list
     * 
     * @param hotel the hotel used
     * @return an arraylist of strings for the guestnames
     */
    private ArrayList<String> getGuestNames(Hotel hotel) {
        ArrayList<String> guestNames = new ArrayList<>();
        for (Room room : hotel.getRooms()) {
            for (Reservation reservation : room.getReservations()) {
                guestNames.add(reservation.getGuestName());
            }
        }
        return guestNames;
    }
    /**
     * prompts user for selecting a guest
     * 
     * @param guestNames arraylist of guestnames
     * @return the chosen guest
     */
    private String promptForGuestSelection(ArrayList<String> guestNames) {
        JComboBox<String> guestComboBox = new JComboBox<>(guestNames.toArray(new String[0]));
        int result = JOptionPane.showConfirmDialog(view, guestComboBox, "Select Guest", JOptionPane.OK_CANCEL_OPTION);

        if (result != JOptionPane.OK_OPTION) {
            return null;
        }

        return (String) guestComboBox.getSelectedItem();
    }
    /**
     * prompts the user for the food they wish to order
     * 
     * @return the price of the food chosen
     */
    private double promptForFoodOrder() {
        String[] foodOptions = {
            "Toccino with Rice: 299.0",
            "Longganisa with Rice: 320.0",
            "Bacon with eggs: 340.0",
            "Iced Coffee: 120.0",
            "Beef Tapa with Egg and Rice: 390.0"
        };

        int choice = JOptionPane.showOptionDialog(view, "Select food to order:", "Food Order",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, foodOptions, foodOptions[0]);

        switch (choice) {
            case 0:
                return 299.0;
            case 1:
                return 320.0;
            case 2:
                return 340.0;
            case 3:
                return 120.0;
            case 4:
                return 390.0;
            default:
                view.setDisplayText("Invalid choice.");
                return -1;
        }
    }
}
