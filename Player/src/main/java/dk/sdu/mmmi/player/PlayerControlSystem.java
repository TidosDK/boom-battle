package dk.sdu.mmmi.player;

import dk.sdu.mmmi.common.data.Direction;
import dk.sdu.mmmi.common.data.Entity;
import dk.sdu.mmmi.common.data.GameData;
import dk.sdu.mmmi.common.data.World;
import dk.sdu.mmmi.common.services.IActor;
import dk.sdu.mmmi.common.services.IEntityProcessingService;
import dk.sdu.mmmi.common.services.IWeapon;

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
        player.setDirection(direction);

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
}
