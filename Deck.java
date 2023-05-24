import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


// Class that represents a deck of cards
public class Deck {
    // List of cards in the deck
    private ArrayList<Card> cards;

    // Constructs a new deck and initializes it with 52 cards
    public Deck() {
        // Initializes the list of cards
        this.cards = new ArrayList<>();
        
        // Array of all possible suits and ranks
        String[] suits = {"c", "d", "h", "s"};
        String[] ranks = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};

        // Creates one card of each suit and rank and adds them to the list
        for (String suit : suits) {
            for (String rank : ranks) {
                this.cards.add(new Card(suit, rank));
            }
        }

        // Shuffles the deck
        Collections.shuffle(this.cards);
    }

    // Removes a card from the deck and returns it
    public Card drawCard() {
        return this.cards.remove(this.cards.size() - 1);
    }

    // Checks if the deck is empty
    public boolean isEmpty() {
        return this.cards.isEmpty();
    }

    // Returns the number of cards left in the deck
    public int size() {
        return this.cards.size();
    }

    // Shuffles the deck
    public void shuffle() {
        Collections.shuffle(this.cards);
    }

    // Deals a card from the deck
    public Card dealCard() {
        return this.cards.remove(0);
    }

    // Adds a card to the deck
    public void addCard(Card card) {
        this.cards.add(card);
    }

    // Returns a copy of the list of cards in the deck
    public List<Card> getCards() {
        return new ArrayList<>(this.cards);
    }
}
