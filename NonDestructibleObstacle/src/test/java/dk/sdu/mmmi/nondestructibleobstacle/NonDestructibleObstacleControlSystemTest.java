package dk.sdu.mmmi.nondestructibleobstacle;

import dk.sdu.mmmi.common.data.entity.Entity;
import dk.sdu.mmmi.common.data.gameproperties.GameData;
import dk.sdu.mmmi.common.data.world.World;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * This class is used to test the NonDestructibleObstacleControlSystem class.
 */
class NonDestructibleObstacleControlSystemTest {
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

        // Mocks the world to retrieve the list of entities
        when(world.getEntities()).thenReturn(new ArrayList<>());
    }

    @Test
    void testDestructibleObstacleCreation() {
        // Creates a new destructible obstacle using the control system
        NonDestructibleObstacleControlSystem nonDestructibleObstacleControlSystem = new NonDestructibleObstacleControlSystem();
        NonDestructibleObstacle nonDestructibleObstacle = (NonDestructibleObstacle) nonDestructibleObstacleControlSystem.createNonDestructibleObstacle(gameData, world);

        world.addEntity(nonDestructibleObstacle);

        // Checks if the destructible obstacle exists in the world
        assertTrue(world.getEntities().contains(nonDestructibleObstacle));
    }

}