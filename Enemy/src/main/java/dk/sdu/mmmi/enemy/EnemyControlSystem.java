package dk.sdu.mmmi.enemy;

import dk.sdu.mmmi.common.data.entity.Direction;
import dk.sdu.mmmi.common.data.entity.Entity;
import dk.sdu.mmmi.common.data.gameproperties.GameData;
import dk.sdu.mmmi.common.data.world.World;
import dk.sdu.mmmi.common.services.IEntityProcessingService;
import dk.sdu.mmmi.common.services.ai.IOptimalBombPlacement;
import dk.sdu.mmmi.common.services.entityproperties.IActor;
import dk.sdu.mmmi.common.services.map.IMap;
import dk.sdu.mmmi.common.services.textureanimator.ITextureAnimator;
import dk.sdu.mmmi.common.services.weapon.IWeapon;
import dk.sdu.mmmi.common.services.weapon.IWeaponController;
import dk.sdu.mmmi.common.services.ai.IPathFinding;
import dk.sdu.mmmi.common.data.ai.Node;

import java.util.*;

import static java.util.stream.Collectors.toList;

/**
 * This class is used to control the enemy entity.
 */
public class EnemyControlSystem implements IActor, IEntityProcessingService {
    private World world;
    private GameData gameData;
    private Enemy enemy;
    private int maxWeapons = 3;
    private IMap map;
    private ArrayList<Node> path = new ArrayList<>();

    private final float movingSpeed = 2.5f;

    private Node nodeBombPlacement;
    ArrayList<Entity> weaponsList = new ArrayList<>();

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

            for (Entity weapon : this.world.getEntities()) {
                if (weapon instanceof IWeapon) {
                    weaponsList.add(weapon);
                }
            }
            for (IPathFinding pathFinding : getIPathFindingProcessing()) {
                if (weaponsList.isEmpty()) {
                    path = pathFinding.pathFind(startNode, goalNode, world.getMap());
                } else {
                    Node temp = runAwayFromBombs(weaponsList);
                    if (!world.getMap().getMap()[temp.getX()][temp.getY()]) {
                        path = pathFinding.pathFind(startNode, runAwayFromBombs(weaponsList), world.getMap());
                    }

                }


            }
            for (IOptimalBombPlacement bombPlacement : getIOptimalBombPlacementProcessing()) {
                nodeBombPlacement = bombPlacement.optimalBombPlacement(startNode, goalNode, world.getMap());
            }
            System.out.println(weaponsList.size());
            checkEnemyStatus();
        }

        if (this.world.getMap() instanceof IMap) {
            map = (IMap) this.world.getMap();
        }
    }


    /**
     * Moves the enemy in the specified direction using the path provide by the pathfinding interface.
     *
     * @param givenPath the optimal path to an IActor.
     */
    private void moveUsingPath(ArrayList<Node> givenPath) {
        boolean canMove = true;
        Direction direction = null;
        if (givenPath != null && !givenPath.isEmpty()) {
            for (Node node : givenPath) {
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

            checkBombPlacement();

        }
    }

    private void checkBombPlacement() {
        if (nodeBombPlacement != null) {
            if (this.enemy.getGridPosition().getX() == nodeBombPlacement.getX() && this.enemy.getGridPosition().getY() == nodeBombPlacement.getY()) {
                placeWeapon();
            }
        }
    }

    public void placeWeapon() {
        if (enemy.getWeapons().size() < maxWeapons) {
            for (IWeaponController weapon : getIWeaponProcessing()) {
                IWeapon bomb = (IWeapon) weapon.createWeapon(this.enemy, this.gameData);
                enemy.getWeapons().add(bomb);
                world.addEntity((Entity) bomb);
            }
        }
    }

    /**
     * Moves the enemy in the specified direction.
     *
     * @param direction The direction to move the enemy
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


    private Node runAwayFromBombs(ArrayList<Entity> weaponsList) {
        // still in development
        int x = 0;
        int y = 0;

        Entity closetWeapon = weaponsList.getFirst();
        ArrayList<int[]> distances = new ArrayList<>();
        int[] furthestDistance = new int[2];

        for (Entity weapon : weaponsList) {
            int minForX = Math.min(enemy.getGridX(), weapon.getGridX());
            int maxForX = Math.max(enemy.getGridX(), weapon.getGridX());

            int differenceX = (maxForX - minForX);

            int minForY = Math.min(enemy.getGridY(), weapon.getGridY());
            int maxForY = Math.max(enemy.getGridY(), weapon.getGridY());
            int differenceY = (maxForY - minForY);

            distances.add(new int[]{differenceX, differenceY,weaponsList.indexOf(weapon)});
        }
        for (int[] distance : distances) {
            if (distance[0] > furthestDistance[0] && distance[1] > furthestDistance[1]) {
                furthestDistance = distance;
                closetWeapon = weaponsList.get(distance[2]);
            }
        }
        System.out.println("X: " + furthestDistance[0] + " Y: " + furthestDistance[1]);
        x = closetWeapon.getGridX() + furthestDistance[0];
        y = closetWeapon.getGridY() + furthestDistance[1];

        return new Node(x, y);
    }

    private Collection<? extends IWeaponController> getIWeaponProcessing() {
        return ServiceLoader.load(IWeaponController.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }

    private Collection<? extends IPathFinding> getIPathFindingProcessing() {
        return ServiceLoader.load(IPathFinding.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }

    private Collection<? extends IOptimalBombPlacement> getIOptimalBombPlacementProcessing() {
        return ServiceLoader.load(IOptimalBombPlacement.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }


}
