public class UnoCard {
    private final UnoColor color;
    private final UnoValue value;

    public UnoCard(UnoColor color, UnoValue value) {
        this.color = color;
        this.value = value;
    }

    public UnoColor getColor() {
        return color;
    }

    public UnoValue getValue() {
        return value;
    }

    public boolean isPlayableOn(UnoCard topCard) {
        return this.color == UnoColor.WILD ||
                this.color == topCard.getColor() ||
                this.value == topCard.getValue();
    }

    public String toColoredString() {
        String colorCode = "";
        switch(color) {
            case RED: colorCode = ConsoleColors.RED; break;
            case GREEN: colorCode = ConsoleColors.GREEN; break;
            case YELLOW: colorCode = ConsoleColors.YELLOW; break;
            case BLUE: colorCode = ConsoleColors.BLUE; break;
            default: colorCode = ""; break;
        }
        return colorCode + (color == UnoColor.WILD ? "" : color.toString() + " ")
                + value.toString() + ConsoleColors.RESET;
    }

    @Override
    public String toString() {
        return (color == UnoColor.WILD ? "" : color.toString() + " ") + value.toString();
    }
}
