package edu.cmu.cs.cs214.hw4.core;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BoardTest {
    private final Board board = Board.getInstance();
    private final Tile tileUp = Deck.getInstance().getTileFromId("A");
    private final Tile tileCenter = Deck.getInstance().getTileFromId("D");
    private final Tile tileDown = Deck.getInstance().getTileFromId("X");

    @Before
    public void setUp() {
        board.place(tileUp, new Position(2, 2), 0);
    }

    @Test
    public void testGrid() {
        Position p = new Position(2, 2);
        assertEquals(board.getGrid().get(p).getId(), "A");
    }

    @Test
    public void testRotates() {
        assertEquals(board.getRotate(tileUp), 0);
    }

    @Test
    public void testGetTile() {
        Position p = new Position(2, 2);
        assertEquals(board.getTile(p).getId(), "A");
        p = new Position(2, 3);
        assertNull(board.getTile(p));
    }

    @Test
    public void testFeatures() {
        board.place(tileCenter, new Position(2, 3), 180);
        Segment down = tileUp.getSegment(Direction.DOWN, 0);
        Segment up =tileCenter.getSegment(Direction.UP, 180);
        board.connectFeature(up, down);
        assertEquals(board.getFeatures().get(up).size(), 1);
        assertEquals(board.getFeatures().get(up).get(0), down);
        down = tileDown.getSegment(Direction.DOWN, 180);
        board.connectFeature(up, down);
        assertEquals(board.getFeatures().get(up).size(), 2);
        assertEquals(board.getFeatures().get(up).get(1), down);
    }
}
