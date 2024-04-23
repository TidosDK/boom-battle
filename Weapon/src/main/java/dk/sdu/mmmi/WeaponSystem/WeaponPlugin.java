package dk.sdu.mmmi.WeaponSystem;

import dk.sdu.mmmi.common.data.Properties.GameData;
import dk.sdu.mmmi.common.data.World.World;
import dk.sdu.mmmi.common.services.IGamePluginService;
import dk.sdu.mmmi.common.data.Entity.Entity;

public class WeaponPlugin implements IGamePluginService {
    @Override
    public void start(World world, GameData gameData) {
    }

    @Override
    public void stop(World world, GameData gameData) {
        for (Entity weapon : world.getEntities(Weapon.class)) {
            world.removeEntity(weapon);
        }
    }
}
