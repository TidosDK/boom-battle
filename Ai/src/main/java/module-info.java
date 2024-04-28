import dk.sdu.mmmi.common.services.ai.IPathFinding;
import dk.sdu.mmmi.ai.PathFinding;
module AI {
    requires Common;
    exports dk.sdu.mmmi.ai;
    provides IPathFinding with PathFinding;

}