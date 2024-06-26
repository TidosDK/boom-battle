package dk.sdu.mmmi.common.data.gameproperties;

/**
 * This class is used to store data that is used in the game.
 */
public class GameData {

    private static GameData instance = null;
    private final GameKeys keys = GameKeys.getInstance();
    private final int gameScreenWidth = 800;
    private final int gameScreenHeight = 800;
    private static final float SCALER = 1.6f;
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
        return this.keys;
    }

    public int getGameScreenWidth() {
        return this.gameScreenWidth;
    }

    public int getGameScreenHeight() {
        return this.gameScreenHeight;
    }

    public float getDeltaTime() {
        return this.deltaTime;
    }

    public void setDeltaTime(float deltaTime) {
        this.deltaTime = deltaTime;
    }

    public float getScaler() {
        return SCALER;
    }
}
