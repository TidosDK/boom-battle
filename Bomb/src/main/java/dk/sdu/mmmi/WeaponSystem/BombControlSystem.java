package dk.sdu.mmmi.WeaponSystem;

import dk.sdu.mmmi.common.data.entity.Coordinates;
import dk.sdu.mmmi.common.data.entity.Entity;
import dk.sdu.mmmi.common.data.entity.TextureLayer;
import dk.sdu.mmmi.common.data.gameproperties.GameData;
import dk.sdu.mmmi.common.data.world.GridPosition;
import dk.sdu.mmmi.common.data.world.World;
import dk.sdu.mmmi.common.services.entityproperties.IDamageable;
import dk.sdu.mmmi.common.services.IEntityProcessingService;
import dk.sdu.mmmi.common.services.textureanimator.ITextureAnimatorController;
import dk.sdu.mmmi.common.services.weapon.IWeaponController;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class BombControlSystem implements IEntityProcessingService, IWeaponController {

    private HashMap<Entity, Float> explosionCreationTimes = new HashMap<>();

    @Override
    public synchronized void process(World world, GameData gameData) {
        for (Entity entity : world.getEntities(Bomb.class)) {
            Bomb bomb = (Bomb) entity;
            if (bomb.calculateTimeTillExplosion(gameData) <= 0) {
                // Explosion time reached; trigger the explosion visuals and effects.
                Collection<Coordinates> blastArea = bomb.calculateBlastArea(world);

                for (Coordinates coord : blastArea) {
                    // For each coordinate in the blast area, create an explosion entity with the appropriate texture.
                    Path texturePath = bomb.getFireExplosionTexturePath(coord);

                    Explosion explosion = new Explosion(texturePath, coord.getX(), coord.getY(), gameData.getScaler(), gameData.getScaler(), 1f);
                    explosion.setBomb(bomb);
                    // Add creation time to the HashMap
                    explosionCreationTimes.put(explosion, gameData.getDeltaTime());
                    world.addEntity(explosion);
                }

                // Damage calculation and handling.
                this.dealDamage(blastArea, world, bomb);

                // Remove the bomb entity after all explosion entities are placed.
                world.removeEntity(bomb);
            } else {
                // Set the texture path to the bomb as it counts down to explosion.
                bomb.setTexturePath(bomb.getActiveTexturePath(BombAnimations.PLACEMENT.getValue()));
            }
        }
        for (Entity e : world.getEntities(Explosion.class)) {
            Explosion expl = (Explosion) e;
            float creationTime = explosionCreationTimes.getOrDefault(expl, 0f);

            if (expl.getElapsedTime() + gameData.getDeltaTime() >= expl.getAnimTime() + creationTime) {
                world.removeEntity(expl);
                explosionCreationTimes.remove(expl);
            } else {
                expl.setElapsedTime(expl.getElapsedTime() + gameData.getDeltaTime());
                // Damage Calculation:
                Bomb sourceBomb = expl.getBomb(); // Assumes you've added a 'getBomb' method to Explosion
                Collection<Coordinates> blastArea = sourceBomb.calculateBlastArea(world);
                dealDamage(blastArea, world, sourceBomb);
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
                        // debugging HP print System.out.println("Lifepoints for player: " + damageable.getLifepoints());
                    }
                }
            }
        }
    }

    @Override
    public Entity createWeapon(Entity weaponPlacer, GameData gameData) {
        Path defaultTexture = Paths.get("Bomb/src/main/resources/bomb_textures/planted/bomb-planted-2.png");
        Bomb bomb = new Bomb(defaultTexture, gameData.getScaler(), gameData.getScaler());
        for (ITextureAnimatorController animatorController : getITextureAnimatorController()) {
            bomb.addAnimator(BombAnimations.PLACEMENT.getValue(), animatorController.createTextureAnimator(gameData, Paths.get("Bomb/src/main/resources/bomb_textures/planted/"), 0, 5, 16f));
        }
        bomb.setCoordinates(new Coordinates(new GridPosition(weaponPlacer.getGridX(), weaponPlacer.getGridY())));
        bomb.setDamagePoints(2);
        bomb.setBlastLength(3);
        bomb.setTimeSincePlacement(gameData.getDeltaTime());
        bomb.setTimeTillExplosionInSeconds(2f);
        bomb.setTextureLayer(TextureLayer.POWER_UP.getValue());
        return bomb;
    }

    private Collection<? extends ITextureAnimatorController> getITextureAnimatorController() {
        return ServiceLoader.load(ITextureAnimatorController.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
