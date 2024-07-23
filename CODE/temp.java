import java.util.ArrayList;

public class temp {
   
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

