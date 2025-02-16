import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UnoGame extends Game {
    private final UnoGameEngine engine;

    public UnoGame() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Do you want to simulate the game (auto-play)? (Y/N):");
        String simulationChoice = scanner.nextLine().trim().toUpperCase();
        boolean simulate = simulationChoice.equals("Y") || simulationChoice.equals("YES");

        int numPlayers = 0;
        List<UnoPlayer> players = new ArrayList<>();
        System.out.println("Enter number of players (2-10):");
        while (true) {
            try {
                numPlayers = Integer.parseInt(scanner.nextLine());
                if (numPlayers < 2 || numPlayers > 10) {
                    System.out.println("Please enter a number between 2 and 10.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please try again.");
            }
        }

        if (simulate) {
            for (int i = 0; i < numPlayers; i++) {
                players.add(new BasicUnoPlayer("AI_Player_" + (i + 1)));
            }
        } else {
            // to allow mix of human and AI players.
            for (int i = 0; i < numPlayers; i++) {
                System.out.println("Enter name for player " + (i + 1) + ":");
                String name = scanner.nextLine();
                System.out.println("Is " + name + " a human player? (Y/N):");
                String type = scanner.nextLine().trim().toUpperCase();
                if (type.equals("Y") || type.equals("YES")) {
                    players.add(new HumanUnoPlayer(name));
                } else {
                    players.add(new BasicUnoPlayer(name));
                }
            }
        }

        UnoDeck deck = new UnoDeck(UnoCardFactory.createStandardUnoDeck());
        engine = new UnoGameEngine(players, deck, simulate);

        engine.addObserver(new UnoGameObserver() {
            @Override
            public void onCardPlayed(UnoPlayer player, UnoCard card) {
                System.out.println(player.getName() + " played " + card.toColoredString());
            }
            @Override
            public void onGameWon(UnoPlayer winner) {
                System.out.println("Game won by " + winner.getName());
            }
        });
    }

    @Override
    public void play() {
        engine.startGame();
    }
}
