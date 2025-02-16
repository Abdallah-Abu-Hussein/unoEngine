import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class BasicUnoPlayer implements UnoPlayer {
    private final String name;
    private final List<UnoCard> hand;
    private final Random random;

    public BasicUnoPlayer(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
        this.random = new Random();
    }

    @Override
    public void drawCard(UnoCard card) {
        hand.add(card);
    }

    @Override
    public UnoCard playCard(UnoCard topCard) {
        List<UnoCard> playable = new ArrayList<>();
        // Collect all playable cards in a basic loop
        for (UnoCard card : hand) {
            if (card.isPlayableOn(topCard)) {
                playable.add(card);
            }
        }
        // If no card is playable, return null to signal draw
        if (playable.isEmpty()) {
            return null;
        }
        // Otherwise, pick a random playable card
        UnoCard chosen = playable.get(random.nextInt(playable.size()));
        hand.remove(chosen);
        return chosen;
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
