package dk.sdu.mmmi.basicmapsystem;

import dk.sdu.mmmi.common.data.entity.Coordinates;
import dk.sdu.mmmi.common.data.entity.Direction;
import dk.sdu.mmmi.common.data.gameproperties.GameData;
import dk.sdu.mmmi.common.data.world.World;
import dk.sdu.mmmi.common.services.entityproperties.ICollidable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

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
    void isTileObstacleReturnsTrueForOutOfBounds() {
        assertTrue(map.isTileObstacle(-1, 0));
        assertTrue(map.isTileObstacle(0, -1));
        assertTrue(map.isTileObstacle(11, 0));
        assertTrue(map.isTileObstacle(0, 11));
    }

    @Test
    void isTileObstacleReturnsFalseForInBounds() {
        assertFalse(map.isTileObstacle(0, 0));
    }

    @Test
    void setMapTileReturnsFalseForOutOfBounds() {
        assertFalse(map.setMapTile(-1, 0, true));
        assertFalse(map.setMapTile(0, -1, true));
        assertFalse(map.setMapTile(11, 0, true));
        assertFalse(map.setMapTile(0, 11, true));
    }

    @Test
    void setMapTileReturnsTrueForInBounds() {
        assertTrue(map.setMapTile(0, 0, true));
    }

    @Test
    void isMoveAllowedReturnsFalseForOutOfBounds() {
        assertFalse(map.isMoveAllowed(-1, 0, Direction.UP));
        assertFalse(map.isMoveAllowed(0, -1, Direction.UP));
        assertFalse(map.isMoveAllowed(11, 0, Direction.UP));
        assertFalse(map.isMoveAllowed(0, 11, Direction.UP));
    }


    @Test
    void outOfBoundsReturnsTrueForOutOfBounds() {
        assertTrue(map.outOfBounds(-1, 0));
        assertTrue(map.outOfBounds(0, -1));
        assertTrue(map.outOfBounds(11, 0));
        assertTrue(map.outOfBounds(0, 11));
    }

    @Test
    void updateMapTest() {
        BasicMap mockMap = spy(new BasicMap());
        mockMap.setMap(generator.basicMap(11, 11, world, GameData.getInstance()));
        mockMap.updateMap();
        assertFalse(mockMap.isTileObstacle(0, 0)); // This is a corner and not an obstacle by default
        // We add an obstacle to the map on square (0, 0)
        Collection<ICollidable> collidables = new ArrayList<>();
        MockCollidable collidable = new MockCollidable();
        collidable.setCoordinates(new Coordinates(0, 0));
        collidables.add(collidable);
        // We mock the getCollidables method to return our collection of collidables
        doReturn(collidables).when(mockMap).getCollidables();
        // Test that the map is updated and that the tile is now an obstacle
        mockMap.updateMap();
        assertTrue(mockMap.isTileObstacle(0, 0));
    }
}
