package dk.sdu.mmmi.player;

import dk.sdu.mmmi.common.data.Direction;
import dk.sdu.mmmi.common.data.Entity;
import dk.sdu.mmmi.common.data.GameData;
import dk.sdu.mmmi.common.data.World;
import dk.sdu.mmmi.common.services.IActor;
import dk.sdu.mmmi.common.services.IEntityProcessingService;
import dk.sdu.mmmi.common.services.IWeapon;
import dk.sdu.mmmi.common.services.Map.IMap;

public class PlayerControlSystem implements IActor, IEntityProcessingService { // implements IDamageable
    private World world;
    private GameData gameData;
    private Player player;
    private IWeapon[] weapons;

    private final float MOVING_SPEED = 10f;

    @Override
    public void process(World world, GameData gameData) {
        this.world = world;
        this.gameData = gameData;

        for (Entity player : world.getEntities(Player.class)) {
            this.player = (Player) player;
            checkMovement();
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
    }

    // @Override
    public void placeWeapon() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    // @Override
    public void takeDamage() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    // @Override
    public void move(Direction direction) {
        System.out.println("Moving player: x: %f, y: %f" + player.getX() + player.getY());
        player.setDirection(direction);
        if (World.getInstance().getMap() instanceof IMap) {
            IMap map = (IMap) World.getInstance().getMap();
            if (!map.isMoveAllowed((int) player.getX(), (int) player.getY(), direction)) {
                return;
            }
        }
        switch (direction) {
            case LEFT:
                player.setX(player.getX() + (float) (MOVING_SPEED * -1 * gameData.getDeltaTime()));
                break;
            case RIGHT:
                player.setX(player.getY() + (float) (MOVING_SPEED * 1 * gameData.getDeltaTime()));
                break;
            case UP:
                player.setY(player.getY() + (float) (MOVING_SPEED * -1 * gameData.getDeltaTime()));
                break;
            case DOWN:;
                player.setY(player.getY() - (float) (MOVING_SPEED * 1 * gameData.getDeltaTime()));
                break;
        }
    }
}
