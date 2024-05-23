package dk.sdu.mmmi.basicmapsystem;

import dk.sdu.mmmi.common.data.entity.Coordinates;
import dk.sdu.mmmi.common.data.entity.Entity;
import dk.sdu.mmmi.common.data.gameproperties.GameData;
import dk.sdu.mmmi.common.data.world.GridPosition;
import dk.sdu.mmmi.common.data.world.Map;
import dk.sdu.mmmi.common.data.world.World;
import dk.sdu.mmmi.common.obstacle.Obstacle;
import dk.sdu.mmmi.common.obstacle.destructible.IDestructibleObstacle;
import dk.sdu.mmmi.common.obstacle.destructible.IDestructibleObstacleController;
import dk.sdu.mmmi.common.obstacle.nondestructible.INonDestructibleObstacle;
import dk.sdu.mmmi.common.obstacle.nondestructible.INonDestructibleObstacleController;
import dk.sdu.mmmi.common.obstacleFactory.IObstacleFactory;
import dk.sdu.mmmi.common.obstacleFactory.ObstacleFactory;

import java.util.Collection;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

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
