package edu.cmu.cs.cs214.hw4.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Iterator;

/**
 * The main class of the core, containing operations during the game.
 */
public class GameManager {
    private Tile currTile;
    private Position currPosition;
    private final int numPlayers;
    private int currPlayerId;
    private final Board board;
    private final Deck deck;
    private final Iterator<Tile> iterator;
    private final List<Player> playerList;
    private final Map<Integer, Integer> scores;
    private List<GameChangeListener> listeners;
    private static final Direction[] DIRECTIONS = {Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT};

    /**
     * Constructor function. Initializes basic game information.
     * @param n number of players.
     */
    public GameManager(int n) {
        numPlayers = n;
        playerList = new ArrayList<>();
        scores = new HashMap<>();
        for (int i = 1; i <= n; ++i) {
            playerList.add(new Player(i));
            scores.put(i, 0);
        }
        currPlayerId = 0;
        board = Board.getInstance();
        deck = Deck.getInstance();
        iterator = deck.iterator();
        currTile = null;
        listeners = new ArrayList<>();
    }

    /**
     * Adds listeners to the listeners list.
     * @param listener Class object implements GameChangeListener.
     */
    public void addGameChangeListener(GameChangeListener listener) {
        listeners.add(listener);
    }

    private void notifyTilePlaced(Tile tile, Position p, int rotate) {
        for (GameChangeListener listener: listeners) {
            listener.placeTile(tile, p, rotate);
        }
    }

    private void notifyMeeplePlaced(Player player, Direction d) {
        for (GameChangeListener listener: listeners) {
            listener.placeMeeple(player, d);
        }
    }

    private void notifyNextRound() {
        for (GameChangeListener listener: listeners) {
            listener.nextRound();
        }
    }

    private void notifyReturnMeeples(Set<Segment> content) {
        for (GameChangeListener listener: listeners) {
            listener.returnMeeples(content);
        }
    }

    private void notifyUpdateScoreBoard() {
        for (GameChangeListener listener: listeners) {
            listener.updateScoreBoard();
        }
    }

    private void notifyEndGame() {
        for (GameChangeListener listener: listeners) {
            listener.endGame();
        }
    }

    /**
     * Gets the next tile from the decker.
     * @return the next tile.
     */
    public Tile getNextTile() {
        while (iterator.hasNext()) {
            Tile tile = iterator.next();
            if (!checkValidCurrentTile(tile)) continue;
            return tile;
        }
        return null;
    }

    /**
     * Gets the current tile.
     * @return the current tile.
     */
    public Tile getCurrTile() {
        return currTile;
    }

    /**
     * Generates the current player.
     */
    public void getCurrPlayerId() {
        if (currPlayerId == numPlayers) {
            currPlayerId = 1;
        } else currPlayerId++;
    }

    /**
     * Gets the player list.
     * @return the player list.
     */
    public List<Player> getPlayerList() {
        return playerList;
    }

    /**
     * Gets the score map.
     * @return the score map.
     */
    public Map<Integer, Integer> getScores() {
        return scores;
    }

    /**
     * Gets the current player.
     * @return the current player.
     */
    public Player getCurrPlayer() {
        return playerList.get(currPlayerId - 1);
    }

    //================== CHECK CURRENT TILE IS VALID ======================

    /**
     * When decker hands out a tile, checks whether the tile is valid.
     * @param tile the current tile.
     * @return whether the tile is valid.
     */
    public boolean checkValidCurrentTile(Tile tile) {
        Map<Position, Tile> grid = board.getGrid();
        if (grid.isEmpty()) return true;
        for (Position p : grid.keySet()) {
            for (Direction direction : DIRECTIONS) {
                Position adjP = p.getAdj(direction);
                if (board.getTile(adjP) == null) {
                    for (int r = 0; r <= 270; r += 90)
                        if (checkLegalPlaceTile(tile, adjP, 0)) return true;
                }
            }
        }
        return false;
    }

    //================== PLACE TILE ======================

    /**
     * When user decides to place the current tile on position `p`
     * with rotation = `r`, checks whether the placement is legal.
     * @param tile the current tile.
     * @param p the position that the tile is to be placed on.
     * @param r the rotation of the tile.
     * @return whether this operation is legal.
     */
    public boolean checkLegalPlaceTile(Tile tile, Position p, int r) {
        if (board.getTile(p) != null) return false;
        boolean hasNeighbor = false;
        for (Direction direction : DIRECTIONS) {
            Tile neighbor = board.getTile(p.getAdj(direction));
            if (neighbor == null) continue;
            hasNeighbor = true;
            if (!tile.getSegment(direction, r).getType().matches(neighbor.getSegment(direction.getOppo(), board.getRotate(neighbor)).getType()))
                return false;
        }
        return hasNeighbor;
    }

    /**
     * Places the tile on the board after passing the legal check.
     * @param tile the current tile.
     * @param p the position decided by the user.
     * @param r the rotation decided by the user.
     */
    public void placeTile(Tile tile, Position p, int r) {
        currTile = tile;
        currPosition = p;
        board.place(currTile, p, r);
        // update the feature graph from `tile` itself
        currTile.getSegment(Direction.CENTER, r).setPosition(p);
        for (Direction direction : DIRECTIONS) {
            Segment thisSeg = currTile.getSegment(direction, r);
            thisSeg.setPosition(p);
            if (thisSeg.getType() == SegmentType.FIELD) continue;
            board.getFeatures().put(thisSeg, new ArrayList<>());
            for (Direction direction1 : DIRECTIONS) {
                if (direction1 == direction) continue;
                Segment seg = currTile.getSegment(direction1, r);
                if (!thisSeg.getType().matches(seg.getType())) continue;
                if (thisSeg.getType() == SegmentType.ROADEND && seg.getType() == SegmentType.ROADEND) continue;
                if (thisSeg.getType() == SegmentType.CITYWALL && seg.getType() == SegmentType.CITYWALL) continue;
                board.connectFeature(thisSeg, seg);
            }
        }
        // update the feature graph from neighbors
        for (Direction direction : DIRECTIONS) {
            Tile neighbor = board.getTile(p.getAdj(direction));
            if (neighbor == null) continue;
            Segment thisSeg = currTile.getSegment(direction, r);
            if (thisSeg.getType() == SegmentType.FIELD) continue;
            Segment seg = neighbor.getSegment(direction.getOppo(), board.getRotate(neighbor));
            board.connectFeature(thisSeg, seg);
            board.connectFeature(seg, thisSeg);
        }

        notifyTilePlaced(tile, p, r);
    }

    //================== PLACE MEEPLES ======================

    /**
     * Checks whether the current player has meeples to place.
     * @return whether a meeple can be placed by the player.
     */
    public boolean checkMeepleValid() {
        return !(getCurrPlayer().getUsedMeeple() == 7);
    }

    /**
     * Checks whether the user has placed the meeple on a legal place.
     * @param direction the direction of the tile that the user decides to place the meeple on.
     * @return whether this operation is legal.
     */
    public boolean checkLegalPlaceMeeple(Direction direction) {
        Segment seg = currTile.getSegment(direction, board.getRotate(currTile));
        if (seg.getType() == SegmentType.FIELD || seg.getType() == SegmentType.NONE) return false;
        if (seg.getType() == SegmentType.MONASTERY) return true;
        // bfs from `seg` to get all the segments linked with it
        Set<Segment> visited = new HashSet<>();
        Queue<Segment> queue = new LinkedList<>();
        queue.add(seg);
        while (!queue.isEmpty()) {
            Segment head = queue.poll();
            if (head.getPlayer() != null) return false;
            visited.add(head);
            List<Segment> list = board.getFeatures().get(head);
            if (list == null) continue;
            for (Segment segment : list) {
                if (visited.contains(segment)) continue;
                queue.add(segment);
            }
        }
        return true;
    }

    /**
     * Places the meeple on the board after passing the legal check.
     * @param direction the direction of the tile that the user decides to place the meeple on.
     */
    public void placeMeeple(Direction direction) {
        Segment seg = currTile.getSegment(direction, board.getRotate(currTile));
        seg.setPlayer(getCurrPlayer());
        getCurrPlayer().addUsedMeeple();

        notifyMeeplePlaced(getCurrPlayer(), direction);
    }

    //================== UPDATING AND SCORING ======================

    /**
     * Updates the features and scores after the current tile (and meeple) has been placed.
     */
    public void update() {
        updateMonastery(currPosition, true);
        updateFeatures(currPosition, true);
        updateScores();

        notifyNextRound();
    }

    /**
     * Updates the score of a monastery.
     * @param position the position that the updating process is going to be start with.
     * @param needComplete whether the scoring process requires the feature to be completed.
     */
    private void updateMonastery(Position position, boolean needComplete) {
        for (Direction direction : Direction.values()) {
            Tile neighbor = board.getTile(position.getAdj(direction));
            if (neighbor == null) continue;
            Segment centerSeg = neighbor.getSegment(Direction.CENTER, board.getRotate(neighbor));
            if (centerSeg.getType() != SegmentType.MONASTERY || centerSeg.getScored()) continue;
            Feature monastery = new Monastery(centerSeg);
            monastery.complete();
            if (needComplete && !monastery.isComplete()) continue;
            addScores(monastery.getOwners(), monastery.getScore());

            notifyReturnMeeples(monastery.getContent());
        }
    }

    /**
     * Updates the score of a road or a city.
     * @param position the position that the updating process is going to be start with.
     * @param needComplete whether the scoring process requires the feature to be completed.
     */
    private void updateFeatures(Position position, boolean needComplete) {
        Tile tile = board.getTile(position);
        for (Direction direction : DIRECTIONS) {
            Segment seg = tile.getSegment(direction, board.getRotate(tile));
            if (seg.getType() == SegmentType.FIELD || seg.getScored()) continue;
            Feature feature;
            if (seg.getType().matches(SegmentType.ROAD)) feature = new Road(seg);
            else feature = new City(seg);
            feature.complete();
            if (needComplete && !feature.isComplete()) continue;
            addScores(feature.getOwners(), feature.getScore());

            notifyReturnMeeples(feature.getContent());
        }
    }

    /**
     * Add scores to the owners of the feature and return meeples to the players.
     * @param owners the owners of the current feature
     * @param score the score of the current feature
     */
    private void addScores(Map<Player, Integer> owners, int score) {
        int maxMeeples = 0;
        for (int val : owners.values()) {
            if (val > maxMeeples)
                maxMeeples = val;
        }

        for (Player player : owners.keySet()) {
            int n = owners.get(player);
            if (n == maxMeeples) {
                player.addScore(score);
            }
            player.returnMeeple(n);
        }
    }

    /**
     * Loads the updated scores to the score map.
     */
    private void updateScores() {
        for (Player player : playerList) {
            scores.put(player.getId(), player.getScore());
        }

        notifyUpdateScoreBoard();
    }

    /**
     * The scoring process after all tiles are placed or returned.
     */
    public void endGameScoring() {
        Map<Position, Tile> grid = board.getGrid();
        for (Position p : grid.keySet()) {
            updateMonastery(p, false);
            updateFeatures(p, false);
        }
        updateScores();

        notifyEndGame();
    }
}
