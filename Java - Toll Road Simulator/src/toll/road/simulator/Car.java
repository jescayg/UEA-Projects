package toll.road.simulator;

/**
 *
 * @author rkb18wyu
 */
public class Car extends Vehicle
{
    private int numberOfSeats;
    
    public int getNumbOfSeats()
    {
        return numberOfSeats; //Accessor 
    }
    
    public Car(String reg, String manu, int numberOfSeats)
    {
        super(reg,manu);
        this.numberOfSeats = numberOfSeats;
    }
    
    @Override
    int calculateBasicTripCost() /*overrides the method in Vehicle to calculate 
            the trip based on the number of seats the car has*/
    {
        if (numberOfSeats <= 5) 
        {
            return 500;
        }
        else 
        {
            return 600;
        }
    }
    
    @Override
    public String toString()
    {
        return "Registration number = " + getRegNumb() + ", Manufacturer = " + 
                getManufacturer() + ", Number of seats = " + getNumbOfSeats() 
                + ", Cost = £" + calculateBasicTripCost();
    }
    
    /*Testing with different numbers to also test the calculateBasicTripCost()
    method
    public static void main(String args[])
    {
        Car t = new Car("HQ09WIJ","Ford",6);
        String str = t.toString();
        System.out.println(t);
        Car t2 = new Car("HQ09WIJ","Ford",3);
        String str2 = t2.toString();
        System.out.println(t2);
    } */
}
