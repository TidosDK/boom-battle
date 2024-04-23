package dk.sdu.mmmi.common.data.Entity;

import dk.sdu.mmmi.common.data.World.GridPosition;

public class Entity {
    private Coordinates coordinates;
    private String texturePath;
    private float width;
    private float height;
    private Direction direction;

    public Entity(String texturePath, float width, float height) {
        this.coordinates = new Coordinates(0, 0);
        this.texturePath = texturePath;
        this.width = width;
        this.height = height;
        this.direction = Direction.UP;
    }

    public void setX(float x) {
        coordinates.setX(x);
    }

    public float getX() {
        return coordinates.getX();
    }

    public void setY(float y) {
        coordinates.setY(y);
    }

    public float getY() {
        return coordinates.getY();
    }

    public int getGridX() {
        return coordinates.getGridPosition().getX();
    }

    public int getGridY() {
        return coordinates.getGridPosition().getY();
    }

    public GridPosition getGridPosition() {
        return coordinates.getGridPosition();
    }

    public float getRotation() {
        switch (direction) {
            default:
                return 0;
            case DOWN:
                return 180;
            case LEFT:
                return 90;
            case RIGHT:
                return 270;
        }
    }

    /*
     * ----------------------------
     * Default Getters and Setters
     * ----------------------------
     */

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public String getTexturePath() {
        return texturePath;
    }

    public void setTexturePath(String texturePath) {
        this.texturePath = texturePath;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
