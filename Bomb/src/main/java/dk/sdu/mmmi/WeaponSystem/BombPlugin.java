package dk.sdu.mmmi.WeaponSystem;

import dk.sdu.mmmi.common.data.gameproperties.GameData;
import dk.sdu.mmmi.common.data.world.World;
import dk.sdu.mmmi.common.services.IGamePluginService;
import dk.sdu.mmmi.common.data.entity.Entity;

public class BombPlugin implements IGamePluginService {
    @Override
    public void start(World world, GameData gameData) {
    }

    @Override
    public void stop(World world, GameData gameData) {
        for (Entity weapon : world.getEntities(Bomb.class)) {
            world.removeEntity(weapon);
        }
    }
}
