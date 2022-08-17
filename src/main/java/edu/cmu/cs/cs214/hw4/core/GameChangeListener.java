package edu.cmu.cs.cs214.hw4.core;

import java.util.Set;

/**
 * The listener interface.
 */
public interface GameChangeListener {
    /**
     * Places the current tile onto the GUI board.
     * @param tile the current tile to be placed.
     * @param p the current position in which the tile is to be placed.
     * @param rotate the current rotation of the tile.
     */
    void placeTile(Tile tile, Position p, int rotate);

    /**
     * Places the meeple onto the GUI board.
     * @param player the current player of the game.
     * @param direction the direction on which the player decides to place the meeple.
     */
    void placeMeeple(Player player, Direction direction);

    /**
     * Triggers the next round of the game.
     */
    void nextRound();

    /**
     * Removes the meeples from the tiles on the GUI board.
     * @param content the segment content of the feature that is finished scoring.
     */
    void returnMeeples(Set<Segment> content);

    /**
     * Updates the score board on the GUI.
     */
    void updateScoreBoard();

    /**
     * Ends the game and displays the winner.
     */
    void endGame();
}
