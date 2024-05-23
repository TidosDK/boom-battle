import dk.sdu.mmmi.common.ai.IPathFinding;
import dk.sdu.mmmi.ai.PathFinding;
import dk.sdu.mmmi.common.ai.IOptimalBombPlacement;
import dk.sdu.mmmi.ai.BombPlacing;

module AI {
    requires Common;
    requires CommonObstacle;
    requires CommonAi;
    exports dk.sdu.mmmi.ai;
    provides IPathFinding with PathFinding;
    provides IOptimalBombPlacement with BombPlacing;
}