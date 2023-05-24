import java.util.Objects;


// Class that represents a card
public class Card implements Comparable<Card> {
    // The card's rank and suit
    private String rank;
    private String suit;
    
    // Whether the card is a trump card
    private boolean isTrump;
    private String img;

    // Constructs a new card with the given suit and rank
    public Card(String suit, String rank) {
        this.rank = rank;
        this.suit = suit;
        this.isTrump = false;
        this.img = suit + rank + ".png";
    }

    public String getImageName() {
        return this.img;
    }

    // Returns the card's rank
    public String getRank() {
        return this.rank;
    }

    // Returns the card's suit
    public String getSuit() {
        return this.suit;
    }

    // Checks if the card is a trump card
    public boolean isTrump() {
        return this.isTrump;
    }

    // Sets whether the card is a trump card
    public void setTrump(boolean isTrump) {
        this.isTrump = isTrump;
    }

    // Returns a string representation of the card
    public String toString() {
        return this.suit + this.rank;
    }

    // Returns the numerical value of the card's rank
    public int getRankValue() {
        // Each case corresponds to a rank and its value
        switch (this.rank) {
            case "A":
                return 14;
            case "K":
                return 13;
            case "Q":
                return 12;
            case "J":
                return 11;
            case "10":
                return 10;
            case "9":
                return 9;
            case "8":
                return 8;
            case "7":
                return 7;
            case "6":
                return 6;
            case "5":
                return 5;
            case "4":
                return 4;
            case "3":
                return 3;
            case "2":
                return 2;
            default:
                return 0;
        }
    }

    // Override the equals method to compare cards by rank and suit
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Card card = (Card) obj;
        return rank.equals(card.rank) && suit.equals(card.suit);
    }

    // Override the hashCode method to generate a unique hash code for each card
    @Override
    public int hashCode() {
        return Objects.hash(rank, suit);
    }

    // Override the compareTo method to compare cards by their rank value
    @Override
    public int compareTo(Card otherCard) {
        return this.getRankValue() - otherCard.getRankValue();
    }

    // Parses a card from a string
    public static Card parseCard(String cardStr) {
        if (cardStr == null || cardStr.isEmpty()) {
            throw new IllegalArgumentException("Invalid card string: " + cardStr);
        }
        // The first character is the suit and the rest is the rank
        String suit = cardStr.substring(0, 1).toLowerCase();  
        String rank = cardStr.substring(1).toUpperCase();  
        // Converts "1" to "10"
        if (rank.equals("1")) {
            rank = "10";
        }
        // Constructs a new card with the parsed suit and rank
        return new Card(suit, rank);
    }

    // Checks if the card can be played on another card
    public boolean canPlayOn(Card leadCard) {
        if (leadCard == null) {
            return true;
        }
        // The card can be played if the suits or ranks match
        if (leadCard.getSuit().equals(this.getSuit()) || leadCard.getRank().equals(this.getRank())) {
            return true;
        }
        return false;
    }
}
