import dk.sdu.mmmi.common.services.IEntityProcessingService;
import dk.sdu.mmmi.common.services.IGamePluginService;
import dk.sdu.mmmi.common.services.map.IMap;
import dk.sdu.mmmi.common.textureanimator.ITextureAnimatorController;
import dk.sdu.mmmi.common.weapon.IWeaponController;
import dk.sdu.mmmi.common.weapon.IWeapon;
import dk.sdu.mmmi.player.PlayerControlSystem;
import dk.sdu.mmmi.player.PlayerPlugin;

module Player {
    uses IMap;
    uses IWeapon;
    uses IWeaponController;
    uses ITextureAnimatorController;
    requires Common;
    requires CommonTextureAnimator;
    requires CommonWeapon;
    provides IGamePluginService with PlayerPlugin;
    provides IEntityProcessingService with PlayerControlSystem;

    // For unittest only:
    uses IGamePluginService;
}