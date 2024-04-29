package dk.sdu.mmmi.destructibleobstacle;

/**
 * Enum for the different animations of the destructible object.
 */
public enum DestructibleObstacleAnimations {
    DESTROY(0);

    private final int value;

    DestructibleObstacleAnimations(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
