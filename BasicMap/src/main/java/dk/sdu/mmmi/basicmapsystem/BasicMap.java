package dk.sdu.mmmi.basicmapsystem;

import dk.sdu.mmmi.common.data.entity.Coordinates;
import dk.sdu.mmmi.common.data.entity.Direction;
import dk.sdu.mmmi.common.data.gameproperties.GameData;
import dk.sdu.mmmi.common.data.world.GridPosition;
import dk.sdu.mmmi.common.data.world.Map;
import dk.sdu.mmmi.common.services.entityproperties.ICollidable;
import dk.sdu.mmmi.common.services.map.IMap;
import dk.sdu.mmmi.common.services.map.IMapProcessingService;

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
    public boolean isMoveAllowed(int gridX, int gridY, Direction direction) {
        boolean[][] map = this.getMap();
        int maxX = map.length - 1;
        int maxY = map[0].length - 1;
        try {
            return switch (direction) {
                case UP -> (gridY == maxY) ? handleEdgeCases(gridX, gridY, direction) : !map[gridX][gridY + 1];
                case DOWN -> (gridY == 0) ? handleEdgeCases(gridX, gridY, direction) : !map[gridX][gridY - 1];
                case LEFT -> (gridX == 0) ? handleEdgeCases(gridX, gridY, direction) : !map[gridX - 1][gridY];
                case RIGHT -> (gridX == maxX) ? handleEdgeCases(gridX, gridY, direction) : !map[gridX + 1][gridY];
                default -> false;
            };
        } catch (IndexOutOfBoundsException e) {
            return false;
        }

    }

    private boolean handleEdgeCases(float x, float y, Direction direction) {
        float xUnscaled = x / GameData.getInstance().getScaler();
        float yUnscaled = y / GameData.getInstance().getScaler();
        return switch (direction) {
            case UP -> yUnscaled < getHeight() - 1;
            case DOWN -> yUnscaled > 0;
            case LEFT -> xUnscaled < getWidth() - 1;
            case RIGHT -> xUnscaled > 0;
            default -> false;
        };

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
