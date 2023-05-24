import java.util.ArrayList;
import java.util.List;

// Class that represents a player
public class Player {
    // Player's name
    private String name;

    // Cards in the player's hand
    private List<Card> hand;

    // List of tricks won by the player
    private List<List<Card>> tricksWon;

    // Constructs a new player with the given name
    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
        this.tricksWon = new ArrayList<>();
    }

    // Adds a card to the player's hand
    public void drawCard(Card card) {
        this.hand.add(card);
    }

    // Attempts to play a card from the player's hand
    public boolean playCard(Card card, Card leadCard) {
        // Checks if the player has the card and if the card can be played
        if (this.hand.contains(card) && card.canPlayOn(leadCard)) {
            // Removes the card from the player's hand and returns true
            this.hand.remove(card);
            return true;
        } else {
            // The card could not be played; returns false
            return false;
        }
    }

    // Returns the cards in the player's hand
    public List<Card> getHand() {
        return this.hand;
    }

    // Returns the tricks won by the player
    public List<List<Card>> getTricksWon() {
        return this.tricksWon;
    }

    // Adds a trick to the list of tricks won by the player
    public void addTrick(List<Card> trick) {
        this.tricksWon.add(trick);
    }

    // Returns the latest trick won by the player
    public List<Card> getLatestTrick() {
        if (this.tricksWon.size() > 0) {
            return this.tricksWon.get(this.tricksWon.size() - 1);
        } else {
            return null;
        }
    }

    // Returns the player's name
    public String getName() {
        return this.name;
    }

    // Sets the player's turn
    public void setTurn(boolean b) {
    }

    // Returns a string representation of the player
    @Override
    public String toString() {
        return "Player Name: " + this.name + ", Hand: " + this.hand;
    }

    // Checks if the player has a card that can be played
    public boolean hasPlayableCard(Card leadCard) {
        for (Card card : hand) {
            if (card.canPlayOn(leadCard)) {
                return true;
            }
        }
        return false;
    }
}
