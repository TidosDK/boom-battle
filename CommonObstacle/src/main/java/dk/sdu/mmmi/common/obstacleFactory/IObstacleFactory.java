package dk.sdu.mmmi.common.obstacleFactory;

import dk.sdu.mmmi.common.data.gameproperties.GameData;
import dk.sdu.mmmi.common.data.world.World;
import dk.sdu.mmmi.common.obstacle.Obstacle;

public interface IObstacleFactory {
    Obstacle createNonDestructibleObstacle(GameData gameData, World world);

    Obstacle createDestructibleObstacle(GameData gameData, World world);
}
