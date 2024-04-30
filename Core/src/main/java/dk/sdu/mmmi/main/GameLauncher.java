package dk.sdu.mmmi.main;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import dk.sdu.mmmi.common.data.gameproperties.GameData;

public final class GameLauncher {
    // This constructor ensures no instance can exist of this utility class.
    private GameLauncher() {
    }

    public static void main(String[] arg) {
        GameData gameData = GameData.getInstance();
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setForegroundFPS(60);
        config.setTitle("boom-battle");
        config.setWindowedMode(gameData.getGameScreenWidth(), gameData.getGameScreenHeight());
        new Lwjgl3Application(new Main(), config);
    }
}
