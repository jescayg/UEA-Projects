/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toll.road.simulator;

/**
 *
 * @author RKB18WYU
 */
public class CustomerNotFoundException extends Exception
{
    public CustomerNotFoundException()
    {
    }
    
    @Override
    public String toString()
    {
        return "Error: Customer not found. Please try again.";
    }
}