package dk.sdu.mmmi.destructibleobstacle;

import dk.sdu.mmmi.common.data.entity.Entity;
import dk.sdu.mmmi.common.data.world.World;
import dk.sdu.mmmi.common.services.entityproperties.IDamageable;
import dk.sdu.mmmi.common.services.map.IMap;
import dk.sdu.mmmi.common.obstacle.destructible.IDestructibleObstacle;
import dk.sdu.mmmi.common.services.entityproperties.ICollidable;
import dk.sdu.mmmi.common.services.textureanimator.IAnimatable;
import dk.sdu.mmmi.common.services.textureanimator.ITextureAnimator;

import java.nio.file.Path;
import java.util.HashMap;

public class DestructibleObstacle extends Entity implements IDestructibleObstacle, ICollidable, IAnimatable, IDamageable {
    private World world;
    private HashMap<Integer, ITextureAnimator> animators;
    private int lifePoints;

    public DestructibleObstacle(World world, float width, float height, Path texturePath) {
        super(texturePath, width, height);
        this.world = world;
        this.animators = new HashMap<>();
        this.lifePoints = 1;
    }

    @Override
    public void destroyObstacle() {
        ITextureAnimator destroyAnimator = animators.get(DestructibleObstacleAnimations.DESTROY.getValue());
        if (destroyAnimator != null) { // Guard for ITextureAnimator being module
            this.setTexturePath(destroyAnimator.getCurrentTexturePath());
            if (destroyAnimator.getCurrentTextureIndex() == destroyAnimator.getNumberOfTextures() - 1) {
                world.removeEntity(this);
            }
            // TODO: You have to fix animation for destructible obstacle
            world.removeEntity(this);
            if (world.getMap() instanceof IMap map) {
                map.setMapTile(this.getGridX(), this.getGridY(), false);
            }
        } else {
            world.removeEntity(this);
        }
    }

    @Override
    public Path getActiveTexturePath(Integer key) {
        if (animators.get(key) == null) {
            return getTexturePath();
        }
        return animators.get(key).getCurrentTexturePath();
    }

    @Override
    public void addAnimator(Integer key, ITextureAnimator animator) {
        animators.put(key, animator);
    }

    @Override
    public HashMap<Integer, ITextureAnimator> getAnimators() {
        return animators;
    }

    @Override
    public void setAnimators(HashMap<Integer, ITextureAnimator> animators) {
        this.animators = animators;
    }

    @Override
    public int getLifepoints() {
        return lifePoints;
    }

    @Override
    public void setLifepoints(int lifepoints) {
        this.lifePoints = lifepoints;
    }

    @Override
    public void removeLifepoints(int amount) {
        lifePoints -= amount;
        if (lifePoints <= 0) {
            destroyObstacle();
        }
    }
}
