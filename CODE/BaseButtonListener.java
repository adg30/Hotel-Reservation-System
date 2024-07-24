import java.awt.event.ActionListener;
import java.util.ArrayList;

public abstract class BaseButtonListener implements ActionListener {
    protected ArrayList<Hotel> hotels;
    protected HotelView view;

    public BaseButtonListener(ArrayList<Hotel> hotels, HotelView view) {
        this.hotels = hotels;
        this.view = view;
    }
}
