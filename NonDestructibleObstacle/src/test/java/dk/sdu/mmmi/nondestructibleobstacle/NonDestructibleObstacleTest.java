package dk.sdu.mmmi.nondestructibleobstacle;

import dk.sdu.mmmi.common.data.entity.Entity;
import dk.sdu.mmmi.common.data.gameproperties.GameData;
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
 * This class is used to test the NonDestructibleObstacle class.
 */
class NonDestructibleObstacleTest {
    private NonDestructibleObstacle nonDestructibleObstacle;
    private GameData gameData;
    private World world;
    private Path texture;

    @BeforeEach
    void setup() {
        // Creates a new destructible obstacle and mocks the game data and world.
        texture = Paths.get("NonDestructibleObstacle/src/main/resources/non_destructible_obstacle_textures/non-destructible-wall.png");
        gameData = mock(GameData.class);
        world = mock(World.class);
        nonDestructibleObstacle = new NonDestructibleObstacle(world, 2f, 2f, texture);

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

    // Verifies functional requirement F-05 & F-05a
    @Test
    void testDestructibleObstacleExists() {
        // Adds the destructible obstacle to the world
        world.addEntity(nonDestructibleObstacle);

        // Checks if the destructible obstacle exists in the world
        assertTrue(world.getEntities().contains(nonDestructibleObstacle));
    }

    @Test
    void testDestructibleObstacleRemoval() {
        // Adds the destructible obstacle to the world
        world.addEntity(nonDestructibleObstacle);

        // Removes the destructible obstacle from the world
        world.removeEntity(nonDestructibleObstacle);


        // Checks if the destructible obstacle exists in the world
        assertFalse(world.getEntities().contains(nonDestructibleObstacle));
    }
}