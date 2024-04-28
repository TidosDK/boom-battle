package dk.sdu.mmmi.enemy;

public enum EnemyAnimations {
    LEFT(1),
    RIGHT(2),
    UP(3),
    DOWN(4),
    STILL(5),
    DIE(6);

    private final int value;

    EnemyAnimations(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
