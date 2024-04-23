package dk.sdu.mmmi.WeaponSystem;

import dk.sdu.mmmi.common.data.entity.Coordinates;
import dk.sdu.mmmi.common.data.entity.Entity;
import dk.sdu.mmmi.common.data.entity.TextureLayer;
import dk.sdu.mmmi.common.data.gameproperties.GameData;
import dk.sdu.mmmi.common.data.world.GridPosition;
import dk.sdu.mmmi.common.data.world.World;
import dk.sdu.mmmi.common.services.entityproperties.IDamageable;
import dk.sdu.mmmi.common.services.IEntityProcessingService;
import dk.sdu.mmmi.common.services.weapon.IWeaponController;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;

public class BombControlSystem implements IEntityProcessingService, IWeaponController {

    private HashMap<Entity, Float> explosionCreationTimes = new HashMap<>();

    @Override
    public synchronized void process(World world, GameData gameData) {
        for (Entity entity : world.getEntities(Bomb.class)) {
            Bomb weapon = (Bomb) entity;
            if (weapon.calculateTimeTillExplosion(gameData) <= 0) {
                // Explosion time reached; trigger the explosion visuals and effects.
                Collection<Coordinates> blastArea = weapon.calculateBlastArea(world);

                for (Coordinates coord : blastArea) {
                    // For each coordinate in the blast area, create an explosion entity with the appropriate texture.
                    Path texturePath = weapon.getFireExplosionTexturePath(coord, world);

                    Explosion explosion = new Explosion(texturePath, coord.getX(), coord.getY(), gameData.getScaler(), gameData.getScaler(), 1f);
                    // Add creation time to the HashMap
                    explosionCreationTimes.put(explosion, gameData.getDeltaTime());
                    world.addEntity(explosion);
                }

                // Damage calculation and handling.
                this.dealDamage(blastArea, world, weapon);

                // Remove the weapon entity after all explosion entities are placed.
                world.removeEntity(weapon);

            } else {
                // Set the texture path to the bomb as it counts down to explosion.
                weapon.setTexturePath(weapon.getCurrentExplosionAnimatorPath());
            }
        }
        for (Entity e : world.getEntities(Explosion.class)) {
            Explosion expl = (Explosion) e;
            float creationTime = explosionCreationTimes.getOrDefault(expl, 0f);

            if (expl.getElapsedTime() + gameData.getDeltaTime() >= expl.getAnimTime() + creationTime) {
                world.removeEntity(expl);
                explosionCreationTimes.remove(expl); // Remove from HashMap after removal
            } else {
                expl.setElapsedTime(expl.getElapsedTime() + gameData.getDeltaTime()); // Initial elapsed time (accumulate delta time)
            }
        }
    }


    private static void dealDamage(Collection<Coordinates> blastArea, World world, Bomb weapon) {
        for (Coordinates coordinates : blastArea) {
            for (Entity entity : world.getEntities()) {
                if (entity.getGridPosition().equals(coordinates.getGridPosition())) {
                    if (entity instanceof IDamageable) {
                        IDamageable damageable = (IDamageable) entity;
                        damageable.removeLifepoints(weapon.getDamagePoints());
                        System.out.println("Lifepoints for player: " + damageable.getLifepoints());
                    }
                }
            }
        }
    }

    @Override
    public Entity createWeapon(Entity weaponPlacer, GameData gameData) {
        Bomb weapon = new Bomb(gameData, Paths.get("Bomb/src/main/resources/bomb_textures/planted/bomb-planted-2.png"), gameData.getScaler(), gameData.getScaler());
        weapon.setAnimTime(20f);
        weapon.createFireExplosionAnimators(gameData);
        weapon.setCoordinates(new Coordinates(new GridPosition(weaponPlacer.getGridX(), weaponPlacer.getGridY())));
        weapon.setDamagePoints(2);
        weapon.setBlastLength(3);
        weapon.setTimeSincePlacement(gameData.getDeltaTime());
        weapon.setTimeTillExplosionInSeconds(2f);
        weapon.setTextureLayer(TextureLayer.POWER_UP.getValue());
        return weapon;
    }
}
