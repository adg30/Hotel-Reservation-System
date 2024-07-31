import java.awt.event.ActionEvent;
import java.util.ArrayList;

/**
 * Listener class for the exit button that exits the program.
 */
public class ExitButtonListener extends BaseButtonListener{
    /**
     * Constructor for initializing hotels and view.
     * 
     * @param hotels The list of hotels.
     * @param view   The HotelView instance.
     */
    public ExitButtonListener(ArrayList<Hotel> hotels, HotelView view){
        super(hotels,view);
    }
    /**
     * Handles the action performed when the button is clicked.
     * 
     * @param e The ActionEvent triggered by the button click.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        System.exit(0);//exit program
    }
    
}
