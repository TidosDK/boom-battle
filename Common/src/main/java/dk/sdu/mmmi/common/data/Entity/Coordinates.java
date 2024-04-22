package dk.sdu.mmmi.common.data.Entity;

import dk.sdu.mmmi.common.data.World.GridPosition;

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
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public GridPosition getGridPosition() {
        // If floats are above .5, round up
        return new GridPosition(Math.round(this.x), Math.round(this.y));
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

//    private GridPosition calculateGridPosition(float x_val, float y_val) throws IllegalArgumentException {
//        // Calculate the discrete position based on x and y
//        return new GridPosition((int) x_val, (int) y_val);
//    }
}

