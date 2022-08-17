package edu.cmu.cs.cs214.hw4.core;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * The road subclass represents the road features.
 */
public class Road extends Feature {
    private int length;
    private static final int COMPLETESCORE = 1;
    private static final int INCOMPLETESCORE = 1;

    /**
     * Constructor function of the road features.
     * @param seg the init segment of the feature.
     */
    public Road(Segment seg) {
        initSegment = seg;
        content = new HashSet<>();
        owners = new HashMap<>();
        isComplete = false;
        length = 0;
    }

    /**
     * Get road length.
     */
    private void getLength() {
        Set<Position> positions = new HashSet<>();
        for (Segment segment : content) {
            positions.add(segment.getPosition());
        }
        length = positions.size();
    }

    @Override
    public int getScore() {
        int unitScore = isComplete ? COMPLETESCORE : INCOMPLETESCORE;
        getLength();
        setScoredMark();
        return length * unitScore;
    }
}
