package dk.sdu.mmmi.nondestructibleobstacle;

import dk.sdu.mmmi.common.data.Entity.Entity;
import dk.sdu.mmmi.common.data.World.World;
import dk.sdu.mmmi.common.services.Entity.ICollidable;
import dk.sdu.mmmi.common.services.Obstacle.nondestructible.INonDestructibleObstacle;

import java.nio.file.Path;

public class NonDestructibleObstacle extends Entity implements INonDestructibleObstacle, ICollidable {
    private World world;

    public NonDestructibleObstacle(World world, float width, float height, Path texturePath) {
        super(texturePath, width, height);
        this.world = world;
    }
}
