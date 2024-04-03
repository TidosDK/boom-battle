package dk.sdu.mmmi.main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;

public class Main extends ApplicationAdapter {
    OrthographicCamera camera;
    SpriteBatch batch;
    Sprite player;

    private final float MOVING_SPEED = 2.0f;
    private final float ROTATION_SPEED = 200.0f;
    private float deltaTime = 0;


    @Override
    public void create() {
        camera = new OrthographicCamera(20f, 20f * (Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth())); // camera should have same aspect ratio as the window, but in meters
        batch = new SpriteBatch();
        player = new Sprite(new Texture("/Users/mj/Documents/[9] Schule/Semester 4/SP4/boom-battle/Core/src/main/resources/personLeft1.png"));

        player.setSize(2.0f, 2.0f);
        player.setOriginCenter();
//		player.setPosition(Gdx.graphics.getWidth() / 2 - player.getWidth() / 2, Gdx.graphics.getHeight() / 2 - player.getHeight() / 2); // when no camera is used
        player.setPosition(0, 0); // when camera is used
    }


    @Override
    public void render() {
        this.deltaTime = Gdx.graphics.getDeltaTime();

        ScreenUtils.clear(1, 1, 1, 1); // white background
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        player.draw(batch);
        batch.end();

    }

    @Override
    public void dispose() {
        batch.dispose();
        player.getTexture().dispose();
    }
}
