package dk.sdu.mmmi.common.data.World;

import dk.sdu.mmmi.common.data.Properties.GameData;

public class GridPosition {
    private int x;
    private int y;

    public GridPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public GridPosition(float x, float y) {
        float scaler = GameData.getInstance().getScaler();
        this.x = Math.round(x / scaler);
        this.y = Math.round(y / scaler);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GridPosition that = (GridPosition) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        int result = Integer.hashCode(x);
        result = 31 * result + Integer.hashCode(y);
        return result;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    @Override
    public String toString() {
        return "GridPosition{" + "x=" + x + ", y=" + y + "}";
    }

}
