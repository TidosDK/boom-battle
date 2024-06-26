package dk.sdu.mmmi.common.obstacle.nondestructible;

import dk.sdu.mmmi.common.data.gameproperties.GameData;
import dk.sdu.mmmi.common.data.world.World;

public interface INonDestructibleObstacleController {
    /**
     * Creates an instance of a non-destructible obstacle.
     * @param gameData
     * @param world
     * @return INonDestructibleObstacle
     */
    INonDestructibleObstacle createNonDestructibleObstacle(GameData gameData, World world);
}
