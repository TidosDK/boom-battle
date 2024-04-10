import dk.sdu.mmmi.WeaponSystem.WeaponPlugin;
import dk.sdu.mmmi.WeaponSystem.WeaponControlSystem;
import dk.sdu.mmmi.common.services.Entity.IEntityProcessingService;
import dk.sdu.mmmi.common.services.IGamePluginService;
import dk.sdu.mmmi.common.services.TextureAnimator.ITextureAnimatorController;
import dk.sdu.mmmi.common.services.Entity.Weapon.IWeaponController;

module Weapon {
    uses ITextureAnimatorController;
    requires Common;
    provides IGamePluginService with WeaponPlugin;
    provides IWeaponController with WeaponControlSystem;
    provides IEntityProcessingService with WeaponControlSystem;
}