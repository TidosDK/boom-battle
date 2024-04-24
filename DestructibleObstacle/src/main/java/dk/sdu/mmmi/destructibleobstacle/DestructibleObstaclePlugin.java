package dk.sdu.mmmi.destructibleobstacle;

import dk.sdu.mmmi.common.data.entity.Entity;
import dk.sdu.mmmi.common.data.gameproperties.GameData;
import dk.sdu.mmmi.common.data.world.World;
import dk.sdu.mmmi.common.services.IGamePluginService;

public class DestructibleObstaclePlugin implements IGamePluginService {
    @Override
    public void start(World world, GameData gameData) {
        System.out.println("Module started: DestructibleObstacle");
    }

    @Override
    public void stop(World world, GameData gameData) {
        for (Entity destructibleObstacle : world.getEntities(DestructibleObstacle.class)) {
            world.removeEntity(destructibleObstacle);
        }
    }
}
