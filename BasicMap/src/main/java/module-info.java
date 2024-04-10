import dk.sdu.mmmi.common.services.Entity.ICollidable;

module BasicMap {
    uses ICollidable;
    requires Common;
    provides dk.sdu.mmmi.common.services.Map.IMap with dk.sdu.mmmi.BasicMapSystem.BasicMap;
    provides dk.sdu.mmmi.common.services.Map.IMapProcessingService with dk.sdu.mmmi.BasicMapSystem.BasicMap;
    provides dk.sdu.mmmi.common.services.Map.IMapGenerator with dk.sdu.mmmi.BasicMapSystem.BasicMapGenerator;
}