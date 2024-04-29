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
import java.util.stream.Collectors;

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
        boolean destructibleObstacleExists = false;
        for (Entity entity : world.getEntities()) {
            if (entity.getClass().equals(DestructibleObstacle.class)) {
                destructibleObstacleExists = true;
                break;
            }
        }

        // Asserts that the destructible obstacle exists in the world
        assertTrue(destructibleObstacleExists);
    }

    @Test
    void testDestruction() {
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


        // Adds the destructible obstacle to the world
        world.addEntity(destructibleObstacle);

        // Destroys the destructible obstacle
        destructibleObstacle.destroyObstacle();

        // Checks if the destructible obstacle exists in the world
        boolean destructibleObstacleExists = false;
        for (Entity entity : world.getEntities()) {
            if (entity.getClass().equals(DestructibleObstacle.class)) {
                destructibleObstacleExists = true;
                break;
            }
        }

        // Asserts that the destructible obstacle does not exist in the world
        assertFalse(destructibleObstacleExists);
    }

    @Test
    void testTakeDamage() {
        // Arrange
        destructibleObstacle.setLifepoints(1);
        ArrayList<Entity> mockEntityList = new ArrayList<>();

        // Mock the world to return the destructible obstacle
        doAnswer(invocation -> {
            ArrayList<Entity> entities = new ArrayList<>();
            for (Entity entity : mockEntityList) {
                if (entity.getClass().equals(DestructibleObstacle.class)) {
                    entities.add(entity);
                }
            }
            return entities;
        }).when(world).getEntities(DestructibleObstacle.class);
//        doAnswer(invocation -> mockEntityList.stream()
//                .filter(entity -> entity.getClass().equals(DestructibleObstacle.class))
//                .collect(Collectors.toList())).when(world).getEntities(DestructibleObstacle.class);

        // Mock world.removeEntity, using the mock list from above
        doAnswer(invocation -> {
            mockEntityList.remove(invocation.getArgument(0));
            return null;
        }).when(world).removeEntity(any(Entity.class));

        // Act
        mockEntityList.add(destructibleObstacle);

        DestructibleObstacleControlSystem destructibleObstacleControlSystem = new DestructibleObstacleControlSystem();
        destructibleObstacleControlSystem.process(world, gameData); // Should do nothing.

        destructibleObstacle.removeLifepoints(1); // Should take the destructible obstacle to 1 Life points.
        assertEquals(1, destructibleObstacle.getLifepoints());

        destructibleObstacle.removeLifepoints(1); // Should take the player to 0 Life points, "killing" it.
        assertEquals(0, destructibleObstacle.getLifepoints());
        destructibleObstacleControlSystem.process(world, gameData); // Should remove the destructible obstacle from the world.

        // Asserts the player has been removed from the world.
        assertTrue(mockEntityList.isEmpty());
    }
}