import dk.sdu.mmmi.common.obstacle.destructible.IDestructibleObstacleController;
import dk.sdu.mmmi.common.services.IGamePluginService;
import dk.sdu.mmmi.common.textureanimator.ITextureAnimator;
import dk.sdu.mmmi.common.textureanimator.ITextureAnimatorController;
import dk.sdu.mmmi.destructibleobstacle.DestructibleObstacleControlSystem;
import dk.sdu.mmmi.destructibleobstacle.DestructibleObstaclePlugin;

module DestructibleObstacle {
    requires Common;
    requires CommonObstacle;
    requires CommonTextureAnimator;
    uses ITextureAnimator;
    uses ITextureAnimatorController;
    provides IGamePluginService with DestructibleObstaclePlugin;
    provides IDestructibleObstacleController with DestructibleObstacleControlSystem;
}