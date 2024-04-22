package dk.sdu.mmmi.common.data.Entity;

import dk.sdu.mmmi.common.data.World.GridPosition;

import java.nio.file.Path;

public class Entity {
    private Coordinates coordinates;
    private int textureLayer;
    private Path texturePath;
    private float width;
    private float height;
    private Direction direction;

    public Entity(Path texturePath, float width, float height) {
        this.coordinates = new Coordinates(0, 0);
        this.texturePath = texturePath;
        this.textureLayer = TextureLayer.DEFAULT.getValue();
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

    public Path getTexturePath() {
        return texturePath;
    }

    public void setTexturePath(Path texturePath) {
        this.texturePath = texturePath;
    }

    public int getTextureLayer() {
        return textureLayer;
    }

    public void setTextureLayer(int textureLayer) {
        this.textureLayer = textureLayer;
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
