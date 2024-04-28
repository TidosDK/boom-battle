package dk.sdu.mmmi.ai;


import dk.sdu.mmmi.common.data.ai.Node;
import dk.sdu.mmmi.common.data.world.Map;
import dk.sdu.mmmi.common.services.ai.IPathFinding;


import java.util.ArrayList;


/**
 * PathFinding class that implements the IPathFinding interface.
 */
public class PathFinding implements IPathFinding {

    private Node[][] nodeMap;
    private int maxCol;
    private int maxRow;
    private ArrayList<Node> openList = new ArrayList<>();
    private ArrayList<Node> checkedList = new ArrayList<>();
    private ArrayList<Node> pathList = new ArrayList<>();


    /**
     * Method that finds a path from the start node to the goal node.
     *
     * @param goalNode is the node you are trying to reach
     * @param map      is the map you are trying to navigate
     */
    @Override
    public ArrayList<Node> pathFind(Node start, Node goalNode, Map map) {
        maxCol = map.getWidth();
        maxRow = map.getHeight();
        nodeMap = new Node[maxCol][maxRow];
        start.setStart(true);
        goalNode.setGoal(true);


        int col = 0;
        int row = 0;

        while (col < (maxCol + 1) && row < maxRow) {
            nodeMap[col][row] = new Node(col, row);

            col++;

            if (col == maxCol) {
                col = 0;
                row++;
            }


        }

        for (int i = 0; i < nodeMap.length; i++) {
            for (int j = 0; j < nodeMap[0].length; j++) {
                if (map.getMap()[i][j]) {
                    nodeMap[i][j].setObstacle(true);
                }
            }
        }
        aStar(start, goalNode, nodeMap);


        return pathList;


    }

    /**
     * Method that finds a path from the start node to the goal node.
     *
     * @param startNode is the node you are starting from
     * @param goalNode  is the node you are trying to reach
     * @param map       is the map you are trying to navigate
     */
    public void aStar(Node startNode, Node goalNode, Node[][] map) {
        Node currentNode;
        openList.add(startNode);
        int col;
        int row;
        int bestNodeI = 0;
        int bestNodeFCost = 1000;

        while (openList.size() > 0) {
            currentNode = openList.get(bestNodeI);
            col = currentNode.getX();
            row = currentNode.getY();
            calculateHeuristic(startNode, currentNode, goalNode);


            currentNode.setChecked(true);
            checkedList.add(currentNode);
            openList.remove(currentNode);

            //Up node
            if (row - 1 >= 0) {
                openNode(map[col][row - 1], currentNode);
            }
            //Left node
            if (col - 1 >= 0) {
                openNode(map[col - 1][row], currentNode);
            }

            //Down node
            if (row + 1 < maxRow) {
                openNode(map[col][row + 1], currentNode);
            }
            //Right node
            if (col + 1 < maxCol) {
                openNode(map[col + 1][row], currentNode);
            }

            if (currentNode.getX() == goalNode.getX() && currentNode.getY() == goalNode.getY()) {
                reconstructPath(currentNode, startNode);
            }


            for (int i = 0; i < openList.size(); i++) {
                if (openList.get(i).getF() < bestNodeFCost) {
                    bestNodeI = i;
                    bestNodeFCost = openList.get(i).getF();
                } else if (openList.get(i).getF() == bestNodeFCost) {
                    if (openList.get(i).getG() < openList.get(bestNodeI).getG()) {
                        bestNodeI = i;
                    }
                }
            }

        }


    }

    /**
     * Method that opens a node.
     *
     * @param node        is the node you are trying to open
     * @param currentNode is the node you are coming from
     */
    private void openNode(Node node, Node currentNode) {
        if (!node.isOpen() && !node.isObstacle()) {
            node.setOpen(true);
            node.setParent(currentNode);
            openList.add(node);
        }
    }

    /**
     * Method that reconstructs the optimal path.
     *
     * @param goalNode  is the node you are trying to reach
     * @param startNode is the node you are starting from
     */
    public void reconstructPath(Node goalNode, Node startNode) {
        Node current = goalNode;
        while (current != startNode) {
            current = current.getParent();
            if (current != startNode) {
                pathList.add(current);
            }
        }
    }

    /**
     * Method that calculates the heuristic.
     *
     * @param startNode   is the node you are starting from
     * @param currentNode is the node you are calculating the heuristic for
     * @param goalNode    is the node you are trying to reach
     * @return the node with the calculated heuristic
     */
    public Node calculateHeuristic(Node startNode, Node currentNode, Node goalNode) {
        currentNode.setG(Math.abs((currentNode.getX() - startNode.getX()) + (currentNode.getY() - startNode.getY())));
        currentNode.setH(Math.abs((currentNode.getX() - goalNode.getX()) + (currentNode.getY() - goalNode.getY())));
        currentNode.setF((currentNode.getG() + currentNode.getH()));

        return currentNode;
    }

}
