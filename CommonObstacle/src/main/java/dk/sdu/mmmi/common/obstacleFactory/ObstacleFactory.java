package dk.sdu.mmmi.common.obstacleFactory;

import dk.sdu.mmmi.common.data.gameproperties.GameData;
import dk.sdu.mmmi.common.data.world.World;
import dk.sdu.mmmi.common.obstacle.Obstacle;
import dk.sdu.mmmi.common.obstacle.destructible.IDestructibleObstacleController;
import dk.sdu.mmmi.common.obstacle.nondestructible.INonDestructibleObstacleController;

import java.util.Collection;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class ObstacleFactory implements IObstacleFactory {
    INonDestructibleObstacleController nonDestructibleObstacleController;
    IDestructibleObstacleController destructibleObstacleController;

    public ObstacleFactory() {
        this.nonDestructibleObstacleController = getNonDestructibleObstacleControllers().stream().findFirst().orElse(null);
        this.destructibleObstacleController = getDestructibleObstacleControllers().stream().findFirst().orElse(null);
    }

    @Override
    public Obstacle createNonDestructibleObstacle(GameData gameData, World world) throws IllegalStateException {
        if (nonDestructibleObstacleController == null) {
            return null;
        }
        if (nonDestructibleObstacleController.createNonDestructibleObstacle(gameData, world) instanceof Obstacle obstacle) {
            return obstacle;
        } else throw new IllegalStateException("NonDestructibleObstacleController did not return an Obstacle instance");
    }

    @Override
    public Obstacle createDestructibleObstacle(GameData gameData, World world) throws IllegalStateException {
        if (destructibleObstacleController == null) {
            return null;
        }
        if (destructibleObstacleController.createDestructibleObstacle(gameData, world) instanceof Obstacle obstacle) {
            return obstacle;
        } else throw new IllegalStateException("DestructibleObstacleController did not return an Obstacle instance");
    }

    private Collection<? extends INonDestructibleObstacleController> getNonDestructibleObstacleControllers() {
        return ServiceLoader.load(INonDestructibleObstacleController.class)
                .stream().map(ServiceLoader.Provider::get).collect(toList());
    }

    private Collection<? extends IDestructibleObstacleController> getDestructibleObstacleControllers() {
        return ServiceLoader.load(IDestructibleObstacleController.class)
                .stream().map(ServiceLoader.Provider::get).collect(toList());
    }

    public boolean hasNonDestructibleObstacleController() {
        return nonDestructibleObstacleController != null;
    }

    public boolean hasDestructibleObstacleController() {
        return destructibleObstacleController != null;
    }


}
