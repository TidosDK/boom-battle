package dk.sdu.mmmi.common.data.entity;

/**
 * Enum for the different layers of textures.
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
     * Default: layer for default textures.
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
     * Character: layer for character and NPC textures.
     */
    CHARACTER(10);

    private final int value;

    TextureLayer(int value) {
        this.value = value;
    }

    /**
     * Get the value of the layer.
     *
     * @return the value of the layer
     */
    public int getValue() {
        return this.value;
    }
}
