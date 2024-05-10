package dk.sdu.mmmi.basicmapsystem;

import dk.sdu.mmmi.common.data.entity.Direction;
import dk.sdu.mmmi.common.data.gameproperties.GameData;
import dk.sdu.mmmi.common.data.world.World;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BasicMapTest {
    private BasicMap map;
    private BasicMapGenerator generator;
    private final World world = World.getInstance();

    @BeforeEach
    void setUp() {
        map = new BasicMap();
        generator = new BasicMapGenerator();
        map.setMap(generator.basicMap(map.getWidth(), map.getHeight(), world, GameData.getInstance()));

    }

    @Test
    void isTileObstacle_returnsTrueForOutOfBounds() {
        assertTrue(map.isTileObstacle(-1, 0));
        assertTrue(map.isTileObstacle(0, -1));
        assertTrue(map.isTileObstacle(11, 0));
        assertTrue(map.isTileObstacle(0, 11));
    }

    @Test
    void isTileObstacle_returnsFalseForInBounds() {
        assertFalse(map.isTileObstacle(0, 0));
    }

    @Test
    void setMapTile_returnsFalseForOutOfBounds() {
        assertFalse(map.setMapTile(-1, 0, true));
        assertFalse(map.setMapTile(0, -1, true));
        assertFalse(map.setMapTile(11, 0, true));
        assertFalse(map.setMapTile(0, 11, true));
    }

    @Test
    void setMapTile_returnsTrueForInBounds() {
        assertTrue(map.setMapTile(0, 0, true));
    }

    @Test
    void isMoveAllowed_returnsFalseForOutOfBounds() {
        assertFalse(map.isMoveAllowed(-1, 0, Direction.UP));
        assertFalse(map.isMoveAllowed(0, -1, Direction.UP));
        assertFalse(map.isMoveAllowed(11, 0, Direction.UP));
        assertFalse(map.isMoveAllowed(0, 11, Direction.UP));
    }

    @Test
    void isMoveAllowed_returnsTrueForInBounds() {
        assertTrue(map.isMoveAllowed(0, 0, Direction.UP));
    }

    @Test
    void outOfBounds_returnsTrueForOutOfBounds() {
        assertTrue(map.outOfBounds(-1, 0));
        assertTrue(map.outOfBounds(0, -1));
        assertTrue(map.outOfBounds(11, 0));
        assertTrue(map.outOfBounds(0, 11));
    }

    @Test
    void outOfBounds_returnsFalseForInBounds() {
        assertFalse(map.outOfBounds(0, 0));
    }
}