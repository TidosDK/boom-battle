package dk.sdu.mmmi.destructibleObstacle;

import dk.sdu.mmmi.common.data.Entity.Entity;
import dk.sdu.mmmi.common.data.World.Map;
import dk.sdu.mmmi.common.data.World.World;
import dk.sdu.mmmi.common.services.Obstacle.Destructible.IDestructibleObstacle;
import dk.sdu.mmmi.common.services.Entity.ICollidable;
import dk.sdu.mmmi.common.services.TextureAnimator.IAnimatable;
import dk.sdu.mmmi.common.services.TextureAnimator.ITextureAnimator;

import java.util.HashMap;
import java.util.UUID;

public class DestructibleObstacle extends Entity implements IDestructibleObstacle, ICollidable, IAnimatable {
    private World world;
    private HashMap<UUID, ITextureAnimator> animators;

    public DestructibleObstacle(World world, float width, float height, String texturePath) {
        super(texturePath, width, height);
        this.world = world;
        this.animators = new HashMap<>();
    }

    @Override
    public void startDestruction() {
        world.removeEntity(this);
    }

    @Override
    public String getActiveTexturePath(UUID key) {
        if (animators.get(key) == null) {
            return getTexturePath();
        }
        return animators.get(key).getCurrentImagePath();
    }

    @Override
    public void addAnimator(UUID key, ITextureAnimator animator) {
        animators.put(key, animator);
    }

    @Override
    public HashMap<UUID, ITextureAnimator> getAnimators() {
        return animators;
    }

    @Override
    public void setAnimators(HashMap<UUID, ITextureAnimator> animators) {
        this.animators = animators;
    }
}
