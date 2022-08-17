package edu.cmu.cs.cs214.hw4.core;

import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class DeckTest {
    private final Deck testDeck = Deck.getInstance();
    private final Iterator<Tile> iter = testDeck.iterator();

    @Test
    public void testIterator() {

        int count = 0;
        while (iter.hasNext()) {
            iter.next();
            count++;
        }
        assertEquals(count, 72);
    }

    @Test (expected = NoSuchElementException.class)
    public void testException() {
        while (iter.hasNext()) {
            iter.next();
        }
        iter.next();
    }

}
