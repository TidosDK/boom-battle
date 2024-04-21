package dk.sdu.mmmi.nondestructibleobstacle;

import dk.sdu.mmmi.common.data.Entity.TextureLayer;
import dk.sdu.mmmi.common.data.Properties.GameData;
import dk.sdu.mmmi.common.data.World.World;
import dk.sdu.mmmi.common.services.Obstacle.nondestructible.INonDestructibleObstacle;
import dk.sdu.mmmi.common.services.Obstacle.nondestructible.INonDestructibleObstacleController;

import java.nio.file.Path;

public class NonDestructibleObstacleControlSystem implements INonDestructibleObstacleController {

    @Override
    public INonDestructibleObstacle createNonDestructibleObstacle(GameData gamedata, World world) {
        Path defaultTexture = Path.of("DestructibleObstacle/src/main/resources/destructible_obstacle_textures/block.png");
        INonDestructibleObstacle nonDestructibleObstacle = new NonDestructibleObstacle(world, gamedata.getScaler(), gamedata.getScaler(), defaultTexture);

        // Set texture layer of non destructible obstacle instance
        if (nonDestructibleObstacle instanceof NonDestructibleObstacle) {
            ((NonDestructibleObstacle) nonDestructibleObstacle).setTextureLayer(TextureLayer.CONSTRUCTIONS.getValue());
        }

        return nonDestructibleObstacle;
    }
}
