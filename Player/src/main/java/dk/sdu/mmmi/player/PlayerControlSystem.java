package dk.sdu.mmmi.player;

import dk.sdu.mmmi.common.data.Properties.GameData;
import dk.sdu.mmmi.common.data.Entity.Direction;
import dk.sdu.mmmi.common.data.Entity.Entity;
import dk.sdu.mmmi.common.data.World.GridPosition;
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

public class PlayerControlSystem implements IActor, IEntityProcessingService { // implements IDamageable
    private World world;
    private GameData gameData;
    private Player player;
    private int maxWeapons = 3;
    private IMap map = null;

    private final float MOVING_SPEED = 10f;

    @Override
    public void process(World world, GameData gameData) {
        this.world = world;
        this.gameData = gameData;

        for (Entity player : world.getEntities(Player.class)) {
            this.player = (Player) player;

            List<IWeapon> weaponsToBeRemoved = new ArrayList<>();
            for (IWeapon weapon : this.player.getWeapons()) {
                if (!world.getEntities().contains(weapon)) {
                    weaponsToBeRemoved.add(weapon);
                }
            }
            for (IWeapon weapon : weaponsToBeRemoved) {
                this.player.removeWeapon(weapon);
            }

            checkMovement();
        }

        if (world.getMap() instanceof IMap) {
            map = (IMap) world.getMap();
        }
    }

    private void checkMovement() {
        if (gameData.getKeys().isDown(gameData.getKeys().getLEFT())) {
            move(Direction.LEFT);
        } else if (gameData.getKeys().isDown(gameData.getKeys().getRIGHT())) {
            move(Direction.RIGHT);
        } else if (gameData.getKeys().isDown(gameData.getKeys().getDOWN())) {
            move(Direction.DOWN);
        } else if (gameData.getKeys().isDown(gameData.getKeys().getUP())) {
            move(Direction.UP);
        }

        // Should check for isPressed instead of isDown
        if (gameData.getKeys().isPressed(gameData.getKeys().getSPACE())) {
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
    public void takeDamage() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    // @Override
    public void move(Direction direction) {
        float newY;
        float newX;
        player.setDirection(direction);
        if (World.getInstance().getMap() instanceof IMap) {
            IMap map = (IMap) World.getInstance().getMap();
            if (!map.isMoveAllowed(abs(player.getX()), abs(player.getY()), direction)) {
                return;
            }
        }

        switch (direction) {
            case LEFT:
                newX = player.getX() - (MOVING_SPEED * gameData.getDeltaTime());
                player.setX((newX < 0) ? 0 : newX);
                player.setTexturePath(player.getCurrentWalkLeftAnimatorPath());
                break;
            case RIGHT:
                newX = player.getX() + (MOVING_SPEED * gameData.getDeltaTime());
                player.setX((newX > world.getMap().getWidth()-1) ? (float) world.getMap().getWidth() - 1 : newX);
                break;
            case UP:
                newY = player.getY() + (MOVING_SPEED * gameData.getDeltaTime());
                player.setY((newY > world.getMap().getHeight()-1) ? (float) world.getMap().getHeight()-1 : newY);
                break;
            case DOWN:
                newY = player.getY() - (MOVING_SPEED * gameData.getDeltaTime());
                player.setY((newY < 0) ? 0 : newY);
                player.setTexturePath(player.getCurrentWalkDownAnimatorPath());
                break;
        }
    }

    private Collection<? extends IWeaponController> getIWeaponProcessing() {
        return ServiceLoader.load(IWeaponController.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
