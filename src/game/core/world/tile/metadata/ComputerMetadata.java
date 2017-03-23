package game.core.world.tile.metadata;

import game.core.util.Coordinates;

/**
 * Created by samtebbs on 22/02/2017.
 */
public class ComputerMetadata extends TileMetadata {

    public static final String PLAYER = "p", ON_FIRE = "f";

    public ComputerMetadata(Coordinates tile, String owningPlayer) {
        super(tile);
        setVar(PLAYER, owningPlayer);
        setVar(ON_FIRE, false);
    }

}
