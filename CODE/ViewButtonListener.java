import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;


public class ViewButtonListener extends BaseButtonListener {
    public ViewButtonListener(ArrayList<Hotel> hotels, HotelView view){
        super(hotels,view);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Hotel selectedHotel = selectHotel(hotels, view, "View Hotel", "Select a hotel to view:");
        if (selectedHotel != null) {
            viewHotel(selectedHotel);
        }
    }
  

  private void viewHotel(Hotel hotel) {
    String[] options = {"High-level info", "Low-level info"};
    int choice = JOptionPane.showOptionDialog(view, "Which level of info would you like to view?", "View Hotel",
            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

    if (choice == 0) {
        String message = "\nName: " + hotel.getName() + "\nNumber of rooms: " + hotel.getRooms().size() + "\nTotal revenue: " + hotel.sumTotal();
        view.setDisplayText(message);
    } else if (choice == 1) {
        String[] lowLevelOptions = {"Total number of booked & available rooms for a selected date", "Information about the selected room", "Information about a selected reservation"};
        int lowChoice = JOptionPane.showOptionDialog(view, "Select the low-level info to view:", "Low-level info",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, lowLevelOptions, lowLevelOptions[0]);

        switch (lowChoice) {
            case 0:
                int date = Integer.parseInt(JOptionPane.showInputDialog(view, "Enter date:"));
                int countAvail = 0, countBooked = 0;
                for (Room room : hotel.getRooms()) {
                    if (room.getAvailability(date)) countAvail++;
                    else countBooked++;
                }
                view.setDisplayText("Date: " + date + "\nAvailable rooms: " + countAvail + "\nBooked rooms: " + countBooked);
                break;
            case 1:
                String roomIdInput = JOptionPane.showInputDialog(view, "Enter room ID:");
                int roomID = Integer.parseInt(roomIdInput);
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
                break;
            case 2:
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
                StringBuilder reservationInfoBuilder = new StringBuilder();
                boolean reservationFound = false;
                for (Room room : hotel.getRooms()) {
                    for (Reservation reservation : room.getReservations()) {
                        if (reservation.getGuestName().equals(guestName)) {
                            reservationInfoBuilder.append("Guest name: ").append(reservation.getGuestName())
                                    .append("\nRoom ID: ").append(room.getID())
                                    .append("\nCheck-in: ").append(reservation.getCheckin())
                                    .append("\nCheck-out: ").append(reservation.getCheckout())
                                    .append("\nPrice per day: ").append(room.getPrice())
                                    .append("\nTotal price: ").append(getTotalPrice(hotel, reservation, room)).append("\n");
                            reservationFound = true;
                        }
                    }
                }
                if (!reservationFound) {
                    view.setDisplayText("Reservation cannot be found.");
                } else {
                    view.setDisplayText(reservationInfoBuilder.toString());
                }
                break;
            default:
                view.setDisplayText("Invalid choice.");
        }
    }
  }

  /**
     * Calculates the total price for the reservation based on the room price and the number of days.
     *
     * @return the total price of the reservation
     */
    public double getTotalPrice(Hotel hotel, Reservation reservation, Room room) {
        double totalPrice = 0;
        for (int i = reservation.getCheckin(); i < reservation.getCheckout(); i++) {
            totalPrice += room.getPrice() * hotel.getDatePrice().get(i - 1);
        }
        return totalPrice;
    }
}