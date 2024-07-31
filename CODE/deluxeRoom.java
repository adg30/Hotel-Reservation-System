
/**
 * deluxeRoom class that inherits room, with an increased price
 */
public class deluxeRoom extends Room{
    /**
     * constructor for a deluxe room
     * 
     * @param ID
     * @param price
     */
    public deluxeRoom(int ID, double price){
        super(ID, price * 1.35, 2);
    }
}
