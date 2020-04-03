package toll.road.simulator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author RKB18WYU
 */

public class TollRoad 
{
    private int moneyMade;
    ArrayList<CustomerAccount> Customers = new ArrayList<>();
    
    public int getMoneyMade()
    {
        return moneyMade;
    }
    
    public ArrayList<CustomerAccount> getCustomers()
    {
        return Customers;
    }
    
    public void addCustomer(CustomerAccount acc)
    {
        Customers.add(acc);
    }
    
    public CustomerAccount findCustomer(String regNumb) 
            throws CustomerNotFoundException
    {
        int i = 0;
        while (this.Customers.size() > i )
/*I previously used a for loop however I changed to 
a while loop here as if the customer is found before it will exit the loop */
        {
            if (this.Customers.get(i).getVehicle().getRegNumb().equals(regNumb))
            {
                return this.Customers.get(i);
            }
            i++;
        }
        throw new CustomerNotFoundException();
    }

    public void chargeCustomer(String registrationNumber) 
            throws InsufficientAccountBalanceException,CustomerNotFoundException
    {
        CustomerAccount customerFound = null;
        customerFound = findCustomer(registrationNumber);
        int tripCost = customerFound.makeTrip();
        moneyMade = moneyMade + tripCost;
    }
    
    /* Previous Charge Customer method, deleted because I need to catch the 
    exceptions in main rather than tollroad

    public void chargeCustomer(String registrationNumber)
    {
        CustomerAccount customerFound = null;
        try
        {
            customerFound = findCustomer(registrationNumber);
        }
        catch (CustomerNotFoundException e)
        {
            System.err.print("CustomerAccount does not exist.");
            System.err.println("Caught the exception" + e);
        }
        try
        {
            int tripCost = customerFound.makeTrip();
            moneyMade = moneyMade +tripCost;
        }

        catch (InsufficientAccountBalanceException e)
        {
            System.err.println("Caught the exception" + e);
        }
    }
    */

    @Override
    public String toString()
    {
        return "moneyMade=" + moneyMade + ", Customers=" + Customers;
    }
}
