package dk.sdu.mmmi.enemy;

import dk.sdu.mmmi.common.data.entity.Direction;
import dk.sdu.mmmi.common.data.entity.Entity;
import dk.sdu.mmmi.common.data.gameproperties.GameData;
import dk.sdu.mmmi.common.data.world.World;
import dk.sdu.mmmi.common.services.IEntityProcessingService;
import dk.sdu.mmmi.common.services.entityproperties.IActor;
import dk.sdu.mmmi.common.services.map.IMap;
import dk.sdu.mmmi.common.services.weapon.IWeapon;
import dk.sdu.mmmi.common.services.weapon.IWeaponController;
import dk.sdu.mmmi.player.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ServiceLoader;

import static java.lang.Math.abs;
import static java.util.stream.Collectors.toList;

public class EnemyControlSystem implements IActor, IEntityProcessingService { // implements IDamageable
    private World world;
    private GameData gameData;
    private Enemy enemy;
    private int maxWeapons = 3;
    private IMap map = null;

    private final float MOVING_SPEED = 10f;


    @Override
    public void process(World world, GameData gameData) {
        this.world = world;
        this.gameData = gameData;
        Node goalNode = null;
        Node startNode;
        ArrayList<Node> path;

        for (Entity enemy : world.getEntities(Enemy.class)) {
            this.enemy = (Enemy) enemy;
            startNode = new Node((int) this.enemy.getX(), (int) this.enemy.getY());

            List<IWeapon> weaponsToBeRemoved = new ArrayList<>();
            for (IWeapon weapon : this.enemy.getWeapons()) {
                if (!world.getEntities().contains(weapon)) {
                    weaponsToBeRemoved.add(weapon);
                }
            }
            for (IWeapon weapon : weaponsToBeRemoved) {
                this.enemy.removeWeapon(weapon);
            }

            for (Entity player : world.getEntities(Player.class)) {
                goalNode = new Node(player.getGridPosition().getX(), player.getGridPosition().getY());
            }
            for (IPathFinding pathFinding : getIPathFindingProcessing()) {
                path = pathFinding.pathFind(startNode, goalNode, world.getMap());
                moveUsingPath(path);
            }
        }


        if (world.getMap() instanceof IMap) {
            map = (IMap) world.getMap();
        }
    }

    private void drawUsingPath(ArrayList<Node> path) {

    }
    private void moveUsingPath(ArrayList<Node> path) {

        if (path != null && !path.isEmpty()) {

            for (Node node : path) {
                if (enemy.getGridPosition().getX() < node.getX()) {
                    move(Direction.RIGHT);
                } else if (enemy.getGridPosition().getX() > node.getX()) {
                    move(Direction.LEFT);
                } else if (enemy.getGridPosition().getY() < node.getY()) {
                    move(Direction.UP);
                } else if (enemy.getGridPosition().getY() > node.getY()) {
                    move(Direction.DOWN);
                }

            }
        }
    }

    // @Override
    public void placeWeapon() {
        if (enemy.getWeapons().size() < maxWeapons) {
            for (IWeaponController weapon : getIWeaponProcessing()) {
                enemy.getWeapons().add((IWeapon) weapon.createWeapon(this.enemy, this.gameData));
                world.addEntity((Entity) enemy.getWeapons().get(enemy.getWeapons().size() - 1));
            }
        }
    }

    // @Override
    public void takeDamage() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    // @Override
    public void move(Direction direction) {
        float scaler = gameData.getScaler();
        float newY;
        float newX;
        enemy.setDirection(direction);
        if (World.getInstance().getMap() instanceof IMap) {
            IMap map = (IMap) World.getInstance().getMap();
            if (!map.isMoveAllowed(abs(enemy.getX()), abs(enemy.getY()), direction)) {
                return;
            }
        }

        switch (direction) {
            case LEFT:
                newX = enemy.getX() - (MOVING_SPEED * gameData.getDeltaTime()) * scaler;
                enemy.setX((newX < 0) ? 0 : newX);
                enemy.setTexturePath(enemy.getActiveTexturePath(animations.LEFT));
                break;
            case RIGHT:
                newX = enemy.getX() + (MOVING_SPEED * gameData.getDeltaTime()) * scaler;
                enemy.setX((newX > ((world.getMap().getWidth() - 1) * scaler)) ? ((world.getMap().getWidth() - 1) * scaler) : newX);
                enemy.setTexturePath(enemy.getActiveTexturePath(animations.RIGHT));
                break;
            case UP:
                newY = enemy.getY() + (MOVING_SPEED * gameData.getDeltaTime()) * scaler;
                enemy.setY(newY);
                enemy.setTexturePath(enemy.getActiveTexturePath(animations.UP));
                break;
            case DOWN:
                newY = enemy.getY() - (MOVING_SPEED * gameData.getDeltaTime()) * gameData.getScaler();
                enemy.setY((newY < 0) ? 0 : newY);
                enemy.setTexturePath(enemy.getActiveTexturePath(animations.DOWN));
                break;
        }
    }

    private Collection<? extends IWeaponController> getIWeaponProcessing() {
        return ServiceLoader.load(IWeaponController.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }

    private Collection<? extends IPathFinding> getIPathFindingProcessing() {
        return ServiceLoader.load(IPathFinding.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }

}
