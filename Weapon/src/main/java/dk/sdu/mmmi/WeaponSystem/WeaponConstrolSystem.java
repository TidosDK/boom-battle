package dk.sdu.mmmi.WeaponSystem;

import dk.sdu.mmmi.common.data.Entity;
import dk.sdu.mmmi.common.data.GameData;
import dk.sdu.mmmi.common.data.World;
import dk.sdu.mmmi.common.services.IEntityProcessingService;
import dk.sdu.mmmi.common.services.IWeapon;

public class WeaponConstrolSystem implements IEntityProcessingService, IWeapon {
    private int damage = 2;

    @Override
    public void process(World world, GameData gameData) {
//        for (Entity weapon : world.getEntities(Weapon.class)) {
//            // Process...
//        }
    }

    @Override
    public int getDamage() {
        return this.damage;
    }

    @Override
    public void setDamage(int damage) {
        this.damage = damage;
    }

    @Override
    public Entity createWeapon(Entity weaponPlacer, GameData gameData) {
        Entity weapon = new Weapon("Weapon/src/main/resources/weapon.png", 2f, 2f);
        weapon.setX(weaponPlacer.getX());
        weapon.setY(weaponPlacer.getY());
        setDamage(2);
        return weapon;
    }
}
