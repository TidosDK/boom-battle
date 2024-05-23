package dk.sdu.mmmi.basicmapsystem;

import dk.sdu.mmmi.common.data.entity.Coordinates;
import dk.sdu.mmmi.common.data.gameproperties.GameData;
import dk.sdu.mmmi.common.data.world.GridPosition;
import dk.sdu.mmmi.common.data.world.World;
import dk.sdu.mmmi.common.obstacle.Obstacle;
import dk.sdu.mmmi.common.obstacleFactory.ObstacleFactory;


public class MapRenderer {

    private final GameData gameData;
    private final World world;
    private final ObstacleFactory obstacleFactory;

    public MapRenderer(GameData gameData, World world) {
        this.gameData = gameData;
        this.world = world;
        this.obstacleFactory = new ObstacleFactory();
    }

    public void createNonDestructibleObstacle(int gridX, int gridY) {
        if (!obstacleFactory.hasNonDestructibleObstacleController()) {
            return;
        }

        Obstacle obst = obstacleFactory.createNonDestructibleObstacle(gameData, world);

        obst.setCoordinates(new Coordinates(new GridPosition(gridX, gridY)));
        world.addEntity(obst);
    }

    public void createPathTile(int gridX, int gridY) {
        PathTile pathTile = new PathTile(gameData.getScaler(), gameData.getScaler());
        pathTile.setCoordinates(new Coordinates(new GridPosition(gridX, gridY)));
        world.addEntity(pathTile);
    }

    public void createDestructibleObstacle(int gridX, int gridY) {
        if (!obstacleFactory.hasDestructibleObstacleController()) {
            return;
        }
        try {
            Obstacle obst = obstacleFactory.createDestructibleObstacle(gameData, world);
            obst.setCoordinates(new Coordinates(new GridPosition(gridX, gridY)));
            world.addEntity(obst);
        } catch (IllegalStateException e) {
            System.out.println("Could not create destructible obstacle.");
        }
    }


}
