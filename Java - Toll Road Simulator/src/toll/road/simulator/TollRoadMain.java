package toll.road.simulator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.charset.IllegalCharsetNameException;
import java.util.ArrayList;
import static toll.road.simulator.CustomerAccount.*;

/**
 *
 * @author rkb18wyu
 */
public class TollRoadMain extends TollRoad 
{
    public static TollRoad initialiseTollRoadFromFile() 
    {
        TollRoad road = new TollRoad();
        try 
        {

            BufferedReader br = new BufferedReader(new FileReader
        ("customerData.txt"));
            String currentLine = br.readLine();
            String customerData = "";
            while (currentLine != null) 
            {
                /* Reads through the txt file line by line and 
                assigns the txt file to the collection */
                customerData = customerData + currentLine;
                currentLine = br.readLine();
            }
            String[] parts = customerData.split("#");
            for (int i = 0; i < parts.length; i++) 
            {
                /* Splits the collection up and stores 
                the values to corresponding identifiers */
                String[] info = parts[i].split(",");
                String vehicleType = info[0];
                String regNum = info[1];
                String firstName = info[2];
                String lastName = info[3];
                String vehicleMake = info[4];
                int vehicleInfo = Integer.parseInt(info[5]);
                int startingBalance = Integer.parseInt(info[6]);
                String discountType = info[7];
                // The collection is stored based on the vehicle type.
                Vehicle vehicle = null;
                switch (vehicleType) 
                {
                    case "Van":
                        vehicle = new Van(regNum, vehicleMake, vehicleInfo);
                        break;
                    case "Car":
                        vehicle = new Car(regNum, vehicleMake, vehicleInfo);
                        break;
                    case "Truck":
                        vehicle = new Truck(regNum, vehicleMake, vehicleInfo);
                        break;
                    default:
                        // Error handling if incorrect vehicle type
                        System.out.println("Error: Vehicle type not found.");
                        break;
                }
                CustomerAccount customer = new CustomerAccount(firstName,
                        lastName, vehicle, startingBalance);
                road.addCustomer(customer);

                switch (discountType) 
                {
                    case "STAFF":
                        customer.activateStaffDiscount();
                        break;
                    case "FRIENDS_AND_FAMILY":
                        customer.activateFriendsAndFamilyDiscount();
                        break;
                    default:
                        break;
                }
            }
            br.close();
            //ends the bufferreader and calls flush
        } 
        catch (Exception e) 
        {
            // Error handling for the buffer reader
            System.err.println(e);
        }
        return road;
    }

    public static void simulateFromFile(TollRoad road) 
    {
        try 
        {
            BufferedReader br = new BufferedReader(new FileReader(
                    "transactions.txt"));
            String currentLine = br.readLine();
            String transactions = "";
            while (currentLine != null) 
            {
                /*Reads through the txt file line by line and assigns the 
                txt file to the collection*/
                transactions = transactions + currentLine;
                currentLine = br.readLine();
            }
            String[] parts = transactions.split("\\$");
            for (int i = 0; i < parts.length; i++) 
            {
                /* Splits the collection up and stores the 
                values to corresponding identifiers */
                String[] info = parts[i].split(",");
                String operation = info[0];
                String regNum = info[1];
                // The collection is stored based on the vehicle type.
                try 
                {
                    CustomerAccount customerAccount = road.findCustomer(regNum);
                    switch (operation) 
                    {
                        case "addFunds":
                            int amount = Integer.parseInt(info[2]);
                            CustomerAccount.addFunds(amount);
                            if (customerAccount == null) 
                            {
                                System.out.println(regNum + ": " + operation
                                        + " failed."
                                        + " CustomerAccount does not exist");
                            } 
                            else 
                            {
                                System.out.println(regNum + ": " + amount
                                        + " added successfully");
                            }
                            break;
                        case "makeTrip":
                            road.chargeCustomer(regNum);
                            if (customerAccount == null) 
//If no customer account is found it prints the error messge that
                            {
                                System.out.println(regNum + ": " + operation
                                        + " failed."
                                        + " CustomerAccount does not exist");
                            } 
                            else 
                            {
                                System.out.println(regNum + ": Trip completed "
                                        + "successfully");
                            }
                            break;
                        default:
                            // Error handling if incorrect operation
                            System.out.println("Error: Operation not found.");
                            break;
                    }
                } 
                catch (CustomerNotFoundException e) 
                {
                    System.out.println(regNum + ":" + operation + " failed. "
                            + "CustomerAccount does not exist");
                } 
                catch (InsufficientAccountBalanceException ee) 
                {
                    System.err.println("Insufficient Account Balance");
                }
            }
        } 
        catch (Exception e) 
        {
            // Error handling for the buffer reader
            System.err.println(e);
        }
    }

    public static void main(String args[]) 
    {
        TollRoad test;
        test = initialiseTollRoadFromFile();
        simulateFromFile(test);
        System.out.println("Total money made is £" + test.getMoneyMade());
    }
}
