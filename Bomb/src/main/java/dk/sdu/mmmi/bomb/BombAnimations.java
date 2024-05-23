package dk.sdu.mmmi.bomb;

public enum BombAnimations {
    PLACEMENT(1);

    private final int value;

    BombAnimations(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
