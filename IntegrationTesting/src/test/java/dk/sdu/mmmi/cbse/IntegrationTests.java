import dk.sdu.mmmi.common.services.entityproperties.IActor;
import dk.sdu.mmmi.common.data.world.World;
import dk.sdu.mmmi.common.data.entity.Entity;
import dk.sdu.mmmi.main.Main;
import org.junit.jupiter.api.Test;

import java.util.ServiceLoader;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntegrationTests {

    @Test
    public void testOneActorPerModule() {
        // Load all IActor implementations
        List<IActor> actors = ServiceLoader.load(IActor.class).stream()
                .map(ServiceLoader.Provider::get)
                .collect(Collectors.toList());

        System.out.println(actors.size());

        // Simulate the start of the game
        Main game = new Main();
        game.create();

        // Get World instance
        World world = World.getInstance();

        // Get all entities in the world that are IActors
        int actorCount = 0;
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