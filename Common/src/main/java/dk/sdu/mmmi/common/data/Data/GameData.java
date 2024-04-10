package dk.sdu.mmmi.common.data.Data;

/**
 * This class is used to store data that is used in the game.
 */
public class GameData {

    private static GameData instance = null;
    private final GameKeys keys = GameKeys.getInstance();
    private float deltaTime = 0;

    private GameData() {
    }

    public static GameData getInstance() {
        if (instance == null) {
            return new GameData();
        }
        return instance;
    }

    public GameKeys getKeys() {
        return keys;
    }

    public float getDeltaTime() {
        return deltaTime;
    }

    public void setDeltaTime(float deltaTime) {
        this.deltaTime = deltaTime;
    }
}
