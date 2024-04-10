package dk.sdu.mmmi.main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import dk.sdu.mmmi.common.data.*;
import dk.sdu.mmmi.common.data.Map;
import dk.sdu.mmmi.common.services.IEntityProcessingService;
import dk.sdu.mmmi.common.services.IGamePluginService;
import dk.sdu.mmmi.common.services.Map.IMap;
import dk.sdu.mmmi.common.services.Map.IMapGenerator;

import java.util.*;

import static java.util.stream.Collectors.toList;

public class Main extends ApplicationAdapter {
    private GameData gameData;
    private World world;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private HashMap<Entity, Sprite> entitySprites = new HashMap<>();
    private ShapeRenderer shapeRenderer;

    @Override
    public void create() {
        // Setup of game
        gameData = GameData.getInstance();
        world = World.getInstance();
        camera = new OrthographicCamera();
        float width = 20f;
        float height = 20f * (Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth());
        camera.setToOrtho(false, width, height);
        shapeRenderer = new ShapeRenderer();

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
        gameData.setDeltaTime(Gdx.graphics.getDeltaTime());
        ScreenUtils.clear(1, 1, 1, 1); // white background
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

        // Create sprites for new entities
        for (Entity entity : world.getEntities()) {
            if (!entitySprites.containsKey(entity)) {
                Sprite sprite = createSprite(entity);
                entitySprites.put(entity, sprite);
            }
        }

        for (Entity entity : world.getEntities()) {
            updateSprite(entity);
        }

        // Draw map
        drawMap();

        // Render entities
        batch.begin();
        for (Sprite sprite : entitySprites.values()) {
            sprite.draw(batch);
        }
        batch.end();
    }

    private void drawMap() {
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(0, 0, 0, 1);
        IMap map = world.getMap() instanceof IMap ? (IMap) world.getMap() : null;
        if (map != null) {
            int width = world.getMap().getWidth();
            int height = world.getMap().getHeight();
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    if (map.isTileObstacle(x, y)) {
                        shapeRenderer.setColor(Color.BLACK);
                        shapeRenderer.rect(x, y, 1, 1);
                    }
                }
            }
        }
        shapeRenderer.rect(0,0, world.getMap().getWidth(), world.getMap().getHeight());
        shapeRenderer.end();
    }

    /**
     * Creates a sprite from an entity
     *
     * @param entity the entity to create a sprite from
     * @return the sprite
     */
    private Sprite createSprite(Entity entity) {
        Texture texture = new Texture(entity.getTexturePath());
        Sprite sprite = new Sprite(texture);

        sprite.setSize(entity.getWidth(), entity.getHeight());
        sprite.setOriginCenter(); // set origin to the center of the sprite based on the size

        return sprite;
    }

    /**
     * Updates the sprites position and rotation of an entity
     *
     * @param entity the entity to update the sprite of
     */
    private void updateSprite(Entity entity) {
        Sprite sprite = entitySprites.get(entity);
        Coordinates coords = entity.getCoordinates();

        sprite.setPosition(coords.getX(), coords.getY());
        sprite.setRotation(entity.getRotation());
    }

    /**
     * Checks if keys are pressed and updates the keys in gameData
     */
    private void updateKeys() {
        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            gameData.getKeys().setKey(gameData.getKeys().getLEFT(), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            gameData.getKeys().setKey(gameData.getKeys().getRIGHT(), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
            gameData.getKeys().setKey(gameData.getKeys().getUP(), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            gameData.getKeys().setKey(gameData.getKeys().getDOWN(), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            gameData.getKeys().setKey(gameData.getKeys().getSPACE(), true);
        }


        if (!Gdx.input.isKeyPressed(Input.Keys.A) && !Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            gameData.getKeys().setKey(gameData.getKeys().getLEFT(), false);
        }
        if (!Gdx.input.isKeyPressed(Input.Keys.D) && !Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            gameData.getKeys().setKey(gameData.getKeys().getRIGHT(), false);
        }
        if (!Gdx.input.isKeyPressed(Input.Keys.W) && !Gdx.input.isKeyPressed(Input.Keys.UP)) {
            gameData.getKeys().setKey(gameData.getKeys().getUP(), false);
        }
        if (!Gdx.input.isKeyPressed(Input.Keys.S) && !Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            gameData.getKeys().setKey(gameData.getKeys().getDOWN(), false);
        }
        if (!Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            gameData.getKeys().setKey(gameData.getKeys().getSPACE(), false);
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
     * Get all plugins
     *
     * @return a collection of all instances of IGamePluginService
     */
    private Collection<? extends IGamePluginService> getPluginServices() {
        return ServiceLoader.load(IGamePluginService.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }

    /**
     * Get all entity processing services
     *
     * @return a collection of all instances of IEntityProcessingService
     */
    private Collection<? extends IEntityProcessingService> getEntityProcessingServices() {
        return ServiceLoader.load(IEntityProcessingService.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }

    private void createWorld() {
        this.world = World.getInstance();
        ServiceLoader.load(IMapGenerator.class).stream().findFirst().ifPresent(provider -> {
            IMapGenerator mapGen = provider.get();
            mapGen.generateMap(world);
        });


    }
}
