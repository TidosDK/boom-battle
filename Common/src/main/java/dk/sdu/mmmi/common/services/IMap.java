package dk.sdu.mmmi.common.services;

import dk.sdu.mmmi.common.data.Direction;

public interface IMap {

    boolean isTileObstacle(int x, int y);
    boolean setMapTile(int x, int y, boolean isObstacle);
    boolean isMoveAllowed(int x, int y, Direction direction);
}
