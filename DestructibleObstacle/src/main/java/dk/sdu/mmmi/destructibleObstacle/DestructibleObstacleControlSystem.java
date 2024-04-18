package dk.sdu.mmmi.destructibleObstacle;

import dk.sdu.mmmi.common.data.Entity.TextureLayer;
import dk.sdu.mmmi.common.data.Properties.GameData;
import dk.sdu.mmmi.common.data.World.World;
import dk.sdu.mmmi.common.services.Obstacle.Destructible.IDestructibleObstacle;
import dk.sdu.mmmi.common.services.Obstacle.Destructible.IDestructibleObstacleController;
import dk.sdu.mmmi.common.services.TextureAnimator.IAnimatable;
import dk.sdu.mmmi.common.services.TextureAnimator.ITextureAnimatorController;

import java.util.Collection;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class DestructibleObstacleControlSystem implements IDestructibleObstacleController {
    @Override
    public IDestructibleObstacle createDestructibleObstacle(GameData gamedata, World world) {
        IDestructibleObstacle destructibleObstacle = new DestructibleObstacle(world, gamedata.getScaler(), gamedata.getScaler(), "DestructibleObstacle/src/main/resources/destructible_obstacle_textures/block.png");

        // Set texture layer of destructible obstacle instance
        if (destructibleObstacle instanceof DestructibleObstacle) {
            ((DestructibleObstacle) destructibleObstacle).setTextureLayer(TextureLayer.CONSTRUCTIONS.getValue());
        }

        // Add animator to destructible obstacle instance
        if (destructibleObstacle instanceof IAnimatable) {
            for (ITextureAnimatorController textureAnimatorController : getITextureAnimatorController()) {
                ((IAnimatable) destructibleObstacle).addAnimator(DestructibleObjectAnimations.DESTROY.getValue(), textureAnimatorController.createTextureAnimator(gamedata, "DestructibleObstacle/src/main/resources/destructible_obstacle_textures/destroy", 0, 5, 20f));
            }
        }

        return destructibleObstacle;
    }

    /**
     * Get all ITextureAnimatorController implementations
     *
     * @return Collection of ITextureAnimatorController
     */
    private Collection<? extends ITextureAnimatorController> getITextureAnimatorController() {
        return ServiceLoader.load(ITextureAnimatorController.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
