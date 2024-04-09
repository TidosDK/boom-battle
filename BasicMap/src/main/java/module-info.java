
module BasicMap {
    requires Common;
    provides dk.sdu.mmmi.common.services.Map.IMap with dk.sdu.mmmi.BasicMapSystem.BasicMap;
    provides dk.sdu.mmmi.common.services.Map.IMapProcessingService with dk.sdu.mmmi.BasicMapSystem.BasicMap;
    provides dk.sdu.mmmi.common.services.Map.IMapGenerator with dk.sdu.mmmi.BasicMapSystem.BasicMapGenerator;
}