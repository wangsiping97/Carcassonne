package edu.cmu.cs.cs214.hw4.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * The board of the game, containing relationship information between position and tile,
 * tile and rotation, and the adjacent lists of feature graphs.
 */
public class Board {
    private static final Board BOARD = new Board();
    private final Map<Position, Tile> grid;
    private final Map<Tile, Integer> rotates;
    /**The adjacent lists of feature graphs, in Segment.*/
    private final Map<Segment, List<Segment>> features;

    /**
     * Constructor function, initializes grid, features and rotates.
     */
    public Board() {
        grid = new HashMap<>();
        features = new HashMap<>();
        rotates = new HashMap<>();
    }

    /**
     * Gets the only instance of this class.
     * @return the only instance.
     */
    public static Board getInstance() {
        return BOARD;
    }

    /**
     * Gets the grid (relationship between positions and tiles).
     * @return the grid of the board.
     */
    public Map<Position, Tile> getGrid() {
        return grid;
    }

    /**
     * Gets the rotation of a specific tile.
     * @param tile a specific tile.
     * @return its rotation.
     */
    public int getRotate(Tile tile) {
        return rotates.get(tile);
    }

    /**
     * Gets the adjacent lists of the feature graphs.
     * @return the adjacent lists of the feature graphs.
     */
    public Map<Segment, List<Segment>> getFeatures() {
        return features;
    }

    /**
     * Gets the tile on a specific position.
     * @param p the specific position.
     * @return the tile on that position.
     */
    public Tile getTile(Position p) {
        if (grid.containsKey(p)) return grid.get(p);
        return null;
    }

    /**
     * Places the tile on a specific position with a specific rotation.
     * @param tile the tile needs to be placed.
     * @param p the position where it is to be placed.
     * @param r the rotation of the tile.
     */
    public void place(Tile tile, Position p, int r) {
        rotates.put(tile, r);
        grid.put(p, tile);
    }

    /**
     * Adds seg2 to seg1's adjacent list.
     * @param seg1 a segment.
     * @param seg2 another segment.
     */
    public void connectFeature(Segment seg1, Segment seg2) {
        List<Segment> list;
        if (features.containsKey(seg1)) {
            list = features.get(seg1);
        } else {
            list = new ArrayList<>();
        }
        list.add(seg2);
        features.put(seg1, list);
    }
}
