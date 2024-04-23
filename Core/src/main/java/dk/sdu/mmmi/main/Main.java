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
import dk.sdu.mmmi.common.data.entity.Coordinates;
import dk.sdu.mmmi.common.data.entity.Entity;
import dk.sdu.mmmi.common.data.gameproperties.GameData;
import dk.sdu.mmmi.common.data.world.World;
import dk.sdu.mmmi.common.services.IEntityProcessingService;
import dk.sdu.mmmi.common.services.IGamePluginService;
import dk.sdu.mmmi.common.services.map.IMap;
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

    @Override
    public void create() {
        // Setup of game
        gameData = GameData.getInstance();
        world = World.getInstance();
        camera = new OrthographicCamera();
        float width = 19f;
        float height = 19f * (Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth());
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

        // Sort entities based on texture layer before rendering
        List<Entity> sortedEntities = sortEntitiesByTextureLayer(world.getEntities());

        // Create sprites for new entities
        for (Entity entity : sortedEntities) {
            if (!entitySprites.containsKey(entity)) {
                Sprite sprite = createSprite(entity);
                entitySprites.put(entity, sprite);
            }
        }

        // Update all entity sprites
        for (Entity entity : sortedEntities) {
            updateSprite(entity);
        }

        // Draw map
        drawMap();

        // Render all entities
        batch.begin();
        for (Entity entity : sortedEntities) {
            entitySprites.get(entity).draw(batch);
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
                        shapeRenderer.rect(x * gameData.getScaler(), y * gameData.getScaler(), gameData.getScaler(), gameData.getScaler());
                    }
                }
            }
        }
        shapeRenderer.rect(0, 0, world.getMap().getWidth() * gameData.getScaler(), world.getMap().getHeight() * gameData.getScaler());
        shapeRenderer.end();
    }

    /**
     * Sorts entities based on their texture layer.
     *
     * @param entities list of entities to sort
     * @return a new list with the input entities sorted by texture layer
     */
    private List<Entity> sortEntitiesByTextureLayer(List<Entity> entities) {
        List<Entity> sortedEntities = new ArrayList<>(entities);
        Comparator<Entity> textureLayerComparator = Comparator.comparingInt(Entity::getTextureLayer); // Comparator for sorting entities based on texture layer value
        Collections.sort(sortedEntities, textureLayerComparator); // Sort entities with the comparator
        return sortedEntities;
    }

    /**
     * Creates a sprite from an entity.
     *
     * @param entity the entity to create a sprite from
     * @return the sprite
     */
    private Sprite createSprite(Entity entity) {
        Texture texture = new Texture(entity.getTexturePath().toString());
        Sprite sprite = new Sprite(texture);

        sprite.setSize(entity.getWidth(), entity.getHeight());
        sprite.setOriginCenter(); // set origin to the center of the sprite based on the size

        return sprite;
    }

    /**
     * Updates the sprites position and rotation of an entity.
     *
     * @param entity the entity to update the sprite of
     */
    private void updateSprite(Entity entity) {
        Sprite sprite = entitySprites.get(entity);
        Coordinates coords = entity.getCoordinates();

        sprite.getTexture().dispose();
        sprite.setTexture(new Texture(entity.getTexturePath().toString()));

        sprite.setPosition(coords.getX(), coords.getY());
//        sprite.setRotation(entity.getRotation());
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
        ServiceLoader.load(IMapGenerator.class).stream().findFirst().ifPresent(provider -> {
            IMapGenerator mapGen = provider.get();
            mapGen.generateMap(world);
        });
    }
}
