package dk.sdu.mmmi.destructibleobstacle;

import dk.sdu.mmmi.common.data.entity.Entity;
import dk.sdu.mmmi.common.data.gameproperties.GameData;
import dk.sdu.mmmi.common.data.world.World;
import dk.sdu.mmmi.common.services.obstacle.destructible.IDestructibleObstacle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * This class is used to test the DestructibleObstacleControlSystem class.
 */
class DestructibleObstacleControlSystemTest {
    private DestructibleObstacle destructibleObstacle;
    private GameData gameData;
    private World world;

    @BeforeEach
    void setup() {
        // Creates a new destructible obstacle and mocks the game data and world.
        gameData = mock(GameData.class);
        world = mock(World.class);

        // Mocks the world to add entities
        doAnswer(invocation -> {
            Entity entity = invocation.getArgument(0);
            world.getEntities().add(entity);
            return null;
        }).when(world).addEntity(any(Entity.class));

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
}