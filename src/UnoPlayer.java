import java.util.List;

public interface UnoPlayer {
    void drawCard(UnoCard card);
    UnoCard playCard(UnoCard topCard);
    List<UnoCard> getHand();
    String getName();
}