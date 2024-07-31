/**
 * standardRoom class that inherits room, with the normal price.
 */
public class standardRoom extends Room{
    /**
     * constructor for a standard room
     * 
     * @param ID the id of the room
     * @param price the price of the room
     */
    public standardRoom(int ID, double price){
        super(ID, price, 1);
    }

}
