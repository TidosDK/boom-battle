package dk.sdu.mmmi.common.services.Obstacle.destructible;

import dk.sdu.mmmi.common.data.Properties.GameData;
import dk.sdu.mmmi.common.data.World.World;

public interface IDestructibleObstacleController {
    /**
     * Creates an instance of an destructible obstacle
     * @param gameData
     * @param world
     * @return IDestructibleObstacle
     */
    IDestructibleObstacle createDestructibleObstacle(GameData gameData, World world);
}
