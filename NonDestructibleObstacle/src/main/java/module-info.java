import dk.sdu.mmmi.common.services.IGamePluginService;
import dk.sdu.mmmi.common.obstacle.nondestructible.INonDestructibleObstacleController;
import dk.sdu.mmmi.nondestructibleobstacle.NonDestructibleObstacleControlSystem;
import dk.sdu.mmmi.nondestructibleobstacle.NonDestructibleObstaclePlugin;

module NonDestructibleObstacle {
    requires Common;
    requires CommonObstacle;
    provides IGamePluginService with NonDestructibleObstaclePlugin;
    provides INonDestructibleObstacleController with NonDestructibleObstacleControlSystem;
}