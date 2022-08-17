package edu.cmu.cs.cs214.hw4.gui;

import edu.cmu.cs.cs214.hw4.core.GameManager;
import edu.cmu.cs.cs214.hw4.core.GameChangeListener;
import edu.cmu.cs.cs214.hw4.core.Player;
import edu.cmu.cs.cs214.hw4.core.Tile;
import edu.cmu.cs.cs214.hw4.core.Position;
import edu.cmu.cs.cs214.hw4.core.Direction;
import edu.cmu.cs.cs214.hw4.core.Segment;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.ButtonGroup;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;

/**
 * The GUI frame of a running game.
 */
public class Game extends JFrame implements GameChangeListener {
    private final GameManager gameManager;
    private Tile nextTile;
    private JButton currTileButton;
    private final JButton nextTileButton;
    private final Map<Position, ImageIcon> meepleMap;
    private int currRotate;
    private Position currPosition;
    private final List<BufferedImage> tileImages;
    private final JButton[][] board;
    private JPanel scoreBoard;
    private final JLabel[][] scoreLabels;
    private final JLabel informationLabel;
    private final JLabel currentPlayerLabel;
    private final JButton placeMeeple;
    private static final int WIDTH = 90;
    private static final int INITIAL_BOARD_WIDTH = 51;
    private static final int CONFIG_ROW = 4;
    private static final int CONFIG_COL = 6;
    private static final int DOT_RADIUS = 10;
    private static final int DOT_OFFSET = 10;
    private static final Color[] PLAYERCOLOR = {Color.red, Color.blue, Color.green, Color.yellow, Color.black};

    /**
     * The constructor function for the GUI frame.
     * @param n the number of players.
     */
    public Game(int n) {
        // set up frame
        setSize(1000, 1000);
        setLocationRelativeTo(null);
        Container contain = this.getContentPane();
        ((JPanel) contain).setOpaque(false);

        gameManager = new GameManager(n);
        gameManager.addGameChangeListener(this);
        tileImages = new ArrayList<>();
        meepleMap = new HashMap<>();
        board = new JButton[INITIAL_BOARD_WIDTH][INITIAL_BOARD_WIDTH];
        scoreLabels = new JLabel[n + 1][3];

        // start game
        Tile initTile = gameManager.getNextTile();
        gameManager.placeTile(initTile, new Position(INITIAL_BOARD_WIDTH / 2, INITIAL_BOARD_WIDTH / 2), 0);
        newRound();

        try {
            BufferedImage image = ImageIO.read(new File("src/main/resources/Carcassonne.png"));
            for (int i = 0; i < CONFIG_ROW; i++) {
                for (int j = 0; j < CONFIG_COL; j++) {
                    BufferedImage tileImage = image.getSubimage(j * WIDTH,
                            i * WIDTH, WIDTH, WIDTH);
                    tileImages.add(tileImage);
                }
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("Error when reading Carcassonne.png");
        }
        // set up the current player label
        informationLabel = new JLabel("Game Information: ");
        currentPlayerLabel = new JLabel("Current Player: " + gameManager.getCurrPlayer().getId());
        JPanel northPanel = new JPanel(new GridLayout(2, 1));
        northPanel.add(currentPlayerLabel);
        northPanel.add(informationLabel);
        contain.add(northPanel, BorderLayout.NORTH);

        // set up game board
        JPanel gameBoard = createGameBoard(initTile);
        JScrollPane scrollBoard = new JScrollPane(gameBoard);
        contain.add(scrollBoard, BorderLayout.CENTER);

        // set up the nextTile button
        currTileButton = new JButton();
        nextTileButton = new JButton();
        nextTileButton.setIcon(new ImageIcon(getTileImage(nextTile, 0)));
        nextTileButton.addActionListener(e -> {
            if (currRotate == 270) {
                currRotate = 0;
            }
            else currRotate += 90;
            nextTileButton.setIcon(new ImageIcon(getTileImage(nextTile, currRotate)));
        });

        contain.add(nextTileButton, BorderLayout.EAST);

        // set up place meeple button group
        ButtonGroup directionGroup = new ButtonGroup();
        JRadioButton north = new JRadioButton("NORTH");
        JRadioButton south = new JRadioButton("SOUTH");
        JRadioButton east = new JRadioButton("EAST");
        JRadioButton west = new JRadioButton("WEST");
        JRadioButton center = new JRadioButton("MONASTERY");
        JRadioButton noPlace = new JRadioButton("NO PLACE");
        directionGroup.add(north);
        directionGroup.add(south);
        directionGroup.add(east);
        directionGroup.add(west);
        directionGroup.add(center);
        directionGroup.add(noPlace);

        // set up place meeple button
        placeMeeple = new JButton("Confirm Meeple");
        placeMeeple.setEnabled(false);
        placeMeeple.addActionListener(e -> {
            if (north.isSelected()) {
                if (!gameManager.checkLegalPlaceMeeple(Direction.UP)) {
                    informationLabel.setText("Game Information: Illegal Meeple Placement!");
                    return;
                }
                informationLabel.setText("Game Information: Meeple Placed!");
                gameManager.placeMeeple(Direction.UP);
            }
            else if (west.isSelected()) {
                if (!gameManager.checkLegalPlaceMeeple(Direction.LEFT)) {
                    informationLabel.setText("Game Information: Illegal Meeple Placement!");
                    return;
                }
                informationLabel.setText("Game Information: Meeple Placed!");
                gameManager.placeMeeple(Direction.LEFT);
            }
            else if (south.isSelected()) {
                if (!gameManager.checkLegalPlaceMeeple(Direction.DOWN)) {
                    informationLabel.setText("Game Information: Illegal Meeple Placement!");
                    return;
                }
                informationLabel.setText("Game Information: Meeple Placed!");
                gameManager.placeMeeple(Direction.DOWN);
            }
            else if (east.isSelected()) {
                if (!gameManager.checkLegalPlaceMeeple(Direction.RIGHT)) {
                    informationLabel.setText("Game Information: Illegal Meeple Placement!");
                    return;
                }
                informationLabel.setText("Game Information: Meeple Placed!");
                gameManager.placeMeeple(Direction.RIGHT);
            }
            else if (center.isSelected()) {
                if (!gameManager.checkLegalPlaceMeeple(Direction.CENTER)) {
                    informationLabel.setText("Game Information: Illegal Meeple Placement!");
                    return;
                }
                informationLabel.setText("Game Information: Meeple Placed!");
                gameManager.placeMeeple(Direction.CENTER);
            }
            else {
                informationLabel.setText("Game Information: No Meeple Placement.");
                gameManager.update();
            }
            placeMeeple.setEnabled(false);
        });

        JPanel buttonField = new JPanel(new GridLayout(1, 7));
        buttonField.add(north);
        buttonField.add(south);
        buttonField.add(west);
        buttonField.add(east);
        buttonField.add(center);
        buttonField.add(noPlace);
        buttonField.add(placeMeeple);

        contain.add(buttonField, BorderLayout.SOUTH);

        // set up score board
        createScoreBoard(n);
        contain.add(scoreBoard, BorderLayout.WEST);

        // display the frame
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void newRound() {
        nextTile = gameManager.getNextTile();
        gameManager.getCurrPlayerId();
        currRotate = 0;
    }

    private int getTileIdx(Tile tile) {
        return tile.getId().toCharArray()[0] - 'A';
    }

    private BufferedImage getTileImage(Tile tile, int rotate) {
        int idx = getTileIdx(tile);
        BufferedImage bi = tileImages.get(idx);
        bi = rotateClockwise(bi, rotate / 90);
        return bi;
    }

    private static BufferedImage rotateClockwise(BufferedImage src, int n) {
        int weight = src.getWidth();
        int height = src.getHeight();

        AffineTransform at = AffineTransform.getQuadrantRotateInstance(n, weight / 2.0, height / 2.0);
        AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);

        BufferedImage dest = new BufferedImage(weight, height, src.getType());
        op.filter(src, dest);
        return dest;
    }

    private JPanel createGameBoard(Tile initTile) {
        JButton initTileButton = new JButton();
        initTileButton.setIcon(new ImageIcon(getTileImage(initTile, 0)));

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(INITIAL_BOARD_WIDTH, INITIAL_BOARD_WIDTH));

        for (int row = 0; row < INITIAL_BOARD_WIDTH; row++) {
            for (int col = 0; col < INITIAL_BOARD_WIDTH; col++) {
                if (row == INITIAL_BOARD_WIDTH / 2 && col == INITIAL_BOARD_WIDTH / 2) {
                    panel.add(initTileButton);
                }
                else {
                    board[row][col] = new JButton();
                    int r = row;
                    int c = col;
                    board[row][col].addActionListener(e -> {
                        if (nextTile == null) return;
                        Position p = new Position(c, r);
                        if (!gameManager.checkLegalPlaceTile(nextTile, p, currRotate)) {
                            informationLabel.setText("Game Information: Illegal Tile Placement!");
                            return;
                        }
                        informationLabel.setText("Game Information: Tile Placed!");
                        gameManager.placeTile(nextTile, p, currRotate);
                    });
                    panel.add(board[row][col]);
                }
            }
        }
        return panel;
    }

    private void createScoreBoard(int numPlayers) {
        scoreBoard = new JPanel();
        scoreBoard.setLayout(new GridLayout(numPlayers + 1, 3, 10, 10));
        updateScoreBoard();
    }

    private static BufferedImage withCircle(BufferedImage src, Color color, int x, int y) {
        BufferedImage dest = new BufferedImage(src.getWidth(), src.getHeight(), src.getType());

        Graphics2D g = (Graphics2D) dest.getGraphics();
        g.drawImage(src, 0, 0, null);
        g.setColor(color);
        g.fillOval(x - DOT_RADIUS, y - DOT_RADIUS, 2 * DOT_RADIUS, 2 * DOT_RADIUS);
        g.dispose();

        return dest;
    }

    @Override
    public void placeTile(Tile tile, Position p, int rotate) {
        currPosition = p;
        int x = p.getX(), y = p.getY();
        if (x == INITIAL_BOARD_WIDTH / 2 && y == INITIAL_BOARD_WIDTH / 2) return;
        board[y][x].setIcon(new ImageIcon(getTileImage(tile, rotate)));
        currTileButton = board[y][x];
        nextTileButton.setEnabled(false);
        for (int i = 0; i < INITIAL_BOARD_WIDTH; ++i) {
            for (int j = 0; j < INITIAL_BOARD_WIDTH; ++j) {
                if (i == INITIAL_BOARD_WIDTH / 2 && j == INITIAL_BOARD_WIDTH / 2) continue;
                if (board[i][j].getIcon() != null) continue;
                board[i][j].setEnabled(false);
            }
        }
        if (!gameManager.checkMeepleValid()) {
            gameManager.update();
            return;
        }
        placeMeeple.setEnabled(true);
    }

    @Override
    public void placeMeeple(Player player, Direction d) {
        // place meeple
        int x, y;
        switch(d) {
            case UP:
                x = WIDTH / 2;
                y = DOT_OFFSET;
                break;
            case LEFT:
                x = DOT_OFFSET;
                y = WIDTH / 2;
                break;
            case DOWN:
                x = WIDTH / 2;
                y = WIDTH - DOT_OFFSET;
                break;
            case RIGHT:
                x = WIDTH - DOT_OFFSET;
                y = WIDTH / 2;
                break;
            case CENTER:
                x = WIDTH / 2;
                y = WIDTH / 2;
                break;
            default:
                throw new IllegalArgumentException("Illegal Direction");
        }

        BufferedImage lastPlaced = getTileImage(nextTile, currRotate);
        BufferedImage meeple = withCircle(lastPlaced, PLAYERCOLOR[gameManager.getCurrPlayer().getId() - 1], x, y);
        currTileButton.setIcon(new ImageIcon(meeple));
        meepleMap.put(currPosition, new ImageIcon(lastPlaced));
        gameManager.update();
    }

    @Override
    public void nextRound() {
        nextTile = gameManager.getNextTile();
        if (nextTile == null) {
            gameManager.endGameScoring();
            return;
        }
        gameManager.getCurrPlayerId();
        currRotate = 0;
        currentPlayerLabel.setText("Current Player: " + gameManager.getCurrPlayer().getId());
        nextTileButton.setIcon(new ImageIcon(getTileImage(nextTile, 0)));
        nextTileButton.setEnabled(true);
        for (int i = 0; i < INITIAL_BOARD_WIDTH; ++i) {
            for (int j = 0; j < INITIAL_BOARD_WIDTH; ++j) {
                if (i == INITIAL_BOARD_WIDTH / 2 && j == INITIAL_BOARD_WIDTH / 2) continue;
                board[i][j].setEnabled(true);
            }
        }
    }

    @Override
    public void returnMeeples(Set<Segment> content) {
        for (Segment seg : content) {
            if (seg.getPlayer() == null) continue;
            Position p = seg.getPosition();
            int x = p.getX(), y = p.getY();
            ImageIcon icon = meepleMap.get(p);
            board[y][x].setIcon(icon);
        }
    }

    @Override
    public void updateScoreBoard() {
        scoreBoard.removeAll();
        scoreLabels[0][0] = new JLabel("Player ID");
        scoreLabels[0][1] = new JLabel("Score");
        scoreLabels[0][2] = new JLabel("Used Meeple(s)");
        scoreBoard.add(scoreLabels[0][0]);
        scoreBoard.add(scoreLabels[0][1]);
        scoreBoard.add(scoreLabels[0][2]);

        int i = 1;
        for (Player player : gameManager.getPlayerList()) {
            scoreLabels[i][0] = new JLabel("Player " + player.getId());
            scoreLabels[i][1] = new JLabel(String.valueOf(player.getScore()));
            scoreLabels[i][2] = new JLabel(String.valueOf(player.getUsedMeeple()));
            scoreLabels[i][0].setForeground(PLAYERCOLOR[player.getId() - 1]);
            scoreBoard.add(scoreLabels[i][0]);
            scoreBoard.add(scoreLabels[i][1]);
            scoreBoard.add(scoreLabels[i][2]);
            i++;
        }
    }

    @Override
    public void endGame() {
        StringBuilder sb = new StringBuilder();
        Map<Integer, Integer> scores = gameManager.getScores();
        int maxScore = 0;
        for (int score : scores.values()) {
            if (score > maxScore) {
                maxScore = score;
            }
        }
        for (int playerId : scores.keySet()) {
            if (scores.get(playerId) == maxScore)
                sb.append(String.format("Player %d;", playerId));
        }
        informationLabel.setText("Game Information: Game Over. Winner: " + sb.toString());
    }
}
