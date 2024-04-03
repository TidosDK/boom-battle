package dk.sdu.mmmi.common.data;

public class Entity {
    private Coordinates coordinates;


    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates (Coordinates coordinates) {
        this.coordinates = coordinates;
    }


    public void setX(float x) {
        coordinates.setX(x);
    }


    public void setY(float y) {
        coordinates.setY(y);
    }

    public GridPosition getGridPosition() {
        return coordinates.getGridPosition();
    }
}
