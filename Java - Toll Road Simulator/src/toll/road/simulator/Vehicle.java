package toll.road.simulator;

/**
 *
 * @author rkb18wyu
 */
public abstract class Vehicle 
{
    private String regNumb; //stores the reg number of the vehicle
    private String manufacturer; //stores the manufacturer of the vehicle 
    abstract int calculateBasicTripCost(); /* this method is based on the type
    of vehicle and therefore is overidden to calculated the cost in other 
    classes*/
    
    public Vehicle(String regNumb, String manufacturer)
    {
        this.regNumb = regNumb;
        this.manufacturer = manufacturer;
    }
    
    public String getRegNumb()
    {
        return regNumb;
    }
    
    public String getManufacturer()
    {
        return manufacturer;
    }
    
    @Override
    public String toString()
    {
        return "Registration number = " + getRegNumb() + ", Manufacturer = " + 
                getManufacturer();
    }
   
}
