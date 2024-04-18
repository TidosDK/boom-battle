import dk.sdu.mmmi.common.services.Obstacle.Destructible.IDestructibleObstacleController;
import dk.sdu.mmmi.common.services.TextureAnimator.ITextureAnimator;
import dk.sdu.mmmi.destructibleObstacle.DestructibleObstacleControlSystem;

module DestructibleObstacle {
    requires Common;
    uses ITextureAnimator;
    provides IDestructibleObstacleController with DestructibleObstacleControlSystem;
}