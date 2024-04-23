package dk.sdu.mmmi.main;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public final class GameLauncher {
    // This constructor ensures no instance can exist of this utility class.
    private GameLauncher() {
    }

    public static void main(String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setForegroundFPS(60);
        config.setTitle("boom-battle");
        config.setWindowedMode(850, 820);
        new Lwjgl3Application(new Main(), config);
    }
}
