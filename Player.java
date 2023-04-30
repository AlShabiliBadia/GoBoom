import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private List<Card> hand;
    private List<List<Card>> tricksWon;

    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
        this.tricksWon = new ArrayList<>();
    }

    public void drawCard(Card card) {
        this.hand.add(card);
    }

    public boolean playCard(Card card, Card leadCard) {
        if (this.hand.contains(card) && card.canPlayOn(leadCard)) {
            this.hand.remove(card);
            return true;
        } else {
            return false;
        }
    }
    

    public List<Card> getHand() {
        return this.hand;
    }

    public List<List<Card>> getTricksWon() {
        return this.tricksWon;
    }

    public void addTrick(List<Card> trick) {
        this.tricksWon.add(trick);
    }

    public List<Card> getLatestTrick() {
        if (this.tricksWon.size() > 0) {
            return this.tricksWon.get(this.tricksWon.size() - 1);
        } else {
            return null;
        }
    }

    public int getScore() {
        int score = 0;
        for (List<Card> trick : this.tricksWon) {
            for (Card card : trick) {
                score += card.getPointValue();
            }
        }
        return score;
    }

    public String getScoreString() {
        return "Player " + this.name.substring(6) + " = " + this.getScore();
    }

    public String getName() {
        return this.name;
    }

    public void setTurn(boolean b) {
    }
}
