package dk.sdu.mmmi.BasicMapSystem;

import dk.sdu.mmmi.common.data.Entity.Direction;
import org.junit.Before;
import org.junit.Test;


import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;


public class BasicMapTest {

    private BasicMap basicMap;

    @Before
    public void setUp() {
        basicMap = new BasicMap();
    }

    @Test
    public void isMoveAllowed_returnsFalse_whenXIsNegative() {
        assertFalse(basicMap.isMoveAllowed(-1, 0, Direction.UP));
    }

    @Test
    public void isMoveAllowed_returnsFalse_whenYIsNegative() {
        assertFalse(basicMap.isMoveAllowed(0, -1, Direction.UP));
    }

    @Test
    public void isMoveAllowed_returnsTrue_whenMoveIsWithinBounds() {
        assertTrue(basicMap.isMoveAllowed(5, 5, Direction.UP));
    }

    @Test
    public void isMoveAllowed_returnsFalse_whenMoveIsOutOfBounds() {
        assertFalse(basicMap.isMoveAllowed(11, 11, Direction.UP));
    }

    @Test
    public void isMoveAllowed_handlesEdgeCase_whenMovingUpAtMaxY() {
        assertTrue(basicMap.isMoveAllowed(5, 10, Direction.UP));
    }

    @Test
    public void isMoveAllowed_handlesEdgeCase_whenMovingDownAtMinY() {
        assertTrue(basicMap.isMoveAllowed(5, 0, Direction.DOWN));
    }

    @Test
    public void isMoveAllowed_handlesEdgeCase_whenMovingLeftAtMinX() {
        assertTrue(basicMap.isMoveAllowed(0, 5, Direction.LEFT));
    }

    @Test
    public void isMoveAllowed_handlesEdgeCase_whenMovingRightAtMaxX() {
        assertTrue(basicMap.isMoveAllowed(10, 5, Direction.RIGHT));
    }
}