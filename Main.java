import java.util.*;
import java.util.AbstractMap.SimpleEntry;


public class Main {
  private Map < String, Player > players; // Map of players with player names as keys and Player objects as values
  private Deck deck; // Deck object that will be used in the game
  private List<Map.Entry<String, Card>> centerCards; // List of cards currently in play, along with the player who played them
  private boolean gameRunning; // Flag indicating whether the game is currently running
  private Card roundLeadCard; // The leading card in the current round
  private int trickCount; // Number of tricks played so far
  private String currentPlayer; // The name of the player whose turn it is
  private Card centerCard; // The card in the center of the table
  private boolean isFirstRound; // Flag indicating whether it is the first round of the game
  private Card leadCard; // The leading card in the current trick


  public Main() {
    this.players = new LinkedHashMap < > ();
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
    case "A":
    case "5":
    case "9":
    case "K":
      return "Player1";
    case "2":
    case "6":
    case "10":
      return "Player2";
    case "3":
    case "7":
    case "J":
      return "Player3";
    case "4":
    case "8":
    case "Q":
      return "Player4";
    default:
      return "Player1";
    }
  }

  public String getNextPlayer() {
    List < String > playerNames = new ArrayList < > (this.players.keySet());
    int currentIndex = playerNames.indexOf(this.currentPlayer);
    int nextIndex = (currentIndex + 1) % playerNames.size();
    return playerNames.get(nextIndex);
  }

  public void printState() {
    System.out.println("Trick #" + (this.trickCount + 1));
    for (Map.Entry < String, Player > entry: this.players.entrySet()) {
      System.out.println(entry.getKey() + ": " + entry.getValue().getHand());
    }
    System.out.println("Center : " + this.centerCards);
    System.out.println("Deck   : " + this.deck.getCards());
    //System.out.println("Score: Player1 = 0 | Player2 = 0 | Player3 = 0 | Player4 = 0");
    System.out.println("Turn : " + this.currentPlayer);
    System.out.print("> ");
}


public String getWinnerIndex() {
  if (centerCards.isEmpty()) {
    return null;
  }

  Map.Entry<String, Card> leadPlay = centerCards.get(0);
  String leadSuit = leadPlay.getValue().getSuit();

  Map.Entry<String, Card> highestPlay = leadPlay;

  for (int i = 1; i < centerCards.size(); i++) {
    Map.Entry<String, Card> play = centerCards.get(i);
    if (play.getValue().getSuit().equals(leadSuit) && getCardRankValue(play.getValue()) > getCardRankValue(highestPlay.getValue())) {
      highestPlay = play;
    }
  }

  return highestPlay.getKey();
}




private int getCardRankValue(Card card) {
    switch (card.getRank()) {
        case "A":
            return 14;
        case "K":
            return 13;
        case "Q":
            return 12;
        case "J":
            return 11;
        default:
            return Integer.parseInt(card.getRank());
    }
}





  public boolean isGameOver() {
    return this.trickCount >= 10;
  }


  public void printWinner() {
    String winnerName = "";

    for (Map.Entry < String, Player > entry: players.entrySet()) {
      if (!entry.getValue().getHand().isEmpty()) {
        winnerName = entry.getKey();
        break;
      }
    }

    System.out.println("The winner is " + winnerName);
}


  public void playTrick(Trick trick) {
    for (int i = 0; i < 4; i++) {
      System.out.println("\n" + trick.getCurrentPlayer().getName() + " turn:");

      Card leadCard = (i == 0) ? null : trick.getCardsPlayed().get(0);
      Card cardToPlay = null;

      for (Card card: trick.getCurrentPlayer().getHand()) {
        if (leadCard == null || card.canPlayOn(leadCard)) {
          cardToPlay = card;
          break;
        }
      }

      if (cardToPlay != null) {
        trick.getCurrentPlayer().playCard(cardToPlay, leadCard);
        trick.getCardsPlayed().add(cardToPlay);

        if (i == 0) {
          trick.setLeadSuit(cardToPlay.getSuit());
          trick.setLeadRank(cardToPlay.getRank());
        }
      }

      trick.nextPlayer();
    }
  }

  public String startNewRound() {
    String winnerKey = getWinnerIndex();
    if (winnerKey == null) {
        System.out.println("No cards were played in this round.");
        return null;
    }

    Player winner = this.players.get(winnerKey);
    if(winner != null) {
      System.out.println(winner.getName() + " wins the round!");
  } else {
      System.out.println("Winner not found");
  }
  

    this.centerCards.clear();
    this.isFirstRound = false;

    this.trickCount++;
    return winnerKey;
}


public void start() {
  isFirstRound = true;
  this.centerCard = this.deck.dealCard();
  this.centerCards.add(new SimpleEntry<>("Center", centerCard)); 
  this.leadCard = centerCard; 
  this.roundLeadCard = centerCard; 
  this.dealCards();
  String startingPlayerName = this.getStartingPlayer(this.centerCard);
  if (startingPlayerName != null) {
    this.currentPlayer = startingPlayerName;
    this.players.get(this.currentPlayer).setTurn(true);
  } else {
    this.currentPlayer = "Player1";
    this.players.get(this.currentPlayer).setTurn(true);
  }
  this.printState();
}

public void play() {
  Scanner scanner = new Scanner(System.in);

  while (this.gameRunning) {

      if ((isFirstRound && centerCards.size() == 0) || (!isFirstRound && centerCards.size() % 4 == 0)) {
          this.printState();
      }

      Player currentPlayer = this.players.get(this.currentPlayer);
      System.out.println("> ");

      String command = scanner.nextLine().toUpperCase(); 

      if (command.equals("E")) {
          System.out.println("Exiting game...");
          this.gameRunning = false;
      } else if (command.equals("D")) {
          while (!currentPlayer.hasPlayableCard(this.roundLeadCard) && !this.deck.isEmpty()) {
              Card drawnCard = this.deck.dealCard();
              currentPlayer.drawCard(drawnCard);
              System.out.println("You drew " + drawnCard);
          }
          if (!currentPlayer.hasPlayableCard(this.roundLeadCard)) {
              System.out.println("No playable card found. Skipping your turn.");
              this.currentPlayer = this.getNextPlayer();
          }
      } else {
          if (command.length() < 2) {
              System.out.println("Invalid card. Try again.");
              continue;
          }

          Card cardToPlay = Card.parseCard(command);

          if (currentPlayer.getHand().contains(cardToPlay)) {
              boolean success = currentPlayer.playCard(cardToPlay, this.roundLeadCard);

              if (success) {
                  this.centerCards.add(new SimpleEntry<>(this.currentPlayer, cardToPlay));
                  if (this.centerCards.size() == 1) {
                      this.roundLeadCard = cardToPlay;
                      this.leadCard = cardToPlay;
                  }
                  this.currentPlayer = this.getNextPlayer();
                  if ((isFirstRound && centerCards.size() % 5 != 0) || (!isFirstRound && centerCards.size() % 4 != 0) ) {
                      this.printState();
                  }
              } else {
                  System.out.println("Invalid move. Try again.");
              }
          } else {
              System.out.println("You don't have that card in your hand. Try again.");
          }
      }

      // Check if a trick has been won
      if ((isFirstRound && centerCards.size() == 5) || (!isFirstRound && centerCards.size() == 4)) {
          String winnerKey = startNewRound();  
          if (winnerKey == null) {
              System.out.println("No cards were played in this trick.");
              continue;
          }
          System.out.println("*** " + this.players.get(winnerKey).getName() + " wins Trick #" + this.trickCount + " ***");
          this.currentPlayer = winnerKey;  

          // Check if the game is over
          int playersWithCards = 0;
          for (Player player : this.players.values()) {
              if (!player.getHand().isEmpty()) {
                  playersWithCards++;
              }
          }

          if (playersWithCards == 1) {
              for (Player player : this.players.values()) {
                  if (!player.getHand().isEmpty()) {
                      System.out.println(player.getName() + " wins the game!");
                      this.gameRunning = false;
                      break;
                  }
              }

              if (this.gameRunning) {
                  dealCards();
                  this.isFirstRound = true;
                  this.trickCount = 0;
                  this.leadCard = null;
                  this.roundLeadCard = null;
              }
          } else {
              this.leadCard = null;
              this.roundLeadCard = null;
          }
      }
  }
  scanner.close();
}




  
  

public static void main(String[] args) {
  Scanner scanner = new Scanner(System.in);

  System.out.println("Welcome to the GoBoom game!");

  System.out.println("S - to start the game");
  System.out.println("E - to exit the game"); 
  System.out.println("info - to get more information about the game.");

  while (true) {
      String userInput = scanner.nextLine();

      if (userInput.equalsIgnoreCase("S")) {
          Main game = new Main();
          game.start();
          game.play();
          break;
      } else if (userInput.equalsIgnoreCase("E")) {
          System.out.println("Thank you for your interest. Exiting the game...");
          break;
      } else if (userInput.equalsIgnoreCase("info")) {
          System.out.println("GoBoom is a fun and challenging card game...");
          System.out.println("This game is created by Group 15 using JAVA.");
          System.out.println("How to play the game?");
          System.out.println("1- The game will explain it's self while you are playing");
          System.out.println("2- All you need to do, is to make sure that you can finish your cards faster then others");
          System.out.println("3- When your turns comes you can play a card that have the same suit or rank as the lead card");
          System.out.println("4- To play a card just write the card suit and rank with out space [suit][rank]");
          System.out.println("5- And when you don't have an playable card just write 'd' to draw a card from the deck");
          System.out.println("\n\nExcited to start the game?? ");
          System.out.println("Enter 'S' to start the game or 'E' to exit.");
      } else {
          System.out.println("Invalid input. Please enter 'S' to start, 'E' to exit, or 'info' for game info.");
      }
  }
}
}