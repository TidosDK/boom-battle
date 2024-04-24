module AI {
    requires Common;
    requires BasicMap;
    provides dk.sdu.mmmi.common.services.AI.IPathFinding with dk.sdu.mmmi.AI.PathFinding;

}