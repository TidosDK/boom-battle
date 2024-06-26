package dk.sdu.mmmi.common.data.world;

import dk.sdu.mmmi.common.data.entity.Entity;

import java.util.ArrayList;
import java.util.List;

/**
 * The World class is a container for the map and entities in the game.
 * The World class is used to pass the map and entities between the game and the plugins.
 */
public class World {
    private static World instance;
    private Map map;
    private ArrayList<Entity> entities;

    private World() {
        this.entities = new ArrayList<>();
    }

    public static World getInstance() {
        if (instance == null) {
            instance = new World();
        }
        return instance;
    }

    public Map getMap() {
        return this.map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public ArrayList<Entity> getEntities() {
        return this.entities;
    }

    public List<Entity> getEntities(Class<? extends Entity>... entityClasses) {
        List<Entity> entityList = new ArrayList<>();

        for (Entity entity : this.getEntities()) {
            for (Class<? extends Entity> entityClass : entityClasses) {
                if (entityClass.isInstance(entity)) {
                    entityList.add(entity);
                }
            }
        }
        return entityList;
    }

    public void addEntity(Entity entity) {
        this.entities.add(entity);
    }

    public void removeEntity(Entity entity) {
        this.entities.remove(entity);
    }

    public void generateDefaultMap() {
        DefaultMapGenerator.generateMap(this);
    }
}
