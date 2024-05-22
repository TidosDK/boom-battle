import dk.sdu.mmmi.common.services.IEntityProcessingService;
import dk.sdu.mmmi.common.services.IGamePluginService;
import dk.sdu.mmmi.common.services.entityproperties.IActor;
import dk.sdu.mmmi.common.services.map.IMap;
import dk.sdu.mmmi.common.services.textureanimator.ITextureAnimatorController;
import dk.sdu.mmmi.common.services.weapon.IWeaponController;
import dk.sdu.mmmi.common.services.weapon.IWeapon;
import dk.sdu.mmmi.player.PlayerControlSystem;
import dk.sdu.mmmi.player.PlayerPlugin;

module Player {
    uses IMap;
    uses IWeapon;
    uses IWeaponController;
    uses ITextureAnimatorController;
    requires Common;
    provides IGamePluginService with PlayerPlugin;
    provides IEntityProcessingService with PlayerControlSystem;
    provides IActor with PlayerControlSystem;

    // For unittest only:
    uses IGamePluginService;
}