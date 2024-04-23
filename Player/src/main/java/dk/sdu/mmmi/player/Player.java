package dk.sdu.mmmi.player;

import dk.sdu.mmmi.common.data.entity.Entity;
import dk.sdu.mmmi.common.services.entityproperties.IDamageable;
import dk.sdu.mmmi.common.services.textureanimator.IAnimatable;
import dk.sdu.mmmi.common.services.textureanimator.ITextureAnimator;
import dk.sdu.mmmi.common.services.weapon.IWeapon;

import java.nio.file.Path;
import java.util.*;

public class Player extends Entity implements IDamageable, IAnimatable {
    private List<IWeapon> weapons;
    private HashMap<Integer, ITextureAnimator> animators;
    private int lifePoints = 1;

    public Player(Path texturePath, float width, float height) {
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
