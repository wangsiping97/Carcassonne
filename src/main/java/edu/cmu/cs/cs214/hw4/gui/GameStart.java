package edu.cmu.cs.cs214.hw4.gui;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.GridLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * The start window of the game.
 */
public class GameStart extends JFrame {
    private final static ImageIcon BGIMAGE = new ImageIcon("src/main/resources/bg0.jpeg");
    private int numPlayers;

    /**
     * The constructor function of the start frame.
     */
    public GameStart() {
        numPlayers = 0;
        JPanel startPanel = new JPanel(new GridLayout(3, 1, 50, 200));
        startPanel.setBackground(null);
        startPanel.setOpaque(false);
        JLabel playerLabel = new JLabel("Number of Players: ");
        JTextField playerText = new JTextField(20);
        JPanel playerPanel = new JPanel(new GridLayout(1, 2));
        playerPanel.add(playerLabel);
        playerPanel.add(playerText);
        JButton startButton = new JButton("START");
        startButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                try {
                    numPlayers = Integer.valueOf(playerText.getText().trim());
                } catch (Exception ex) {
                    numPlayers = 0;
                }
                if (numPlayers < 2 || numPlayers > 5) return;

                try {
                    new Game(numPlayers);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                closeThis();
            }
        });
        JButton exitButton = new JButton("EXIT");
        exitButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                closeThis();
            }
        });
        startPanel.add(playerPanel);
        startPanel.add(startButton);
        startPanel.add(exitButton);
        this.setSize(new Dimension(800, 650));
        this.addBgImage();
        this.setLocationRelativeTo(null);
        Container contain = this.getContentPane();
        ((JPanel) contain).setOpaque(false);
        contain.add(startPanel, BorderLayout.CENTER);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    private void addBgImage() {
        Image suitableImg = BGIMAGE.getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_FAST);
        ImageIcon bgimg = new ImageIcon(suitableImg);
        JLabel imgLabel = new JLabel(bgimg);
        this.getLayeredPane().add(imgLabel, new Integer(Integer.MIN_VALUE));
        imgLabel.setBounds(0, 0, this.getWidth(), this.getHeight());
    }

    private void closeThis() {
        this.dispose();
    }
}