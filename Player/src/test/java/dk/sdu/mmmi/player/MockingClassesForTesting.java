package dk.sdu.mmmi.player;

import dk.sdu.mmmi.common.data.entity.Entity;
import dk.sdu.mmmi.common.data.gameproperties.GameData;
import dk.sdu.mmmi.common.weapon.IWeapon;
import dk.sdu.mmmi.common.weapon.IWeaponController;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class MockWeapon extends Entity implements IWeapon {

    private int damagePoints = 1;

    public MockWeapon(Path texturePath, float width, float height) {
        super(texturePath, width, height);
    }

    @Override
    public int getDamagePoints() {
        return this.damagePoints;
    }

    @Override
    public void setDamagePoints(int damage) {
        this.damagePoints = damage;
    }
}

class MockWeaponController implements IWeaponController {
    @Override
    public Entity createWeapon(Entity weaponPlacer, GameData gameData) {
        return new MockWeapon(Paths.get("Player/src/main/resources/personLeft1.png"), 2f, 2f);
    }
}

// Defined to override ServiceLoader as weapon component is not available in when testing.
class TestPlayerControlSystem extends PlayerControlSystem {
    @Override
    protected Collection<? extends IWeaponController> getIWeaponProcessing() {
        List<IWeaponController> weaponControllers = new ArrayList<>();
        weaponControllers.add(new MockWeaponController());
        return weaponControllers;
    }
}
