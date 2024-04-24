package dk.sdu.mmmi.nondestructibleobstacle;

import dk.sdu.mmmi.common.data.entity.Entity;
import dk.sdu.mmmi.common.data.gameproperties.GameData;
import dk.sdu.mmmi.common.data.world.World;
import dk.sdu.mmmi.common.services.IGamePluginService;

public class NonDestructibleObstaclePlugin implements IGamePluginService {
    @Override
    public void start(World world, GameData gameData) {
        System.out.println("Module started: NonDestructibleObstacle");
    }

    @Override
    public void stop(World world, GameData gameData) {
        for (Entity nonDestructibleObstacle : world.getEntities(NonDestructibleObstacle.class)) {
            world.removeEntity(nonDestructibleObstacle);
        }
    }
}
