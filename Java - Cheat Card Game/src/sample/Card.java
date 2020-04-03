package sample;

import org.jetbrains.annotations.TestOnly;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

/**
 * Update log:
 *
 *      Completed:
 *          Card Class
 *              2)  Rank + Suit enums + GetPrev + getValue + RandomSuit
 *              3)  Class attributes + constructor + accessors + toString
 *              4)  comparable interface
 *              5/6)  Difference values/rank
 *              7) CompareAscending and CompareSuit methods
 *
 *
 *
 *      Working on:
 *          Card class
 *                  serializable(1) - Still to do
 *
 * 8) This question requires you to demonstrate your understanding of lambdas
 * and the use of comparators. Write a static method in the Card class called
 * selectTest that contains a list of cards that you initialise in the method. It does
 * not matter what these cards are. selectTest should take a single card as
 * input, iterate across the list and compare to each card in three ways, printing
 * relevant information to the console. It should compare with the two
 * comparators from question 7 and with a lambda. The lambda should
 * implement the logic that Card A is greater than Card B if the rank of A is
 * greater than the rank of B or, if the ranks are equal, then Card A is greater
 * than Card B if the suit of A is less than the suit of B. This is simply an exercise
 * to test you know how to do this.
 *
 * 9. Write a main method in Card that demonstrates your code is correct by calling
 * the methods you have implemented with informative output to the console.
 * Also demonstrate how to serialise a Card.
 *
 *
 *      STILL TO WORK ON:
 *
 *
 */
public class Card implements Serializable, Comparable<Card>
{
    public final void writeCard(Card card) throws IOException
    {

    }
    // Class attributes
    private Rank rank;
    private Suit suit;

    public enum Rank
    {
        TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8),
        NINE(9), TEN(10), JACK(10), QUEEN(10), KING(10), ACE(11);

        // Method to return the integer value of a card's rank
        final int value;

        Rank(int x)
        {
            value = x;
        }

        public int getValue()
        {
            return value;
        }

        // Method to return the previous enum
        public Rank getPrevious()
        {
            int previous = ordinal() -1;
            if (previous < 0)
            {
                previous = previous + values().length;
            }
            return Rank.values()[previous];
        }

        // Method to return the next enum (Useful for question 2)
        public Rank getNext()
        {
            int next = ordinal() +1;
            if (next > 12)
            {
                next = TWO.ordinal();
            }
            return Rank.values()[next];
        }
    }

    public enum Suit
    {
        CLUBS, DIAMONDS, HEARTS, SPADES;

        // Method to select a suit at random
        public static Suit randomSuit()
        {
            double random = Math.random() * 3; // Gives values 0-3
            int rounded = (int)Math.round(random); // Cast to int
            return Suit.values()[rounded];
        }
    }


    // Constructor
    public Card(Rank rank, Suit suit)
    {
        this.rank = rank;
        this.suit = suit;
    }


    // Accessors
    public Suit getSuit()
    {
        return suit;
    }
    public Rank getRank()
    {
        return rank;
    }


    // To String Method

    @Override
    public String toString()
    {
        return (rank + " of " + suit);
    }

    // Comparable interface
    public int compareTo(Card o)
    {
        int compareRank = difference(this.rank, o.rank);
        int compareSuit = this.suit.ordinal() - o.suit.ordinal();
        if (compareRank>0)
        {
            return 1;
        }
        if (compareRank<0)
        {
            return -1;
        }
        else {
            if (compareSuit<0)
            {
                return 1;
            }
            else if (compareSuit==0)
            {
                return 0;
            }
            else {
                return -1;
            }
        }
    }

    // Method to find the difference between the ranks of cards ordinal e.g. 1:13
    public static int difference(Rank a, Rank b)
    {
        return a.ordinal() - b.ordinal();
    }

    // Method to find the difference between the card values(1:10) i.e face cards = 10
    public static  int differenceValue(Rank a, Rank b)
    {
        return a.getValue() - b.getValue();

    }

    // This class is to sort the cards in ascending order of rank
    public static class CompareAscending implements Comparator<Card>
    {
        public int compare(Card a, Card b)
        {
            int compare = difference(a.rank, b.rank);
            if (compare > 0)
            {
                return 1;
            }
            if (compare < 0)
            {
                return -1;
            } else {
                return a.suit.compareTo(b.suit);
            }
        }
    }
    // This class is to sort the cards in descending order of rank
    public static class CompareDescending implements Comparator<Card>
    {
        public int compare (Card a, Card b)
        {
            int compare = difference(a.rank,b.rank);
            if (compare>0)
            {
                return -1;
            }
            if (compare<0)
            {
                return 1;
            }
            else {
                return a.suit.compareTo(b.suit);
            }
        }
    }

    public static class CompareSuit implements Comparator<Card>
    {
        public int compare (Card a, Card b)
        {
            return a.suit.compareTo(b.suit);
        }
    }

    public static void SelectTest(Card cardToCompare)
    {

        ArrayList<Card> cardArrayList = new ArrayList<Card>();


        for (int i=0;i<13;i++)
        {
            Card cardA = new Card(Rank.values()[i],Suit.randomSuit());
            Card cardB = new Card(Rank.FOUR.getPrevious(),Suit.randomSuit()); // 4 added to make the list unordered
            cardArrayList.add(cardA);
            cardArrayList.add(cardB);
        }

        Card cardA = new Card(Rank.TEN,Suit.DIAMONDS);
        cardArrayList.add(cardA);
        Card cardB = new Card(Rank.TEN,Suit.SPADES);
        cardArrayList.add(cardB);
        Card cardC = new Card(Rank.TWO,Suit.CLUBS);
        cardArrayList.add(cardC);
        Card cardD = new Card(Rank.SIX,Suit.HEARTS);
        cardArrayList.add(cardD);
        Card cardE = new Card(Rank.SIX,Suit.DIAMONDS);
        cardArrayList.add(cardE);

        System.out.println("Difference of 10 DIA / 10 SPADE " + differenceValue(cardA.rank,cardB.rank));
        System.out.println("============================================");
        System.out.println("List of cards Unsorted");
        for (Card i : cardArrayList)
        {
            System.out.println(i);
        }
        cardArrayList.sort(new CompareAscending() );
        System.out.println("============================================");
        System.out.println("Sorted into ascending order::");
        for (Card i : cardArrayList)
        {
            System.out.println(i);
        }
        cardArrayList.sort(new CompareDescending() );
        System.out.println("============================================");
        System.out.println("Sorted into descending order::");
        for (Card i : cardArrayList)
        {
            System.out.println(i);
        }
        System.out.println("============================================");
        System.out.println("List of cards ordered by suit");
        cardArrayList.sort(new CompareSuit());
        for (Card i : cardArrayList)
        {
            System.out.println(i);
        }
    }

    //Lambda attempt
    public void sortByRankThenSuit()
    {
        List<Card> cardList = new ArrayList<Card>();
        Card cardA = new Card(Rank.TEN,Suit.DIAMONDS);
        cardList.add(cardA);
        Card cardB = new Card(Rank.ACE,Suit.SPADES);
        cardList.add(cardB);
        System.out.println("**************************************");
        for (Card card: cardList)
        {
            System.out.println(card);
        }
        System.out.println("**************************************");
        cardList.sort(
                (Card card1, Card card2)-> {
                    return cardA.getRank().compareTo(cardB.getRank());
                }
        );
        for (Card card: cardList)
        {
            System.out.println(card);
        }
    }


    public static void main(String[]args)
    {
        Card a = new Card(Rank.FOUR,Suit.HEARTS);
        SelectTest(a);
        previousTesting();
        a.sortByRankThenSuit();

        try{
            Card cardA = new Card(Rank.FOUR,Suit.HEARTS);
            //Creating stream and writing the object
            FileOutputStream fout = new FileOutputStream("100256606"); // Student ID
            ObjectOutputStream out = new ObjectOutputStream(fout);
            out.writeObject(cardA);
            out.flush();
            //closing the stream
            out.close();
        }catch(Exception e){System.out.println(e);}
    }


    public static void previousTesting()
    {

        System.out.println("==================================");
        //Creating 3 different cards for testing
        Card cardA = new Card(Rank.TEN,Suit.CLUBS);
        System.out.println(cardA);
        System.out.println(cardA+" has the previous rank of " + cardA.rank.getPrevious());
        System.out.println(cardA+ " has the value: " + cardA.rank.value);
        System.out.println(cardA+" The Previous suit is "+cardA.rank.getPrevious());
        Card cardB = new Card(Rank.TEN,Suit.randomSuit());
        System.out.println(cardB);
        System.out.println("The difference between " + cardA + " and " + cardB +" is "+
                differenceValue(cardA.getRank(),cardB.getRank()));
        System.out.println("The difference between " + cardA + " and " + cardB +" is "+
                difference(cardA.getRank(),cardB.getRank()));
        Card cardC = new Card(Rank.JACK,Suit.CLUBS);
        System.out.println(cardC);

        System.out.println();



        // Testing printing out 13 cards of random suits

        for (int i = 0; i < Card.Rank.values().length; i++) // For every rank(13)
        {
            Card newCard = new Card(Rank.values()[i],Suit.randomSuit());
            System.out.println(newCard.rank.getPrevious());

        }

    }
}

