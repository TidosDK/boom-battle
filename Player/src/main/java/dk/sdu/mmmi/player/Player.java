package dk.sdu.mmmi.player;

import dk.sdu.mmmi.common.data.Entity;
import dk.sdu.mmmi.common.services.IWeapon;

import java.util.ArrayList;
import java.util.List;

public class Player extends Entity {
    private List<IWeapon> weapons;

    public Player(String texturePath, float width, float height) {
        super(texturePath, width, height);
        weapons = new ArrayList<>(); // TODO: Better data structure
    }

    public List<IWeapon> getWeapons() {
        return weapons;
    }

    public void removeWeapon(IWeapon weapon) {
        this.weapons.remove(weapon);
    }
}
