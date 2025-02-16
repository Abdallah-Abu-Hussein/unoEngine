import java.util.ArrayList;
import java.util.List;

public class UnoCardFactory {
    public static List<UnoCard> createStandardUnoDeck() {
        List<UnoCard> deck = new ArrayList<>();
        // For each (excluding WILD)
        for (UnoColor color : UnoColor.values()) {
            if (color == UnoColor.WILD) continue;
            deck.add(new UnoCard(color, UnoValue.ZERO)); // remember only one ZERO per color
            for (int i = 0; i < 2; i++) {
                for (UnoValue value : UnoValue.values()) {
                    if (value == UnoValue.ZERO || value == UnoValue.WILD || value == UnoValue.WILD_DRAW_FOUR)
                        continue;
                    deck.add(new UnoCard(color, value));
                }
            }
        }
        for (int i = 0; i < 4; i++) {
            deck.add(new UnoCard(UnoColor.WILD, UnoValue.WILD));
            deck.add(new UnoCard(UnoColor.WILD, UnoValue.WILD_DRAW_FOUR));
        }
        return deck;
    }
}
