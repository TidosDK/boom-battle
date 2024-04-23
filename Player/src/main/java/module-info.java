import dk.sdu.mmmi.common.services.IEntityProcessingService;
import dk.sdu.mmmi.common.services.IGamePluginService;
import dk.sdu.mmmi.common.services.map.IMap;
import dk.sdu.mmmi.common.services.textureanimator.ITextureAnimatorController;
import dk.sdu.mmmi.common.services.weapon.IWeaponController;
import dk.sdu.mmmi.common.services.weapon.IWeapon;

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