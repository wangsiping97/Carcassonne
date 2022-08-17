package edu.cmu.cs.cs214.hw4.core;

import org.junit.Test;

import static org.junit.Assert.*;

public class SegmentTest {
    private final Segment testSeg = new Segment(SegmentType.ROAD);

    @Test
    public void testGetType() {
        assertEquals(testSeg.getType(), SegmentType.ROAD);
    }

    @Test
    public void testPlayer() {
        testSeg.setPlayer(new Player(1));
        assertEquals(testSeg.getPlayer().getId(), 1);
    }

    @Test
    public void testPosition() {
        Position p = new Position(1, 1);
        testSeg.setPosition(p);
        assertEquals(testSeg.getPosition(), p);
    }

    @Test
    public void testScored() {
        assertFalse(testSeg.getScored());
        testSeg.setScored();
        assertTrue(testSeg.getScored());
    }

    @Test
    public void testToString() {
        assertEquals(testSeg.toString(), "ROAD");
    }

    @Test
    public void testSegmentMatches() {
        assertTrue(SegmentType.ROAD.matches(SegmentType.ROADEND));
        assertTrue(SegmentType.ROADEND.matches(SegmentType.ROAD));
        assertTrue(SegmentType.CITY.matches(SegmentType.CITYOFARMS));
        assertTrue(SegmentType.CITY.matches(SegmentType.CITYWALL));
        assertTrue(SegmentType.CITYWALL.matches(SegmentType.CITYOFARMS));
        assertTrue(SegmentType.MONASTERY.matches(SegmentType.MONASTERY));
        assertFalse(SegmentType.MONASTERY.matches(SegmentType.NONE));
        assertFalse(SegmentType.ROAD.matches(SegmentType.CITY));
        assertFalse(SegmentType.NONE.matches(SegmentType.ROADEND));
        assertFalse(SegmentType.CITY.matches(SegmentType.NONE));
    }
}
