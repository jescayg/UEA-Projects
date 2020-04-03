package sample;

/**
 * Basic strategy following the rule set:
 *
 1. Never cheat unless you have to. If a cheat is required, play a single
 card selected randomly;

 2. If not cheating, always play the maximum number of cards possible of
 the lowest rank possible;

 3. Call another player a cheat only when certain they are cheating (based
 on your own hand)
 */

public class BasicStrategy implements Strategy
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
        for (Card handCard : h.getHandArrayList())
        {
            if (handCard.getRank() == b.r | handCard.getRank() == b.r.getNext())
            {
                return false;
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
        //If they can play
        if (!cheat(b,h))
        {
            int numLowerCards = h.countRank(b.getRank());

            if (numLowerCards>0)
            {
                for (int i=0;i<h.getHandArrayList().size();i++)
                {
                    Card card = h.getHandArrayList().get(i);
                    // If they can play the same rank play all
                    if (card.getRank() == b.getRank())
                    {
                        bid.setRank(card.getRank());
                        bid.getHand().addSingleCard(card);
                        h.removeSingleCard(card);
                    }
                }
                return bid;
            }
            else
            {
                for (int i=0;i<h.getHandArrayList().size();i++)
                {
                    Card card = h.getHandArrayList().get(i);
                    // If they can't play the same card play the next rank
                    if (card.getRank() == b.getRank().getNext())
                    {
                        bid.setRank(card.getRank());
                        bid.getHand().addSingleCard(card);
                        h.removeSingleCard(card);
                    }
                }
                return bid;
            }
        }
            // If can't play, play a random card
        else
        {
            double random = Math.random()*h.getHandArrayList().size();
            int rounded = (int) Math.round(random);
            Card randomCard = h.getHandArrayList().get(rounded);
            bid.setRank(b.getRank()); // lies by saying the random card is the same rank being played
            bid.getHand().addSingleCard(randomCard);
            h.removeSingleCard(randomCard);
            return bid;
        }
    }



    /**
     * @param b the current bid
     * @return true if this player is going to call cheat  on the last play b
     */

    public boolean callCheat(Hand h, Bid b)
    {
        int noOfRankOwned = h.countRank(b.getRank());
        int ranksRemaining = 4 - noOfRankOwned;
        if (ranksRemaining<=0)
        {
            return true;
        }
        return false;
    }


    public static void main(String[] args)
    {
        //testCheat();
        //testChooseBid();
       testCallCheat();

    }

    public static void testCheat()
    {
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
        //Test if it will cheat
        BasicStrategy basicStrategy = new BasicStrategy();
        Bid bid = new Bid();
        System.out.println(basicStrategy.cheat(bid,hand));
        System.out.println(bid.getRank());
        // 2nd test
        hand = new Hand();
        Card card = new Card(Card.Rank.SIX,Card.Suit.HEARTS);
        hand.addSingleCard(card);
        System.out.println(basicStrategy.cheat(bid,hand));
        System.out.println(bid.getRank());
    }

    public static void testChooseBid()
    {
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
        BasicStrategy basicStrategy = new BasicStrategy();
        Bid bid = new Bid();
        System.out.println(basicStrategy.chooseBid(bid,hand,true).h);
        System.out.println(basicStrategy.chooseBid(bid,hand,false).h);
    }

    public static void testCallCheat()
    {

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
        Bid bid = new Bid();
        BasicStrategy basicStrategy = new BasicStrategy();
        // Expecting true as the user has all the two's
        System.out.println(basicStrategy.callCheat(hand,bid));

    }

}