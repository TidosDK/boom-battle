package dk.sdu.mmmi.common.services.Weapon;

import dk.sdu.mmmi.common.data.Entity.Entity;
import dk.sdu.mmmi.common.data.Data.GameData;

public interface IWeaponController {
    Entity createWeapon(Entity weaponPlacer, GameData gameData);
}
