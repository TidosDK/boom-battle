package dk.sdu.mmmi.WeaponSystem;

import dk.sdu.mmmi.common.data.GameData;
import dk.sdu.mmmi.common.data.World;
import dk.sdu.mmmi.common.services.IGamePluginService;
import dk.sdu.mmmi.common.data.Entity;
import dk.sdu.mmmi.common.services.IWeapon;

public class WeaponPlugin implements IGamePluginService {
    private Entity weapon;

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
