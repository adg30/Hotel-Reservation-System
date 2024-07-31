import java.util.ArrayList;

/**
 * The main class that initializes the entire hotel reservation system
 */
public class Main {

    /**
     * The main method initializes the model, view, and controller, and sets the view to be visible.
     *
     * @param args the command-line arguments.
     */
    public static void main(String[] args) {
        // Create the model
        ArrayList<Hotel> hotels = new ArrayList<>();

        // Create the view
        HotelView view = new HotelView();

        // Create the controller
        HotelController controller = new HotelController(hotels, view);
        
        // Set the view visible
        view.setVisible(true);
    }
}