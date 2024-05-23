package dk.sdu.mmmi.basicmapsystem;

import dk.sdu.mmmi.common.data.entity.Coordinates;
import dk.sdu.mmmi.common.data.entity.Entity;
import dk.sdu.mmmi.common.data.gameproperties.GameData;
import dk.sdu.mmmi.common.data.world.GridPosition;
import dk.sdu.mmmi.common.data.world.Map;
import dk.sdu.mmmi.common.data.world.World;
import dk.sdu.mmmi.common.obstacle.destructible.IDestructibleObstacle;
import dk.sdu.mmmi.common.obstacle.destructible.IDestructibleObstacleController;
import dk.sdu.mmmi.common.obstacle.nondestructible.INonDestructibleObstacle;
import dk.sdu.mmmi.common.obstacle.nondestructible.INonDestructibleObstacleController;

import java.util.Collection;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class MapRenderer {

    private final GameData gameData;
    private final World world;
    private final INonDestructibleObstacleController nonDestructObstCon;
    private final IDestructibleObstacleController destructObstCon;

    public MapRenderer(GameData gameData, World world) {
        this.gameData = gameData;
        this.world = world;
        this.nonDestructObstCon = getNonDestructibleObstacleControllers().stream().findFirst().orElse(null);
        this.destructObstCon = getDestructibleObstacleControllers().stream().findFirst().orElse(null);
    }

    public void createNonDestructibleObstacle(int gridX, int gridY) {
        if (nonDestructObstCon == null) {
            return;
        }

        INonDestructibleObstacle obst = nonDestructObstCon.createNonDestructibleObstacle(gameData, world);
        if (obst instanceof Entity) {
            ((Entity) obst).setCoordinates(new Coordinates(new GridPosition(gridX, gridY)));
            world.addEntity((Entity) obst);
        }
    }

    public void createPathTile(int gridX, int gridY) {
        PathTile pathTile = new PathTile(gameData.getScaler(), gameData.getScaler());
        pathTile.setCoordinates(new Coordinates(new GridPosition(gridX, gridY)));
        world.addEntity(pathTile);
    }

    public void createDestructibleObstacle(int gridX, int gridY) {
        if (destructObstCon == null) {
            return;
        }
        IDestructibleObstacle obst = destructObstCon.createDestructibleObstacle(gameData, world);
        if (obst instanceof Entity entity) {
            entity.setCoordinates(new Coordinates(new GridPosition(gridX, gridY)));
            world.addEntity(entity);
        }
    }


    public void renderMap(Map map) {

        if (nonDestructObstCon == null) {
            return;
        }


        INonDestructibleObstacle obst = nonDestructObstCon.createNonDestructibleObstacle(gameData, world);
        if (obst instanceof Entity) {
            ((Entity) obst).setCoordinates(new Coordinates(new GridPosition(5, 5)));
            world.addEntity((Entity) obst);
        }

    }

    private Collection<? extends INonDestructibleObstacleController> getNonDestructibleObstacleControllers() {
        return ServiceLoader.load(INonDestructibleObstacleController.class)
                .stream().map(ServiceLoader.Provider::get).collect(toList());
    }

    private Collection<? extends IDestructibleObstacleController> getDestructibleObstacleControllers() {
        return ServiceLoader.load(IDestructibleObstacleController.class)
                .stream().map(ServiceLoader.Provider::get).collect(toList());
    }


}
