package toll.road.simulator;
import java.util.Comparator;

/**
 *
 * @author rkb18wyu
 */
public class CustomerAccount implements Comparable<CustomerAccount>
{
    private String firstName;
    private String secondName;
    private Vehicle vehicle;
    private static int accountBalance;
    private enum discountType{NONE,STAFF,FRIENDS_AND_FAMILY};
    discountType DiscountType;

    public CustomerAccount(String firstName, String secondName, Vehicle vehicle,
            int accountBalance)
   {
       this.firstName = firstName;
       this.secondName = secondName;
       this.vehicle = vehicle;
       this.accountBalance = accountBalance;
       discountType DiscountType = discountType.NONE; 
       //sets the default discount value to NONE 
       this.DiscountType = DiscountType;
   }
    
   public String getFirstName()
   {
       return firstName;
   }
   
   public String getSecondName()
   {
       return secondName;
   }
   
   public Vehicle getVehicle()
   {
       return vehicle;
   }
      
   public int getAccountBalance()
   {
       return accountBalance;
   }
   
   public discountType getDiscountType()
   {
       return DiscountType;
   }
          
   public void activateStaffDiscount()
   {
       DiscountType = discountType.STAFF;
   }
   
   public void activateFriendsAndFamilyDiscount()
   {
       if (DiscountType != discountType.STAFF)
       {
           DiscountType = discountType.FRIENDS_AND_FAMILY;
       }
   }
   
   public void deactivateDiscount()
   {
       DiscountType = discountType.NONE;
   }
   
   public static void addFunds(int amount)
   {
       accountBalance = accountBalance + amount;
   }
   
   public int makeTrip() throws InsufficientAccountBalanceException
   {
       int costOfTrip = 0; //Initialised to 0.
       if (null != getDiscountType())
                  //Statements based on discount types
        switch (getDiscountType()) {
        //Gives staff a 50% discount
            case STAFF:
                costOfTrip = (int) (vehicle.calculateBasicTripCost() / 2);
                break;
        //Gives family and friends a 10% discount
            case FRIENDS_AND_FAMILY:
                costOfTrip = (int) (vehicle.calculateBasicTripCost() * 0.9);
                break;
        /*If there is no discount it just calculates the full cost of
        the trip */
            case NONE:
                costOfTrip = (vehicle.calculateBasicTripCost());
                break;
            default:
                break;
        }
       
       //Statements based on the account balance and the cost of the trip 
       if (costOfTrip > accountBalance)
       {
           throw new InsufficientAccountBalanceException();
       }
       else if (costOfTrip <= accountBalance)
       {
           accountBalance = accountBalance - costOfTrip;
       }
       return costOfTrip;
   }
   
   //Comparable interface
   @Override
   public int compareTo(CustomerAccount acc)
   {
       int compareValue = 
               (this.vehicle.getRegNumb().compareTo(acc.vehicle.getRegNumb()));
       if (compareValue < 0)
       {
           return -1;
       }
       else if (compareValue > 0)
       {
           return 1;
       }
       else
       {
           return 0;
       }
   }

    @Override
    public String toString()
    {
        return "CustomerAccount{" +
                "firstName='" + getFirstName() + '\'' +
                ", secondName='" + getSecondName() + '\'' +
                ", vehicle=" + getVehicle() +
                ", accountBalance=" + getAccountBalance() +
                ", DiscountType=" + getDiscountType() +
                '}';
    }

     /*Testing all the different changes that can be made to the discount
    public void testingDiscounts()
    {

        CustomerAccount t = new CustomerAccount
    ("Jose","Phelps",Vehicle t1 = new Vehicle("HQ09WIJ", "Ford", 600),700);
        t.getDiscountType();
        t.activateStaffDiscount();
        t.getDiscountType();
        t.deactivateDiscount();
        t.getDiscountType();
        t.activateFriendsAndFamilyDiscount();
        t.getDiscountType();
    }*/
}
