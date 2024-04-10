package dk.sdu.mmmi.player;

import dk.sdu.mmmi.common.data.Properties.GameData;
import dk.sdu.mmmi.common.data.Entity.Entity;
import dk.sdu.mmmi.common.services.TextureAnimator.ITextureAnimator;
import dk.sdu.mmmi.common.services.Entity.Weapon.IWeapon;
import dk.sdu.mmmi.common.services.TextureAnimator.ITextureAnimatorController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class Player extends Entity {
    private List<IWeapon> weapons;
    private ITextureAnimator walkLeftAnimator;
    private ITextureAnimator walkRightAnimator;
    private ITextureAnimator walkUpAnimator;
    private ITextureAnimator walkDownAnimator;

    public Player(GameData gameData, String texturePath, float width, float height) {
        super(texturePath, width, height);
        weapons = new ArrayList<>(); // TODO: Better data structure

        if (!getITextureAnimatorController().isEmpty()) {
            ITextureAnimatorController animatorController = getITextureAnimatorController().stream().findFirst().get();

            if (animatorController != null) {
                walkLeftAnimator = animatorController.createTextureAnimator(gameData, "Player/src/main/resources/left", 0, 6, 20f);
                walkRightAnimator = animatorController.createTextureAnimator(gameData, "Player/src/main/resources/right", 0, 6, 20f);
                walkUpAnimator = animatorController.createTextureAnimator(gameData, "Player/src/main/resources/up", 0, 6, 20f);
                walkDownAnimator = animatorController.createTextureAnimator(gameData, "Player/src/main/resources/down", 0, 6, 20f);
            }
        }

    }

    public List<IWeapon> getWeapons() {
        return weapons;
    }

    public void removeWeapon(IWeapon weapon) {
        this.weapons.remove(weapon);
    }

    public String getCurrentWalkDownAnimatorPath() {
        if (walkDownAnimator == null) {
            return getTexturePath();
        } else {
            return walkDownAnimator.getCurrentImagePath();
        }
    }

    public String getCurrentWalkUpAnimatorPath() {
        if (walkUpAnimator == null) {
            return getTexturePath();
        } else {
            return walkUpAnimator.getCurrentImagePath();
        }
    }

    public String getCurrentWalkLeftAnimatorPath() {
        if (walkLeftAnimator == null) {
            return getTexturePath();
        } else {
            return walkLeftAnimator.getCurrentImagePath();
        }
    }

    public String getCurrentWalkRightAnimatorPath() {
        if (walkRightAnimator == null) {
            return getTexturePath();
        } else {
            return walkRightAnimator.getCurrentImagePath();
        }
    }

    private Collection<? extends ITextureAnimatorController> getITextureAnimatorController() {
        return ServiceLoader.load(ITextureAnimatorController.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }

}
