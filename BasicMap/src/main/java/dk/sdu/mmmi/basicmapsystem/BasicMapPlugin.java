package dk.sdu.mmmi.basicmapsystem;

import dk.sdu.mmmi.common.data.entity.Entity;
import dk.sdu.mmmi.common.data.gameproperties.GameData;
import dk.sdu.mmmi.common.data.world.World;
import dk.sdu.mmmi.common.services.IGamePluginService;

public class BasicMapPlugin implements IGamePluginService {
    @Override
    public void start(World world, GameData gameData) {
        System.out.println("Module started: BasicMap");
    }

    @Override
    public void stop(World world, GameData gameData) {
        for (Entity pathTile : world.getEntities(PathTile.class)) {
            world.removeEntity(pathTile);
        }

        world.setMap(null);
    }
}
