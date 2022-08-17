package edu.cmu.cs.cs214.hw4.core;

/**
 * The class represents a position on the board.
 */
public class Position {
    private final int x;
    private final int y;
    private static final int MAX_ROW = 51;
    private static final int MAX_COL = 51;

    /**
     * The constructor function. Initializes the position.
     * @param x the column number.
     * @param y the row number.
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the col information of the current position.
     * @return the x element.
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the row information of the current position.
     * @return the y element.
     */
    public int getY() {
        return y;
    }

    /**
     * Checks whether the position is valid on the board.
     * @return whether it is valid.
     */
    private boolean isValid() {
        return x >= 0 && y >= 0 && x < MAX_COL && y < MAX_ROW;
    }

    /**
     * Gets the adjacent position to a certain direction.
     * @param direction the direction of the adjacent position.
     * @return the adjacent position.
     */
    public Position getAdj(Direction direction) {
        int newX, newY;
        switch (direction) {
            case UP: newX = x; newY = y - 1; break;
            case DOWN: newX = x; newY = y + 1; break;
            case LEFT: newX = x - 1; newY = y; break;
            case RIGHT: newX = x + 1; newY = y; break;
            case UPLEFT: newX = x - 1; newY = y - 1; break;
            case UPRIGHT: newX = x + 1; newY = y - 1; break;
            case DOWNLEFT: newX = x - 1; newY = y + 1; break;
            case DOWNRIGHT: newX = x + 1; newY = y + 1; break;
            default: newX = x; newY = y; break;
        }
        Position p = new Position(newX, newY);
        if (p.isValid()) return p;
        return null;
    }

    @Override
    public boolean equals (Object o) {
        if (o == null) return false;
        if (o.getClass() != Position.class) return false;
        Position other = (Position)o;
        return other.x == x && other.y == y;
    }

    @Override
    public int hashCode() {
        return x * 101 + y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
