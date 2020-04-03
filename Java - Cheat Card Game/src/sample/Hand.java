package sample;

import org.jetbrains.annotations.NotNull;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Hand 
{
    private ArrayList<Card> handArrayList;
    private int [] numOfEachRank;

    public Hand()
    {
        handArrayList =new ArrayList<Card>();
        updateRankCount();
    }

    public int[] getNumOfEachRank()
    {
        return numOfEachRank;
    }


    public ArrayList<Card> getHandArrayList()
    {
        return handArrayList;
    }

    public Hand(@NotNull Card[] cards)
    {
        for (Card i : cards)
        {
            addSingleCard(i);
        }
        updateRankCount();
    }
    public Hand(@NotNull Hand hand)
    {
        addHand(hand);
        updateRankCount();
    }
    // Method to take rank as an argument and return the number of cards of that rank
    public int countRank(Card.Rank rank)
    {
        updateRankCount();
        return numOfEachRank[rank.ordinal()];
    }

    public String toString()
    {
        System.out.println("cards in hand: ");
        for (Card i : handArrayList)
        {
            System.out.print(i + ", ");
        }
        System.out.println();
        System.out.println("\n number of each rank");
        for (int i : numOfEachRank)
        {
            System.out.print(i+" , ");
        }
        return null;
    }

    // Method to calculate the value of the cards in the hand
    public int handValue()
    {
        int value = 0;
        for (Card i : handArrayList)
        {
            value = value + i.getRank().getValue();
        }
        return value;
    }

    // Method to check if the hand array contains a complete set of cards of the same suit
    public boolean isFlush()
    {
        for (Card i : handArrayList)
        {
            for (Card j : handArrayList)
            {
                if (i.getSuit()!=j.getSuit())
                {
                    return false;
                }
            }
        }
        return true;
    }

    // Method to check if the hand array is a descending set of cards with no duplicates
    public boolean isStraight()
    {
        // Use sort by descending to sort the hand then compare rank
        handArrayList.sort(new Card.CompareDescending());
        for (int i=1;i>=handArrayList.size()-1;i++)
        {
            Card card1 = handArrayList.get(i-1);
            Card card2 = handArrayList.get(i);
            if (card2.getRank() != card1.getRank().getPrevious())
            {
                return false;
            }
        }
        return true;
    }

    public void addSingleCard(Card card)
    {
        handArrayList.add(card);
        updateRankCount();
    }
    public void addHand(Hand hand)
    {
        handArrayList.addAll(hand.handArrayList);
        updateRankCount();
    }
    public void addCollection()
    {

    }
    public void updateRankCount()
    {
        numOfEachRank = new int[13];
        for (int i = 0; i< numOfEachRank.length; i++)
        {
            numOfEachRank[i]=0;
        }
        for (Card i : handArrayList)
        {
            numOfEachRank[i.getRank().ordinal()]++;
        }
    }

    public void sortDescending()
    {
        handArrayList.sort(new Card.CompareDescending());
    }
    public void sortAscending()
    {
        handArrayList.sort(new Card.CompareAscending());
    }
    public void removeSingleCard(Card card)
    {
        if (handArrayList.contains(card))
        {
            handArrayList.remove(card);
            updateRankCount();
        }
    }
    public void removeAll(Hand hand)
    {
        for (Card card : hand.handArrayList)
        {
            removeSingleCard(card);
        }
        updateRankCount();
    }
    public void removePosition(int pos)
    {
        handArrayList.remove(pos);
        updateRankCount();
    }


    public static void main(String[] args)
    {
        testing();
        testing();

        try
        {
            Hand h = new Hand();
            //Creating stream and writing the object
            FileOutputStream fout = new FileOutputStream("100256616"); // Student ID 100256606 +10
            ObjectOutputStream out = new ObjectOutputStream(fout);
            out.writeObject(h);
            //closing the stream
            out.close();
        }
        catch(Exception e){System.out.println(e);
        }
    }

    public static void testing()
    {
        // Building a hand to test functions
        Hand hand = new Hand();
        for (int i=0;i<13;i++)
        {
            Card cardA = new Card(Card.Rank.values()[i], Card.Suit.randomSuit());
            hand.addSingleCard(cardA);
        }
        Card cardD = new Card(Card.Rank.EIGHT,Card.Suit.DIAMONDS);
        Card cardE = new Card(Card.Rank.EIGHT,Card.Suit.CLUBS);
        hand.addSingleCard(cardD);
        hand.addSingleCard(cardE);
        System.out.println(hand);
        hand.removeSingleCard(cardE);
        System.out.println("card removed"+hand);
        System.out.println("hand value " + hand.handValue());


        System.out.println("Hand in ascending order:");
        hand.sortAscending();
        System.out.println(hand);
        hand.removePosition(2);
        System.out.println("Hand after removing 3rd index: \n");
        System.out.println(hand);

        System.out.println("Hand after removing 3rd index: \n");

        System.out.println("Hand in descending order:");
        hand.sortDescending();
        System.out.println(hand);
        System.out.println("Hand after being removed");
        hand.removeAll(hand);
        System.out.println(hand);
    }
}
