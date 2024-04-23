package dk.sdu.mmmi.common.data.Entity;

/**
 * Enum for the different layers of textures
 */
public enum TextureLayer {
    /**
     * Background: layer for background textures (e.g. grass)
     */
    BACKGROUND(0),

    /**
     * Obstacle: layer for construction related textures (e.g. walls)
     */
    CONSTRUCTIONS(2),

    /**
     * Default: layer for default textures
     */
    DEFAULT(4),

    /**
     * Power up: layer for power up textures (e.g. bombs)
     */
    POWER_UP(6),

    /**
     * Effect: layer for effect textures (e.g. explosions)
     */
    EFFECT(8),

    /**
     * Character: layer for character and NPC textures
     */
    CHARACTER(10);

    private final int VALUE;

    TextureLayer(int value) {
        this.VALUE = value;
    }

    /**
     * Get the value of the layer
     *
     * @return the value of the layer
     */
    public int getValue() {
        return VALUE;
    }
}
