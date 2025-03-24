package engine;

import board.Board;
import gameplay.Dice;
import gameplay.Player;
import persistence.PlayerManager;


import java.util.List;

public class BoardGame {
    private List<Player> players;
    private TurnManager turnManager;
    private Dice dice;
    private Board board;
    private int[] positions;

    public BoardGame() {
        // empty constructor; setup happens separately
    }

    /**
     * loads players from csv file or registers new ones if none exist
     */
    public void setupPlayers() {
        String filePath = "players.csv";

        players = PlayerManager.loadPlayers(filePath);
        if (players.isEmpty()) {
            System.out.println("No existing player profiles found. Registering new ones...");
            players = PlayerManager.registerPlayers(filePath);
        } else {
            System.out.println("Loaded player profiles from file");
        }

        turnManager = new TurnManager(players);
        dice = new Dice();
        positions = new int[players.size()];
    }

    /**
     * Initializes a new Board of the given size
     * @param size size of the board; for example 50
     */
    public void setupBoard(int size) {
        board = new Board(size);
    }

    /**
     * Runs the game logic (currently for a terminal demo)
     * TODO: update for human playing
     * throws error if players or board have not been set up
     */
    public void startGame() {
        if (players == null || players.isEmpty()) {
            throw new IllegalArgumentException("No players found");
        }
        if (board == null) {
            throw new IllegalStateException("No board found");
        }

        for (int turn = 0; turn < 10; turn++) {
            Player currentPlayer = turnManager.getCurrentPlayer();
            int playerIndex = players.indexOf(currentPlayer);

            System.out.println("\n--- Turn " + (turn + 1) + " ---");
            System.out.println("Current player: " + currentPlayer.getNameOfPiece());

            int diceRoll = dice.roll();
            System.out.println("Rolled a: " + diceRoll);

            int newPosition = board.movePlayer(positions[playerIndex], diceRoll);
            positions[playerIndex] = newPosition;
            System.out.println(currentPlayer.getNameOfPiece() + " moves to square " + newPosition);

            turnManager.nextTurn(diceRoll);
        }
        System.out.println("\n Game demo over after 10 turns");
    }
}
