import dk.sdu.mmmi.common.services.Entity.IEntityProcessingService;
import dk.sdu.mmmi.common.services.IGamePluginService;
import dk.sdu.mmmi.common.services.Map.IMap;
import dk.sdu.mmmi.common.services.TextureAnimator.ITextureAnimatorController;
import dk.sdu.mmmi.common.services.Entity.Weapon.IWeaponController;
import dk.sdu.mmmi.common.services.Entity.Weapon.IWeapon;

module Player {
    uses IMap;
    uses IWeapon;
    uses IWeaponController;
    uses ITextureAnimatorController;
    requires Common;
    provides IGamePluginService with dk.sdu.mmmi.player.PlayerPlugin;
    provides IEntityProcessingService with dk.sdu.mmmi.player.PlayerControlSystem;

    // For unittest only:
    uses IGamePluginService;
}