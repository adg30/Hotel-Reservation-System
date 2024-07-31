/**
 * executiveRoom class that inherits room, with an increased price
 */
public class executiveRoom extends Room{
    /**
     * constructor for an executive room
     * 
     * @param ID the id of the room
     * @param price the price of the room
     */
    public executiveRoom(int ID, double price){
        super(ID, price * 1.20, 3);
    }
}
