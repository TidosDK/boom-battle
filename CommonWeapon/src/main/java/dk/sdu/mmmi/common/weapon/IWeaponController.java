package dk.sdu.mmmi.common.weapon;

import dk.sdu.mmmi.common.data.entity.Entity;
import dk.sdu.mmmi.common.data.gameproperties.GameData;

public interface IWeaponController {
    Entity createWeapon(Entity weaponPlacer, GameData gameData);
}
