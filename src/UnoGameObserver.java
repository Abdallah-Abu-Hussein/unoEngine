public interface UnoGameObserver {
    void onCardPlayed(UnoPlayer player, UnoCard card);
    void onGameWon(UnoPlayer winner);
}
