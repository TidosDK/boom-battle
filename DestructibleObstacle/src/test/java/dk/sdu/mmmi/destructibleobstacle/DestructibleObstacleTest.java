package dk.sdu.mmmi.destructibleobstacle;

import dk.sdu.mmmi.common.data.entity.Entity;
import dk.sdu.mmmi.common.data.gameproperties.GameData;
import dk.sdu.mmmi.common.data.world.World;
import dk.sdu.mmmi.common.services.obstacle.destructible.IDestructibleObstacle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * This class is used to test the DestructibleObstacle class.
 */
class DestructibleObstacleTest {
    private DestructibleObstacle destructibleObstacle;
    private GameData gameData;
    private World world;
    private Path texture;

    @BeforeEach
    void setup() {
        // Creates a new destructible obstacle and mocks the game data and world.
        texture = Paths.get("DestructibleObstacle/src/main/resources/destructible_obstacle_textures/block.png");
        gameData = mock(GameData.class);
        world = mock(World.class);
        destructibleObstacle = new DestructibleObstacle(world, 2f, 2f, texture);

        // Mocks the world to add entities
        doAnswer(invocation -> {
            Entity entity = invocation.getArgument(0);
            world.getEntities().add(entity);
            return null;
        }).when(world).addEntity(any(Entity.class));

        // Mocks the world to remove entities
        doAnswer(invocation -> {
            Entity entity = invocation.getArgument(0);
            world.getEntities().remove(entity);
            return null;
        }).when(world).removeEntity(any(Entity.class));

        // Mocks the world to retrieve the list of entities
        when(world.getEntities()).thenReturn(new ArrayList<>());
    }

    // Verifies functional requirement F-05 & F-05b
    @Test
    void testDestructibleObstacleCreation() {
        // Adds the destructible obstacle to the world
        DestructibleObstacleControlSystem destructibleObstacleControlSystem = new DestructibleObstacleControlSystem();
        IDestructibleObstacle iDestructibleObstacle = destructibleObstacleControlSystem.createDestructibleObstacle(gameData, world);

        if (iDestructibleObstacle instanceof DestructibleObstacle obstacle) {
            world.addEntity(obstacle);
        }

        // Checks if the destructible obstacle exists in the world
        assertTrue(world.getEntities().contains(iDestructibleObstacle));
    }

    @Test
    void testDestruction() {
        // Adds the destructible obstacle to the world
        world.addEntity(destructibleObstacle);

        // Destroys the destructible obstacle
        destructibleObstacle.destroyObstacle();

        // Checks if the destructible obstacle exists in the world
        assertFalse(world.getEntities().contains(destructibleObstacle));
    }

    @Test
    void testTakeDamage() {
        // Setting up the test
        DestructibleObstacleControlSystem destructibleObstacleControlSystem = new DestructibleObstacleControlSystem();
        world.addEntity(destructibleObstacle);
        destructibleObstacle.setLifepoints(2);

        // Should take the destructible obstacle to 1 Life points.
        destructibleObstacle.removeLifepoints(1);
        assertEquals(1, destructibleObstacle.getLifepoints());

        // Should do nothing
        destructibleObstacleControlSystem.process(world, gameData);
        assertTrue(world.getEntities().contains(destructibleObstacle));

        // Should take the destructible obstacle to 0 Life points
        destructibleObstacle.removeLifepoints(1);
        assertEquals(0, destructibleObstacle.getLifepoints());

        // Should "kill" / remove the destructible obstacle from the world.
        destructibleObstacleControlSystem.process(world, gameData);

        assertFalse(world.getEntities().contains(destructibleObstacle));
    }
}