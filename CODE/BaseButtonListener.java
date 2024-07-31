import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;
/**
 * 
 * Abstract base class for button listeners that handles common functionality.
 */
public abstract class BaseButtonListener implements ActionListener {
    /**
     * the hotels in the listener
     */
    protected ArrayList<Hotel> hotels;
    /**
     * The view in the listener
     */
    protected HotelView view;

    /**
     * Constructor to initialize hotels and view.
     * 
     * @param hotels The list of hotels.
     * @param view   The HotelView instance.
     */
    public BaseButtonListener(ArrayList<Hotel> hotels, HotelView view) {
        this.hotels = hotels;
        this.view = view;
    }
    /**
     * Prompts the user to select a hotel from a list.
     * 
     * @param hotels  The list of hotels.
     * @param view    The HotelView instance.
     * @param title   The title of the selection dialog.
     * @param message The message displayed in the selection dialog.
     * @return The selected Hotel instance or null if no hotel is selected.
     */
    public Hotel selectHotel(ArrayList<Hotel> hotels, HotelView view, String title, String message) {
        if (hotels.isEmpty()) {
            view.setDisplayText("No hotels available.");
            return null;
        }
        
        String[] hotelNames = new String[hotels.size()];//make an array of hotel names for selection
        for (int i = 0; i < hotels.size(); i++) {
            hotelNames[i] = hotels.get(i).getName();
        }
        //show a dialog to select a hotel
        String selectedHotelName = (String) JOptionPane.showInputDialog(view, message, title, JOptionPane.PLAIN_MESSAGE, null, hotelNames, hotelNames[0]);

        if (selectedHotelName == null) {
            view.setDisplayText("No hotel selected.");
            return null;
        }
        //find the selected hotel by name
        Hotel selectedHotel = null;
        for (Hotel hotel : hotels) {
            if (hotel.getName().equals(selectedHotelName)) {
                selectedHotel = hotel;
                break;
            }
        }

        if (selectedHotel == null) {
            view.setDisplayText("Hotel not found.");
            return null;
        }

        return selectedHotel;
    }
    /**
     * Prompts the user to enter an integer within a specified range.
     * 
     * @param message The message displayed in the input dialog.
     * @param min     The minimum value of the input.
     * @param max     The maximum value of the input.
     * @return The entered integer within the specified range.
     */
    public int getIntInput(String message, int min, int max) {
        int input = 0;
        do {
            try {
                String strInput = JOptionPane.showInputDialog(message);
                input = Integer.parseInt(strInput);
            } catch (NumberFormatException e) {
                view.setDisplayText("Invalid input. Please enter a number.");
            }

            if (input < min || input > max)
            view.setDisplayText("Invalid input. please enter within the range given");
        } while (input < min || input > max);
        return input;
    }
    /**
    * Prompts the user to enter a single character.
    * 
    * @param message The message displayed in the input dialog.
    * @return The entered character.
    */
    public char getCharInput(String message) {
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
    /**
     * Prompts the user to enter a double value within a specified range.
     * 
     * @param message The message displayed in the input dialog.
     * @param min     The minimum value of the input.
     * @param max     The maximum value of the input.
     * @return The entered double value within the specified range.
     */
    public double getDoubleInput(String message, double min, double max) {
      double input = 0;
      do {
        try {
            String strInput = JOptionPane.showInputDialog(message);
            input = Double.parseDouble(strInput);
        } catch (NumberFormatException e) {
            view.setDisplayText("Invalid input. Please enter a valid percentage.");
        }
        if (input < min || input > max)
        view.setDisplayText("Invalid input. please enter within the range given");
      } while (input < min || input > max);
      return input;
    }
}
