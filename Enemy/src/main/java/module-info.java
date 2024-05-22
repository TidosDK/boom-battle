import dk.sdu.mmmi.common.services.IEntityProcessingService;
import dk.sdu.mmmi.common.services.IGamePluginService;
import dk.sdu.mmmi.common.services.ai.IOptimalBombPlacement;
import dk.sdu.mmmi.common.services.map.IMap;
import dk.sdu.mmmi.common.services.textureanimator.ITextureAnimatorController;
import dk.sdu.mmmi.common.weapon.IWeapon;
import dk.sdu.mmmi.common.weapon.IWeaponController;
import dk.sdu.mmmi.enemy.EnemyControlSystem;
import dk.sdu.mmmi.enemy.EnemyPlugin;
import dk.sdu.mmmi.common.services.ai.IPathFinding;

module Enemy {
    exports dk.sdu.mmmi.enemy;
    uses IMap;
    uses IWeapon;
    uses IWeaponController;
    uses ITextureAnimatorController;
    uses IPathFinding;
    uses IOptimalBombPlacement;
    requires Common;
    requires CommonWeapon;
    provides IGamePluginService with EnemyPlugin;
    provides IEntityProcessingService with EnemyControlSystem;
}