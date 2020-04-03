package sample;

import java.util.Scanner;

public class HumanStategy implements Strategy {
    @Override
    public boolean cheat(Bid b, Hand h)
    {
        System.out.print("Current bid: " + b);
        System.out.print("Current hand: " + h);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Do you want to cheat? [y/n]");
        String cheat = scanner.nextLine();  // Read user input
        if (cheat=="y")
        {
            return true;
        }
        if (cheat=="n")
        {
            for (Card card : h.getHandArrayList())
            {
                // Only if the user really can play the truth
                if (card.getRank() == b.getRank() | card.getRank() == b.getRank().getNext())
                {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public Bid chooseBid(Bid b, Hand h, boolean cheat)
    {
        return null;
    }

    @Override
    public boolean callCheat(Hand h, Bid b)
    {
        System.out.print("Current bid: " + b);
        System.out.print("Current hand: " + h);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Do you want to cheat? [y/n]");
        String cheat = scanner.nextLine();  // Read user input
        if (cheat=="y")
        {
            return true;
        }
        if (cheat=="n")
        {
            return false;
        }
        else {
            System.out.print("Invalid try again");
            callCheat(h,b);
        }
        return false;
    }
}
