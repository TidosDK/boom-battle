package dk.sdu.mmmi.bomb;

import dk.sdu.mmmi.common.data.entity.Entity;
import dk.sdu.mmmi.common.data.entity.TextureLayer;

import java.nio.file.Path;

/**
 * Explosion: Entity class for the explosion tile.
 */
public class Explosion extends Entity {
    private float elapsedTime;
    private float explosionTime;
    private Bomb bomb;

    /**
     * Constructor for the Explosion class.
     *
     * @param texturePath   Path to the texture
     * @param x             X coordinate of the explosion tile
     * @param y             Y coordinate of the explosion tile
     * @param width         Width of the explosion texture
     * @param height        Height of the explosion texture
     * @param explosionTime Duration of the explosion. The explosion will disappear after this time has passed.
     */
    public Explosion(Path texturePath, float x, float y, float width, float height, float explosionTime) {
        super(texturePath, width, height);
        super.setTextureLayer(TextureLayer.DEFAULT.getValue());
        this.setX(x);
        this.setY(y);
        this.explosionTime = explosionTime;
    }

    public void setElapsedTime(float time) {
        this.elapsedTime = time;
    }

    public float getElapsedTime() {
        return this.elapsedTime;
    }

    public float getExplosionTime() {
        return this.explosionTime;
    }

    public void setBomb(Bomb bomb) {
        this.bomb = bomb;
    }

    public Bomb getBomb() {
        return this.bomb;
    }

}
