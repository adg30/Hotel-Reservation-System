import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public abstract class BaseButtonListener implements ActionListener {
    protected ArrayList<Hotel> hotels;
    protected HotelView view;

    public BaseButtonListener(ArrayList<Hotel> hotels, HotelView view) {
        this.hotels = hotels;
        this.view = view;
    }

    public Hotel selectHotel(ArrayList<Hotel> hotels, HotelView view, String title, String message) {
        if (hotels.isEmpty()) {
            view.setDisplayText("No hotels available.");
            return null;
        }

        String[] hotelNames = hotels.stream().map(Hotel::getName).toArray(String[]::new);
        String selectedHotelName = (String) JOptionPane.showInputDialog(view, message,
                title, JOptionPane.PLAIN_MESSAGE, null, hotelNames, hotelNames[0]);

        if (selectedHotelName == null) {
            view.setDisplayText("No hotel selected.");
            return null;
        }

        Hotel selectedHotel = hotels.stream().filter(hotel -> hotel.getName().equals(selectedHotelName)).findFirst().orElse(null);
        if (selectedHotel == null) {
            view.setDisplayText("Hotel not found.");
            return null;
        }

        return selectedHotel;
    }
}
