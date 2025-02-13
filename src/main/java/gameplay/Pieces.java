package gameplay;

import javafx.scene.paint.Color;

/**
 * Represents a game piece with a specific name, color, and quantity.
 * <p>
 * This class is used to manage game pieces in a gameplay scenario.
 * </p>
 */
public class Pieces {

    /**
     * Enumeration of possible colors for game pieces.
     * <p>
     * Each enum constant is associated with a JavaFX {@link Color} instance.
     * </p>
     */
    public enum PieceColor {
        RED(Color.RED),
        BLUE(Color.BLUE),
        GREEN(Color.GREEN),
        YELLOW(Color.YELLOW),
        VIOLET(Color.VIOLET),
        PINK(Color.PINK),
        WHITE(Color.WHITE),
        BLACK(Color.BLACK),
        ORANGE(Color.ORANGE),
        PURPLE(Color.PURPLE);

        private final Color fxColor;

        /**
         * Constructs a {@code PieceColor} with the specified JavaFX color.
         *
         * @param fxColor the JavaFX {@link Color} associated with this piece color.
         */
        PieceColor(Color fxColor) {
            this.fxColor = fxColor;
        }

        /**
         * Returns the JavaFX {@link Color} associated with this piece color.
         *
         * @return the JavaFX {@link Color} of this piece color.
         */
        public Color getFxColor() {     // Will be used later for the GUI
            return fxColor;
        }
    }

    private String nameOfPiece;
    private PieceColor color;

    /**
     * Constructs a {@code Pieces} instance with the specified number of pieces, name, and color.
     *
     * @param nameOfPiece the name of the piece.
     * @param color       the {@link PieceColor} of the piece.
     */
    public Pieces(String nameOfPiece, PieceColor color) {
        setNameOfPiece(nameOfPiece);
        setColorOfPiece(color);
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
     * Sets the color of the piece.
     *
     * @param color the {@link PieceColor} to assign to the piece.
     */
    public void setColorOfPiece(PieceColor color) {
        this.color = color;
    }

    /**
     * Returns the name of the piece.
     *
     * @return the name of the piece.
     */
    public String getNameOfPiece() {
        return nameOfPiece;
    }

    /**
     * Returns the color of the piece.
     *
     * @return the {@link PieceColor} of the piece.
     */
    public PieceColor getColorOfPiece() {
        return color;
    }
}
