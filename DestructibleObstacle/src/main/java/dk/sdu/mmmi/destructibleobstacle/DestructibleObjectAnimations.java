package dk.sdu.mmmi.destructibleobstacle;

/**
 * Enum for the different animations of the destructible object.
 */
public enum DestructibleObjectAnimations {
    DESTROY(0);

    private final int value;

    DestructibleObjectAnimations(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
