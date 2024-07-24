import java.awt.event.ActionEvent;
import java.util.ArrayList;


//pretty much the getHotel function
public class SearchButtonListener extends BaseButtonListener {
    public SearchButtonListener(ArrayList<Hotel> hotels, HotelView view){
        super(hotels,view);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String query = view.getSearchQuery().toLowerCase();

        if (query.isEmpty()) {
            view.setDisplayText("Please enter a hotel name to search.");
            return;
        }

        StringBuilder builder = new StringBuilder("Search results:\n");
        boolean found = false;
        for (Hotel hotel : hotels) {
            if (hotel.getName().toLowerCase().contains(query)) {
                builder.append("Hotel Name: ").append(hotel.getName())
                        .append(", Rooms: ").append(hotel.getRooms().size())
                        .append("\n");
                found = true;
            }
        }

        if (!found) {
            builder.append("No hotels found with the name '").append(query).append("'.");
        }

        view.setDisplayText(builder.toString());
    }
  }
