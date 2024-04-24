package dk.sdu.mmmi.destructibleobstacle;

import dk.sdu.mmmi.common.data.entity.TextureLayer;
import dk.sdu.mmmi.common.data.gameproperties.GameData;
import dk.sdu.mmmi.common.data.world.World;
import dk.sdu.mmmi.common.services.obstacle.destructible.IDestructibleObstacle;
import dk.sdu.mmmi.common.services.obstacle.destructible.IDestructibleObstacleController;
import dk.sdu.mmmi.common.services.textureanimator.IAnimatable;
import dk.sdu.mmmi.common.services.textureanimator.ITextureAnimatorController;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class DestructibleObstacleControlSystem implements IDestructibleObstacleController {
    @Override
    public IDestructibleObstacle createDestructibleObstacle(GameData gamedata, World world) {
        Path defaultTexture = Paths.get("DestructibleObstacle/src/main/resources/destructible_obstacle_textures/block.png");
        IDestructibleObstacle destructibleObstacle = new DestructibleObstacle(world, gamedata.getScaler(), gamedata.getScaler(), defaultTexture);

        // Set texture layer of destructible obstacle instance
        if (destructibleObstacle instanceof DestructibleObstacle) {
            ((DestructibleObstacle) destructibleObstacle).setTextureLayer(TextureLayer.CONSTRUCTIONS.getValue());
        }

        // Add animator to destructible obstacle instance
        if (destructibleObstacle instanceof IAnimatable) {
            for (ITextureAnimatorController textureAnimatorController : getITextureAnimatorController()) {
                Path destructionAnimationPath = Paths.get("DestructibleObstacle/src/main/resources/destructible_obstacle_textures/destroy");
                ((IAnimatable) destructibleObstacle).addAnimator(DestructibleObjectAnimations.DESTROY.getValue(), textureAnimatorController.createTextureAnimator(gamedata, destructionAnimationPath, 0, 5, 20f));
            }
        }

        return destructibleObstacle;
    }

    /**
     * Get all ITextureAnimatorController implementations.
     *
     * @return Collection of ITextureAnimatorController
     */
    private Collection<? extends ITextureAnimatorController> getITextureAnimatorController() {
        return ServiceLoader.load(ITextureAnimatorController.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
