import java.util.*;

public class Game {
    private Map<String, Player> players;
    private Deck deck;
    private List<Card> centerCards;
    private boolean gameRunning;
    private int trickCount;
    private Object currentPlayer;
    private Card centerCard;
    private Card leadCard;

    public Game() {
        this.players = new LinkedHashMap<>();
        this.players.put("Player1", new Player("Player1"));
        this.players.put("Player2", new Player("Player2"));
        this.players.put("Player3", new Player("Player3"));
        this.players.put("Player4", new Player("Player4"));
        this.deck = new Deck();
        this.centerCards = new ArrayList<>();
        this.gameRunning = true;
        this.trickCount = 0;
    }

  public void dealCards() {
    for (Player player: this.players.values()) {
      for (int i = 0; i < 7; i++) {
        player.drawCard(this.deck.dealCard());
      }
    }
  }

  public String getStartingPlayer(Card centerCard) {
    String rank = centerCard.getRank();
    switch (rank) {
        case "A": case "5": case "9": case "K":
            return "Player1";
        case "2": case "6": case "10":
            return "Player2";
        case "3": case "7": case "J":
            return "Player3";
        case "4": case "8": case "Q":
            return "Player4";
        default:
            return "Player1";
    }
}


  public String getNextPlayer() {
    // Suppose players' names are stored in an ordered list
    List<String> playerNames = new ArrayList<>(this.players.keySet());
    int currentIndex = playerNames.indexOf(this.currentPlayer);
    int nextIndex = (currentIndex + 1) % playerNames.size();
    return playerNames.get(nextIndex);
}

  public void printState() {
    System.out.println("Trick #" + (this.trickCount + 1));  // Increase trickCount in your code when a trick is won
    for (Map.Entry<String, Player> entry : this.players.entrySet()) {
        System.out.println(entry.getKey() + ": " + entry.getValue().getHand());
    }
    System.out.println("Center : " + this.centerCards);
    System.out.println("Deck   : " + this.deck.getCards());
    System.out.print("Score: ");
    for (Map.Entry<String, Player> entry : this.players.entrySet()) {
        System.out.print(entry.getKey() + " = " + entry.getValue().getScore() + " | ");
    }
    System.out.println("\nTurn : " + this.currentPlayer);
    System.out.print("> ");
}


public int getWinnerIndex() {
  // assuming first card played is the lead card
  Card leadCard = this.centerCards.get(0);
  String leadSuit = leadCard.getSuit();

  Card winningCard = leadCard;
  int winnerIndex = 0;

  for (int i = 1; i < this.centerCards.size(); i++) {
      Card card = this.centerCards.get(i);
      if (card.getSuit().equals(leadSuit) && card.compareTo(winningCard) > 0) {
          winningCard = card;
          winnerIndex = i;
      }
  }

  return winnerIndex;
}


  public boolean isGameOver() {
    // This is just an example. Replace it with your game logic.
    return this.trickCount >= 10;
  }

  public void printScores() {
    for (Player player: this.players.values()) {
      System.out.println(player.getName() + ": " + player.getScore());
    }
  }

  public void printWinner() {
    int maxScore = Integer.MIN_VALUE;
    String winnerName = "";

    for (Map.Entry<String, Player> entry : players.entrySet()) {
        if (entry.getValue().getScore() > maxScore) {
            maxScore = entry.getValue().getScore();
            winnerName = entry.getKey();
        }
    }

    System.out.println("The winner is " + winnerName + " with a score of " + maxScore);
}

public void start() {
  this.centerCard = this.deck.dealCard();
  this.centerCards.add(centerCard);
  this.leadCard = centerCard; // set leadCard as centerCard at the start
  this.dealCards();
  String startingPlayerName = this.getStartingPlayer(this.centerCard);
  if (startingPlayerName != null) {
      Player startingPlayer = this.players.get(startingPlayerName);
      this.currentPlayer = startingPlayer.getName();
      startingPlayer.setTurn(true); // set startingPlayer's turn to true
  } else {
      // If no match is found for the center card's rank, set the current player to Player1 as a default
      this.currentPlayer = "Player1";
      this.players.get(this.currentPlayer).setTurn(true);
  }
  this.printState();
}




public void play() {
  Scanner scanner = new Scanner(System.in);

  while (this.gameRunning) {
      Player currentPlayer = this.players.get(this.currentPlayer);
      System.out.println("> ");

      String command = scanner.nextLine().toUpperCase(); // Case-insensitive commands

      if (command.equals("E")) {
          System.out.println("Exiting game...");
          this.gameRunning = false;
      } else if (command.equals("D")) {
          Card drawnCard;
          boolean foundPlayableCard = false;

          // Draw cards until a playable card is found or the deck is empty
          while (!foundPlayableCard && !this.deck.isEmpty()) {
              drawnCard = this.deck.drawCard();
              currentPlayer.drawCard(drawnCard);
              System.out.println("You drew " + drawnCard);
              foundPlayableCard = drawnCard.canPlayOn(this.leadCard);
          }

          // If no playable card is found and the deck is empty, skip the player's turn
          if (!foundPlayableCard) {
              System.out.println("No playable card found. Skipping your turn.");
              this.currentPlayer = this.getNextPlayer();
          }
      } else {
          String cardRank = command.substring(1);
          String cardSuit = command.substring(0, 1);
          Card cardToPlay = new Card(cardRank, cardSuit);
          boolean success = currentPlayer.playCard(cardToPlay, this.leadCard);

          if (success) {
              this.centerCards.add(cardToPlay);
              this.leadCard = cardToPlay;
              this.currentPlayer = this.getNextPlayer();
              this.printState();  // print the state after the player has made a successful move
          } else {
              System.out.println("Invalid move. Try again.");
          }
      }

      // Check if a trick has been won
      if (this.centerCards.size() == 4) {
          this.trickCount++;
          int winningIndex = this.getWinnerIndex();
          Player winningPlayer = new ArrayList<Player>(this.players.values()).get(winningIndex);
          winningPlayer.addTrick(this.centerCards);
          System.out.println(winningPlayer.getName() + " wins the trick!");
          // Clear the center cards and set the next lead card
          this.centerCards.clear();
          this.leadCard = winningPlayer.getLatestTrick().get(0);
          this.currentPlayer = winningPlayer.getName();

          // Check if the game is over
          if (this.isGameOver()) {
            System.out.println("Game over!");
            this.gameRunning = false;
            this.printScores();
            this.printWinner();
          } else {
            // If the deck is empty, shuffle the cards won in tricks and add them to the deck
            if (this.deck.isEmpty()) {
              List<Card> allTrickCards = new ArrayList<>();
              for (Player player: this.players.values()) {
                for (List<Card> trick: player.getTricksWon()) {
                  allTrickCards.addAll(trick);
                }
              }
              Collections.shuffle(allTrickCards);
              for (Card card: allTrickCards) {
                this.deck.addCard(card);
              }
            }
          }
      }
  }
  scanner.close();
}


  public static void main(String[] args) {
    Game game = new Game();
    game.start();
    game.play();
  }

}
