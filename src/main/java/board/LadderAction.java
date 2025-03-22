package board;

import gameplay.Player;

/**
 * Represents an action that moves a player to a new tile when they land on a ladder.
 * This class implements the {@link TileAction} interface.
 */
public class LadderAction implements TileAction {
    private int fromTile;
    private int toTile;

    public LadderAction(int fromTile, int toTile){
        this.fromTile = fromTile;
        this.toTile = toTile;
    }

    @Override
    public void perform(Player player){
        // sysout
        // perform(tofile)
    }
}
