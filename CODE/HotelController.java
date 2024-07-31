
import java.util.ArrayList;
/**
 * controller for the hotel reservation system
 */
public class HotelController {
  private ArrayList<Hotel> hotels;
  private HotelView view;
  /**
   * constructor for hotel controller, also initializes all button listeners
   * 
   * @param hotels list of hotels
   * @param view hotel view instance 
   */
  public HotelController(ArrayList<Hotel> hotels, HotelView view) {
    this.hotels = hotels;
    this.view = view;

    this.view.addCreateButtonListener(new CreateButtonListener(hotels,view));
    this.view.addManageButtonListener(new ManageButtonListener(hotels,view));
    this.view.addViewButtonListener(new ViewButtonListener(hotels, view));
    this.view.addBookButtonListener(new BookButtonListener(hotels, view));
    this.view.addExitButtonListener(new ExitButtonListener(hotels, view));
    this.view.addOrderFoodButtonListener(new OrderFoodButtonListener(hotels, view));

  }


}
