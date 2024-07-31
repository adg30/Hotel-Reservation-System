import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;

/**
 * Handles the action of viewing hotel information
 */
public class ViewButtonListener extends BaseButtonListener {
    /**
     * Constructs a ViewButtonListener with the specified hotels and view.
     *
     * @param hotels the list of hotels.
     * @param view the hotel view.
     */
    public ViewButtonListener(ArrayList<Hotel> hotels, HotelView view){
        super(hotels, view);
    }

    /**
     * Handles the action performed when the button is clicked.
     * 
     * @param e The ActionEvent triggered by the button click.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Hotel selectedHotel = selectHotel(hotels, view, "View Hotel", "Select a hotel to view:");
        if (selectedHotel != null) {
            viewHotel(selectedHotel);
        }
    }

     /**
     * Handles the process of viewing information about the specified hotel.
     *
     * @param hotel the hotel to view information about.
     */
    private void viewHotel(Hotel hotel) {
        String[] options = {"High-level info", "Low-level info"};
        int choice = JOptionPane.showOptionDialog(view, "Which level of info would you like to view?", "View Hotel",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        if (choice == 0) {
            displayHighLevelInfo(hotel);
        } else if (choice == 1) {
            displayLowLevelInfo(hotel);
        }
    }
     /**
     * Displays high-level information about the specified hotel.
     *
     * @param hotel the hotel to display high-level information about.
     */
    private void displayHighLevelInfo(Hotel hotel) {
        String message = "\nName: " + hotel.getName() + "\nNumber of rooms: " + hotel.getRooms().size() + "\nTotal revenue: " + hotel.sumTotal();
        view.setDisplayText(message);
    }
     /**
     * Displays low-level information about the specified hotel.
     *
     * @param hotel the hotel to display low-level information about.
     */
    private void displayLowLevelInfo(Hotel hotel) {
        String[] lowLevelOptions = {
            "Total number of booked & available rooms for a selected date",
            "Information about the selected room",
            "Information about a selected reservation"
        };
        int lowChoice = JOptionPane.showOptionDialog(view, "Select the low-level info to view:", "Low-level info",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, lowLevelOptions, lowLevelOptions[0]);

        switch (lowChoice) {
            case 0:
                displayRoomAvailabilityForDate(hotel);
                break;
            case 1:
                displayRoomInfo(hotel);
                break;
            case 2:
                displayReservationInfo(hotel);
                break;
            default:
                view.setDisplayText("Invalid choice.");
        }
    }
    /**
     * Displays room availability for a specific date in the specified hotel.
     *
     * @param hotel the hotel to check room availability in.
     */
    private void displayRoomAvailabilityForDate(Hotel hotel) {
        try {
            int date = Integer.parseInt(JOptionPane.showInputDialog(view, "Enter date:"));
            int countAvail = 0, countBooked = 0;
            for (Room room : hotel.getRooms()) {
                if (room.getAvailability(date)) countAvail++;
                else countBooked++;
            }
            view.setDisplayText("Date: " + date + "\nAvailable rooms: " + countAvail + "\nBooked rooms: " + countBooked);
        } catch (NumberFormatException e) {
            view.setDisplayText("Invalid date input.");
        }
    }
    /**
     * Displays information about a specific room in the specified hotel.
     *
     * @param hotel the hotel to get room information from.
     */
    private void displayRoomInfo(Hotel hotel) {
        try {
            int roomID = Integer.parseInt(JOptionPane.showInputDialog(view, "Enter room ID:"));
            Room selectedRoom = null;
            for (Room room : hotel.getRooms()) {
                if (room.getID() == roomID) {
                    selectedRoom = room;
                    break;
                }
            }

            if (selectedRoom == null) {
                view.setDisplayText("Room not found.");
            } else {
                String roomInfo = "\nRoom ID: " + selectedRoom.getID() + "\nPrice: " + selectedRoom.getPrice()
                        + "\nNumber of reservations: " + selectedRoom.getReservations().size();
                view.setDisplayText(roomInfo);
            }
        } catch (NumberFormatException e) {
            view.setDisplayText("Invalid room ID.");
        }
    }
    /**
     * Displays information about a specific reservation in the specified hotel.
     *
     * @param hotel the hotel to get reservation information from.
     */
    private void displayReservationInfo(Hotel hotel) {
        boolean hasReservations = false;
        for (Room room : hotel.getRooms()) {
            if (!room.getReservations().isEmpty()) {
                hasReservations = true;
                break;
            }
        }
        if (!hasReservations) {
            view.setDisplayText("No reservations found.");
            return;
        }

        StringBuilder reservationsBuilder = new StringBuilder("\nReservations:\n");
        for (Room room : hotel.getRooms()) {
            for (Reservation reservation : room.getReservations()) {
                reservationsBuilder.append("Reservation by: \"").append(reservation.getGuestName()).append("\"\n");
            }
        }

        String guestName = JOptionPane.showInputDialog(view, reservationsBuilder.toString() + "Enter a guest name to view more information:");
        if (guestName == null || guestName.isEmpty()) {
            view.setDisplayText("Guest name not provided.");
            return;
        }

        StringBuilder reservationInfoBuilder = new StringBuilder();
        boolean reservationFound = false;
        for (Room room : hotel.getRooms()) {
            for (Reservation reservation : room.getReservations()) {
                if (reservation.getGuestName().equals(guestName)) {
                    boolean discounted = !reservation.getDiscountCode().isEmpty();
                    reservationInfoBuilder.append("Guest name: ").append(reservation.getGuestName())
                        .append("\nRoom ID: ").append(room.getID())
                        .append("\nCheck-in: ").append(reservation.getCheckin())
                        .append("\nCheck-out: ").append(reservation.getCheckout())
                        .append("\nPrice per day: ").append(room.getPrice())
                        .append("\nTotal price: ").append(getTotalPrice(hotel, reservation, room));
                    if (discounted) {
                        reservationInfoBuilder.append(" (DISCOUNTED)")
                        .append("Discount code: ").append(reservation.getDiscountCode()).append("\n");
                    }
                        reservationInfoBuilder.append("\n");
                    reservationFound = true;
                }
            }
        }

        if (!reservationFound) {
            view.setDisplayText("Reservation cannot be found.");
        } else {
            view.setDisplayText(reservationInfoBuilder.toString());
        }
    }

    /**
     * Calculates the total price for the reservation based on the room price and the number of days.
     *
     * @return the total price of the reservation
     */
    public double getTotalPrice(Hotel hotel, Reservation reservation, Room room) {
        double totalPrice = 0;
        double discount = 1.0;
        if (!reservation.getDiscountCode().isEmpty()) {
            discount = getDiscountPercentage(reservation.getDiscountCode());
        }

        for (int i = reservation.getCheckin(); i < reservation.getCheckout(); i++) {
            totalPrice += room.getPrice() * hotel.getDatePrice().get(i - 1);
        }

        if (discount == -1){
            return totalPrice -= room.getPrice() * hotel.getDatePrice().get(reservation.getCheckin() - 1);
        }

        return totalPrice * discount;
    }

    /**
     * Retrieves the discount percentage based on the discount code.
     *
     * @param discountCode the discount code.
     * @return the discount percentage.
     */
    private double getDiscountPercentage(String discountCode){
        if ("PAYDAY".equals(discountCode)){
            return 0.93;
        } else if ("I_WORK_HERE".equals(discountCode)){
            return 0.9;
        } else if ("STAY4_GET1".equals(discountCode)){
            return -1;
        }
        return 1.0;
    }
}
