package edu.cmu.cs.cs214.hw4;

import edu.cmu.cs.cs214.hw4.gui.GameStart;

/**
 * The Main class of the carcassonne game.
 */
public class Main {
    /**
     * The main function.
     * @param args the command-line arguments.
     */
    public static void main(String[] args) {
        try {
            new GameStart();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
