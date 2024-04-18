package dk.sdu.mmmi.common.data.Entity;

/**
 * Enum for the different layers of textures
 */
public enum TextureLayer {
    /**
     * Background: layer for background textures (e.g. walls)
     */
    BACKGROUND(0),

    /**
     * Default: layer for default textures
     */
    DEFAULT(2),

    /**
     * Power up: layer for power up textures (e.g. bombs)
     */
    POWER_UP(4),

    /**
     * Effect: layer for effect textures (e.g. explosions)
     */
    EFFECT(5),

    /**
     * Character: layer for character and NPC textures
     */
    CHARACTER(8);

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
