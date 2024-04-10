package dk.sdu.mmmi.player;

import dk.sdu.mmmi.common.data.Entity.Entity;
import dk.sdu.mmmi.common.enums.animations;
import dk.sdu.mmmi.common.services.TextureAnimator.ITextureAnimator;
import dk.sdu.mmmi.common.services.Entity.Weapon.IWeapon;

import java.util.*;

public class Player extends Entity {
    private List<IWeapon> weapons;
    private Map<animations, ITextureAnimator> animator;

    public Player(String texturePath, float width, float height) {
        super(texturePath, width, height);
        this.weapons = new ArrayList<>(); // TODO: Better data structure.
        this.animator = new HashMap<>(); // TODO: Better data structure.
    }

    public List<IWeapon> getWeapons() {
        return weapons;
    }

    public void removeWeapon(IWeapon weapon) {
        this.weapons.remove(weapon);
    }

    public String getAnimator(animations key) {
        if (animator.get(key) == null) {
            return getTexturePath();
        }
        return animator.get(key).getCurrentImagePath();
    }

    public void addAnimator(animations key, ITextureAnimator animator) {
        this.animator.put(key, animator);
    }
}
