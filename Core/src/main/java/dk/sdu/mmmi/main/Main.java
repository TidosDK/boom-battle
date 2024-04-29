package dk.sdu.mmmi.main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import dk.sdu.mmmi.common.data.gameproperties.GameData;
import dk.sdu.mmmi.main.screens.PlayScreen;


public class Main extends Game {
    private GameData gameData;

    @Override
    public void create() {
        this.gameData = GameData.getInstance();
        setScreen(new PlayScreen(this));
    }

    @Override
    public void render() {
        // update keys is relevant for all screens
        this.updateKeys();

        super.render();
    }

    /**
     * Checks if keys are pressed and updates the keys in gameData.
     */
    private void updateKeys() {
        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            this.gameData.getKeys().setKey(this.gameData.getKeys().getLeft(), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            this.gameData.getKeys().setKey(this.gameData.getKeys().getRight(), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
            this.gameData.getKeys().setKey(this.gameData.getKeys().getUp(), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            this.gameData.getKeys().setKey(this.gameData.getKeys().getDown(), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            this.gameData.getKeys().setKey(this.gameData.getKeys().getSpace(), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.K)) {
            this.gameData.getKeys().setKey(this.gameData.getKeys().getGetPos(), true);
        }


        if (!Gdx.input.isKeyPressed(Input.Keys.A) && !Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            this.gameData.getKeys().setKey(this.gameData.getKeys().getLeft(), false);
        }
        if (!Gdx.input.isKeyPressed(Input.Keys.D) && !Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            this.gameData.getKeys().setKey(this.gameData.getKeys().getRight(), false);
        }
        if (!Gdx.input.isKeyPressed(Input.Keys.W) && !Gdx.input.isKeyPressed(Input.Keys.UP)) {
            this.gameData.getKeys().setKey(this.gameData.getKeys().getUp(), false);
        }
        if (!Gdx.input.isKeyPressed(Input.Keys.S) && !Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            this.gameData.getKeys().setKey(this.gameData.getKeys().getDown(), false);
        }
        if (!Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            this.gameData.getKeys().setKey(this.gameData.getKeys().getSpace(), false);
        }
        if (!Gdx.input.isKeyPressed(Input.Keys.K)) {
            this.gameData.getKeys().setKey(this.gameData.getKeys().getGetPos(), false);
        }
    }

    @Override
    public void dispose() {
        screen.dispose(); // Note: The screen's dispose methods are not otherwise called automatically.
        super.dispose(); // Don't know if this is necessary
    }
}
