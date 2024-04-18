package dk.sdu.mmmi.WeaponSystem;

import dk.sdu.mmmi.common.data.Entity.Entity;
import dk.sdu.mmmi.common.data.Properties.GameData;
import dk.sdu.mmmi.common.data.World.World;
import dk.sdu.mmmi.common.services.Entity.IEntityProcessingService;
import dk.sdu.mmmi.common.services.Entity.Weapon.IWeaponController;

public class WeaponControlSystem implements IEntityProcessingService, IWeaponController {

    @Override
    public synchronized void process(World world, GameData gameData) {
        for (Entity entity : world.getEntities(Weapon.class)) {
            Weapon weapon = (Weapon) entity;
            weapon.setTexturePath(weapon.getCurrentExplosionAnimatorPath());
            if (weapon.calculateTimeTillExplosion(gameData) <= 0) {
                System.out.println("EXPLOSION");
                // Calculate blast explosion
                world.removeEntity(weapon);
            }
        }
    }

    @Override
    public Entity createWeapon(Entity weaponPlacer, GameData gameData) {
        Weapon weapon = new Weapon(gameData, "Weapon/src/main/resources/planted/bomb-planted-2.png", gameData.getScaler(), gameData.getScaler());
        weapon.setX(weaponPlacer.getX());
        weapon.setY(weaponPlacer.getY());
        weapon.setDamagePoints(2);
        weapon.setTimeSincePlacement(gameData.getDeltaTime());
        weapon.setTimeTillExplosionInSeconds(2f);
        return weapon;
    }
}
