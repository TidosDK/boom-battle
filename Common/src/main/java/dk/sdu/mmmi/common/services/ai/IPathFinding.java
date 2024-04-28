package dk.sdu.mmmi.common.services.ai;

import dk.sdu.mmmi.common.data.ai.Node;
import dk.sdu.mmmi.common.data.world.Map;

import java.util.ArrayList;
/**
 * Interface to be used for pathfinding
 * This interface is used to find a path from a start node to a goal node
 * @see dk.sdu.mmmi.common.data.ai.Node
 */
public interface IPathFinding {
    ArrayList<Node> pathFind(Node start, Node goal, Map map);
}
