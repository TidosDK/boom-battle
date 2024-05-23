package dk.sdu.mmmi.nondestructibleobstacle;

import dk.sdu.mmmi.common.data.entity.TextureLayer;
import dk.sdu.mmmi.common.data.gameproperties.GameData;
import dk.sdu.mmmi.common.data.world.World;
import dk.sdu.mmmi.common.obstacle.nondestructible.INonDestructibleObstacle;
import dk.sdu.mmmi.common.obstacle.nondestructible.INonDestructibleObstacleController;

import java.nio.file.Path;

public class NonDestructibleObstacleControlSystem implements INonDestructibleObstacleController {

    @Override
    public INonDestructibleObstacle createNonDestructibleObstacle(GameData gamedata, World world) {
        Path defaultTexture = Path.of("NonDestructibleObstacle/src/main/resources/non_destructible_obstacle_textures/non-destructible-wall.png");
        NonDestructibleObstacle nonDestructibleObstacle = new NonDestructibleObstacle(world, gamedata.getScaler(), gamedata.getScaler(), defaultTexture);

        // Set texture layer of non destructible obstacle instance
        nonDestructibleObstacle.setTextureLayer(TextureLayer.CONSTRUCTIONS.getValue());

        return nonDestructibleObstacle;
    }
}
