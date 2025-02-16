import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class UnoDeck {
    private final Stack<UnoCard> drawPile;
    private final Stack<UnoCard> discardPile;

    public UnoDeck(List<UnoCard> cards) {
        this.drawPile = new Stack<>();
        this.drawPile.addAll(cards);
        Collections.shuffle(drawPile);
        this.discardPile = new Stack<>();
    }

    public UnoCard drawCard() {
        if (drawPile.isEmpty()) {
            UnoCard topDiscard = discardPile.pop();
            drawPile.addAll(discardPile);
            discardPile.clear();
            discardPile.push(topDiscard);
            Collections.shuffle(drawPile);
        }
        return drawPile.pop();
    }

    public void discard(UnoCard card) {
        discardPile.push(card);
    }

    public UnoCard peekDiscard() {
        return discardPile.peek();
    }

    public void replaceTopCard(UnoCard newCard) {
        if (!discardPile.isEmpty()) {
            discardPile.pop();
        }
        discardPile.push(newCard);
    }
}
