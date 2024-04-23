package dk.sdu.mmmi.common.data.entity;

import dk.sdu.mmmi.common.data.gameproperties.GameData;
import dk.sdu.mmmi.common.data.world.GridPosition;

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

    public Coordinates(GridPosition gridPosition) {
        this.x = gridPosition.getX() * GameData.getInstance().getScaler();
        this.y = gridPosition.getY() * GameData.getInstance().getScaler();
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public int getGridX() {
        return getGridPosition().getX();
    }

    public int getGridY() {
        return getGridPosition().getY();
    }

    public GridPosition getGridPosition() {
        return new GridPosition(x, y);
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    private GridPosition calculateGridPosition(float xParam, float yParam) throws IllegalArgumentException {
        // Calculate the discrete position based on x and y
        return new GridPosition(xParam, yParam);
    }

    // to String
    @Override
    public String toString() {
        return "Coordinates{" + "x=" + x + ", y=" + y + "}";
    }
}

