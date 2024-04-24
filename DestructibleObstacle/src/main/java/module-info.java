import dk.sdu.mmmi.common.services.IGamePluginService;
import dk.sdu.mmmi.common.services.obstacle.destructible.IDestructibleObstacleController;
import dk.sdu.mmmi.common.services.textureanimator.ITextureAnimator;
import dk.sdu.mmmi.common.services.textureanimator.ITextureAnimatorController;
import dk.sdu.mmmi.destructibleobstacle.DestructibleObstacleControlSystem;
import dk.sdu.mmmi.destructibleobstacle.DestructibleObstaclePlugin;

module DestructibleObstacle {
    requires Common;
    uses ITextureAnimator;
    uses ITextureAnimatorController;
    provides IGamePluginService with DestructibleObstaclePlugin;
    provides IDestructibleObstacleController with DestructibleObstacleControlSystem;
}