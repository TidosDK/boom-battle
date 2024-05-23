package dk.sdu.mmmi.bomb;

import dk.sdu.mmmi.common.data.gameproperties.GameData;
import dk.sdu.mmmi.common.data.world.World;
import dk.sdu.mmmi.common.services.IGamePluginService;
import dk.sdu.mmmi.common.data.entity.Entity;

import java.nio.file.Paths;

public class BombPlugin implements IGamePluginService {
    @Override
    public void start(World world, GameData gameData) {
        // Set path for explosion textures
        Bomb.setExplosionRightPath(Paths.get("Bomb/src/main/resources/bomb_textures/explosion/right/right-explosion-2.png"));
        Bomb.setExplosionLeftPath(Paths.get("Bomb/src/main/resources/bomb_textures/explosion/left/left-explosion-2.png"));
        Bomb.setExplosionUpPath(Paths.get("Bomb/src/main/resources/bomb_textures/explosion/up/up-explosion-2.png"));
        Bomb.setExplosionDownPath(Paths.get("Bomb/src/main/resources/bomb_textures/explosion/down/down-explosion-2.png"));
        Bomb.setExplosionMidHorizontalPath(Paths.get("Bomb/src/main/resources/bomb_textures/explosion/mid-horizontal/mid-hori-explosion-2.png"));
        Bomb.setExplosionMidVerticalPath(Paths.get("Bomb/src/main/resources/bomb_textures/explosion/mid-vertical/mid-vert-explosion-2.png"));
        Bomb.setExplosionCenterPath(Paths.get("Bomb/src/main/resources/bomb_textures/explosion/center/center-explosion-2.png"));
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
