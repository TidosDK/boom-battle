package dk.sdu.mmmi.enemy;


import dk.sdu.mmmi.common.data.entity.Entity;
import dk.sdu.mmmi.common.services.entityproperties.IActor;
import dk.sdu.mmmi.common.services.entityproperties.IDamageable;
import dk.sdu.mmmi.common.textureanimator.IAnimatable;
import dk.sdu.mmmi.common.textureanimator.ITextureAnimator;
import dk.sdu.mmmi.common.weapon.IWeapon;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class is used to create an enemy entity.
 * an enemy entity is an entity that can be damaged and can animate.
 */
public class Enemy extends Entity implements IDamageable, IAnimatable, IActor {
    private List<IWeapon> weapons;
    private HashMap<Integer, ITextureAnimator> animators;
    private int lifePoints = 1;

    public Enemy(Path texturePath, float width, float height) {
        super(texturePath, width, height);
        this.weapons = new ArrayList<>();
        this.animators = new HashMap<>();
    }

    public List<IWeapon> getWeapons() {
        return weapons;
    }

    public void removeWeapon(IWeapon weapon) {
        this.weapons.remove(weapon);
    }


    @Override
    public void removeLifepoints(int amount) {
        lifePoints -= amount;
    }

    @Override
    public int getLifepoints() {
        return lifePoints;
    }

    @Override
    public void setLifepoints(int lifepoints) {
        this.lifePoints = lifepoints;
    }

    @Override
    public Path getActiveTexturePath(Integer key) {
        if (animators.get(key) == null) {
            return getTexturePath();
        }
        return animators.get(key).getCurrentTexturePath();
    }

    @Override
    public void addAnimator(Integer key, ITextureAnimator animator) {
        this.animators.put(key, animator);
    }

    @Override
    public HashMap<Integer, ITextureAnimator> getAnimators() {
        return animators;
    }

    @Override
    public void setAnimators(HashMap<Integer, ITextureAnimator> animators) {
        this.animators = animators;
    }
}
