package dk.sdu.mmmi.common.services.ai;

import dk.sdu.mmmi.common.data.ai.Node;
import dk.sdu.mmmi.common.data.world.Map;

import java.util.ArrayList;

public interface IPathFinding {
    ArrayList<Node> pathFind(Node start, Node goal, Map map);
}
