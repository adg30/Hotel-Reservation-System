import java.util.ArrayList;
/**
*Represents a room in a hotel.
**/
public class Room {
    /** 
    *The ID of this room
    */
    private int ID;
    /** 
    *The price of this room
    */
    private double price;
    private double totalPrice;
    /** 
    *An array containing the availability of this room
    */
    private boolean[] availability;
    /** 
    *An arraylist containing the reservations of this room
    */
    private ArrayList<Reservation> reservations;
    private int type;

    
    /**
    * Constructs a room with the specified ID and price
    *
    * @param ID    the ID of the room
    * @param price the price of the room
    **/
    public Room(int ID, double price, int type){
        int i;
        this.ID = ID;
        this.price = price;
        this.totalPrice = 0;
        this.type = type;
        this.availability = new boolean[31];
        this.reservations = new ArrayList<Reservation>();
        for(i = 0; i < 31; i++)
        {
            this.availability[i] = true;
        }
    
    }

    public int getType(){
        return this.type;
    }

    /**
    * Gets the ID of the room.
    *
    * @return the ID of the room
    **/
    public int getID(){
        return ID;
    }
    /**
    * Gets the price of the room.
    *
    * @return the price of the room
    **/
    public double getPrice(){
        return price;
    }
    /**
    * Gets the list of reservations for the room.
    *
    * @return the list of reservations of the room
    **/
    public ArrayList<Reservation> getReservations(){
        return this.reservations;
    }
    /**
    * Gets the availability of the room on a specific day.
    *
    * @param index the index/day to check availability for
    * @return true if the room is available, false otherwise
    **/
    public boolean getAvailability(int index){
        return this.availability[index];
    }

    public boolean getAvailabilityRange(int index1, int index2){
        
            for (int day = index1 - 1; day < index2 - 1; day++) {//keep the -1 thingy dont forget
              if (!this.availability[day]) {
                return false;
              }
            }
        return true;
    }
    /**
    * sets the price of the room.
    *
    * @param price the new price of the room
    **/
    public void setPrice(double price){ 
        this.price = price;
    }
 
    /**
     * Adds a reservation for the room.
     *
     * @param guestName the name of the guest
     * @param checkin   the check-in day (0-30)
     * @param checkout  the check-out day (0-30)
     */
    public void addReservation(String guestName, int checkin, int checkout) {   
        Reservation newReservation = new Reservation(guestName, checkin, checkout, this);
            reservations.add(newReservation);
            updateAvailability();
        }
    /**
     * Updates the availability of the room based on the current reservations.
     */
    public void updateAvailability() {
        // Reset all days to available in case some reservations were removed
        for (int i = 0; i < availability.length; i++) {
            availability[i] = true;
        }

        //Loop through each reservation and mark the reserved days as unavailable
        for (int j = 0; j < reservations.size(); j++) {
            Reservation reservation = reservations.get(j);
            int checkin = reservation.getCheckin() - 1;  // Adjust for zero-indexed array
            int checkout = reservation.getCheckout() - 1;  // Adjust for zero-indexed array

            // Ensure checkin and checkout are within the bounds of availability array
            if (checkin >= 0 && checkout <= availability.length && checkin < checkout) {
                for (int day = checkin; day < checkout; day++) {
                    availability[day] = false;
                }
            }   
        }
    }

    public void addTotalPrice(double price)
    {
        this.totalPrice += price;
    }
    public double getTotalPrice()
    {
        return this.totalPrice;
    }
}