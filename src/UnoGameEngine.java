import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class UnoGameEngine {
    private final List<UnoPlayer> players;
    private final UnoDeck deck;
    private final List<UnoGameObserver> observers;
    private int currentPlayerIndex;
    private int direction; // 1 for clockwise, -1 for counter-clockwise
    private final boolean simulateMode; // new flag to determine simulation mode
    private static final Scanner scanner = new Scanner(System.in);

    public UnoGameEngine(List<UnoPlayer> players, UnoDeck deck, boolean simulateMode) {
        if (players.size() < 2) {
            throw new IllegalArgumentException("At least two players required.");
        }
        this.players = players;
        this.deck = deck;
        this.observers = new ArrayList<>();
        this.currentPlayerIndex = 0;
        this.direction = 1;
        this.simulateMode = simulateMode;
        // Deal initial hand (7 cards per player)
        for (int i = 0; i < 7; i++) {
            for (UnoPlayer player : players) {
                player.drawCard(deck.drawCard());
            }
        }
        // Start the discard pile with one card.
        UnoCard firstCard = deck.drawCard();
        // If the first card is wild, set a default color.
        if (firstCard.getColor() == UnoColor.WILD) {
            firstCard = new UnoCard(UnoColor.RED, firstCard.getValue());
        }
        deck.discard(firstCard);
    }

    public void addObserver(UnoGameObserver observer) {
        observers.add(observer);
    }

    public void startGame() {
        boolean gameWon = false;
        while (!gameWon) {
            try {
                UnoPlayer currentPlayer = players.get(currentPlayerIndex);
                UnoCard topCard = deck.peekDiscard();
                System.out.println("\nCurrent player: " + currentPlayer.getName());
                System.out.println("Top card: " + topCard.toColoredString());

                // If in simulation mode, print the current player's hand horizontally.
                if (simulateMode) {
                    System.out.print("Player's hand: ");
                    for (int i = 0; i < currentPlayer.getHand().size(); i++) {
                        System.out.print("[" + i + "]: " + currentPlayer.getHand().get(i).toColoredString() + "  ");
                    }
                    System.out.println();
                }

                UnoCard playedCard = currentPlayer.playCard(topCard);
                if (playedCard != null) {
                    deck.discard(playedCard);
                    notifyCardPlayed(currentPlayer, playedCard);
                    // Process special effects; note we pass the current player.
                    processCardEffect(playedCard, currentPlayer);
                    if (currentPlayer.getHand().isEmpty()) {
                        notifyGameWon(currentPlayer);
                        System.out.println(currentPlayer.getName() + " wins the game!");
                        gameWon = true;
                        break;
                    }
                } else {
                    // Player opts to draw a card.
                    UnoCard drawn = deck.drawCard();
                    currentPlayer.drawCard(drawn);
                    System.out.println(currentPlayer.getName() + " drew a card: " + drawn.toColoredString());

                    // Print the updated hand horizontally.
                    System.out.print("Updated Hand: ");
                    for (UnoCard card : currentPlayer.getHand()) {
                        System.out.print(card.toColoredString() + "  ");
                    }
                    System.out.println();
                }
                moveToNextPlayer();
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
                e.printStackTrace();
                moveToNextPlayer(); // Skip turn on error.
            }
        }
    }


    private void processCardEffect(UnoCard card, UnoPlayer currentPlayer) {
        switch(card.getValue()) {
            case REVERSE:
                direction *= -1;
                break;
            case SKIP:
                moveToNextPlayer();
                break;
            case DRAW_TWO:
                UnoPlayer nextPlayer = getNextPlayer();
                nextPlayer.drawCard(deck.drawCard());
                nextPlayer.drawCard(deck.drawCard());
                moveToNextPlayer();
                break;
            case WILD:
                UnoColor chosenColor = chooseColor(currentPlayer);
                UnoCard newWild = new UnoCard(chosenColor, UnoValue.WILD);
                deck.replaceTopCard(newWild);
                System.out.println("Wild played. Color changed to " + chosenColor);
                break;
            case WILD_DRAW_FOUR:
                UnoColor chosenColorDrawFour = chooseColor(currentPlayer);
                UnoCard newWildDrawFour = new UnoCard(chosenColorDrawFour, UnoValue.WILD_DRAW_FOUR);
                deck.replaceTopCard(newWildDrawFour);
                System.out.println("Wild Draw Four played. Color changed to " + chosenColorDrawFour);
                UnoPlayer nextPlayer4 = getNextPlayer();
                for (int i = 0; i < 4; i++) {
                    nextPlayer4.drawCard(deck.drawCard());
                }
                moveToNextPlayer();
                break;
            default:
                break;
        }
    }

    private UnoColor chooseColor(UnoPlayer player) {
        if (player instanceof HumanUnoPlayer) {
            System.out.println(player.getName() + ", choose a color (RED, YELLOW, GREEN, BLUE):");
            while (true) {
                String input = scanner.nextLine().trim().toUpperCase();
                try {
                    return UnoColor.valueOf(input);
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid color. Please enter one of: RED, YELLOW, GREEN, BLUE");
                }
            }
        } else {
            UnoColor[] colors = {UnoColor.RED, UnoColor.YELLOW, UnoColor.GREEN, UnoColor.BLUE};
            UnoColor chosen = colors[new Random().nextInt(colors.length)];
            System.out.println(player.getName() + " chooses color " + chosen);
            return chosen;
        }
    }

    private void moveToNextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + direction + players.size()) % players.size();
    }

    private UnoPlayer getNextPlayer() {
        int nextIndex = (currentPlayerIndex + direction + players.size()) % players.size();
        return players.get(nextIndex);
    }

    private void notifyCardPlayed(UnoPlayer player, UnoCard card) {
        for (UnoGameObserver observer : observers) {
            observer.onCardPlayed(player, card);
        }
    }

    private void notifyGameWon(UnoPlayer winner) {
        for (UnoGameObserver observer : observers) {
            observer.onGameWon(winner);
        }
    }
}
