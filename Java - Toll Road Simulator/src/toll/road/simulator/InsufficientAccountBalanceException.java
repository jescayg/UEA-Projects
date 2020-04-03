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
public class InsufficientAccountBalanceException extends Exception
{
    public InsufficientAccountBalanceException()
    {
    }
    
    @Override
    public String toString()
    {
        return "Error: Insufficient account balance. Please add funds to the "
                + "account and then try again.";
    }
}
