package edu.cmu.cs.cs214.hw4.core;

/**
 * The class representing players.
 */
public class Player {
    private final int id;
    private int score;
    private int usedMeeple;

    /**
     * Constructor function. Initializes basic information of a player.
     * @param id the player's id.
     */
    public Player(int id) {
        this.id = id;
        score = 0;
        usedMeeple = 0;
    }

    /**
     * Gets the player's id.
     * @return the player's id.
     */
    public int getId() {
        return id;
    }

    /**
     * Adds scores to the player's score.
     * @param n the scores to be added.
     */
    public void addScore(int n) {
        score += n;
    }

    /**
     * Gets the player's score.
     * @return the player's score.
     */
    public int getScore() {
        return score;
    }

    /**
     * Gets the number of meeples that has been placed on the board.
     * @return the number of placed meeple.
     */
    public int getUsedMeeple() {
        return usedMeeple;
    }

    /**
     * Adds one the number of meeples that has been placed.
     */
    public void addUsedMeeple() {
        usedMeeple++;
    }

    /**
     * Returns a certain amount of meeples from the board.
     * @param n the number of meeples to be returned.
     */
    public void returnMeeple(int n) {
        usedMeeple -= n;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (o.getClass() != Player.class) return false;
        Player other = (Player)o;
        return (other.id == this.id);
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return id + ": " + score;
    }
}
