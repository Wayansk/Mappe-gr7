package gameplay;

import board.Tile;

/**
 * Represents a game piece with a specific name, color, and quantity.
 * <p>
 * This class is used to manage game pieces in a gameplay scenario.
 * </p>
 */
public class Player {


    private String nameOfPiece;
    private Tile currentTile;

    /**
     * Constructs a {@code Pieces} instance with the specified number of pieces, name, and color.
     *
     * @param nameOfPiece the name of the piece.
     * @param currentTile the current tile of the player
     */
    public Player(String nameOfPiece, Tile currentTile) {
        setNameOfPiece(nameOfPiece);
        setCurrentTile(currentTile);
    }

    public void setCurrentTile(Tile currentTile){
        this.currentTile = currentTile;
    }

    /**
     * Sets the name of the piece.
     *
     * @param nameOfPiece the name to assign to the piece.
     */
    public void setNameOfPiece(String nameOfPiece) {
        this.nameOfPiece = nameOfPiece;
    }

    public Tile getCurrentTile(){
        return currentTile;
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
