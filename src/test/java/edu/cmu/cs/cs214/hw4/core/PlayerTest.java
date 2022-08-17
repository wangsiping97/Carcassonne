package edu.cmu.cs.cs214.hw4.core;

import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {
    private final Player testPlayer = new Player(1);

    @Test
    public void testId() {
        assertEquals(testPlayer.getId(), 1);
        assertEquals(testPlayer.toString(), "1: 0");
    }

    @Test
    public void testScore() {
        assertEquals(testPlayer.getScore(), 0);
        testPlayer.addScore(5);
        assertEquals(testPlayer.getScore(), 5);
    }

    @Test
    public void testMeeple() {
        assertEquals(testPlayer.getUsedMeeple(), 0);
        testPlayer.addUsedMeeple();
        assertEquals(testPlayer.getUsedMeeple(), 1);
        testPlayer.returnMeeple(1);
        assertEquals(testPlayer.getUsedMeeple(), 0);
    }
}
