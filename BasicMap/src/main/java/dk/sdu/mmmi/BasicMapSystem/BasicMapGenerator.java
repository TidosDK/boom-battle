package dk.sdu.mmmi.BasicMapSystem;

import dk.sdu.mmmi.common.data.World.World;
import dk.sdu.mmmi.common.data.World.Map;
import dk.sdu.mmmi.common.services.Map.IMapGenerator;

public class BasicMapGenerator implements IMapGenerator {


    public void generateMap(World world) {
        Map map = new BasicMap();
        map.setMap(basicMap(map.getWidth(), map.getHeight()));
        world.setMap(map);
    }

    /**
     * Generates a default map for the game where every other column has a wall on every other row
     * @param width the width of the map
     * @param height the height of the map
     * @return a 2D boolean array representing the map
     */
    public boolean[][] basicMap(int width, int height) {
        boolean[][] map = new boolean[width][height];
        // Generate a map where every other column has a wall on every other row
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                map[x][y] = (x % 2 == 1) && (y % 2 == 1);
            }
        }
        return map;
    }
}


