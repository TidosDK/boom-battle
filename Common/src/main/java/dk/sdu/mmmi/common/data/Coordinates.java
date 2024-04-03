package dk.sdu.mmmi.common.data;

/**
 * A dataclass representing a grid position on a map.
 */
public class Coordinates {
    private float x;
    private float y;


    public Coordinates(float x_discrete, float y) {
        this.x = x_discrete;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public GridPosition getGridPosition() {
        return new GridPosition((int) x, (int) y);
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
