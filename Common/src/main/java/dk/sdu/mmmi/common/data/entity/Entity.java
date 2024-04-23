package dk.sdu.mmmi.common.data.entity;

import dk.sdu.mmmi.common.data.world.GridPosition;

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
        this.coordinates.setX(x);
    }

    public float getX() {
        return this.coordinates.getX();
    }

    public void setY(float y) {
        this.coordinates.setY(y);
    }

    public float getY() {
        return this.coordinates.getY();
    }

    public int getGridX() {
        return coordinates.getGridPosition().getX();
    }

    public int getGridY() {
        return coordinates.getGridPosition().getY();
    }

    public GridPosition getGridPosition() {
        return this.coordinates.getGridPosition();
    }

    public float getRotation() {
        switch (this.direction) {
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
        return this.coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Path getTexturePath() {
        return this.texturePath;
    }

    public void setTexturePath(Path texturePath) {
        this.texturePath = texturePath;
    }

    public int getTextureLayer() {
        return this.textureLayer;
    }

    public void setTextureLayer(int textureLayer) {
        this.textureLayer = textureLayer;
    }

    public float getWidth() {
        return this.width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return this.height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public Direction getDirection() {
        return this.direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
