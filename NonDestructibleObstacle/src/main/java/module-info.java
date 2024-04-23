import dk.sdu.mmmi.common.services.IGamePluginService;
import dk.sdu.mmmi.common.services.obstacle.nondestructible.INonDestructibleObstacleController;
import dk.sdu.mmmi.nondestructibleobstacle.NonDestructibleObstacleControlSystem;
import dk.sdu.mmmi.nondestructibleobstacle.NonDestructibleObstaclePlugin;

module NonDestructibleObstacle {
    requires Common;
    provides IGamePluginService with NonDestructibleObstaclePlugin;
    provides INonDestructibleObstacleController with NonDestructibleObstacleControlSystem;
}