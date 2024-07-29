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

        String[] hotelNames = new String[hotels.size()];//make an array of hotel names
        for (int i = 0; i < hotels.size(); i++) {
            hotelNames[i] = hotels.get(i).getName();
        }

        String selectedHotelName = (String) JOptionPane.showInputDialog(view, message,
                title, JOptionPane.PLAIN_MESSAGE, null, hotelNames, hotelNames[0]);

        if (selectedHotelName == null) {
            view.setDisplayText("No hotel selected.");
            return null;
        }

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
