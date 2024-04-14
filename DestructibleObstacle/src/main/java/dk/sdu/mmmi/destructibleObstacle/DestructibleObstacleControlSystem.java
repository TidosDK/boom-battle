package dk.sdu.mmmi.destructibleObstacle;

import dk.sdu.mmmi.common.data.Properties.GameData;
import dk.sdu.mmmi.common.data.World.World;
import dk.sdu.mmmi.common.services.Obstacle.Destructible.IDestructibleObstacle;
import dk.sdu.mmmi.common.services.Obstacle.Destructible.IDestructibleObstacleControlSystem;
import dk.sdu.mmmi.common.services.TextureAnimator.ITextureAnimatorController;

import java.util.Collection;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class DestructibleObstacleControlSystem implements IDestructibleObstacleControlSystem {
    @Override
    public IDestructibleObstacle createDestructibleObstacle(GameData gamedata, World world) {
        IDestructibleObstacle destructibleObstacle = new DestructibleObstacle(world, gamedata.getScaler(), gamedata.getScaler(), "DestructibleObstacle/src/main/resources/block.png");

        return destructibleObstacle;
    }

    private Collection<? extends ITextureAnimatorController> getITextureAnimatorController() {
        return ServiceLoader.load(ITextureAnimatorController.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
