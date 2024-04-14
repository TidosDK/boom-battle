import dk.sdu.mmmi.common.services.Obstacle.Destructible.IDestructibleObstacleControlSystem;
import dk.sdu.mmmi.common.services.TextureAnimator.ITextureAnimator;
import dk.sdu.mmmi.destructibleObstacle.DestructibleObstacleControlSystem;

module DestructibleObstacle {
    requires Common;
    uses ITextureAnimator;
    provides IDestructibleObstacleControlSystem with DestructibleObstacleControlSystem;
}