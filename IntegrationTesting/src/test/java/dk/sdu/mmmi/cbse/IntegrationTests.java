package dk.sdu.mmmi.cbse;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import dk.sdu.mmmi.common.data.gameproperties.GameData;
import dk.sdu.mmmi.common.services.entityproperties.IActor;
import dk.sdu.mmmi.common.data.world.World;
import dk.sdu.mmmi.common.data.entity.Entity;
import dk.sdu.mmmi.main.Main;
import org.junit.jupiter.api.Test;

import java.util.ServiceLoader;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntegrationTests {

    @Test
    public void testOneActorPerModule() {
        // Load all IActor implementations
        List<IActor> actors = ServiceLoader.load(IActor.class).stream().map(ServiceLoader.Provider::get).collect(toList());

        System.out.println(actors.size());

        Main game = new Main();

        // Start The game
        GameData gameData = GameData.getInstance();
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setForegroundFPS(60);
        config.setTitle("boom-battle test");
        config.setWindowedMode(gameData.getGameScreenWidth(), gameData.getGameScreenHeight());

        Lwjgl3Application app = new Lwjgl3Application(game, config);

        game.create(); // Is called only after the game has been closed. This does not work.

        // Get World instance
        World world = World.getInstance();

        int actorCount = 0;
        // Get all entities in the world that are IActors
        List<Entity> actorEntities = world.getEntities();
        for (Entity entity : actorEntities) {
            if (entity instanceof IActor) {
                actorCount++;
            }
        }
        System.out.println(actorCount);

        // Assert that the number of IActor entities is equal to the number of IActor implementations
        assertEquals(actors.size(), actorCount);
    }
}