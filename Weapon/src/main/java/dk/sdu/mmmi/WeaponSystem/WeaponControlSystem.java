package dk.sdu.mmmi.WeaponSystem;

import dk.sdu.mmmi.common.data.Entity;
import dk.sdu.mmmi.common.data.GameData;
import dk.sdu.mmmi.common.data.World;
import dk.sdu.mmmi.common.services.IEntityProcessingService;
import dk.sdu.mmmi.common.services.IWeapon;

import java.util.ArrayList;
import java.util.List;

public class WeaponControlSystem implements IEntityProcessingService, IWeapon {
    @Override
    public synchronized void process(World world, GameData gameData) {
        for (Entity entity : world.getEntities(Weapon.class)) {
            Weapon weapon = (Weapon) entity;
            if (weapon.calculateTimeTillExplosion(gameData) <= 0) {
                System.out.println("EXPLOSION");
                // Calculate blast explosion
                world.removeEntity(weapon);
            }
        }
    }

    @Override
    public Entity createWeapon(Entity weaponPlacer, GameData gameData) {
        Weapon weapon = new Weapon("Weapon/src/main/resources/weapon.png", 2f, 2f);
        weapon.setX(weaponPlacer.getX());
        weapon.setY(weaponPlacer.getY());
        weapon.setDamagePoints(2);
        weapon.setTimeSincePlacement(gameData.getDeltaTime());
        weapon.setTimeTillExplosionInSeconds(2f);
        return weapon;
    }
}
