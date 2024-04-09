package dk.sdu.mmmi.BasicMapSystem;

import dk.sdu.mmmi.common.data.Direction;
import dk.sdu.mmmi.common.data.GridPosition;
import dk.sdu.mmmi.common.data.Map;
import dk.sdu.mmmi.common.data.Coordinates;
import dk.sdu.mmmi.common.services.ICollidable;
import dk.sdu.mmmi.common.services.Map.IMap;
import dk.sdu.mmmi.common.services.Map.IMapProcessingService;

import java.util.Collection;
import java.util.ServiceLoader;

/**
 * A representation of a fully implemented map.
 * With its extention of Map and implementation of IMap and IMapProcessingService
 * it is able to be used as a map in the game.
 */
public class BasicMap extends Map implements IMap, IMapProcessingService {
    public BasicMap() {
        super(10,10);
    }

    @Override
    public boolean isTileObstacle(int x, int y) {
        boolean[][] map = getMap();
        if (x < 0 || x >= map.length || y < 0 || y >= map[0].length) {
            return true;
        }
        return map[x][y];
    }

    @Override
    public boolean setMapTile(int x, int y, boolean isObstacle) throws RuntimeException{
        if (x < 0 || x >= getMap().length || y < 0 || y >= getMap()[0].length) {
            return false;
        }
        getMap()[x][y] = isObstacle;

        if (getMap()[x][y] == isObstacle) {
            return true;
        } else {
            throw new RuntimeException("Failed to set map tile");
        }
    }

    @Override
    public boolean isMoveAllowed(int x, int y, Direction direction) {
        boolean[][] map = this.getMap();
        if (x < 0 || x >= map.length || y < 0 || y >= map[0].length) {
            return false;
        }
        switch (direction) {
            case UP:
                return y - 1 >= 0 && !map[x][y - 1];
            case DOWN:
                return y + 1 < map[0].length && !map[x][y + 1];
            case LEFT:
                return x - 1 >= 0 && !map[x - 1][y];
            case RIGHT:
                return x + 1 < map.length && !map[x + 1][y];
            default:
                return false;
        }
    }

    @Override
    public void processMap() {
        this.updateMap();
        // Further implementation of map processing should follow here...

    }

    /**
     * Iterates over all collidables and updates the map accordingly.
     */
    private void updateMap() {
        for (ICollidable collidable : getCollidables()) {
            Coordinates position = collidable.getCoordinates();
            GridPosition gridPosition = position.getGridPosition();
            this.setMapTile(gridPosition.getX(), gridPosition.getY(), true);
        }
    }

    /**
     * Gets all collidables from the ServiceLoader.
     * @return a collection of all collidables
     */
    private Collection<? extends ICollidable> getCollidables() {
        return ServiceLoader.load(ICollidable.class).stream().map(ServiceLoader.Provider::get).toList();
    }
}
