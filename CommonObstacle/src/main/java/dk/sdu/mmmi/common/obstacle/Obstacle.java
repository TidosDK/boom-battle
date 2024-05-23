package dk.sdu.mmmi.common.obstacle;

import dk.sdu.mmmi.common.data.entity.Entity;
import dk.sdu.mmmi.common.data.world.World;

import java.nio.file.Path;

public abstract class Obstacle extends Entity {
    private final World world;

    public Obstacle(Path texturePath, float width, float height, World world) {
        super(texturePath, width, height);
        this.world = world;
    }

    public World getWorld() {
        return world;
    }
}
