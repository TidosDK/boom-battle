package dk.sdu.mmmi.enemy;

import dk.sdu.mmmi.common.data.entity.Direction;
import dk.sdu.mmmi.common.data.entity.Entity;
import dk.sdu.mmmi.common.data.gameproperties.GameData;
import dk.sdu.mmmi.common.data.world.World;
import dk.sdu.mmmi.common.services.IEntityProcessingService;
import dk.sdu.mmmi.common.services.entityproperties.IActor;
import dk.sdu.mmmi.common.services.map.IMap;
import dk.sdu.mmmi.common.services.textureanimator.ITextureAnimator;
import dk.sdu.mmmi.common.services.weapon.IWeapon;
import dk.sdu.mmmi.common.services.weapon.IWeaponController;
import dk.sdu.mmmi.common.services.ai.IPathFinding;
import dk.sdu.mmmi.common.data.ai.Node;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class EnemyControlSystem implements IActor, IEntityProcessingService { // implements IDamageable
    private World world;
    private GameData gameData;
    private Enemy enemy;
    private int maxWeapons = 3;
    private IMap map;
    private ArrayList<Node> path = new ArrayList<>();

    private final float movingSpeed = 1f;

    @Override
    public void process(World worldParam, GameData gameDataParam) {
        this.world = worldParam;
        this.gameData = gameDataParam;
        Node goalNode = null;
        Node startNode;

        for (Entity playerEntity : this.world.getEntities(Enemy.class)) {
            this.enemy = (Enemy) playerEntity;
            startNode = new Node(this.enemy.getGridPosition().getX(), this.enemy.getGridPosition().getY());

            List<IWeapon> weaponsToBeRemoved = new ArrayList<>();
            for (IWeapon weapon : this.enemy.getWeapons()) {
                if (!this.world.getEntities().contains(weapon)) {
                    weaponsToBeRemoved.add(weapon);
                }
            }

            for (IWeapon weapon : weaponsToBeRemoved) {
                this.enemy.removeWeapon(weapon);
            }

            for (Entity player : this.world.getEntities()) {
                if (player instanceof IActor && player != this.enemy) {
                    goalNode = new Node(player.getGridPosition().getX(), player.getGridPosition().getY());
                }
            }

            for (IPathFinding pathFinding : getIPathFindingProcessing()) {
                path = pathFinding.pathFind(startNode, goalNode, world.getMap());
            }
            checkEnemyStatus();
        }

        if (this.world.getMap() instanceof IMap) {
            map = (IMap) this.world.getMap();
        }
    }

    private void drawUsingPath(ArrayList<Node> pathParam) {

    }

    private void moveUsingPath(ArrayList<Node> path) {
        Boolean canMove = true;
        Direction direction = null;
        if (path != null && !path.isEmpty()) {
            for (Node node : path) {
                if (canMove) {
                    int x = node.getX() - enemy.getGridPosition().getX();
                    int y = node.getY() - enemy.getGridPosition().getY();
                    if (x > 0) {
                        direction = Direction.RIGHT;
                    } else if (x < 0) {
                        direction = Direction.LEFT;
                    } else if (y > 0) {
                        direction = Direction.UP;
                    } else if (y < 0) {
                        direction = Direction.DOWN;
                    }
                    if (World.getInstance().getMap() instanceof IMap) {
                        IMap mapInstance = (IMap) World.getInstance().getMap();
                        if (!mapInstance.isMoveAllowed(enemy.getGridX(), enemy.getGridY(), direction)) {
                            continue;
                        } else {
                            move(direction);
                        }
                    }
                    canMove = false;
                }

            }
        }
    }

    /**
     * Checks the enemy status and handles the enemy accordingly whether the enemy is dead or alive.
     */
    private void checkEnemyStatus() {
        if (this.enemy.getLifepoints() <= 0) { // enemy is dead
            ITextureAnimator dieAnimator = this.enemy.getAnimators().get(EnemyAnimations.DIE.getValue());
            if (dieAnimator != null) { // Guard for ITextureAnimator being module
                enemy.setTexturePath(dieAnimator.getCurrentTexturePath());
                if (dieAnimator.getCurrentTextureIndex() == dieAnimator.getNumberOfTextures() - 1) {
                    this.world.removeEntity(this.enemy);
                }
            } else {
                this.world.removeEntity(this.enemy);
            }
        } else { // enemy is alive
            enemy.setTexturePath(enemy.getActiveTexturePath(EnemyAnimations.STILL.getValue()));

            moveUsingPath(path);

        }
    }


    /**
     * Places a bomb in the world.
     */
    public void placeWeapon() {
        if (enemy.getWeapons().size() < maxWeapons) {
            for (IWeaponController weapon : getIWeaponProcessing()) {
                enemy.getWeapons().add((IWeapon) weapon.createWeapon(this.enemy, this.gameData));
                world.addEntity((Entity) enemy.getWeapons().get(enemy.getWeapons().size() - 1));
            }
        }
    }

    /**
     * Moves the player in the specified direction.
     *
     * @param direction The direction to move the player.
     */
    public void move(Direction direction) {
        float scaler = gameData.getScaler();
        float newY;
        float newX;
        float oldX = enemy.getX();
        float oldY = enemy.getY();
        enemy.setDirection(direction);
        if (World.getInstance().getMap() instanceof IMap) {
            IMap mapInstance = (IMap) World.getInstance().getMap();
            if (!mapInstance.isMoveAllowed(enemy.getGridX(), enemy.getGridY(), direction)) {
                return;
            }
        }

        switch (direction) {
            case LEFT:
                newX = oldX - (movingSpeed * gameData.getDeltaTime()) * scaler;
                newY = enemy.getGridY() * scaler;
                enemy.setX((newX < 0) ? 0 : newX);
                enemy.setY(newY);
                enemy.setTexturePath(enemy.getActiveTexturePath(EnemyAnimations.LEFT.getValue()));
                break;
            case RIGHT:
                newX = oldX + (movingSpeed * gameData.getDeltaTime()) * scaler;
                newY = enemy.getGridY() * scaler;
                enemy.setX(Math.min(newX, ((world.getMap().getWidth() - 1) * scaler)));
                enemy.setY(newY);
                enemy.setTexturePath(enemy.getActiveTexturePath(EnemyAnimations.RIGHT.getValue()));
                break;
            case UP:
                newX = enemy.getGridX() * scaler;
                newY = oldY + (movingSpeed * gameData.getDeltaTime()) * scaler;
                enemy.setX(newX);
                enemy.setY(Math.min(newY, ((world.getMap().getHeight() - 1) * scaler)));
                enemy.setTexturePath(enemy.getActiveTexturePath(EnemyAnimations.UP.getValue()));
                break;
            case DOWN:
                newX = enemy.getGridX() * scaler;
                newY = oldY - (movingSpeed * gameData.getDeltaTime()) * gameData.getScaler();
                enemy.setX(newX);
                enemy.setY((newY < 0) ? 0 : newY);
                enemy.setTexturePath(enemy.getActiveTexturePath(EnemyAnimations.DOWN.getValue()));
                break;
            default:
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
