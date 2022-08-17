package edu.cmu.cs.cs214.hw4.core;

import org.junit.Test;

import static org.junit.Assert.*;

public class PositionTest {
    private final Position testP = new Position(1, 1);
    private final Position testP2 = new Position(0, 0);
    private final Position testP3 = new Position(100, 100);

    @Test
    public void testAjd() {
        assertEquals(testP.getAdj(Direction.UP), new Position(1, 0));
        assertEquals(testP.getAdj(Direction.DOWN), new Position(1, 2));
        assertEquals(testP.getAdj(Direction.LEFT), new Position(0, 1));
        assertEquals(testP.getAdj(Direction.RIGHT), new Position(2, 1));
        assertEquals(testP.getAdj(Direction.UPRIGHT), new Position(2, 0));
        assertEquals(testP.getAdj(Direction.UPLEFT), new Position(0, 0));
        assertEquals(testP.getAdj(Direction.DOWNLEFT), new Position(0, 2));
        assertEquals(testP.getAdj(Direction.DOWNRIGHT), new Position(2, 2));
        assertEquals(testP.getAdj(Direction.CENTER), new Position(1, 1));
        assertNull(testP2.getAdj(Direction.UP));
        assertNull(testP3.getAdj(Direction.CENTER));
    }

    @Test
    public void testToString() {
        assertEquals(testP.toString(), "(1, 1)");
    }
}
