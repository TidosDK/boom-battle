package dk.sdu.mmmi.main.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import dk.sdu.mmmi.common.data.gameproperties.GameData;
import dk.sdu.mmmi.main.Main;

public class EndScreen implements Screen {
    private final Main game;
    private Stage stage;
    private Viewport viewport;
    private GameData gameData;


    public EndScreen(Main game) {
        this.game = game;

        this.gameData = GameData.getInstance();

        this.viewport = new FitViewport(150, 100, new OrthographicCamera()); // TODO: Find a better way to set the viewport size.

        this.stage = new Stage(viewport, game.getBatch());

        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.GREEN);

        Table table = new Table();
        table.center();
        table.setFillParent(true); // Fills the entire stage.

        Label gameOverLabel = new Label("GAME OVER", font);
        Label playAgainLabel = new Label("Click to play again", font);

        table.add(gameOverLabel).expandX(); // Expand the label as much as possible, in the x direction.
        table.row(); // Move to the next row.
        table.add(playAgainLabel).expandX().padTop(10f); // Add padding to the top of the label.

        this.stage.addActor(table);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {
        if (Gdx.input.justTouched()) {
            this.game.setScreen(new PlayScreen(this.game));
            this.dispose();
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Not sure what this does. Am following a tutorial.
        this.stage.draw();
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        this.stage.dispose();
    }
}
