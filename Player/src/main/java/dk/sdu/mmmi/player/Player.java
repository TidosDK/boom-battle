package dk.sdu.mmmi.player;

import dk.sdu.mmmi.common.data.Entity;

import java.util.ArrayList;
import java.util.List;

public class Player extends Entity {
    private List<Entity> weapons;
    public Player(String texturePath, float width, float height) {
        super(texturePath, width, height);
        weapons = new ArrayList<>();
    }

    public List<Entity> getWeapons() {
        return weapons;
    }

    public void removeWeapon(Entity weapon) {
        this.weapons.remove(weapon);
    }
}
