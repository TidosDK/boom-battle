import dk.sdu.mmmi.WeaponSystem.BombPlugin;
import dk.sdu.mmmi.WeaponSystem.BombControlSystem;
import dk.sdu.mmmi.common.services.IEntityProcessingService;
import dk.sdu.mmmi.common.services.IGamePluginService;
import dk.sdu.mmmi.common.services.textureanimator.ITextureAnimatorController;
import dk.sdu.mmmi.common.services.weapon.IWeaponController;

module Bomb {
    uses ITextureAnimatorController;
    requires Common;
    provides IGamePluginService with BombPlugin;
    provides IWeaponController with BombControlSystem;
    provides IEntityProcessingService with BombControlSystem;
}