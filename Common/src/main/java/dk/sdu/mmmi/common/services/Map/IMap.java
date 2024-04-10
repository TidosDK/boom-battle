package dk.sdu.mmmi.common.services.Map;

import dk.sdu.mmmi.common.data.Entity.Direction;

/**
 * Represents an interface for a map in the game.
 * The map is represented as a 2D boolean array where each tile can be an obstacle or not.
 */
public interface IMap {
    /**
     * Checks if a given tile is an obstacle or not.
     *
     * @param x the x-coordinate of the tile
     * @param y the y-coordinate of the tile
     * @return true if the tile is an obstacle, false otherwise
     */
    boolean isTileObstacle(int x, int y);

    /**
     * Sets a given tile as an obstacle or not.
     *
     * @param x the x-coordinate of the tile
     * @param y the y-coordinate of the tile
     * @param isObstacle true if the tile should be an obstacle, false otherwise
     * @return true if the operation was successful, false otherwise
     * @throws RuntimeException if the operation fails
     */
    boolean setMapTile(int x, int y, boolean isObstacle) throws RuntimeException;

    /**
     * Checks if a move in a given direction from a given tile is allowed or not.
     *
     * @param x the x-coordinate of the tile
     * @param y the y-coordinate of the tile
     * @param direction the direction of the move
     * @return true if the move is allowed, false otherwise
     */
    boolean isMoveAllowed(int x, int y, Direction direction);
}