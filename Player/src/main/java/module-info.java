import dk.sdu.mmmi.common.services.IEntityProcessingService;
import dk.sdu.mmmi.common.services.IGamePluginService;

module Player {
    uses dk.sdu.mmmi.common.services.Map.IMap;
    requires Common;
    provides IGamePluginService with dk.sdu.mmmi.player.PlayerPlugin;
    provides IEntityProcessingService with dk.sdu.mmmi.player.PlayerControlSystem;
}