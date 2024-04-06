import dk.sdu.mmmi.common.services.IWeapon;
import dk.sdu.mmmi.common.services.IEntityProcessingService;
import dk.sdu.mmmi.common.services.IGamePluginService;

module Weapon {
    requires Common;
    provides IGamePluginService with dk.sdu.mmmi.WeaponSystem.WeaponPlugin;
    provides IWeapon with dk.sdu.mmmi.WeaponSystem.WeaponConstrolSystem;
    provides IEntityProcessingService with dk.sdu.mmmi.WeaponSystem.WeaponConstrolSystem;
}