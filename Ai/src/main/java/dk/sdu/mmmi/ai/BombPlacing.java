package dk.sdu.mmmi.ai;

import dk.sdu.mmmi.common.data.ai.Node;
import dk.sdu.mmmi.common.data.world.Map;
import dk.sdu.mmmi.common.services.ai.IOptimalBombPlacement;


/**
 * Implementation of the IOptimalBombPlacement interface.
 * Used to find the node where a bomb should be placed
 */
public class BombPlacing implements IOptimalBombPlacement {
    @Override
    public Node optimalBombPlacement(Node currentNode, Node goalNode, Map map) {

        if (!map.getMap()[currentNode.getX()][currentNode.getY()]) {
            Node bombPlacement = new Node(currentNode.getX(), currentNode.getY());
            if (isGoodBombPlacement(bombPlacement, goalNode)) {
                return bombPlacement;
            }
        }

        return null;
    }

    /**
     * Checks if the bomb placement is a match compared to the given criteria for a good bomb placement.
     *
     * @param goalNode      the node where the bomb should be placed
     * @param bombPlacement the node where the bomb is placed
     * @return boolean value indicating if the bomb placement is good or not
     */
    private boolean isGoodBombPlacement(Node bombPlacement, Node goalNode) {
        // needs further implementation to use bomb blast length
        if (bombPlacement == null || goalNode == null) {
            return false;
        }
        int minForX = Math.min(bombPlacement.getX(), goalNode.getX());
        int maxForX = Math.max(bombPlacement.getX(), goalNode.getX());

        int differenceX = (maxForX - minForX);

        int minForY = Math.min(bombPlacement.getY(), goalNode.getY());
        int maxForY = Math.max(bombPlacement.getY(), goalNode.getY());
        int differenceY = (maxForY - minForY);

        return differenceX <= 1 && differenceY <= 1;
    }

}
