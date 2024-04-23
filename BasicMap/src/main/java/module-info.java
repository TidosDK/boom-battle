import dk.sdu.mmmi.common.services.entityproperties.ICollidable;

module BasicMap {
    uses ICollidable;
    requires Common;
    provides dk.sdu.mmmi.common.services.map.IMap with dk.sdu.mmmi.basicmapsystem.BasicMap;
    provides dk.sdu.mmmi.common.services.map.IMapProcessingService with dk.sdu.mmmi.basicmapsystem.BasicMap;
    provides dk.sdu.mmmi.common.services.map.IMapGenerator with dk.sdu.mmmi.basicmapsystem.BasicMapGenerator;
}