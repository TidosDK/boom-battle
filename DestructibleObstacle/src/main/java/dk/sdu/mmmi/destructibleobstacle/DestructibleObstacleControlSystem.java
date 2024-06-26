package dk.sdu.mmmi.destructibleobstacle;

import dk.sdu.mmmi.common.data.entity.Entity;
import dk.sdu.mmmi.common.data.entity.TextureLayer;
import dk.sdu.mmmi.common.data.gameproperties.GameData;
import dk.sdu.mmmi.common.data.world.World;
import dk.sdu.mmmi.common.obstacle.destructible.IDestructibleObstacleController;
import dk.sdu.mmmi.common.obstacle.destructible.IDestructibleObstacle;
import dk.sdu.mmmi.common.services.IEntityProcessingService;
import dk.sdu.mmmi.common.textureanimator.ITextureAnimatorController;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class DestructibleObstacleControlSystem implements IEntityProcessingService, IDestructibleObstacleController {
    @Override
    public void process(World world, GameData gameData) {
        for (Entity entity : world.getEntities(DestructibleObstacle.class)) {
            if (entity instanceof DestructibleObstacle destructibleObstacle) {
                checkDestruction(destructibleObstacle);
            }
        }
    }

    /**
     * Checks the destruction of the destructible obstacle and destroys it if necessary.
     *
     * @param destructibleObstacle The destructible obstacle to check for destruction
     */
    private void checkDestruction(DestructibleObstacle destructibleObstacle) {
        if (destructibleObstacle.getLifepoints() <= 0) { // obstacle is destroyed
            destructibleObstacle.destroyObstacle();
        }
    }

    @Override
    public IDestructibleObstacle createDestructibleObstacle(GameData gamedata, World world) {
        Path defaultTexture = Paths.get("DestructibleObstacle/src/main/resources/destructible_obstacle_textures/block.png");
        DestructibleObstacle destructibleObstacle = new DestructibleObstacle(world, gamedata.getScaler(), gamedata.getScaler(), defaultTexture);

        // Set texture layer of destructible obstacle instance
        destructibleObstacle.setTextureLayer(TextureLayer.CONSTRUCTIONS.getValue());

        // Add animator to destructible obstacle instance
        for (ITextureAnimatorController textureAnimatorController : getITextureAnimatorController()) {
            Path destructionAnimationPath = Paths.get("DestructibleObstacle/src/main/resources/destructible_obstacle_textures/destroy");
            destructibleObstacle.addAnimator(DestructibleObstacleAnimations.DESTROY.getValue(), textureAnimatorController.createTextureAnimator(gamedata, destructionAnimationPath, 0, 5, 20f));
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
