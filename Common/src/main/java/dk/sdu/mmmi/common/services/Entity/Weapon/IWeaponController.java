package dk.sdu.mmmi.common.services.Entity.Weapon;

import dk.sdu.mmmi.common.data.Entity.Entity;
import dk.sdu.mmmi.common.data.Properties.GameData;

public interface IWeaponController {
    Entity createWeapon(Entity weaponPlacer, GameData gameData);
}
