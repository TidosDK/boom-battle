package dk.sdu.mmmi.common.data;

/**
 * This class is used to store the state of the keys used in the game.
 */
public class GameKeys {
    private static boolean[] keys;
    private static boolean[] pressed_keys;
    private static GameKeys instance = null;

    private final int NUM_KEYS = 5;
    private final int UP = 0;
    private final int LEFT = 1;
    private final int RIGHT = 2;
    private final int DOWN = 3;
    private final int SPACE = 4;

    private GameKeys() {
        keys = new boolean[NUM_KEYS];
        pressed_keys = new boolean[NUM_KEYS];
    }

    public static GameKeys getInstance() {
        if (instance == null) {
            instance = new GameKeys();
        }
        return instance;
    }

    public void setKey(int k, boolean b) {
        keys[k] = b;
    }

    public boolean isDown(int k) {
        return keys[k];
    }

    public boolean isPressed(int k) {
        boolean isKeyJustPressed = keys[k] && !pressed_keys[k];
        pressed_keys[k] = keys[k];
        return isKeyJustPressed;
    }

    public int getUP() {
        return UP;
    }

    public int getLEFT() {
        return LEFT;
    }

    public int getRIGHT() {
        return RIGHT;
    }

    public int getDOWN() {
        return DOWN;
    }

    public int getSPACE() {
        return SPACE;
    }
}
