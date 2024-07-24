import java.awt.event.ActionEvent;
import java.util.ArrayList;


public class ExitButtonListener extends BaseButtonListener{
    public ExitButtonListener(ArrayList<Hotel> hotels, HotelView view){
        super(hotels,view);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        System.exit(0);
    }
    
}
