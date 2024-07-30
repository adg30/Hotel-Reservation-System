
import java.util.ArrayList;
/*
TODO: 
      1. Low-level info about selected reservation doesn't show correct total price if with discount or modify date price
      2. Not allowed to change baseprice of rooms while there are reservations for hotel
      3. same with modifydateprice, not allowed if there are resrvations for the hotel


-------------------------------------------------------------
      5. For booking rooms, hotel name shld be displayed for number to be selected
      6. For Removing reservations, display rooms that can be removed
      7. After doing all these changes, try the methods that uses rooms again cuz addRoom, bookRoom, removeReservation, etc.
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
