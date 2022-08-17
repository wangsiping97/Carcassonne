package edu.cmu.cs.cs214.hw4.core;

import java.util.HashMap;
import java.util.HashSet;

/**
 * The subclass represents the monastery features.
 */
public class Monastery extends Feature {
    private int neighbors;
    private static final int COMPLETEMARK = 9;

    /**
     * Constructor function of the monastery features.
     * @param seg the init segment of the feature.
     */
    public Monastery(Segment seg) {
        initSegment = seg;
        isComplete = false;
        neighbors = 0;
        content = new HashSet<>();
        owners = new HashMap<>();
    }

    @Override
    public void complete() {
        content.add(initSegment);
        owners.put(initSegment.getPlayer(), 1);
        for (Direction direction : Direction.values()) {
            Position p = initSegment.getPosition().getAdj(direction);
            if (Board.getInstance().getTile(p) != null)
                neighbors++;
        }
        isComplete = neighbors == COMPLETEMARK;
    }

    @Override
    public int getScore() {
        setScoredMark();
        return neighbors;
    }
}
