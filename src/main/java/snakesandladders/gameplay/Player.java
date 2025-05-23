package snakesandladders.gameplay;

import snakesandladders.board.Tile;

/**
 * Represents a game piece with a specific name and a current tile.
 * <p>
 * This class manages the player’s state during gameplay.
 * </p>
 */
public class Player {

    private String nameOfPiece;
    private Tile currentTile;

    /**
     * Constructs a {@code Player} instance with the specified name.
     * The player's starting tile is set to the provided tile.
     *
     * @param nameOfPiece the name of the piece.
     * @param startingTile the tile on which the player starts.
     */
    public Player(String nameOfPiece, Tile startingTile) {
        setNameOfPiece(nameOfPiece);
        this.currentTile = startingTile;
    }

    /**
     * Updates the current tile of the player on the board.
     *
     * @param newTile the new tile where the player moves.
     */
    public void setCurrentTile(Tile newTile) {
        this.currentTile = newTile;
    }

    /**
     * Returns the current tile of the player.
     *
     * @return the current {@code Tile} object.
     */
    public Tile getCurrentTile() {
        return currentTile;
    }

    /**
     * Sets the name of the piece.
     *
     * @param nameOfPiece the name to assign to the piece.
     */
    public void setNameOfPiece(String nameOfPiece) {
        this.nameOfPiece = nameOfPiece;
    }

    /**
     * Returns the name of the piece.
     *
     * @return the name of the piece.
     */
    public String getNameOfPiece() {
        return nameOfPiece;
    }
}
