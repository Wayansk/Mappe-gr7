package board;

import gameplay.Player;

/**
 * Represents an action that moves a player to a new tile when they land on a ladder.
 * This class implements the {@link TileAction} interface.
 */
public class LadderAction implements TileAction {

    /**
     * The ID of the destination tile where the player will be moved.
     */
    private final int destinationTileId;

    /**
     * A description of the ladder action.
     */
    private final String description;

    /**
     * Constructs a {@code LadderAction} with the specified destination tile ID and description.
     *
     * @param destinationTileId The ID of the tile to which the player will be moved.
     * @param description A brief description of the ladder action.
     */
    public LadderAction(int destinationTileId, String description) {
        this.destinationTileId = destinationTileId;
        this.description = description;
    }

    /**
     * Performs the ladder action, moving the player to the destination tile.
     * This method currently prints the description but should be updated to modify the player's position.
     *
     * @param player The player on whom the action is performed.
     */
    @Override
    public void perform(Player player) {
        System.out.println(description);
        // TODO: Implement method to update player's position
    }
}
