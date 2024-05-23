package dk.sdu.mmmi.common.ai;

import dk.sdu.mmmi.common.data.world.Map;

/**
 * This interface is used to find the optimal placement for a bomb.
 */
public interface IOptimalBombPlacement {
    Node optimalBombPlacement(Node currentNode, Node goalNode, Map map);
}
