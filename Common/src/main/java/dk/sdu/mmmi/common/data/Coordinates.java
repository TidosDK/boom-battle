package dk.sdu.mmmi.common.data;

/**
 * A dataclass representing a grid position on a map.
 */
public class Coordinates {
    private float x;
    private float y;


    public Coordinates(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public GridPosition getGridPosition() {
        // If floats are above .5, round up
        int x = (int) Math.round(this.x);
        int y = (int) Math.round(this.y);
        return new GridPosition(x, y);
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    private GridPosition calculateGridPosition(float x, float y) throws IllegalArgumentException {
        // Calculate the discrete position based on x and y
        return new GridPosition((int) x, (int) y);
    }
}

