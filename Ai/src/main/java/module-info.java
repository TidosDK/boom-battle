import dk.sdu.mmmi.common.services.ai.IPathFinding;
import dk.sdu.mmmi.ai.PathFinding;
import dk.sdu.mmmi.common.services.ai.IOptimalBombPlacement;
import dk.sdu.mmmi.ai.BombPlacing;

module AI {
    requires Common;
    requires CommonObstacle;
    exports dk.sdu.mmmi.ai;
    provides IPathFinding with PathFinding;
    provides IOptimalBombPlacement with BombPlacing;
}