
import java.util.ArrayList;
/*
TODO: 
      1.low-level info about selected reservation doesn't apply the discount so total price & revenue of hotel is diff even if shld be same,
      total revenue of hotel is correct
      2. Maybe display rooms that has removable reservation and the name of the guest?
      3. when reserving then modifying date price its not changing, idk if we need to fix that tho(idt needed since also this logic if
      we update base price of rooms n totalprice isnt changing)
      4. Modify date price isnt affecting the total price displayed on resrvation info and High-level info for total revenue in hotel
      5. Ur not supposed to be able to remove a hotel when it has any reservations


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
