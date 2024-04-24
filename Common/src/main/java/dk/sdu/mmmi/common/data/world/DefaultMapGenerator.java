package dk.sdu.mmmi.common.data.world;

public class DefaultMapGenerator {
    public static void generateMap(World world) {
        Map map = new Map(10, 10);
        map.setMap(new boolean[10][10]);
        world.setMap(map);
    }

    private DefaultMapGenerator() {
    }
}
