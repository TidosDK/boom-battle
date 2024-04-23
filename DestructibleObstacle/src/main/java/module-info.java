import dk.sdu.mmmi.common.services.obstacle.destructible.IDestructibleObstacleController;
import dk.sdu.mmmi.common.services.textureanimator.ITextureAnimator;
import dk.sdu.mmmi.destructibleobstacle.DestructibleObstacleControlSystem;

module DestructibleObstacle {
    requires Common;
    uses ITextureAnimator;
    provides IDestructibleObstacleController with DestructibleObstacleControlSystem;
}