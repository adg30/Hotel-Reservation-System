/**
 * Represents a reservation for a room.
 */
public class Reservation {
    /** 
    *the name of the guest 
    */
    private String guestName;
    /** 
    *check-in day of the guest 
    */
    private int checkin;
    /** 
    *check-out day of the guest 
    */
    private int checkout;
    /** 
    *The room of this reservation
    */
    private Room room; 
    /**
     * Constructs a reservation with the specified details.
     *
     * @param guestName the name of the guest
     * @param checkin   the check-in day (0-30)
     * @param checkout  the check-out day (0-30)
     * @param room      the room associated with the reservation
     */
    public Reservation(String guestName, int checkin, int checkout, Room room){
        this.guestName = guestName;
        this.checkin = checkin;
        this.checkout = checkout;
        this.room = room;
    }

    /**
     * Calculates the total price for the reservation based on the room price and the number of days.
     *
     * @return the total price of the reservation
     */
    public double calculatePrice() {
        int stayDuration = checkout - checkin;
        return stayDuration * room.getPrice(); // Ensure getRate() does not return null
    }

    /**
     * Gets the name of the guest who made the reservation.
     *
     * @return the name of the guest
     */
    public String getGuestName(){
        return guestName;
    }
    /**
     * Gets the check in time of the guest who made the reservation.
     *
     * @return the check in time of the guest
     */
    public int getCheckin(){
        return checkin;
    }
    /**
     * Gets the check in time of the guest who made the reservation.
     *
     * @return the check in time of the guest
     */
    public int getCheckout(){
        return checkout;
    }
    /**
     * Gets the total price using calculate price.
     *
     * @return the total price of the reservation
     */
    public double getTotalPrice(){
        return calculatePrice();
    }
    /**
     * Gets the room associated with the reservation.
     *
     * @return the room associated with the reservation
     */
    public Room getRoom(){
        return this.room;
    }

    
}