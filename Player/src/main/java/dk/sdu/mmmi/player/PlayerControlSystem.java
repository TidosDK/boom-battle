package dk.sdu.mmmi.player;

import dk.sdu.mmmi.common.data.Properties.GameData;
import dk.sdu.mmmi.common.data.Entity.Direction;
import dk.sdu.mmmi.common.data.Entity.Entity;
import dk.sdu.mmmi.common.data.World.World;
import dk.sdu.mmmi.common.services.Entity.IActor;
import dk.sdu.mmmi.common.services.Entity.IEntityProcessingService;
import dk.sdu.mmmi.common.services.Entity.Weapon.IWeapon;
import dk.sdu.mmmi.common.services.Entity.Weapon.IWeaponController;
import dk.sdu.mmmi.common.services.Map.IMap;

import static java.lang.Math.abs;

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
    private IMap map = null;

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

            if (this.player.getLifepoints() <= 0) {
                this.world.removeEntity(playerEntity);
            }

            checkMovement();
        }

        if (this.world.getMap() instanceof IMap) {
            map = (IMap) this.world.getMap();
        }
    }

    private void checkMovement() {
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
        }
    }

    // @Override
    public void placeWeapon() {
        if (player.getWeapons().size() < maxWeapons) {
            for (IWeaponController weapon : getIWeaponProcessing()) {
                player.getWeapons().add((IWeapon) weapon.createWeapon(this.player, this.gameData));
                world.addEntity((Entity) player.getWeapons().get(player.getWeapons().size() - 1));
            }
        }
    }

    // @Override
    public void move(Direction direction) {
        float scaler = gameData.getScaler();
        float newY;
        float newX;
        player.setDirection(direction);
        if (World.getInstance().getMap() instanceof IMap) {
            IMap mapInstance = (IMap) World.getInstance().getMap();
            if (!mapInstance.isMoveAllowed(abs(player.getX()), abs(player.getY()), direction)) {
                return;
            }
        }

        switch (direction) {
            case LEFT:
                newX = player.getX() - (movingSpeed * gameData.getDeltaTime()) * scaler;
                player.setX((newX < 0) ? 0 : newX);
                player.setTexturePath(player.getActiveTexturePath(PlayerAnimations.LEFT.getValue()));
                break;
            case RIGHT:
                newX = player.getX() + (movingSpeed * gameData.getDeltaTime()) * scaler;
                player.setX((newX > ((world.getMap().getWidth() - 1) * scaler)) ? ((world.getMap().getWidth() - 1) * scaler) : newX);
                player.setTexturePath(player.getActiveTexturePath(PlayerAnimations.RIGHT.getValue()));
                break;
            case UP:
                newY = player.getY() + (movingSpeed * gameData.getDeltaTime()) * scaler;
                player.setY(newY);
                player.setTexturePath(player.getActiveTexturePath(PlayerAnimations.UP.getValue()));
                break;
            case DOWN:
                newY = player.getY() - (movingSpeed * gameData.getDeltaTime()) * gameData.getScaler();
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
