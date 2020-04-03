package sample;

import java.util.Random;

/**
 * Thinker strategy following the rule set:

 * 1. Decision on whether to cheat. The Thinker should of course cheat if it has
 * to. It should also occasionally cheat when it doesn’t have to.
 *
 * COMPLETE
 *      10% chance to cheat (if not needed)
 *
 * 2. Choose hand. If cheating, the Thinker should be more likely to choose higher
 * cards to discard than low cards. If not cheating, it should usually play all its
 * cards but occasionally play a random number.
 *
 * COMPLETE
 *      20% chance it wont play all its cards broken up into:
 *          10% chance it plays one card
 *          10% chance it plays a random number of cards
 *
 * 3. Calling Cheat. The Thinker should attempt to make an informed decision to
 * call cheat on another player. It should store all of its own cards it has placed in
 * the discard, then examine this record (in conjunction with the current hand) to
 * decide on whether to call cheat. It should always call cheat if the bid is not
 * possible based on previous known play. It should then call cheat with a small
 * probability p (set as a parameter) dependent on how many of the current rank
 * are in the current discard pile. The exact way you implement this is up to you
 *
 * working on
 * Create set probability p fucntion
 *
 *
 *
 *
 */

public class ThinkerStrategy implements Strategy
{
    /**
     * Decides on whether to cheat or not
     *
     * @param b the bid this player has to follow (i.e the
     *          bid prior to this players turn.
     * @param h The players current hand
     * @return true if the player will cheat, false if not
     */
    public boolean cheat(Bid b, Hand h)
    {
        double a = Math.random()*10;
        int random = (int) a;
        if (random == 0) // 1/10  chance the user will cheat if not required
        {
            return true;
        }
        else {
            for (Card handCard : h.getHandArrayList())
            {
                if (handCard.getRank() == b.getRank() | handCard.getRank() == b.getRank().getNext())
                {
                    return false;
                }
            }
        }
        return true;
    }


    /**
     * @param b     the bid the player has to follow.
     * @param h     The players current hand
     * @param cheat true if the Strategy has decided to cheat (by call to cheat())
     * @return a Bid with the cards to pass to the game and the Rank. This will be
     * different to the rank of thecards if the player is cheating!
     */
    public Bid chooseBid(Bid b, Hand h, boolean cheat)
    {
        Bid bid = new Bid();
        if (!cheat(b,h))
        {
            //Creates new random number
            double a = Math.random()*3.33333333333;
            int random = (int) a;
            int numHigherCards = h.countRank(b.getRank().getNext());
            // 30% chance of playing lower over higher
            if ((numHigherCards > 0) && (random==0))
            {
                int removed =0;
                int size = h.getHandArrayList().size();
                for (int i=0;i<size;i++)
                {
                    Card card = h.getHandArrayList().get(i-removed);
                    if (card.getRank() == b.getRank().getNext())
                    {
                        bid.setRank(card.getRank());
                        bid.getHand().addSingleCard(card);
                        discardedHand.addSingleCard(card);
                        h.removeSingleCard(card);
                        removed++;
                        // 20% chance of stopping after adding a new card (per time adding a card)
                        a = Math.random()*5;
                        random = (int) a;
                        if (random==0)
                        {
                            return bid;
                        }
                    }
                }
                return bid;
            }
            else
            {
                int removed=0;
                int size = h.getHandArrayList().size();
                for (int i=0;i<size;i++)
                {
                    Card card = h.getHandArrayList().get(i-removed);
                    if (card.getRank() == b.getRank())
                    {
                        bid.setRank(card.getRank());
                        bid.getHand().addSingleCard(card);
                        discardedHand.addSingleCard(card);
                        h.removeSingleCard(card);
                        removed++;
                        // 20% chance of stopping after adding a new card (per time adding a card)
                        a = Math.random()*5;
                        random = (int) a;
                        if (random==0)
                        {
                            return bid;
                        }
                    }
                }
                return bid;
            }
        }
        // If can't play, play a random card
        else
        {
            double rnd = Math.random()*h.getHandArrayList().size();
            int rounded = (int) Math.round(rnd);
            Card randomCard = h.getHandArrayList().get(rounded);
            bid.setRank(randomCard.getRank());
            bid.getHand().addSingleCard(randomCard);
            discardedHand.addSingleCard(randomCard);
            h.removeSingleCard(randomCard);
            return bid;
        }
    }


    /**
     *
     * 3. Calling Cheat. The Thinker should attempt to make an informed decision to
     * call cheat on another player. It should store all of its own cards it has placed in
     * the discard, then examine this record (in conjunction with the current hand) to
     * decide on whether to call cheat. It should always call cheat if the bid is not
     * possible based on previous known play. It should then call cheat with a small
     * probability p (set as a parameter) dependent on how many of the current rank
     * are in the current discard pile. The exact way you implement this is up to you
     *
     *
     * @param b the current bid
     * @param h     The players current hand
     * @return true if this player is going to call cheat  on the last play b
     */
    public boolean callCheat(Hand h, Bid b)
    {
        double a = Math.random()*probability;
        int random = (int) a;
        // If the player has more of the rank than there should be based on the players bid call cheat
        if (h.countRank(b.getRank())>(4-b.getCount()))
        {
            return true;
        }
        if (b.getCount() > 4)
        {
            return true;
        }
        int rankInHand = h.countRank(b.getRank());
        int rankInDiscard = discardedHand.countRank(b.getRank());
        int count  = rankInDiscard + rankInHand;
        switch (count)
        {
            case 1:
                if (random>10)
                return true;
            case 2:
                if (random>20){
                    return true;
                }
        }

        int noOfRankOwned = h.countRank(b.getRank());
        int noOfRankDiscarded = discardedHand.countRank(b.getRank());
        int ranksRemaining = 4 - noOfRankOwned + noOfRankDiscarded;
        if (ranksRemaining<=0)
        {
            return true;
        }
        return false;
    }


    // This hands will be used to note all the cards that have left the thinkers hand
    public Hand discardedHand = new Hand();

    public void setProbability(int probability) {
        this.probability = probability;
    }

    private int probability;


    public static void main(String[] args)
    {
        //testCheat();
        testChooseBid();
        //testCallCheat();

    }

    public static void testCheat()
    {
        //Currently cheats every time
        int count = 0;
        for (int z =0;z<30;z++)
        {
            ThinkerStrategy thinkerStrategy = new ThinkerStrategy();
            Hand hand = new Hand();
            Card card = new Card(Card.Rank.ACE, Card.Suit.HEARTS);
            hand.addSingleCard(card);
            Bid bid = new Bid();
            Boolean cheat = thinkerStrategy.cheat(bid,hand);
            if (cheat==true)
            {
                System.out.print("cheated: " +count);
                count++;
            }
        }
    }


    public static void testChooseBid()
    {
        //Test if it will cheat
        for (int z =0;z<30;z++)
        {
            ThinkerStrategy thinkerStrategy = new ThinkerStrategy();
            // Create hand of the whole deck
            Hand hand = new Hand();
            for (int i = 0; i < Card.Suit.values().length; i++)
            {
                for (int j = 0; j < Card.Rank.values().length; j++)
                {
                    Card card = new Card(Card.Rank.values()[j], Card.Suit.values()[i]);
                    hand.addSingleCard(card);
                }
            }
            thinkerStrategy.setProbability(50);
            Bid bid = new Bid();
            bid = thinkerStrategy.chooseBid(bid,hand,thinkerStrategy.cheat(bid,hand));
            System.out.println(bid.getCount());
        }
    }

    public static void testCallCheat()
    {
        // Test if it will call


    }


}