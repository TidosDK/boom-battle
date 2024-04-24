package dk.sdu.mmmi.main.CustomStages;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.sdu.mmmi.common.data.entity.Coordinates;
import dk.sdu.mmmi.common.data.entity.Entity;


import java.nio.file.Path;
import java.util.*;

/**
 * The CustomStage is a class made for organizint different aspects of the game in seperate containers.
 * The CustomStage class should be used like LibGDX stages but with entities and other game objects rather than Actors
 */
public class CustomStage implements ICustomStage {
    private final float xOffset;
    private final float yOffset;
    private final float width;
    private final float height;
    private Collection<Entity> entities;
    private Path backgroundTexturePath;
    private HashMap<Entity, Sprite> entitySprites;

    public CustomStage(float xOffset, float yOffset, float width, float height) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.width = width;
        this.height = height;
        this.entities = new ArrayList<>();
    }

    public CustomStage(Path backgorundTexturePath, float xOffset, float yOffset, float width, float height) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.width = width;
        this.height = height;
        this.entities = new ArrayList<>();
        this.backgroundTexturePath = backgorundTexturePath;
    }

    public CustomStage(float xOffset, float yOffset, float width, float height, Collection<Entity> entities) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.width = width;
        this.height = height;
        this.entities = entities;
    }

    @Override
    public float getXOffset() {
        return xOffset;
    }

    @Override
    public float getYOffset() {
        return yOffset;
    }

    @Override
    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    @Override
    public void removeEntity(Entity entity) {
        entities.remove(entity);
    }

    @Override
    public void clearEntities() {

    }

    @Override
    public List<Entity> getEntities() {
        return (List<Entity>) entities;
    }

    public void setEntities(Collection<Entity> entities) {
        this.entities.clear();
        this.entities.addAll(entities);
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
     * Sorts entities based on their texture layer.
     *
     * @return a new list with the input entities sorted by texture layer
     */
    private List<Entity> sortEntitiesByTextureLayer() {
        List<Entity> sortedEntities = new ArrayList<>(this.entities);
        Comparator<Entity> textureLayerComparator = Comparator.comparingInt(Entity::getTextureLayer); // Comparator for sorting entities based on texture layer value
        Collections.sort(sortedEntities, textureLayerComparator); // Sort entities with the comparator
        return sortedEntities;
    }

    public void drawStage(SpriteBatch batch) {
        // Sort entities based on texture layer before rendering
        List<Entity> sortedEntities = sortEntitiesByTextureLayer();

        // Create sprites for new entities
        for (Entity entity : sortedEntities) {
            if (!entitySprites.containsKey(entity)) {
                Sprite sprite = createSprite(entity);
                entitySprites.put(entity, sprite);
            }
        }

        // Update all entity sprites
        for (Entity entity : sortedEntities) {
            Sprite sprite = entitySprites.get(entity);
            Coordinates coords = entity.getCoordinates();

            sprite.getTexture().dispose();
            sprite.setTexture(new Texture(entity.getTexturePath().toString()));

            sprite.setPosition(coords.getX() + this.xOffset, coords.getY() + this.yOffset);
        }

        // Render all entities
        batch.begin();
        for (Entity entity : sortedEntities) {
            entitySprites.get(entity).draw(batch);
        }
        batch.end();
    }


    public void setEntitySprites(HashMap<Entity, Sprite> entitySprites) {
        this.entitySprites = entitySprites;
    }
}
