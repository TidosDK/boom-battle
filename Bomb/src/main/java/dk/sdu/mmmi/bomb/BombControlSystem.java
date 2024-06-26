package dk.sdu.mmmi.bomb;

import dk.sdu.mmmi.common.data.entity.Coordinates;
import dk.sdu.mmmi.common.data.entity.Entity;
import dk.sdu.mmmi.common.data.entity.TextureLayer;
import dk.sdu.mmmi.common.data.gameproperties.GameData;
import dk.sdu.mmmi.common.data.world.GridPosition;
import dk.sdu.mmmi.common.data.world.World;
import dk.sdu.mmmi.common.services.entityproperties.IDamageable;
import dk.sdu.mmmi.common.services.IEntityProcessingService;
import dk.sdu.mmmi.common.textureanimator.ITextureAnimatorController;
import dk.sdu.mmmi.common.weapon.IWeaponController;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static java.util.stream.Collectors.toList;

/**
 * BombControlSystem: Controls the bomb entities and their explosions.
 */
public class BombControlSystem implements IEntityProcessingService, IWeaponController {
    private HashMap<Entity, Float> explosionCreationTimes = new HashMap<>();
    private World world;
    private GameData gameData;
    private final Path defaultTexturePath = Paths.get("Bomb/src/main/resources/bomb_textures/planted/bomb-planted-2.png");
    private final Path plantedTexturesPath = Paths.get("Bomb/src/main/resources/bomb_textures/planted/");

    @Override
    public synchronized void process(World worldParam, GameData gameDataParam) {
        this.world = worldParam;
        this.gameData = gameDataParam;

        // For each bomb entity in the world, check if the bomb has reached its explosion time.
        for (Entity entity : this.world.getEntities(Bomb.class)) {
            if (entity instanceof Bomb bomb) {
                checkBombExplosionTime(bomb);
            }
        }
        for (Entity entity : this.world.getEntities(Explosion.class)) {
            if (entity instanceof Explosion explosion) {
                removeExplosionWhenFinished(explosion);
            }
        }
    }

    /**
     * Checks if the bomb entity has reached its explosion time and explodes it if so.
     *
     * @param bomb The bomb entity to check.
     */
    public void checkBombExplosionTime(Bomb bomb) {
        if (bomb.calculateTimeTillExplosion(this.gameData) <= 0) {
            bomb.setState(Bomb.State.EXPLODING);
            explodeBomb(bomb);
        } else {
            updateBombCountdownState(bomb);
        }
    }

    /**
     * Lets the bomb entity explode.
     *
     * @param bomb The bomb entity to explode.
     */
    private void explodeBomb(Bomb bomb) {
        Collection<Coordinates> blastArea = bomb.calculateBlastArea(world);
        createExplosions(bomb, blastArea);
        dealDamage(world, blastArea, bomb);
        world.removeEntity(bomb);
    }

    /**
     * Creates explosion entities in the blast area of the bomb.
     *
     * @param bomb      The bomb entity that caused the explosion.
     * @param blastArea The blast area to create explosions in.
     */
    private void createExplosions(Bomb bomb, Collection<Coordinates> blastArea) {
        for (Coordinates coordinate : blastArea) {
            // For each coordinate in the blast area, create an explosion entity with the appropriate texture.
            Path texturePath = bomb.getFireExplosionTexturePath(coordinate);

            Explosion explosion = new Explosion(texturePath, coordinate.getX(), coordinate.getY(), gameData.getScaler(), gameData.getScaler(), 1f);
            explosion.setTextureLayer(TextureLayer.EFFECT.getValue());
            explosion.setBomb(bomb);

            // Add creation time to the HashMap
            this.explosionCreationTimes.put(explosion, gameData.getDeltaTime());
            world.addEntity(explosion);
        }
    }

    /**
     * This method is responsible for updating the bomb's state as it counts down to explosion.
     *
     * @param bomb The bomb entity to set the texture for.
     */
    private void updateBombCountdownState(Bomb bomb) {
        bomb.setTexturePath(bomb.getActiveTexturePath(BombAnimations.PLACEMENT.getValue()));
    }


    /**
     * Checks if explosion time is reached and removes the explosion entity when so.
     *
     * @param explosion The explosion entity to remove.
     */

    public void removeExplosionWhenFinished(Explosion explosion) {
        float creationTime = this.explosionCreationTimes.getOrDefault(explosion, 0f);
        Bomb sourceBomb = explosion.getBomb();

        // Use cached blast area
        Collection<Coordinates> blastArea = sourceBomb.calculateBlastArea(world);

        if (explosion.getElapsedTime() + gameData.getDeltaTime() >= explosion.getExplosionTime() + creationTime) {
            this.explosionCreationTimes.remove(explosion);
            world.removeEntity(explosion);
        } else {
            explosion.setElapsedTime(explosion.getElapsedTime() + gameData.getDeltaTime());
            dealDamage(world, blastArea, sourceBomb);
        }
    }


    /**
     * Deals damage to entities in the blast area.
     *
     * @param world     The world to deal damage in.
     * @param blastArea The blast area to deal damage to.
     * @param weapon    The bomb entity that caused the explosion.
     */
    private static void dealDamage(World world, Collection<Coordinates> blastArea, Bomb weapon) {
        List<IDamageable> damageableList = new ArrayList<>();
        HashMap<GridPosition, List<Entity>> entityMap = new HashMap<>();

        // Create a map of entities in the world
        for (Entity entity : world.getEntities()) {
            GridPosition gridPosition = entity.getGridPosition();
            entityMap.putIfAbsent(gridPosition, new ArrayList<>());
            entityMap.get(gridPosition).add(entity);
        }

        // For each coordinate in the blast area, check if there is an entity at that position
        for (Coordinates coordinates : blastArea) {
            List<Entity> entities = entityMap.get(coordinates.getGridPosition());
            if (entities == null) {
                continue;
            }
            for (Entity entity : entities) {
                if (entity instanceof IDamageable damageable) {
                    damageableList.add(damageable);
                }
            }
        }

        // Deal damage to all damageable entities in the blast area
        damageableList.forEach(damageable -> damageable.removeLifepoints(weapon.getDamagePoints()));
    }


    @Override
    public Entity createWeapon(Entity weaponPlacer, GameData gameDataParam) {
        Bomb bomb = new Bomb(this.defaultTexturePath, gameDataParam.getScaler(), gameDataParam.getScaler());
        for (ITextureAnimatorController animatorController : getITextureAnimatorController()) {
            bomb.addAnimator(BombAnimations.PLACEMENT.getValue(), animatorController.createTextureAnimator(gameDataParam, plantedTexturesPath, 0, 5, 16f));
        }
        bomb.setCoordinates(new Coordinates(new GridPosition(weaponPlacer.getGridX(), weaponPlacer.getGridY())));
        bomb.setDamagePoints(2);
        bomb.setBlastLength(3);
        bomb.setTimeSincePlacement(gameDataParam.getDeltaTime());
        bomb.setTimeTillExplosionInSeconds(2f);
        bomb.setTextureLayer(TextureLayer.POWER_UP.getValue());
        return bomb;
    }

    /**
     * Get all ITextureAnimatorController implementations.
     *
     * @return Collection of ITextureAnimatorController
     */
    private Collection<? extends ITextureAnimatorController> getITextureAnimatorController() {
        return ServiceLoader.load(ITextureAnimatorController.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
