package dk.sdu.mmmi.common.services.Obstacle.Destructible;

import dk.sdu.mmmi.common.data.Properties.GameData;
import dk.sdu.mmmi.common.data.World.World;

public interface IDestructibleObstacleControlSystem {
    IDestructibleObstacle createDestructibleObstacle(GameData gameData, World world);
}
