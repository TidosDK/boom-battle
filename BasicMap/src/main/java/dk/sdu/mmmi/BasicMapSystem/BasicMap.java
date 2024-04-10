package dk.sdu.mmmi.BasicMapSystem;

import dk.sdu.mmmi.common.data.Coordinates;
import dk.sdu.mmmi.common.data.Direction;
import dk.sdu.mmmi.common.data.GridPosition;
import dk.sdu.mmmi.common.data.Map;
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
        super(11, 11);
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
    public boolean setMapTile(int x, int y, boolean isObstacle) throws RuntimeException {
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
    public boolean isMoveAllowed(float x, float y, Direction direction) {
        boolean[][] map = this.getMap();
        int maxX = map.length-1;
        int maxY = map[0].length -1;
        int gridX = Math.round(x);
        int gridY = Math.round(y);
        if (x < 0 || y < 0) {
            return false;
        }
        System.out.println("Checking move allowed");
        System.out.println("X: " + x + " Y: " + y);
        System.out.println("GridX: " + gridX + " GridY: " + gridY);
        System.out.println("MaxX: " + maxX + " MaxY: " + maxY);
        try {
            switch (direction) {
                case UP:
                    return (gridY == maxY) ? handleEdgeCases(x, y, direction) : !map[gridX][gridY + 1];
                case DOWN:
                    return (gridY == 0) ? handleEdgeCases(x, y, direction) : !map[gridX][gridY - 1];
                case LEFT:
                    return (gridX == 0) ? handleEdgeCases(x, y, direction) : !map[gridX - 1][gridY];
                case RIGHT:
                    return (gridX == maxX) ? handleEdgeCases(x, y, direction) : !map[gridX + 1][gridY];
                default:
                    return false;
            }
        } catch (IndexOutOfBoundsException e) {
            return false;
        }

    }

    private boolean handleEdgeCases(float x, float y, Direction direction) {
        System.out.println("Handling edge case");
        switch (direction) {
            case UP:
                return y < getHeight()-1;
            case DOWN:
                return y > 0;
            case LEFT:
                return x < getWidth()-1;
            case RIGHT:
                return x > 0;
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
     *
     * @return a collection of all collidables
     */
    private Collection<? extends ICollidable> getCollidables() {
        return ServiceLoader.load(ICollidable.class).stream().map(ServiceLoader.Provider::get).toList();
    }
}
