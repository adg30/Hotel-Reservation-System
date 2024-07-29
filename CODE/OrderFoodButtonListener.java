import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;


public class OrderFoodButtonListener extends BaseButtonListener{
     public OrderFoodButtonListener(ArrayList<Hotel> hotels, HotelView view) {
        super(hotels, view);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Hotel selectedHotel = selectHotel(hotels, view, "Order Food", "Select a hotel:");
        if (selectedHotel != null) {
            orderFood(selectedHotel);
        } else {
            view.setDisplayText("Invalid Hotel.");
        }
    }

    private void orderFood(Hotel hotel) {
        //TODO:make it display all the guest names
        String guestName = JOptionPane.showInputDialog(view, "Enter guest name:");
        int roomIndex = hotel.findRoomIndexByGuest(guestName);

        if (roomIndex == -1) {
            view.setDisplayText("Guest not found.");
            return;
        }


        String[] foodOptions = {
            "Toccino with Rice: 299.0",
            "Longganisa with Rice: 320.0",
            "Bacon with eggs: 340.0",
            "Iced Coffee: 120.0",
            "Beef Tapa with Egg and Rice: 390.0"
        };

        int choice = JOptionPane.showOptionDialog(view, "Select food to order:", "Food Order",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, foodOptions, foodOptions[0]);

        double price = 0;
        switch (choice) {
            case 0:
                price = 299.0;
                break;
            case 1:
                price = 320.0;
                break;
            case 2:
                price = 340.0;
                break;
            case 3:
                price = 120.0;
                break;
            case 4:
                price = 390.0;
                break;
            default:
                view.setDisplayText("Invalid choice.");
                return;
        }

        hotel.addPrice(price, roomIndex);
        view.setDisplayText(foodOptions[choice].split(":")[0] + " has been ordered.");
    }
}
    

