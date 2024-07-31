/**
 * standardRoom class that inherits room, with the normal price.
 */
public class standardRoom extends Room{
    /**
     * constructor for a standard room
     * 
     * @param ID
     * @param price
     */
    public standardRoom(int ID, double price){
        super(ID, price, 1);
    }

}
