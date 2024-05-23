package dk.sdu.mmmi.common.services.map;

import dk.sdu.mmmi.common.data.world.Map;
import dk.sdu.mmmi.common.data.world.World;

public interface IMapGenerator {

    Map generateMap(World world);
}
