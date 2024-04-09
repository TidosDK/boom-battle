package dk.sdu.mmmi.player;

import dk.sdu.mmmi.common.data.*;
import dk.sdu.mmmi.common.services.IActor;
import dk.sdu.mmmi.common.services.IEntityProcessingService;
import dk.sdu.mmmi.common.services.IWeapon;
import dk.sdu.mmmi.common.services.Map.IMap;

import static java.lang.Math.abs;

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

        switch (direction) {
            case LEFT:
                newX = player.getX() - (MOVING_SPEED * gameData.getDeltaTime());
                player.setX((newX < 0) ? 0 : newX);
                System.out.println("Player pos: " + player.getX() + ", " + player.getY());
                break;
            case RIGHT:
                newX = player.getX() + (MOVING_SPEED * gameData.getDeltaTime());
                player.setX((newX < 0) ? 0 : newX);
                System.out.println("Player pos: " + player.getX() + ", " + player.getY());
                break;
            case UP:
                newY = player.getY() + (MOVING_SPEED * gameData.getDeltaTime());
                player.setY((newY < 0) ? 0 : newY);
                System.out.println("Player pos: " + player.getX() + ", " + player.getY());
                break;
            case DOWN:;
                newY= player.getY() - (MOVING_SPEED * gameData.getDeltaTime());
                player.setY((newY<0) ? 0 : newY);
                System.out.println("Player pos: " + player.getX() + ", " + player.getY());
                break;
        }
    }
}
