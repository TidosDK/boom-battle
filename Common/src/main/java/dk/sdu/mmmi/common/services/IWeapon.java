package dk.sdu.mmmi.common.services;

import dk.sdu.mmmi.common.data.Entity;
import dk.sdu.mmmi.common.data.GameData;

public interface IWeapon {
    // TODO: Implement the following methods
//    int getDamage();
//    void setDamage(int damage);
    Entity createWeapon(Entity weaponPlacer, GameData gameData);
    // TODO: Move the above method to the IWeaponProcessing interface
}
