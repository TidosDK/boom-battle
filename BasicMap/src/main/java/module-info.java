import dk.sdu.mmmi.basicmapsystem.BasicMap;
import dk.sdu.mmmi.basicmapsystem.BasicMapGenerator;
import dk.sdu.mmmi.basicmapsystem.BasicMapPlugin;
import dk.sdu.mmmi.common.services.IGamePluginService;
import dk.sdu.mmmi.common.services.entityproperties.ICollidable;
import dk.sdu.mmmi.common.services.map.IMap;
import dk.sdu.mmmi.common.services.map.IMapGenerator;
import dk.sdu.mmmi.common.services.map.IMapProcessingService;

module BasicMap {
    uses ICollidable;
    uses dk.sdu.mmmi.common.services.obstacle.nondestructible.INonDestructibleObstacleController;
    uses dk.sdu.mmmi.common.services.obstacle.destructible.IDestructibleObstacleController;
    requires Common;
    provides IGamePluginService with BasicMapPlugin;
    provides IMap with BasicMap;
    provides IMapProcessingService with BasicMap;
    provides IMapGenerator with BasicMapGenerator;
}