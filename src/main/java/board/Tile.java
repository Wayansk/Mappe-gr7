package board;

import gameplay.Player;

/**
 * Represents a tile on a game board.
 * Each tile has an ID, an optional action that can be triggered when a player lands on it,
 * and a reference to the next tile on the board.
 */
public class Tile {

    private Tile nextTile;
    private final int tileId;
    private TileAction landAction;

    /**
     * Constructs a Tile with the given ID.
     *
     * @param tileId the unique identifier for this tile
     * @throws IllegalArgumentException if the tileId is less than 1
     */
    public Tile(int tileId){
        if (tileId < 1){
            throw new IllegalArgumentException("Tile ID must be 1, or above.");
        }
        this.tileId = tileId;
    }

    /**
     * Executes the action associated with the tile when a player lands on it.
     * If no action is set for the tile, no action will be performed.
     *
     * @param player the player who lands on the tile
     */
    public void landPlayer(Player player){
        if (landAction != null){
            landAction.perform(player);
        }
    }

    /**
     * Sets the next tile that follows this tile on the game board.
     *
     * @param nextTile the next tile after this one
     */
    public void setNextTile(Tile nextTile){
        this.nextTile = nextTile;
    }

    /**
     * Gets the next tile that follows this tile on the game board.
     *
     * @return the next tile after this one
     */
    public Tile getNextTile(){
        return nextTile;
    }

    /**
     * Gets the unique identifier of this tile.
     *
     * @return the ID of the tile
     */
    public int getTileId(){
        return tileId;
    }

    /**
     * Gets the action that is triggered when a player lands on this tile.
     *
     * @return the land action of this tile, or null if no action is set
     */
    public TileAction getLandAction(){
        return landAction;
    }
}
