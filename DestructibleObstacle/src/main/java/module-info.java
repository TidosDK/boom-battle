import dk.sdu.mmmi.common.services.Obstacle.destructible.IDestructibleObstacleController;
import dk.sdu.mmmi.common.services.TextureAnimator.ITextureAnimator;
import dk.sdu.mmmi.destructibleobstacle.DestructibleObstacleControlSystem;

module DestructibleObstacle {
    requires Common;
    uses ITextureAnimator;
    provides IDestructibleObstacleController with DestructibleObstacleControlSystem;
}