package dk.sdu.mmmi.common.services.Obstacle.nondestructible;

import dk.sdu.mmmi.common.data.Properties.GameData;
import dk.sdu.mmmi.common.data.World.World;

public interface INonDestructibleObstacleController {
    /**
     * Creates an instance of a non-destructible obstacle.
     * @param gameData
     * @param world
     * @return INonDestructibleObstacle
     */
    INonDestructibleObstacle createNonDestructibleObstacle(GameData gameData, World world);
}
