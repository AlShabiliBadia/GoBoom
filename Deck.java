import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Deck {
    private ArrayList<Card> cards;

    public Deck() {
        this.cards = new ArrayList<>();

        // Generate a standard deck of 52 cards
        String[] suits = {"c", "d", "h", "s"};
        String[] ranks = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};

        for (String suit : suits) {
            for (String rank : ranks) {
                this.cards.add(new Card(suit, rank));
            }
        }

        // Shuffle the deck
        Collections.shuffle(this.cards);
    }

    public Card drawCard() {
        // Draw the top card from the deck
        return this.cards.remove(this.cards.size() - 1);
    }

    public boolean isEmpty() {
        // Check if the deck is empty
        return this.cards.isEmpty();
    }

    public int size() {
        // Return the number of cards in the deck
        return this.cards.size();
    }

    public void shuffle() {
        Collections.shuffle(this.cards);
    }
    public Card dealCard() {
        return this.cards.remove(0);
    }
        
    public void addCard(Card card) {
        this.cards.add(card);
      }
      
      public List<Card> getCards() {
        return new ArrayList<>(this.cards);
      }
}
