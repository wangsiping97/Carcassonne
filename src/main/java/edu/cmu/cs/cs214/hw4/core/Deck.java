package edu.cmu.cs.cs214.hw4.core;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Map;
import java.util.HashMap;

/**
 * The Decker class. Shuffles the tiles and hands out the card.
 */
public class Deck implements java.lang.Iterable<Tile> {
    private static final Deck DECK = new Deck();
    private static Map<String, Tile> config;
    private final List<Tile> box;
    private static final String CONFIG_FILE_PATH = "src/main/resources/config.json";
    private static final int CARD_NUM = 72;

    /**
     * Constructor function, gets the tiles and shuffles them.
     */
    public Deck() {
        box = JSONConfigReader.parse(CONFIG_FILE_PATH);
        Collections.shuffle(box);
        config = new HashMap<>();
        for (Tile tile : box) {
            config.put(tile.getId(), tile);
        }
    }

    /**
     * Gets a certain tile from its id (or name).
     * @param id the id of the tile (i.e., A, B, C, ...).
     * @return the required tile.
     */
    public Tile getTileFromId(String id) {
        if (!config.containsKey(id)) return null;
        return config.get(id);
    }

    /**
     * Gets the only instance of the class.
     * @return the only instance of the class.
     */
    public static Deck getInstance() {
        return DECK;
    }

    /**
     * Implements Iterable.
     * @return an iterator.
     */
    public Iterator<Tile> iterator() {
        return new Iterator<>() {
            private int i = 0;
            @Override
            public boolean hasNext() {
                return i < CARD_NUM;
            }

            @Override
            public Tile next() {
                if (i < CARD_NUM) {
                    Tile tile = box.get(i);
                    i++;
                    return tile;
                }
                throw new NoSuchElementException();
            }
        };
    }
}
