import dk.sdu.mmmi.common.services.IGamePluginService;
import dk.sdu.mmmi.enemy.EnemyPlugin;

module Enemy {
    requires Common;
    provides IGamePluginService with EnemyPlugin;
}