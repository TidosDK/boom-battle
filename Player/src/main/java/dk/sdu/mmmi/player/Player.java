package dk.sdu.mmmi.player;

import dk.sdu.mmmi.common.data.Entity.Entity;
import dk.sdu.mmmi.common.enums.animations;
import dk.sdu.mmmi.common.services.TextureAnimator.ITextureAnimator;
import dk.sdu.mmmi.common.services.Entity.Weapon.IWeapon;

import java.util.*;

public class Player extends Entity {
    private List<IWeapon> weapons;
    private Map<animations, ITextureAnimator> activeTexturePaths;

    public Player(String texturePath, float width, float height) {
        super(texturePath, width, height);
        this.weapons = new ArrayList<>(); // TODO: Better data structure.
        this.activeTexturePaths = new HashMap<>(); // TODO: Better data structure.
    }

    public List<IWeapon> getWeapons() {
        return weapons;
    }

    public void removeWeapon(IWeapon weapon) {
        this.weapons.remove(weapon);
    }

    public String getActiveTexturePaths(animations key) {
        if (activeTexturePaths.get(key) == null) {
            return getTexturePath();
        }
        return activeTexturePaths.get(key).getCurrentImagePath();
    }

    public void addAnimator(animations key, ITextureAnimator animator) {
        this.activeTexturePaths.put(key, animator);
    }
}
