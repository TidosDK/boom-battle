import dk.sdu.mmmi.common.services.Obstacle.nondestructible.INonDestructibleObstacleController;
import dk.sdu.mmmi.nondestructibleobstacle.NonDestructibleObstacleControlSystem;

module NonDestructibleObstacle {
    requires Common;
    provides INonDestructibleObstacleController with NonDestructibleObstacleControlSystem;
}