package treasure_hunt;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * Main GUI Window and game logic controller.
 */
public class GameScreen extends JFrame implements ActionListener, MouseListener {
    private JLabel instructionLabel, scoreLabel;
    private JButton newGameButton;
    private GameLogic[][] gridSquares;

    private boolean gameStarted = false;
    private boolean gameEnded = false;
    private int treasureX, treasureY;
    private int turn = 0;
    private int p1Wins = 0, p2Wins = 0;

    public GameScreen() {
        setTitle("🏴‍☠️ Treasure Hunt");
        setSize(600, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        getContentPane().setBackground(new Color(248, 250, 252));
        getContentPane().setLayout(new BorderLayout(15, 15));

        // Top Banner
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(new Color(15, 23, 42));
        headerPanel.setBorder(new EmptyBorder(16, 20, 16, 20));

        JLabel titleLabel = new JLabel("TREASURE HUNT");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(new Color(248, 250, 252));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Find the hidden diamond!");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitleLabel.setForeground(new Color(148, 163, 184));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(titleLabel);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 4)));
        headerPanel.add(subtitleLabel);

        // Control Panel
        JPanel controlPanel = new JPanel(new BorderLayout(10, 10));
        controlPanel.setOpaque(false);
        controlPanel.setBorder(new EmptyBorder(10, 20, 0, 20));

        instructionLabel = new JLabel("Click \"New Game\" to start!", SwingConstants.CENTER);
        instructionLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));

        newGameButton = new JButton(" New Game ");
        newGameButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        newGameButton.setFocusPainted(false);
        newGameButton.setBackground(new Color(209, 213, 219));
        newGameButton.setForeground(Color.BLACK);
        newGameButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        newGameButton.addActionListener(this);

        controlPanel.add(instructionLabel, BorderLayout.CENTER);
        controlPanel.add(newGameButton, BorderLayout.EAST);

        // Grid (5x5)
        JPanel gridPanel = new JPanel(new GridLayout(5, 5, 8, 8));
        gridPanel.setOpaque(false);
        gridPanel.setBorder(new EmptyBorder(10, 20, 10, 20));

        gridSquares = new GameLogic[5][5];
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                gridSquares[x][y] = new GameLogic(x, y);
                gridSquares[x][y].addMouseListener(this);
                gridPanel.add(gridSquares[x][y]);
            }
        }

        // Scoreboard
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footerPanel.setBackground(new Color(241, 245, 249));
        scoreLabel = new JLabel("Score - Player 1 (Blue): 0  |  Player 2 (Red): 0");
        scoreLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        footerPanel.add(scoreLabel);

        JPanel topContainer = new JPanel(new BorderLayout());
        topContainer.setOpaque(false);
        topContainer.add(headerPanel, BorderLayout.NORTH);
        topContainer.add(controlPanel, BorderLayout.SOUTH);

        getContentPane().add(topContainer, BorderLayout.NORTH);
        getContentPane().add(gridPanel, BorderLayout.CENTER);
        getContentPane().add(footerPanel, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(newGameButton)) {
            gameEnded = false;
            gameStarted = true;
            Random rand = new Random();
            treasureX = rand.nextInt(5);
            treasureY = rand.nextInt(5);
            turn = rand.nextInt(2);

            for (int x = 0; x < 5; x++) {
                for (int y = 0; y < 5; y++) {
                    gridSquares[x][y].resetCell();
                }
            }
            updateTurnBanner();
        }
    }

    private void updateTurnBanner() {
        int activePlayer = (turn % 2 == 0) ? 1 : 2;
        if (activePlayer == 1) {
            instructionLabel.setText(" Player 1's Turn");
            instructionLabel.setForeground(GameLogic.P1_COLOR);
        } else {
            instructionLabel.setText(" Player 2's Turn");
            instructionLabel.setForeground(GameLogic.P2_COLOR);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() instanceof GameLogic && gameStarted && !gameEnded) {
            GameLogic clickedCell = (GameLogic) e.getSource();
            if (clickedCell.isMarked()) return;

            int activePlayer = (turn % 2 == 0) ? 1 : 2;

            if (clickedCell.getXcoord() == treasureX && clickedCell.getYcoord() == treasureY) {
                clickedCell.revealTreasure();
                gameEnded = true;
                gameStarted = false;

                if (activePlayer == 1) p1Wins++; else p2Wins++;
                instructionLabel.setText(" Player " + activePlayer + " found the Treasure! 💎");
                scoreLabel.setText("Score - Player 1 (Blue): " + p1Wins + "  |  Player 2 (Red): " + p2Wins);
            } else {
                int dist = Math.abs(treasureX - clickedCell.getXcoord()) + Math.abs(treasureY - clickedCell.getYcoord());
                clickedCell.markCell(activePlayer, dist);
                turn++;
                updateTurnBanner();
            }
        }
    }

    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
}