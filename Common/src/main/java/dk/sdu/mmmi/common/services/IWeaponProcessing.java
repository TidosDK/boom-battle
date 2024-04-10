package dk.sdu.mmmi.common.services;

import dk.sdu.mmmi.common.data.Entity.Entity;
import dk.sdu.mmmi.common.data.Data.GameData;

public interface IWeaponProcessing {
    Entity createWeapon(Entity weaponPlacer, GameData gameData);
}
