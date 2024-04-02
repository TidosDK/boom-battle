package dk.sdu.mmmi.common.data;

/**
 * A dataclass representing a grid position on a map.
 */
public class MapPosition {
    private int x;
    private int y;

    public MapPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
