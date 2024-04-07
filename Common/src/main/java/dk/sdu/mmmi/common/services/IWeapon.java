package dk.sdu.mmmi.common.services;

import dk.sdu.mmmi.common.data.Entity;
import dk.sdu.mmmi.common.data.GameData;

public interface IWeapon {
    Entity createWeapon(Entity weaponPlacer, GameData gameData);
}
