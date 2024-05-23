package dk.sdu.mmmi.main.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import dk.sdu.mmmi.common.data.entity.Entity;
import dk.sdu.mmmi.common.data.gameproperties.GameData;
import dk.sdu.mmmi.common.data.world.World;
import dk.sdu.mmmi.common.services.IEntityProcessingService;
import dk.sdu.mmmi.common.services.IGamePluginService;
import dk.sdu.mmmi.common.services.entityproperties.IActor;
import dk.sdu.mmmi.common.services.map.IMapGenerator;
import dk.sdu.mmmi.main.CustomStages.CustomStage;
import dk.sdu.mmmi.main.CustomStages.ICustomStage;
import dk.sdu.mmmi.main.Main;

import java.util.*;

import static java.util.stream.Collectors.toList;

public class PlayScreen implements Screen {
    private final Main game; // To be used if the PlayScreen needs to call setScreen() to change to EndScreen, for instance.

    // Imported from Main.java
    private GameData gameData;
    private World world;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private HashMap<Entity, Sprite> entitySprites = new HashMap<>();
    private ShapeRenderer shapeRenderer;
    private Collection<ICustomStage> stages;
    private CustomStage gameStage;

    // Related to end game
    private float endGamePauseTimer = Float.POSITIVE_INFINITY;

    /**
     * Constructor for PlayScreen - used roughly the same as the create() method, from the Main class.
     *
     * @param game the Main game instance - supplied so PlayScreen can call the SetScreen method on Main.
     */
    public PlayScreen(Main game) {
        this.game = game;

        // Setup of game - copied from Main.java
        gameData = GameData.getInstance();
        world = World.getInstance();
        camera = new OrthographicCamera();
        float width = 25f;
        float height = 25f * (gameData.getGameScreenHeight() / (float) gameData.getGameScreenWidth());
        camera.setToOrtho(false, width, height);
        shapeRenderer = new ShapeRenderer();
        stages = new ArrayList<>();
        gameStage = new CustomStage(2.5f * gameData.getScaler(), 2.5f * gameData.getScaler(),
                11 * gameData.getScaler(), 11 * gameData.getScaler());
        stages.add(gameStage);
        batch = new SpriteBatch();
        entitySprites = new HashMap<>();
        createWorld();

        // Initial start of plugins
        for (IGamePluginService plugin : getPluginServices()) {
            plugin.start(world, gameData);
        }
    }

    /**
     * Show method from Screen interface - not used in this context.
     */
    @Override
    public void show() {

    }

    /**
     * Render method from Screen interface - used to render the game.
     *
     * @param v float value of the time since the last render - automatically supplied by the Main class.
     */
    @Override
    public void render(float v) {
        // Update game
        ScreenUtils.clear(1, 1, 1, 1);

        gameData.setDeltaTime(v);
        batch.setProjectionMatrix(camera.combined);

        // Disposes & delete entities that doesn't exist anymore
        // Note: We have to add weapons which are going to be removed from the world, as we can't remove elements from
        // world while we are iterating over it. Otherwise, we will get a ConcurrentModificationException as we are
        // modifying a collection which we are iterating over.
        if (entitySprites.keySet().size() != world.getEntities().size()) {
            List<Entity> entitiesToBeRemoved = new ArrayList<>();
            for (Entity entity : entitySprites.keySet()) {
                if (!world.getEntities().contains(entity)) {
                    entitiesToBeRemoved.add(entity);
                }
            }

            if (!entitiesToBeRemoved.isEmpty()) {
                for (Entity entity : entitiesToBeRemoved) {
                    entitySprites.get(entity).getTexture().dispose();
                    entitySprites.remove(entity);
                }
            }
        }

        for (IEntityProcessingService entityProcessingService : getEntityProcessingServices()) {
            entityProcessingService.process(world, gameData);
        }

        gameStage.setEntitySprites(entitySprites);
        gameStage.setEntities(world.getEntities());

        stages.forEach(stage -> stage.drawStage(batch));

        // TEMPORARY: Check if game is over. If so, wait 3 seconds before changing to EndScreen.
        if (isOneOrLessActorAlive()) {
            if (this.endGamePauseTimer != Float.POSITIVE_INFINITY) {
                this.endGamePauseTimer -= v;
            } else {
                this.endGamePauseTimer = 3f;
            }

            if (this.endGamePauseTimer <= 0f) {
                game.setScreen(new EndScreen(game));
                this.dispose();
            }
        }
    }

    /**
     * Resize method from Screen interface - not used in this context.
     *
     * @param i  int value of the new width of the screen.
     * @param i1 int value of the new height of the screen.
     */
    @Override
    public void resize(int i, int i1) {

    }

    /**
     * Pause method from Screen interface - not used in this context.
     */
    @Override
    public void pause() {

    }

    /**
     * Resume method from Screen interface - not used in this context.
     */
    @Override
    public void resume() {

    }

    /**
     * Hide method from Screen interface - not used in this context.
     */
    @Override
    public void hide() {

    }

    /**
     * Dispose method from Screen interface - used to dispose of the game and it's objects, when it is closed.
     */
    @Override
    public void dispose() {
        batch.dispose();
        for (Sprite sprite : entitySprites.values()) {
            sprite.getTexture().dispose();
        }

        for (IGamePluginService plugin : getPluginServices()) {
            plugin.stop(world, gameData);
        }
    }

    /**
     * Get all plugins.
     *
     * @return a collection of all instances of IGamePluginService
     */
    private Collection<? extends IGamePluginService> getPluginServices() {
        return ServiceLoader.load(IGamePluginService.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }

    /**
     * Get all entity processing services.
     *
     * @return a collection of all instances of IEntityProcessingService
     */
    private Collection<? extends IEntityProcessingService> getEntityProcessingServices() {
        return ServiceLoader.load(IEntityProcessingService.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }


    /**
     * Checks if there is only one or less actor alive in the world.
     * Useful for checking if the game is over.
     *
     * @return true if there is only one or less Entity in world, or if there is only one or less IActor in world.
     */
    private boolean isOneOrLessActorAlive() {
        ArrayList<Entity> entities = world.getEntities();

        if (entities.size() <= 1) {
            return true;
        }

        boolean actorFound = false;

        for (Entity entity : entities) {
            if (entity instanceof IActor && !actorFound) {
                actorFound = true;
            } else if (entity instanceof IActor) {
                return false;
            }
        }

        return true;
    }

    private void createWorld() {
        this.world = World.getInstance();
        ServiceLoader.Provider mapGenProvider = ServiceLoader.load(IMapGenerator.class).stream().findFirst().
                orElse(null);
        if (mapGenProvider != null) {
            IMapGenerator mapGen = (IMapGenerator) mapGenProvider.get();
            world.setMap(mapGen.generateMap(world));
        } else {
            world.generateDefaultMap();
        }
    }
}
