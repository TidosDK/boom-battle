package dk.sdu.mmmi.main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import dk.sdu.mmmi.common.data.entity.Entity;
import dk.sdu.mmmi.main.CustomStages.CustomStage;
import dk.sdu.mmmi.common.data.gameproperties.GameData;
import dk.sdu.mmmi.common.data.world.World;
import dk.sdu.mmmi.main.CustomStages.ICustomStage;
import dk.sdu.mmmi.common.services.IEntityProcessingService;
import dk.sdu.mmmi.common.services.IGamePluginService;
import dk.sdu.mmmi.common.services.map.IMapGenerator;

import java.util.*;

import static java.util.stream.Collectors.toList;

public class Main extends ApplicationAdapter {
    private GameData gameData;
    private World world;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private HashMap<Entity, Sprite> entitySprites = new HashMap<>();
    private ShapeRenderer shapeRenderer;
    private Collection<ICustomStage> stages;
    private CustomStage gameStage;

    @Override
    public void create() {
        // Setup of game
        gameData = GameData.getInstance();
        world = World.getInstance();
        camera = new OrthographicCamera();
        float width = 25f;
        float height = 25f * (Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth());
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

    @Override
    public void render() {
        // Update game
        ScreenUtils.clear(1, 1, 1, 1);

        gameData.setDeltaTime(Gdx.graphics.getDeltaTime());
        batch.setProjectionMatrix(camera.combined);
        updateKeys();

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


    }

    /**
     * Checks if keys are pressed and updates the keys in gameData.
     */
    private void updateKeys() {
        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            gameData.getKeys().setKey(gameData.getKeys().getLeft(), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            gameData.getKeys().setKey(gameData.getKeys().getRight(), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
            gameData.getKeys().setKey(gameData.getKeys().getUp(), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            gameData.getKeys().setKey(gameData.getKeys().getDown(), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            gameData.getKeys().setKey(gameData.getKeys().getSpace(), true);
        }


        if (!Gdx.input.isKeyPressed(Input.Keys.A) && !Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            gameData.getKeys().setKey(gameData.getKeys().getLeft(), false);
        }
        if (!Gdx.input.isKeyPressed(Input.Keys.D) && !Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            gameData.getKeys().setKey(gameData.getKeys().getRight(), false);
        }
        if (!Gdx.input.isKeyPressed(Input.Keys.W) && !Gdx.input.isKeyPressed(Input.Keys.UP)) {
            gameData.getKeys().setKey(gameData.getKeys().getUp(), false);
        }
        if (!Gdx.input.isKeyPressed(Input.Keys.S) && !Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            gameData.getKeys().setKey(gameData.getKeys().getDown(), false);
        }
        if (!Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            gameData.getKeys().setKey(gameData.getKeys().getSpace(), false);
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        for (Sprite sprite : entitySprites.values()) {
            sprite.getTexture().dispose();
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

    private void createWorld() {
        this.world = World.getInstance();
        ServiceLoader.Provider mapGenProvider = ServiceLoader.load(IMapGenerator.class).stream().findFirst().
                orElse(null);
        if (mapGenProvider != null) {
            IMapGenerator mapGen = (IMapGenerator) mapGenProvider.get();
            mapGen.generateMap(world);
        } else {
            world.generateDefaultMap();
        }
    }
}
