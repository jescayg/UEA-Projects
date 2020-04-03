package toll.road.simulator;

/**
 *
 * @author rkb18wyu
 */
public class Truck extends Vehicle
{
    private int numTrailers;
    
    private int getNumTrailers()
    {
        return numTrailers;
    }
    
    public Truck(String reg, String manu, int numTrailers)
    {
        super(reg,manu);
        this.numTrailers = this.numTrailers;
    }
    
    @Override
    int calculateBasicTripCost() /*overrides the method in Vehicle to calculate
            the trip based on the number of trailers the vehicle has */
    {
        if (numTrailers <= 1) 
        {
            return 1250;
        }
        else 
        {
            return 1500;
        }
    }
    
    @Override
    public String toString()
    {
        return "Registration number = " + getRegNumb() + ", Manufacturer = " + 
                getManufacturer() + ", Number of trailers = " + getNumTrailers() 
                + ", Cost = £" + calculateBasicTripCost();
    }
    
    /* Testing with different numbers to also test the calculateBasicTripCost()
    method 
    public static void main(String args[])
    {
        Truck t = new Truck("HQ09WIJ","Ford",1);
        String str = t.toString();
        System.out.println(t);
        Truck t2 = new Truck("HQ09WIJ","Ford",3);
        String str2 = t2.toString();
        System.out.println(t2);
    } */
    
 
}
