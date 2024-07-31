/**
 * executiveRoom class that inherits room, with an increased price
 */
public class executiveRoom extends Room{
    /**
     * constructor for an executive room
     * 
     * @param ID
     * @param price
     */
    public executiveRoom(int ID, double price){
        super(ID, price * 1.20, 3);
    }
}
