package dk.sdu.mmmi.player;

import dk.sdu.mmmi.common.data.*;
import dk.sdu.mmmi.common.services.IActor;
import dk.sdu.mmmi.common.services.IEntityProcessingService;
import dk.sdu.mmmi.common.services.IWeapon;
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

            List<Entity> weaponsToBeRemoved = new ArrayList<>();
            for (Entity weapon : this.player.getWeapons()) {
                if (!world.getEntities().contains(weapon)) {
                    weaponsToBeRemoved.add(weapon);
                }
            }
            for (Entity weapon : weaponsToBeRemoved) {
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
            for (IWeapon weapon : getIWeapon()) {
                player.getWeapons().add(weapon.createWeapon(this.player, this.gameData));
                world.addEntity(player.getWeapons().get(player.getWeapons().size() - 1));
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
            GridPosition coords = player.getGridPosition();
            if (!map.isMoveAllowed(abs(coords.getX()), abs(coords.getY()), direction)) {
                return;
            }
        }

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
                newX = player.getX() - (MOVING_SPEED * gameData.getDeltaTime());
                player.setX((newX < 0) ? 0 : newX);
                break;
            case RIGHT:
                newX = player.getX() + (MOVING_SPEED * gameData.getDeltaTime());
                player.setX((newX < 0) ? 0 : newX);
                break;
            case UP:
                newY = player.getY() + (MOVING_SPEED * gameData.getDeltaTime());
                player.setY((newY < 0) ? 0 : newY);
                break;
            case DOWN:;
                newY= player.getY() - (MOVING_SPEED * gameData.getDeltaTime());
                player.setY((newY<0) ? 0 : newY);
                break;
        }
    }

    private Collection<? extends IWeapon> getIWeapon() {
        return ServiceLoader.load(IWeapon.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
