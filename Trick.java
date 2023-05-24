import java.util.ArrayList;

// Class that represents a trick
public class Trick {
    // The players in the trick
    private ArrayList<Player> players;

    // The index of the current player
    private int currentPlayerIndex;

    // The suit and rank that was led in the trick
    private String leadSuit;
    private String leadRank;

    // The cards that have been played in the trick
    private ArrayList<Card> cardsPlayed;

    // Constructs a new trick with the given players
    public Trick(ArrayList<Player> players) {
        this.players = players;
        this.currentPlayerIndex = 0;
        this.cardsPlayed = new ArrayList<>();
    }

    // Returns the current player
    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    // Moves to the next player
    public void nextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    // Plays a card in the trick
    public void playCard(Card card) {
        cardsPlayed.add(card);
        // If this is the first card played in the trick, set the lead suit
        if (cardsPlayed.size() == 1) {
            setLeadSuit(card.getSuit());
        }
    }

    // Returns the cards that have been played in the trick
    public ArrayList<Card> getCardsPlayed() {
        return cardsPlayed;
    }

    // Returns the lead suit
    public String getLeadSuit() {
        return leadSuit;
    }

    // Sets the lead suit
    public void setLeadSuit(String leadSuit) {
        this.leadSuit = leadSuit;
    }

    // Sets the lead rank
    public void setLeadRank(String leadRank) {
        this.leadRank = leadRank;
    }

    // Returns the lead rank
    public String getLeadRank() {
        return this.leadRank;
    }
}