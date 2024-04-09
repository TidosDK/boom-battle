import dk.sdu.mmmi.WeaponSystem.WeaponPlugin;
import dk.sdu.mmmi.WeaponSystem.WeaponControlSystem;
import dk.sdu.mmmi.common.services.IEntityProcessingService;
import dk.sdu.mmmi.common.services.IGamePluginService;
import dk.sdu.mmmi.common.services.IWeaponProcessing;

module Weapon {
    requires Common;
    provides IGamePluginService with WeaponPlugin;
    provides IWeaponProcessing with WeaponControlSystem;
    provides IEntityProcessingService with WeaponControlSystem;
}