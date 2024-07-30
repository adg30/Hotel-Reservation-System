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
     * Gets the room associated with the reservation.
     *
     * @return the room associated with the reservation
     */
    public Room getRoom(){
        return this.room;
    }

    
}