package dk.sdu.mmmi.common.data;

public class Entity {
    private MapPosition mapPosition;
    private float x;
    private float y;


    public MapPosition getMapPosition() {
        return mapPosition;
    }

    public void setMapPosition(MapPosition mapPosition) {
        this.mapPosition = mapPosition;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        if (calcMapPosition(x, this.y)) {
            this.x = x;
        }
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        if (calcMapPosition(this.x, y)) {
            this.y = y;
        }
    }

    private boolean calcMapPosition(float x, float y){
        // Calculate the MapPosition based on x and y
        return true;
    }
}
