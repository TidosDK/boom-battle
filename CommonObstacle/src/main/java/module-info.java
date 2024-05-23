module CommonObstacle {
    uses dk.sdu.mmmi.common.obstacle.nondestructible.INonDestructibleObstacleController;
    uses dk.sdu.mmmi.common.obstacle.destructible.IDestructibleObstacleController;
    requires Common;
    exports dk.sdu.mmmi.common.obstacle.destructible;
    exports dk.sdu.mmmi.common.obstacle.nondestructible;
    exports dk.sdu.mmmi.common.obstacle;
    exports dk.sdu.mmmi.common.obstacleFactory;
}