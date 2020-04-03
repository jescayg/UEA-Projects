
package sample;

import java.util.Random;

/**
 * My strategy
 *
 * If you can win (4 cards in hand and lower):
 *      Y.O.L.O - play all the cards in hand
 * Else if:
 *  have the rank play the single card of that rank
 * else:
 *  always play 2 lowest cards,
 *
 * call cheat 100% if certain based on previous plays
 *
 */

public class MyStrategy implements Strategy
{
    // This hands will be used to note all the cards that have left the  hand
    public Hand discardedHand = new Hand();


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
        for (Card handCard : h.getHandArrayList())
        {
            if (handCard.getRank() == b.r)
            {
                return true;
            }
        }
        return false;
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
        if (h.getHandArrayList().size() <= 4)
        {
            // If you can win play all 4 cards
            bid.getHand().addHand(h);
            h.removeAll(h);
            bid.setRank(b.getRank());
            return bid;
        }
        else {
            Card cardA = h.getHandArrayList().get(0);
            Card cardB = h.getHandArrayList().get(0);
            bid.getHand().addSingleCard(cardA);
            bid.getHand().addSingleCard(cardB);
            bid.setRank(b.getRank().getNext());
            h.removeSingleCard(cardA);
            h.removeSingleCard(cardB);
            return bid;
        }
    }


    /**
     * @param b the current bid
     * @return true if this player is going to call cheat  on the last play b
     */

    public boolean callCheat(Hand h, Bid b)
    {
        // Call strategy: Only call if 100% certain based on all known cards
        int noOfRankOwned = h.countRank(b.getRank());
        int noOfRankDiscarded = discardedHand.countRank(b.getRank());
        int ranksRemaining = 4 - noOfRankOwned + noOfRankDiscarded;
        if (ranksRemaining<=0)
        {
            return true;
        }
        return false;
    }
}