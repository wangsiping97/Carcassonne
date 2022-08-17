package edu.cmu.cs.cs214.hw4.core;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Queue;
import java.util.LinkedList;

/**
 * An abstract class for features.
 */
public abstract class Feature {
    protected Segment initSegment;
    /**All segments in the feature*/
    protected Set<Segment> content;
    /**The number of meeples from all the players among the segments*/
    protected Map<Player, Integer> owners;
    /**Whether the feature is completed*/
    protected boolean isComplete;

    /**
     * Uses breadth-first search to find all segments included in the feature.
     * Updates content, owners during the process, and checks whether the feature
     * is completed.
     */
    public void complete() {
        isComplete = true;
        // update content
        content.add(initSegment);
        Queue<Segment> queue = new LinkedList<>();
        queue.add(initSegment);
        while (!queue.isEmpty()) {
            Segment head = queue.poll();
            // update owners
            Player player = head.getPlayer();
            if (player != null) {
                int meeples = owners.containsKey(player) ? owners.get(player) + 1 : 1;
                owners.put(player, meeples);
            }
            // add new segment to the queue
            List<Segment> list = Board.getInstance().getFeatures().get(head);
            if (list == null) continue;
            Position p = head.getPosition();
            boolean hasNeighbor = false;
            for (Segment segment : list) {
                // check if `head` is connected with a segment on a neighbor tile
                if (!segment.getPosition().equals(p)) hasNeighbor = true;
                if (content.contains(segment)) continue;
                content.add(segment);
                queue.add(segment);
            }
            if (!hasNeighbor) {
                isComplete = false;
            }
        }
    }

    /**
     * Gets whether the feature is completed.
     * @return whether the feature is completed.
     */
    public boolean isComplete() {
        return isComplete;
    }

    /**
     * Updates used information for each segment in `content` to avoid duplicate operation
     */
    protected void setScoredMark() {
        for (Segment segment : content) {
            segment.setScored();
        }
    }

    /**
     * Gets the content segments of the current feature.
     * @return the content segments.
     */
    public Set<Segment> getContent() {
        return content;
    }

    /**
     * Gets the owners of the current feature.
     * @return the oners of the current feature.
     */
    public Map<Player, Integer> getOwners() {
        return owners;
    }

    /**
     * Gets the score for the current feature.
     * Different features have different scoring methods.
     * @return the score of the current feature.
     */
    public abstract int getScore();
}
