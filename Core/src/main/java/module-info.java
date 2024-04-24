import dk.sdu.mmmi.common.services.IGamePluginService;
import dk.sdu.mmmi.common.services.IEntityProcessingService;

module Core {
    requires Common;
    requires com.badlogic.gdx;
    uses IGamePluginService;
    uses IEntityProcessingService;
    uses dk.sdu.mmmi.common.services.map.IMapGenerator;
}
