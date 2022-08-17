package edu.cmu.cs.cs214.hw4.core;

import java.security.DigestException;

/**
 * The class represents each segment of the feature.
 */
public class Segment {
    private final SegmentType type;
    private Player player;
    private Position position;
    private boolean scored;

    /**
     * Constructor function from a certain segment type.
     * @param type the segment type (enum)
     */
    Segment(SegmentType type) {
        this.type = type;
        player = null;
        position = null;
        scored = false;
    }

    /**
     * Gets the segment type of the current object.
     * @return the segment type.
     */
    public SegmentType getType() {
        return type;
    }

    /**
     * Gets the owner of the current segment.
     * @return the owner.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Sets the owner of the current segment.
     * @param player the owner.
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Gets the position of the current segment on the board.
     * @return the position.
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Sets the position of the current segment on the board.
     * @param p the position.
     */
    public void setPosition(Position p) {
        position = p;
    }

    /**
     * Gets whether the current segment has been scored.
     * @return whether the current segment has been scored.
     */
    public boolean getScored() {
        return scored;
    }

    /**
     * Sets the current segment scored.
     */
    public void setScored() {
        scored = true;
    }

    @Override
    public String toString() {
        return type.toString();
    }
}
