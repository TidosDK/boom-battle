package dk.sdu.mmmi.common.data;

import dk.sdu.mmmi.common.services.ICollidable;

public class Map {

    private boolean[][] map;
    private int width;
    private int height;

    public Map(int width, int height) {
        this.width = width;
        this.height = height;
        map = new boolean[width][height];
    }

    public boolean[][] getMap() {
        return map;
    }

    public void setMap(boolean[][] map) {
        this.map = map;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
