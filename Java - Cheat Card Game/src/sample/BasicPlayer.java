package sample;

public class BasicPlayer {
    private Hand hand;
    private Strategy strategy;
    private CardGame reference;
    //private enum Action{PASS,PLAY}
    /**add to the players hand
     * @param c: Card to add
     */
    public void addCard(Card c)
    {
        hand.addSingleCard(c);
    }
    /**
     * 	add all the cards in h to the players hand
     * @param h: hand to add
     */
    public void addHand(Hand h)
    {
        hand.addHand(h);
    }
    /**
     * @return number of cards left in the players hand
     */
    public int cardsLeft()
    {
        return hand.getHandArrayList().size();
    }
    /**
     * @param g: the player should contain a reference to the game it is playing in
     */
    public void setGame(CardGame g)
    {
        reference=g;
    }
    /**
     * @param s: the player should contain a reference to its strategy
     */
    public void setStrategy(Strategy s)
    {
       strategy = s;
    }


    /**
     * Constructs a bid when asked to by the game.
     * @param b: the last bid accepted by the game. .
     * @return the players bid
     */
    public Bid playHand(Bid b)
    {
        Bid newBid = new Bid();
        return newBid;
    }

    /**
     *
     * @param b: the last players bid
     *
     * @return true if calling the last player a cheat.
     */
    public boolean callCheat(Bid b)
    {
        return true;/*
        if (strategy = BasicStrategy)
        {

        }*/
    }

}