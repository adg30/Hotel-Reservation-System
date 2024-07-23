public class deluxeRoom extends Room{
    private String name;
    public deluxeRoom(int ID, double price){
        super(ID, price * 1.35, "deluxeRoom");
    }
}