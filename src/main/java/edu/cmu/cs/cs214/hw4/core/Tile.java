package edu.cmu.cs.cs214.hw4.core;

import java.util.HashMap;
import java.util.Map;

/**
 * The class represents each tile.
 */
public class Tile {
    private final String id;
    private final Map<Direction, Segment> config;

    /**
     * Constructor function. Initializes the basic information of a tile.
     * @param name the id of the tile.
     * @param up the segment type on the up side of the tile.
     * @param down the segment type on the down side of the tile.
     * @param left the segment type on the left side of the tile.
     * @param right the segment type on the right side of the tile.
     * @param center the segment type on the center of the tile.
     */
    public Tile (String name, SegmentType up, SegmentType down, SegmentType left, SegmentType right, SegmentType center) {
        config = new HashMap<>();
        id = name;
        config.put(Direction.UP, new Segment(up));
        config.put(Direction.DOWN, new Segment(down));
        config.put(Direction.LEFT, new Segment(left));
        config.put(Direction.RIGHT, new Segment(right));
        config.put(Direction.CENTER, new Segment(center));
    }

    /**
     * Gets the id of the current tile object.
     * @return the id.
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the segment of the current tile based on a certain direction and rotation.
     * @param direction the direction of the tile.
     * @param rotate the rotation information of the tile.
     * @return the segment.
     */
    public Segment getSegment(Direction direction, int rotate) {
        switch (rotate) {
            case 0: return config.get(direction);
            case 90: return config.get(direction.getRotate90());
            case 180: return config.get(direction.getOppo());
            default: return config.get(direction.getRotate90().getOppo());
        }
    }

    @Override
    public String toString() {
        return id + ":" + config.toString();
    }
}
