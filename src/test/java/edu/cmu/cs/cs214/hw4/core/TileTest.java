package edu.cmu.cs.cs214.hw4.core;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class TileTest {
    private Tile testTile;

    @Before
    public void setUp() {
        testTile = new Tile("V", SegmentType.FIELD, SegmentType.ROAD, SegmentType.ROAD, SegmentType.FIELD, SegmentType.NONE);
    }

    @Test
    public void testParse() {
        List<Tile> test = JSONConfigReader.parse("src/main/resources/config.json");
        int count = 0;
        for (Tile t : test) {
            count++;
        }
        assertEquals(count, 72);
    }

    @Test
    public void testGetSegment() {
        Segment seg = testTile.getSegment(Direction.UP, 0);
        assertEquals(seg.getType(), SegmentType.FIELD);
        seg = testTile.getSegment(Direction.UP, 90);
        assertEquals(seg.getType(), SegmentType.ROAD);
        seg = testTile.getSegment(Direction.UP, 180);
        assertEquals(seg.getType(), SegmentType.ROAD);
        seg = testTile.getSegment(Direction.UP, 270);
        assertEquals(seg.getType(), SegmentType.FIELD);
    }

    @Test
    public void testToString() {
        assertTrue(testTile.toString().contains("UP=FIELD"));
        assertTrue(testTile.toString().contains("DOWN=ROAD"));
        assertTrue(testTile.toString().contains("LEFT=ROAD"));
        assertTrue(testTile.toString().contains("RIGHT=FIELD"));
        assertTrue(testTile.toString().contains("CENTER=NONE"));
    }
}
