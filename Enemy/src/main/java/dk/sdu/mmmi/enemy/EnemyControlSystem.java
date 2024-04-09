package dk.sdu.mmmi.enemy;

import dk.sdu.mmmi.common.data.GameData;
import dk.sdu.mmmi.common.data.World;
import dk.sdu.mmmi.common.services.IActor;
import dk.sdu.mmmi.common.services.IEntityProcessingService;
import dk.sdu.mmmi.common.services.IWeapon;

public class EnemyControlSystem implements IActor, IEntityProcessingService { // implements IDamageable
    private IWeapon[] weapons;

    // @Override
    public void process(World world, GameData gameData) {
        throw new UnsupportedOperationException("Not supported yet.");
        /*
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            this.move(Direction.LEFT);
            }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            this.move(Direction.RIGHT);
            }
        if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
            this.move(Direction.UP);
            }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            this.move(Direction.DOWN);
            }
         */
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
    public void move(Enum direction) {
        throw new UnsupportedOperationException("Not supported yet.");
        /*
        // TODO: Check for collision with obstacles.
        switch(direction) {
            case LEFT:
                this.player.setX(this.player.getX() - 1 * Gdx.graphics.getDeltaTime());
                break;
            case RIGHT:
                this.player.setX(this.player.getX() + 1 * Gdx.graphics.getDeltaTime());
                break;
            case UP:
                this.player.setY(this.player.getY() + 1 * Gdx.graphics.getDeltaTime());
                break;
            case DOWN:
                this.player.setY(this.player.getY() - 1 * Gdx.graphics.getDeltaTime());
                break;
           }

        // make sure the sprite stays within the screen bounds
        if(this.player.getX() < 0) this.player.setX(0);
        if(this.player.getX() > map.getWidth() - this.player.getWidth()) this.player.setX(800 - this.player.getWidth());
        if(this.player.getY() < 0) this.player.setY(0);
        if(this.player.getY() > map.getHeight() - this.player.getHeight()) this.player.setY(800 - this.player.getHeight());
        */
    }
}
