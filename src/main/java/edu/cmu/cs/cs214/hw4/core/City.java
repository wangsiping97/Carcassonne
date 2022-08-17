package edu.cmu.cs.cs214.hw4.core;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * The city subclass represents the city features.
 */
public class City extends Feature {
    private int arms;
    private int size;
    private static final int COMPLETE_SCORE = 2;
    private static final int INCOMPLETE_SCORE = 1;

    /**
     * Constructor function of the city features.
     * @param seg the init segment of the feature.
     */
    public City(Segment seg) {
        initSegment = seg;
        content = new HashSet<>();
        owners = new HashMap<>();
        isComplete = false;
        arms = 0;
        size = 0;
    }

    /**
     * Get city size and the number of city-of-arms.
     */
    private void getSizeandArms() {
        Set<Position> positions = new HashSet<>();
        for (Segment segment : content) {
            positions.add(segment.getPosition());
            if (segment.getType() == SegmentType.CITYOFARMS) arms++;
        }
        size = positions.size();
    }

    @Override
    public int getScore() {
        int unitScore = isComplete ? COMPLETE_SCORE : INCOMPLETE_SCORE;
        getSizeandArms();
        setScoredMark();
        return (size + arms) * unitScore;
    }
}
