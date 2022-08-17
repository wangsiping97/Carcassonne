package edu.cmu.cs.cs214.hw4.core;

import org.junit.Test;

import static org.junit.Assert.*;

public class DirectionTest {
    @Test
    public void testGetOppo() {
        assertEquals(Direction.UP.getOppo(), Direction.DOWN);
        assertEquals(Direction.DOWN.getOppo(), Direction.UP);
        assertEquals(Direction.LEFT.getOppo(), Direction.RIGHT);
        assertEquals(Direction.RIGHT.getOppo(), Direction.LEFT);
        assertEquals(Direction.UPRIGHT.getOppo(), Direction.DOWNLEFT);
        assertEquals(Direction.UPLEFT.getOppo(), Direction.DOWNRIGHT);
        assertEquals(Direction.DOWNLEFT.getOppo(), Direction.UPRIGHT);
        assertEquals(Direction.DOWNRIGHT.getOppo(), Direction.UPLEFT);
        assertEquals(Direction.CENTER.getOppo(), Direction.CENTER);
    }

    @Test
    public void testGetRotate90() {
        assertEquals(Direction.UP.getRotate90(), Direction.LEFT);
        assertEquals(Direction.DOWN.getRotate90(), Direction.RIGHT);
        assertEquals(Direction.LEFT.getRotate90(), Direction.DOWN);
        assertEquals(Direction.RIGHT.getRotate90(), Direction.UP);
        assertEquals(Direction.UPRIGHT.getRotate90(), Direction.UPLEFT);
        assertEquals(Direction.UPLEFT.getRotate90(), Direction.DOWNLEFT);
        assertEquals(Direction.DOWNLEFT.getRotate90(), Direction.DOWNRIGHT);
        assertEquals(Direction.DOWNRIGHT.getRotate90(), Direction.UPRIGHT);
        assertEquals(Direction.CENTER.getRotate90(), Direction.CENTER);
    }
}
