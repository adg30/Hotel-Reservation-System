
/**
 * deluxeRoom class that inherits room, with an increased price
 */
public class deluxeRoom extends Room{
    /**
     * constructor for a deluxe room
     * 
     * @param ID the id of the room
     * @param price the price of the room
     */
    public deluxeRoom(int ID, double price){
        super(ID, price * 1.35, 2);
    }
}
