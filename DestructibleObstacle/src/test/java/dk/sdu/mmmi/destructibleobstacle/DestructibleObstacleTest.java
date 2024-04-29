package dk.sdu.mmmi.destructibleobstacle;

import dk.sdu.mmmi.common.data.entity.Entity;
import dk.sdu.mmmi.common.data.gameproperties.GameData;
import dk.sdu.mmmi.common.data.gameproperties.GameKeys;
import dk.sdu.mmmi.common.data.world.Map;
import dk.sdu.mmmi.common.data.world.World;
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
    private GameKeys gameKeys;
    private World world;
    private Path texture;

    @BeforeEach
    void setup() {
        // Creates a new destructible obstacle and mocks the game data, game keys and world.
        texture = Paths.get("DestructibleObstacle/src/main/resources/destructible_obstacle_textures/block.png");
        gameData = mock(GameData.class);
        gameKeys = mock(GameKeys.class);
        world = mock(World.class);
        destructibleObstacle = new DestructibleObstacle(world, 2f, 2f, texture);

        // Sets the game data's delta time and scaler.
        when(gameData.getDeltaTime()).thenReturn(0.016f);
        when(gameData.getScaler()).thenReturn(1.6f);

        // Sets the game data and the game keys.
        when(gameData.getKeys()).thenReturn(gameKeys);
        when(gameKeys.getUp()).thenReturn(0);
        when(gameKeys.getRight()).thenReturn(1);
        when(gameKeys.getDown()).thenReturn(2);
        when(gameKeys.getLeft()).thenReturn(3);

        // Assigns a mock Map to the World mock.
        when(world.getMap()).thenReturn(mock(Map.class));
        when(world.getMap().getWidth()).thenReturn(10);
        when(world.getMap().getHeight()).thenReturn(10);

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
    void testDestructibleObstacleExists() {
        // Mocks the world to add entities and retrieve the list of entities
        doAnswer(invocation -> {
            Entity entity = invocation.getArgument(0);
            world.getEntities().add(entity);
            return null;
        }).when(world).addEntity(any(Entity.class));
        when(world.getEntities()).thenReturn(new ArrayList<>());

        // Adds the destructible obstacle to the world
        world.addEntity(destructibleObstacle);

        // Checks if the destructible obstacle exists in the world
        assertTrue(world.getEntities().contains(destructibleObstacle));
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