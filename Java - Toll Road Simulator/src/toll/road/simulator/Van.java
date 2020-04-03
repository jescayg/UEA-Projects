package toll.road.simulator;

/**
 *
 * @author rkb18wyu
 */
public class Van extends Vehicle
{
    private int payload;
    
    public int getPayload()
    {
        return payload;
    }
    
    public Van(String reg, String manu, int load)
    {
        super(reg,manu);
        payload = load;
    }
    
    @Override
    int calculateBasicTripCost()  /*overrides the method in Vehicle to calculate
            the trip based on the size of the payload*/
    {
        if (payload <= 600) 
        {
            return 500;
        }
        else if (600 < payload && payload <= 800)
        {
            return 750;
        }
        else
        {
            return 1000;
        }
    }
    
        @Override
    public String toString()
    {
        return "Registration number = " + getRegNumb() + ", Manufacturer = " + 
                getManufacturer() + ", Payload = " + getPayload() 
                + ", Cost = £" + calculateBasicTripCost();
    }
    
    /*/Testing with different numbers to also test the calculateBasicTripCost()
    method
    public static void main(String args[])
    {
        Van t = new Van("HQ09WIJ","Ford",600);
        String str = t.toString();
        System.out.println(t);
        Van t2 = new Van("HQ09WIJ","Ford",650);
        String str2 = t2.toString();
        System.out.println(t2);
        Van t3 = new Van("HQ09WIJ","Ford",850);
        String str3 = t3.toString();
        System.out.println(t3);
    } */
    
}
