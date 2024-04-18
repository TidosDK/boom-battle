package dk.sdu.mmmi.player;

public enum Animations {
    LEFT(1),
    RIGHT(2),
    UP(3),
    DOWN(4);

    private final int value;

    Animations(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
