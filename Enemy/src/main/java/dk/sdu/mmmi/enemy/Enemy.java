package dk.sdu.mmmi.enemy;


import dk.sdu.mmmi.common.data.entity.Entity;
import dk.sdu.mmmi.common.services.textureanimator.ITextureAnimator;
import dk.sdu.mmmi.common.services.weapon.IWeapon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Enemy extends Entity {
    private List<IWeapon> weapons;
    private Map<animations, ITextureAnimator> animators;

    public Enemy(String texturePath, float width, float height) {
        super(texturePath, width, height);
        this.weapons = new ArrayList<>(); // TODO: Better data structure.
        this.animators = new HashMap<>(); // TODO: Better data structure.
    }

    public List<IWeapon> getWeapons() {
        return weapons;
    }

    public void removeWeapon(IWeapon weapon) {
        this.weapons.remove(weapon);
    }

    public String getActiveTexturePath(animations key) {
        if (animators.get(key) == null) {
            return getTexturePath();
        }
        return animators.get(key).getCurrentImagePath();
    }

    public void addAnimator(animations key, ITextureAnimator animator) {
        this.animators.put(key, animator);
    }
}
