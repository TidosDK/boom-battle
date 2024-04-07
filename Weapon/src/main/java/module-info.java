import dk.sdu.mmmi.WeaponSystem.WeaponControlSystem;
import dk.sdu.mmmi.common.services.IWeapon;
import dk.sdu.mmmi.common.services.IEntityProcessingService;
import dk.sdu.mmmi.common.services.IGamePluginService;

module Weapon {
    requires Common;
    provides IGamePluginService with dk.sdu.mmmi.WeaponSystem.WeaponPlugin;
    provides IWeapon with WeaponControlSystem;
    provides IEntityProcessingService with WeaponControlSystem;
}