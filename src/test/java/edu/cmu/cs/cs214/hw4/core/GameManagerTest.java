package edu.cmu.cs.cs214.hw4.core;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameManagerTest {
    private final GameManager manager = new GameManager(3);
    private final Tile tileUp = Deck.getInstance().getTileFromId("A");
    private final Tile tileCenter = Deck.getInstance().getTileFromId("D");
    private final Tile tileDown = Deck.getInstance().getTileFromId("X");
    private final Tile tileCity = Deck.getInstance().getTileFromId("C");

    @Before
    public void setUp() {
        manager.placeTile(tileUp, new Position(2, 2), 0);
        manager.getCurrPlayerId(); // currPlayerId = 1
    }

    @Test
    public void testGetCurrTile() {
        assertEquals(manager.getCurrTile(), tileUp);
    }

    @Test
    public void testCurrPlayer() {
        assertEquals(manager.getCurrPlayer().getId(), 1);
        manager.getCurrPlayerId();
        assertEquals(manager.getCurrPlayer().getId(), 2);
        manager.getCurrPlayerId();
        manager.getCurrPlayerId();
        assertEquals(manager.getCurrPlayer().getId(), 1);
    }

    @Test
    public void testValidCheck() {
        assertTrue(manager.checkValidCurrentTile(tileCenter));
        assertFalse(manager.checkValidCurrentTile(tileCity));
    }

    @Test
    public void testPlaceTile() {
        assertTrue(manager.checkValidCurrentTile(tileCenter));
        assertTrue(manager.checkLegalPlaceTile(tileCenter, new Position(2, 3), 180));
        assertFalse(manager.checkLegalPlaceTile(tileCenter, new Position(1, 2), 0));
        assertFalse(manager.checkLegalPlaceTile(tileCenter, new Position(50, 50), 0));
        manager.placeTile(tileCenter, new Position(2, 3), 180);
        Segment upDown = tileUp.getSegment(Direction.DOWN, 0);
        assertEquals(Board.getInstance().getFeatures().get(upDown).size(), 1);
        Segment centerUp = tileCenter.getSegment(Direction.UP, 180);
        assertEquals(Board.getInstance().getFeatures().get(upDown).get(0), centerUp);
        assertEquals(Board.getInstance().getFeatures().get(centerUp).size(), 2);
        assertEquals(Board.getInstance().getFeatures().get(centerUp).get(1), upDown);
    }

    @Test
    public void testPlaceMeeple() {
        assertTrue(manager.checkMeepleValid());
        assertFalse(manager.checkLegalPlaceMeeple(Direction.UP));
        assertTrue(manager.checkLegalPlaceMeeple(Direction.DOWN));
        manager.placeMeeple(Direction.DOWN);
        assertEquals(tileUp.getSegment(Direction.DOWN, 0).getPlayer().getId(), 1);
        manager.placeTile(tileCenter, new Position(2, 3), 180);
        assertFalse(manager.checkLegalPlaceMeeple(Direction.UP));
        assertFalse(manager.checkLegalPlaceMeeple(Direction.DOWN));
        assertTrue(manager.checkLegalPlaceMeeple(Direction.LEFT));
    }

    @Test
    public void testUpdateMonastery() {
        manager.placeMeeple(Direction.CENTER);
        manager.placeTile(tileCenter, new Position(2, 3), 180);
        manager.endGameScoring();
        assertEquals(manager.getCurrPlayer().getScore(), 2);
    }

    @Test
    public void testUpdateRoad() {
        manager.placeTile(tileCenter, new Position(2, 3), 180);
        manager.placeTile(tileDown, new Position(2, 4), 90);
        manager.placeMeeple(Direction.UP);
        manager.update();
        assertEquals(manager.getCurrPlayer().getScore(), 3);
        manager.getCurrPlayerId();
        manager.placeMeeple(Direction.DOWN);
        manager.endGameScoring();
        assertEquals(manager.getCurrPlayer().getScore(), 1);
    }


    @Test
    public void testUpdateCity1() {
        Tile s = Deck.getInstance().getTileFromId("S");
        manager.placeTile(s, new Position(2, 3), 180);
        manager.placeMeeple(Direction.DOWN);
        assertEquals(manager.getCurrPlayer().getUsedMeeple(), 1);
        manager.endGameScoring();
        assertEquals(manager.getCurrPlayer().getScore(), 2);
        assertEquals(manager.getCurrPlayer().getUsedMeeple(), 0);
    }

    @Test
    public void testUpdateCity2() {
        Tile s = Deck.getInstance().getTileFromId("S");
        manager.placeTile(s, new Position(2, 3), 180);
        manager.placeMeeple(Direction.DOWN);
        manager.placeTile(tileCenter, new Position(1, 3), 0);
        manager.endGameScoring();
        assertEquals(manager.getCurrPlayer().getScore(), 3);
    }

    @Test
    public void testUpdateCity3() {
        Tile s = Deck.getInstance().getTileFromId("S");
        manager.placeTile(s, new Position(2, 3), 180);
        manager.placeMeeple(Direction.DOWN);
        manager.placeTile(tileCenter, new Position(1, 3), 0);
        Tile e = Deck.getInstance().getTileFromId("E");
        manager.placeTile(e, new Position(2, 4), 0);
        Tile h = Deck.getInstance().getTileFromId("H");
        manager.placeTile(h, new Position(3, 3), 0);
        manager.update();
        assertEquals(manager.getCurrPlayer().getScore(), 10);
    }

    @Test
    public void testTwoOwnersRoad() {
        manager.placeMeeple(Direction.DOWN);
        manager.getCurrPlayerId();
        manager.placeTile(tileDown, new Position(2, 4), 0);
        manager.placeMeeple(Direction.UP);
        manager.placeTile(tileCenter, new Position(2, 3), 0);
        manager.update();
        assertEquals(manager.getScores().get(1).intValue(), 3);
        assertEquals(manager.getScores().get(2).intValue(), 3);
    }

    @Test
    public void testTwoOwnersCity() {
        Tile s = Deck.getInstance().getTileFromId("S");
        manager.placeTile(s, new Position(2, 3), 180);
        manager.placeMeeple(Direction.DOWN);
        manager.getCurrPlayerId();
        manager.placeTile(tileCenter, new Position(1, 3), 0);
        manager.placeMeeple(Direction.RIGHT);
        Tile e = Deck.getInstance().getTileFromId("E");
        manager.placeTile(e, new Position(2, 4), 0);
        Tile h = Deck.getInstance().getTileFromId("H");
        manager.placeTile(h, new Position(3, 3), 0);
        manager.update();
        assertEquals(manager.getScores().get(1).intValue(), 10);
        assertEquals(manager.getScores().get(2).intValue(), 10);
    }
}
