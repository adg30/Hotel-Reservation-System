
import java.util.ArrayList;
/*
TODO: 1. Unique hotel names when creating hotel but changing names is good
      2. Initialize rooms when creating hotel. When creating hotel, theres 0 rooms even though numroom is 25 or smth not 0
      3. Add rooms has errors, maybe similar to why Initialize room doesn't work
      4. Modify Price button doesn't work yet, only the one in manage hotel works
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

  }


}
