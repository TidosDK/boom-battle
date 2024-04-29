package dk.sdu.mmmi.player;

import dk.sdu.mmmi.common.data.gameproperties.GameData;
import dk.sdu.mmmi.common.data.entity.Direction;
import dk.sdu.mmmi.common.data.entity.Entity;
import dk.sdu.mmmi.common.data.world.World;
import dk.sdu.mmmi.common.services.entityproperties.IActor;
import dk.sdu.mmmi.common.services.IEntityProcessingService;
import dk.sdu.mmmi.common.services.textureanimator.ITextureAnimator;
import dk.sdu.mmmi.common.services.weapon.IWeapon;
import dk.sdu.mmmi.common.services.weapon.IWeaponController;
import dk.sdu.mmmi.common.services.map.IMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class PlayerControlSystem implements IActor, IEntityProcessingService {
    private World world;
    private GameData gameData;
    private Player player;
    private int maxWeapons = 3;
    private IMap map;

    private final float movingSpeed = 10f;

    @Override
    public void process(World worldParam, GameData gameDataParam) {
        this.world = worldParam;
        this.gameData = gameDataParam;

        for (Entity playerEntity : this.world.getEntities(Player.class)) {
            this.player = (Player) playerEntity;

            List<IWeapon> weaponsToBeRemoved = new ArrayList<>();
            for (IWeapon weapon : this.player.getWeapons()) {
                if (!this.world.getEntities().contains(weapon)) {
                    weaponsToBeRemoved.add(weapon);
                }
            }
            for (IWeapon weapon : weaponsToBeRemoved) {
                this.player.removeWeapon(weapon);
            }

            checkPlayerStatus();
        }

        if (this.world.getMap() instanceof IMap) {
            map = (IMap) this.world.getMap();
        }
    }

    /**
     * Checks the player status and handles the player accordingly whether the player is dead or alive.
     */
    private void checkPlayerStatus() {
        if (this.player.getLifepoints() <= 0) { // player is dead
            ITextureAnimator dieAnimator = this.player.getAnimators().get(PlayerAnimations.DIE.getValue());
            if (dieAnimator != null) { // Guard for ITextureAnimator being module
                player.setTexturePath(dieAnimator.getCurrentTexturePath());
                if (dieAnimator.getCurrentTextureIndex() == dieAnimator.getNumberOfTextures() - 1) {
                    this.world.removeEntity(this.player);
                }
            } else {
                this.world.removeEntity(this.player);
            }
        } else { // player is alive
            player.setTexturePath(player.getActiveTexturePath(PlayerAnimations.STILL.getValue()));
            checkKeyBasedActions();
        }
    }

    /**
     * Executes player actions based on the keys pressed.
     */
    private void checkKeyBasedActions() {
        if (gameData.getKeys().isDown(gameData.getKeys().getLeft())) {
            move(Direction.LEFT);
        } else if (gameData.getKeys().isDown(gameData.getKeys().getRight())) {
            move(Direction.RIGHT);
        } else if (gameData.getKeys().isDown(gameData.getKeys().getDown())) {
            move(Direction.DOWN);
        } else if (gameData.getKeys().isDown(gameData.getKeys().getUp())) {
            move(Direction.UP);
        }

        if (gameData.getKeys().isPressed(gameData.getKeys().getSpace())) {
            this.placeWeapon();
        } else if (gameData.getKeys().isPressed(gameData.getKeys().getGETPOS())) {
            System.out.println("Player position: " + player.getX() + ", " + player.getY());
        }
    }

    /**
     * Places a bomb in the world.
     */
    public void placeWeapon() {
        if (player.getWeapons().size() < maxWeapons) {
            for (IWeaponController weapon : getIWeaponProcessing()) {
                player.getWeapons().add((IWeapon) weapon.createWeapon(this.player, this.gameData));
                world.addEntity((Entity) player.getWeapons().get(player.getWeapons().size() - 1));
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
        float oldX = player.getX();
        float oldY = player.getY();
        player.setDirection(direction);
        if (World.getInstance().getMap() instanceof IMap) {
            IMap mapInstance = (IMap) World.getInstance().getMap();
            if (!mapInstance.isMoveAllowed(player.getGridX(), player.getGridY(), direction)) {
                return;
            }
        }

        switch (direction) {
            case LEFT:
                newX = oldX - (movingSpeed * gameData.getDeltaTime()) * scaler;
                newY = player.getGridY() * scaler;
                player.setX((newX < 0) ? 0 : newX);
                player.setY(newY);
                player.setTexturePath(player.getActiveTexturePath(PlayerAnimations.LEFT.getValue()));
                break;
            case RIGHT:
                newX = oldX + (movingSpeed * gameData.getDeltaTime()) * scaler;
                newY = player.getGridY() * scaler;
                player.setX(Math.min(newX, ((world.getMap().getWidth() - 1) * scaler)));
                player.setY(newY);
                player.setTexturePath(player.getActiveTexturePath(PlayerAnimations.RIGHT.getValue()));
                break;
            case UP:
                newX = player.getGridX() * scaler;
                newY = oldY + (movingSpeed * gameData.getDeltaTime()) * scaler;
                player.setX(newX);
                player.setY(Math.min(newY, ((world.getMap().getHeight() - 1) * scaler)));
                player.setTexturePath(player.getActiveTexturePath(PlayerAnimations.UP.getValue()));
                break;
            case DOWN:
                newX = player.getGridX() * scaler;
                newY = oldY - (movingSpeed * gameData.getDeltaTime()) * gameData.getScaler();
                player.setX(newX);
                player.setY((newY < 0) ? 0 : newY);
                player.setTexturePath(player.getActiveTexturePath(PlayerAnimations.DOWN.getValue()));
                break;
            default:
                break;
        }
    }

    private Collection<? extends IWeaponController> getIWeaponProcessing() {
        return ServiceLoader.load(IWeaponController.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
