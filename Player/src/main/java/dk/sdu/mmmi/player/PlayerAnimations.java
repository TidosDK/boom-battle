package dk.sdu.mmmi.player;

/**
 * Enum for the different animations of the player.
 */
public enum PlayerAnimations {
    LEFT(1),
    RIGHT(2),
    UP(3),
    DOWN(4);

    private final int value;

    PlayerAnimations(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
