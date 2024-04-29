package dk.sdu.mmmi.common.services.ai;

import dk.sdu.mmmi.common.data.ai.Node;

/**
 * Interface to be used for optimal bomb placement.
 * This interface is used to find the optimal placement for a bomb
 */
public interface IOptimalBombPlacement {
    Node optimalBombPlacement(Node goalNode);
}
