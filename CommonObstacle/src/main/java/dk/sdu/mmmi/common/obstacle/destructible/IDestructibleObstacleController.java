package dk.sdu.mmmi.common.obstacle.destructible;

import dk.sdu.mmmi.common.data.gameproperties.GameData;
import dk.sdu.mmmi.common.data.world.World;

public interface IDestructibleObstacleController {
    /**
     * Creates an instance of an destructible obstacle.
     * @param gameData
     * @param world
     * @return IDestructibleObstacle
     */
    IDestructibleObstacle createDestructibleObstacle(GameData gameData, World world);
}
