package dk.sdu.mmmi.destructibleObstacle;

public enum Animations {
    DESTROY(0);

    private final int value;

    Animations(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
