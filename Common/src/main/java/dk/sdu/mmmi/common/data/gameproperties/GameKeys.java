package dk.sdu.mmmi.common.data.gameproperties;

/**
 * This class is used to store the state of the keys used in the game.
 */
public class GameKeys {
    private static boolean[] keys;
    private static boolean[] pressedKeys;
    private static GameKeys instance = null;

    private final int numKeys = 6;
    private final int up = 0;
    private final int left = 1;
    private final int right = 2;
    private final int down = 3;
    private final int space = 4;
    private final int GETPOS = 5;

    private GameKeys() {
        keys = new boolean[this.numKeys];
        pressedKeys = new boolean[this.numKeys];
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
        boolean isKeyJustPressed = keys[k] && !pressedKeys[k];
        pressedKeys[k] = keys[k];
        return isKeyJustPressed;
    }

    public int getUp() {
        return this.up;
    }

    public int getLeft() {
        return this.left;
    }

    public int getRight() {
        return this.right;
    }

    public int getDown() {
        return this.down;
    }

    public int getSpace() {
        return this.space;
    }

    public int getGetPos() {
        return GETPOS;
    }
}
