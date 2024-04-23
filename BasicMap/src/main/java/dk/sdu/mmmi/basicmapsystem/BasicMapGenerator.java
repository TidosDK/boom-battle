package dk.sdu.mmmi.basicmapsystem;

import dk.sdu.mmmi.common.data.world.World;
import dk.sdu.mmmi.common.data.world.Map;
import dk.sdu.mmmi.common.services.map.IMapGenerator;

public class BasicMapGenerator implements IMapGenerator {


    public void generateMap(World world) {
        Map map = new BasicMap();
        map.setMap(basicMap(map.getWidth(), map.getHeight()));
        world.setMap(map);
    }

    /**
     * Generates a default map for the game where every other column has a wall on every other row.
     *
     * @param width  the width of the map
     * @param height the height of the map
     * @return a 2D boolean array representing the map
     */
    public boolean[][] basicMap(int width, int height) {
        boolean[][] map = new boolean[width][height];
        // Generate a map where every other column has a wall on every other row
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (x == 0 || y == 0 || x == (width - 1) || y == (height - 1)) {
                    map[x][y] = false;
                } else {
                    map[x][y] = (x % 2 == 1) && (y % 2 == 1);
                }
            }
        }

        return map;
    }
}


