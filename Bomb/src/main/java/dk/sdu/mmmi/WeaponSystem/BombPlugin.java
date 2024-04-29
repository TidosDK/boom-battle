package dk.sdu.mmmi.WeaponSystem;

import dk.sdu.mmmi.common.data.gameproperties.GameData;
import dk.sdu.mmmi.common.data.world.World;
import dk.sdu.mmmi.common.services.IGamePluginService;
import dk.sdu.mmmi.common.data.entity.Entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BombPlugin implements IGamePluginService {
    @Override
    public void start(World world, GameData gameData) {
        System.out.println("Module started: Bomb");
    }

    @Override
    public void stop(World world, GameData gameData) {
        for (Entity entity : world.getEntities(Bomb.class, Explosion.class)) {
            world.removeEntity(entity);
        }
    }
}
