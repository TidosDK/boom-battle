package dk.sdu.mmmi.WeaponSystem;

import dk.sdu.mmmi.common.data.gameproperties.GameData;
import dk.sdu.mmmi.common.data.world.World;
import dk.sdu.mmmi.common.services.IGamePluginService;
import dk.sdu.mmmi.common.data.entity.Entity;

import java.nio.file.Paths;

public class BombPlugin implements IGamePluginService {
    @Override
    public void start(World world, GameData gameData) {
        // Set path for explosion textures
        Bomb.explosionRightPath = Paths.get("Bomb/src/main/resources/bomb_textures/explosion/right/right-explosion-2.png");
        Bomb.explosionLeftPath = Paths.get("Bomb/src/main/resources/bomb_textures/explosion/left/left-explosion-2.png");
        Bomb.explosionUpPath = Paths.get("Bomb/src/main/resources/bomb_textures/explosion/up/up-explosion-2.png");
        Bomb.explosionDownPath = Paths.get("Bomb/src/main/resources/bomb_textures/explosion/down/down-explosion-2.png");
        Bomb.explosionMidHorizontalPath = Paths.get("Bomb/src/main/resources/bomb_textures/explosion/mid-horizontal/mid-hori-explosion-2.png");
        Bomb.explosionMidVerticalPath = Paths.get("Bomb/src/main/resources/bomb_textures/explosion/mid-vertical/mid-vert-explosion-2.png");
        Bomb.explosionCenterPath = Paths.get("Bomb/src/main/resources/bomb_textures/explosion/center/center-explosion-2.png");
        System.out.println("Module started: Bomb");
    }

    @Override
    public void stop(World world, GameData gameData) {
        for (Entity entity : world.getEntities(Bomb.class, Explosion.class)) {
            world.removeEntity(entity);
        }

        for (Entity explosion : world.getEntities(Explosion.class)) {
            world.removeEntity(explosion);
        }
    }
}
