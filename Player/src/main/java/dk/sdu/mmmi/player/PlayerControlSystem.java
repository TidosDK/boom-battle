package dk.sdu.mmmi.player;

import dk.sdu.mmmi.common.data.Direction;
import dk.sdu.mmmi.common.data.Entity;
import dk.sdu.mmmi.common.data.GameData;
import dk.sdu.mmmi.common.data.World;
import dk.sdu.mmmi.common.services.IActor;
import dk.sdu.mmmi.common.services.IEntityProcessingService;
import dk.sdu.mmmi.common.services.IWeapon;
import dk.sdu.mmmi.common.services.Map.IMap;

import java.util.Collection;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class PlayerControlSystem implements IActor, IEntityProcessingService { // implements IDamageable
    private World world;
    private GameData gameData;
    private Player player;
    private IWeapon[] weapons;
    private IMap map = null;

    private final float MOVING_SPEED = 10f;

    @Override
    public void process(World world, GameData gameData) {
        this.world = world;
        this.gameData = gameData;

        for (Entity player : world.getEntities(Player.class)) {
            this.player = (Player) player;
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

        if (gameData.getKeys().isDown(gameData.getKeys().getSPACE())) {
            this.placeWeapon();
        }
    }

    // @Override
    public void placeWeapon() {
        for (IWeapon weapon : getIWeapon()) {
            world.addEntity(weapon.createWeapon(this.player, this.gameData));
        }
    }

    // @Override
    public void takeDamage() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    // @Override
    public void move(Direction direction) {
        player.setDirection(direction);

        // Check if the game contains a map.
        if (map != null) {
            System.out.println("Map is not null");
            // Check if the player can move in the given direction
            if(!map.isMoveAllowed((int) player.getX(), (int) player.getY(), direction)) {
                return;
            }
        }

        switch (direction) {
            case LEFT:
                player.setX(player.getX() + (float) (MOVING_SPEED * Math.cos(Math.toRadians(player.getRotation() + 90)) * gameData.getDeltaTime()));
                player.setY(player.getY() - (float) (MOVING_SPEED * Math.sin(Math.toRadians(player.getRotation() + 90)) * gameData.getDeltaTime()));
                break;
            case RIGHT:
                player.setX(player.getX() - (float) (MOVING_SPEED * Math.cos(Math.toRadians(player.getRotation() - 90)) * gameData.getDeltaTime()));
                player.setY(player.getY() + (float) (MOVING_SPEED * Math.sin(Math.toRadians(player.getRotation() - 90)) * gameData.getDeltaTime()));
                break;
            case UP:
                player.setX(player.getX() + (float) (MOVING_SPEED * Math.cos(Math.toRadians(player.getRotation() + 90)) * gameData.getDeltaTime()));
                player.setY(player.getY() + (float) (MOVING_SPEED * Math.sin(Math.toRadians(player.getRotation() + 90)) * gameData.getDeltaTime()));
                break;
            case DOWN:
                player.setX(player.getX() - (float) (MOVING_SPEED * Math.cos(Math.toRadians(player.getRotation() - 90)) * gameData.getDeltaTime()));
                player.setY(player.getY() - (float) (MOVING_SPEED * Math.sin(Math.toRadians(player.getRotation() - 90)) * gameData.getDeltaTime()));
                break;
        }
    }

    private Collection<? extends IWeapon> getIWeapon() {
        return ServiceLoader.load(IWeapon.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
