public class Card implements Comparable<Card> {
    
    private String rank;
    private String suit;
    private boolean isTrump;

    public Card(String suit, String rank) {
        this.rank = rank;
        this.suit = suit;
        this.isTrump = false;
    }

    public String getRank() {
        return this.rank;
    }

    public String getSuit() {
        return this.suit;
    }

    public boolean isTrump() {
        return this.isTrump;
    }

    public void setTrump(boolean isTrump) {
        this.isTrump = isTrump;
    }

    public String toString() {
        return this.suit + this.rank;
    }

    public int getRankValue() {
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

    public int getPointValue() {
        switch (rank) {
            case "A":
            case "K":
            case "Q":
            case "J":
                return 10;
            case "10":
            case "9":
                return 20;
            default:
                return 5;
        }
    }
    

    @Override
    public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;

    Card card = (Card) obj;

    if (!suit.equals(card.suit)) return false;
    return rank.equals(card.rank);
    }

    @Override
    public int compareTo(Card otherCard) {
        return this.getRankValue() - otherCard.getRankValue();
    }


    public static Card parseCard(String cardStr) {
        // Assuming the cardStr is in the format "sA" where s is the suit and A is the rank
        String suit = cardStr.substring(0, 1);
        String rank = cardStr.substring(1);
        
        return new Card(suit, rank);
    }

    public boolean canPlayOn(Card leadCard) {
        return this.suit.equals(leadCard.suit) || this.rank.equals(leadCard.rank);
    }
    
    
}
