import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HumanUnoPlayer implements UnoPlayer {
    private final String name;
    private final List<UnoCard> hand;
    private static final Scanner scanner = new Scanner(System.in);

    public HumanUnoPlayer(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
    }

    @Override
    public void drawCard(UnoCard card) {
        hand.add(card);
    }

    @Override
    public UnoCard playCard(UnoCard topCard) {
        System.out.println("\n" + name + ", it's your turn.");
        System.out.println("Top card on discard pile: " + topCard.toColoredString());
        System.out.println("Your hand:");
        for (int i = 0; i < hand.size(); i++) {
            System.out.println("[" + i + "]: " + hand.get(i).toColoredString() + " | ");
        }
        System.out.println("Enter the number corresponding to the card you want to play (or -1 to draw a card):");
        int choice;
        while(true) {
            try {
                choice = Integer.parseInt(scanner.nextLine());
                if(choice == -1) {
                    return null; // Player opts to draw a card
                }
                if(choice < 0 || choice >= hand.size()) {
                    System.out.println("Invalid choice. Please enter a valid number.");
                    continue;
                }
                UnoCard selectedCard = hand.get(choice);
                if(!selectedCard.isPlayableOn(topCard)) {
                    System.out.println("Selected card cannot be played on top of " + topCard.toColoredString());
                    continue;
                }
                hand.remove(choice);
                return selectedCard;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    @Override
    public List<UnoCard> getHand() {
        return hand;
    }

    @Override
    public String getName() {
        return name;
    }
}
