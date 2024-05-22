import dk.sdu.mmmi.bomb.BombPlugin;
import dk.sdu.mmmi.bomb.BombControlSystem;
import dk.sdu.mmmi.common.services.IEntityProcessingService;
import dk.sdu.mmmi.common.services.IGamePluginService;
import dk.sdu.mmmi.common.services.textureanimator.ITextureAnimatorController;
import dk.sdu.mmmi.common.weapon.IWeaponController;

module Bomb {
    uses ITextureAnimatorController;
    requires Common;
    requires CommonWeapon;
    provides IGamePluginService with BombPlugin;
    provides IWeaponController with BombControlSystem;
    provides IEntityProcessingService with BombControlSystem;
}