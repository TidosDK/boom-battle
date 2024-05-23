package dk.sdu.mmmi.common.data.world;

import dk.sdu.mmmi.common.services.map.IMap;
import dk.sdu.mmmi.common.services.map.IMapProcessingService;

/**
 * Represents a base class for a map.
 * The map is represented as a 2D boolean array.
 */
public abstract class Map implements IMap, IMapProcessingService {

    // A 2D boolean array representing the map
    private boolean[][] map;
    // The width of the map
    private int width;
    // The height of the map
    private int height;

    /**
     * Constructs a new Map with the given width and height.
     *
     * @param width  the width of the new map
     * @param height the height of the new map
     */
    public Map(int width, int height) {
        this.width = width;
        this.height = height;
        this.map = new boolean[width][height];
    }

    /**
     * Returns the 2D boolean array representing the map.
     *
     * @return the 2D boolean array representing the map
     */
    public boolean[][] getMap() {
        return this.map;
    }

    /**
     * Sets the 2D boolean array representing the map.
     *
     * @param map the new 2D boolean array representing the map
     */
    public void setMap(boolean[][] map) {
        this.map = map;
    }

    /**
     * Returns the width of the map.
     *
     * @return the width of the map
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Returns the height of the map.
     *
     * @return the height of the map
     */
    public int getHeight() {
        return this.height;
    }


}