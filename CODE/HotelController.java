
import java.util.ArrayList;
/*
TODO: 
*/

public class HotelController {
  private ArrayList<Hotel> hotels;
  private HotelView view;

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
