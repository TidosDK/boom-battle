package dk.sdu.mmmi.destructibleObstacle;

import dk.sdu.mmmi.common.data.Entity.Entity;
import dk.sdu.mmmi.common.data.World.World;
import dk.sdu.mmmi.common.services.Obstacle.Destructible.IDestructibleObstacle;
import dk.sdu.mmmi.common.services.Entity.ICollidable;
import dk.sdu.mmmi.common.services.TextureAnimator.IAnimatable;
import dk.sdu.mmmi.common.services.TextureAnimator.ITextureAnimator;

import java.nio.file.Path;
import java.util.HashMap;

public class DestructibleObstacle extends Entity implements IDestructibleObstacle, ICollidable, IAnimatable {
    private World world;
    private HashMap<Integer, ITextureAnimator> animators;

    public DestructibleObstacle(World world, float width, float height, Path texturePath) {
        super(texturePath, width, height);
        this.world = world;
        this.animators = new HashMap<>();
    }

    @Override
    public void destroyObstacle() {
        // TODO: Implement destruction of obstacle here (e.g. the animation of the obstacle being destroyed)

        world.removeEntity(this);
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
}