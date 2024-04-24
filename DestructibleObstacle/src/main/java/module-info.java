import dk.sdu.mmmi.common.services.obstacle.destructible.IDestructibleObstacleController;
import dk.sdu.mmmi.common.services.textureanimator.ITextureAnimator;
import dk.sdu.mmmi.common.services.textureanimator.ITextureAnimatorController;
import dk.sdu.mmmi.destructibleobstacle.DestructibleObstacleControlSystem;

module DestructibleObstacle {
    requires Common;
    uses ITextureAnimator;
    uses ITextureAnimatorController;
    provides IDestructibleObstacleController with DestructibleObstacleControlSystem;
}