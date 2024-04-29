package dk.sdu.mmmi.nondestructibleobstacle;

import dk.sdu.mmmi.common.data.entity.Entity;
import dk.sdu.mmmi.common.data.gameproperties.GameData;
import dk.sdu.mmmi.common.data.world.World;
import dk.sdu.mmmi.common.services.obstacle.nondestructible.INonDestructibleObstacle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class NonDestructibleObstacleTest {
    private GameData gameData;
    private World world;

    @BeforeEach
    void setup() {
        // Mocks the game data and world.
        gameData = mock(GameData.class);
        world = mock(World.class);

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
    void testDestructibleObstacleCreation() {
        // Creates a new destructible obstacle using the control system
        NonDestructibleObstacleControlSystem nonDestructibleObstacleControlSystem = new NonDestructibleObstacleControlSystem();
        INonDestructibleObstacle iNonDestructibleObstacle = nonDestructibleObstacleControlSystem.createNonDestructibleObstacle(gameData, world);

        // Adds the destructible obstacle to the world
        if (iNonDestructibleObstacle instanceof NonDestructibleObstacle obstacle) {
            world.addEntity(obstacle);
        }

        // Checks if the destructible obstacle exists in the world
        assertTrue(world.getEntities().contains(iNonDestructibleObstacle));
    }

    @Test
    void testDestructibleObstacleRemoval() {
        // Creates a new destructible obstacle using the control system
        NonDestructibleObstacleControlSystem nonDestructibleObstacleControlSystem = new NonDestructibleObstacleControlSystem();
        INonDestructibleObstacle iNonDestructibleObstacle = nonDestructibleObstacleControlSystem.createNonDestructibleObstacle(gameData, world);

        // Adds the destructible obstacle to the world
        if (iNonDestructibleObstacle instanceof NonDestructibleObstacle obstacle) {
            world.addEntity(obstacle);
        }

        // Removes the destructible obstacle from the world
        if (iNonDestructibleObstacle instanceof NonDestructibleObstacle obstacle) {
            world.removeEntity(obstacle);
        }

        // Checks if the destructible obstacle exists in the world
        assertFalse(world.getEntities().contains(iNonDestructibleObstacle));
    }
}