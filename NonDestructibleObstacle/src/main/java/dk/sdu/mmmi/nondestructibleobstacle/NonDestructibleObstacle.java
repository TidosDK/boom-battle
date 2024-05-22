package dk.sdu.mmmi.nondestructibleobstacle;

import dk.sdu.mmmi.common.data.entity.Entity;
import dk.sdu.mmmi.common.data.world.World;
import dk.sdu.mmmi.common.services.entityproperties.ICollidable;
import dk.sdu.mmmi.common.obstacle.nondestructible.INonDestructibleObstacle;

import java.nio.file.Path;

public class NonDestructibleObstacle extends Entity implements INonDestructibleObstacle, ICollidable {
    private World world;

    public NonDestructibleObstacle(World world, float width, float height, Path texturePath) {
        super(texturePath, width, height);
        this.world = world;
    }
}
