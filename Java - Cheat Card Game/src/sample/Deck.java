/**
 * Deck class:
 * Holds methods to:
 * Shuuffle the arrayy of cards,
 * deal the top card from the ddeck
 *
 *
 *
 */

package sample;

import javax.lang.model.type.NullType;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

public class Deck implements Iterator<Card>, Serializable
{
    private final int deckSize = 52;
    private int position;

    public int getDeckSize()
    {
        return deckSize;
    }

    public Card[] getCardArray()
    {
        return cardArray;
    }

    private Card[] cardArray = new Card[deckSize];


    public int getPosition()
    {
        return position;
    }

    // Deck constructor
    public Deck()
    {
        newDeck();
    }

    public int size()
    {
        return deckSize - position;
    }

    //Method to print the current deck out, used for testing.
    public void printDeck()
    {
        for (int i=0;i <deckSize;i++)
        {
            System.out.println(cardArray[i]);
        }
    }


    public final void newDeck()
    {
        position=0;
        cardArray = new Card[deckSize];
            for (int i = 0; i < Card.Suit.values().length; i++)
            {
                for (int j = 0; j < Card.Rank.values().length; j++)
                {
                    Card card = new Card(Card.Rank.values()[j], Card.Suit.values()[i]);
                    this.cardArray[position] = (card);
                    position++;
                }
            }
            position=0;
    }

    @Override
    public boolean hasNext()
    {
        return (position<=deckSize);
    }

    @Override
    public Card next()
    {
        return deal();
    }

    private class dealOrder implements Iterator<Card>
    {
        @Override
        public boolean hasNext()
        {
            return (position<=deckSize);
        }
        @Override
        public Card next()
        {
            return deal();
        }

    }

    //Method to remove the top card from the deck and return it
    private Card deal()
    {
        Card topCard = cardArray[position];
        cardArray[position]=new Card(null,null);
        position++;
        return topCard;

    }

    public void shuffle()
    {
        newDeck();
        Random random = new Random();
        for (int i=0;i<cardArray.length;i++)
        {
            int randomPos = random.nextInt(cardArray.length);
            Card temp = cardArray[i];
            cardArray[i] = cardArray[randomPos];
            cardArray[randomPos] = temp;
        }
    }


    // Haven't yet tested but i believe the logic is correct
    private class OddEvenIterator implements Iterator<Card>
    {
        @Override
        public boolean hasNext() {
            return  (position>=deckSize);

        }
        @Override
        public Card next(){
            if (position==0)
            {
                position++;
                return deal();
            }
            if (position == 51)
            {
                position=0;
                return deal();
            }
            else {
                position++;
                return deal();
            }
        }

    }
    public Iterator<Card> iterator()
    {
        return new OddEvenIterator();
    }


    public static void shuffleTest()
    {
        // Testing shuffle func
        Deck deck = new Deck();
        deck.printDeck();
        System.out.println("===============================");
        deck.shuffle();
        deck.printDeck();
        System.out.println("===============================");
        deck.shuffle();
        deck.printDeck();
        System.out.println("===============================");
    }

    public static void dealTest()
    {
        Deck deck = new Deck();
        deck.printDeck();
        System.out.println("===============================");
        System.out.println(deck.cardArray[deck.getPosition()]);
        deck.deal();
        System.out.println(deck.cardArray[deck.getPosition()]);
        deck.deal();
        System.out.println(deck.cardArray[deck.getPosition()]);
        deck.shuffle();
        System.out.println("===============================");
        deck.printDeck();
        System.out.println("===============================");
        System.out.println(deck.deal());
        System.out.println(deck.deal());
        System.out.println(deck.deal());

        for (int i = 0; i < deck.cardArray.length; i++) {
            System.out.println(deck.next());
        }
        System.out.println("**********************************");
        deck.newDeck();
    }

    public static void iteratorTest()
    {

    }



    public static void main(String[] args)
    {
        dealTest();
        iteratorTest();
        shuffleTest();

        try
        {
            Deck d = new Deck();
            //Creating stream and writing the object
            FileOutputStream fout = new FileOutputStream("100256596"); // Student ID 100256606 -10
            ObjectOutputStream out = new ObjectOutputStream(fout);
            for (Card card : d.cardArray)
            {
                out.writeObject(card);
            }
            out.flush();
            //closing the stream
            out.close();
        }
        catch(Exception e){System.out.println(e);
        }

    }
}
