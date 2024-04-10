package dk.sdu.mmmi.common.data.World;

import dk.sdu.mmmi.common.data.Entity.Entity;

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
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public <child extends Entity> List<Entity> getEntities(Class<child>... entityChildClasses) {
        List<Entity> children = new ArrayList<>();

        for (Entity entity : getEntities()) {
            for (Class<child> childClass : entityChildClasses) {
                if (childClass.equals(entity.getClass())) {
                    children.add(entity);
                }
            }
        }
        return children;
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public void removeEntity(Entity entity) {
        entities.remove(entity);
    }
}
