import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HotelController {
    private Hotel model;
    private HotelView view;

    public HotelController(Hotel model, HotelView view) {
        this.model = model;
        this.view = view;

        // Add listeners to the view
        this.view.addCreateHotelListener(new CreateHotelListener());
    }

    class CreateHotelListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String hotelName = view.getHotelName();
            int numRooms = view.getNumRooms();

            if (hotelName.isEmpty() || numRooms <= 0) {
                view.setOutputText("Invalid input. Please enter a valid hotel name and number of rooms.");
                return;
            }

            model = new Hotel(hotelName, numRooms);
            view.setOutputText("Hotel created: " + hotelName + " with " + numRooms + " rooms.");
        }
    }
}