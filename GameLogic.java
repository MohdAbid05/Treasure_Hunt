package treasure_hunt;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.LineBorder;

/**
 * Handles grid cell styling, hover effects, colors, and labels.
 */
public class GameLogic extends JPanel {
    private final int xcoord;
    private final int ycoord;
    private boolean marked;
    private final JLabel boxLabel;

    // Custom Color Palette
    private static final Color UNMARKED_BG = new Color(243, 244, 246);
    private static final Color HOVER_BG = new Color(229, 231, 235);
    private static final Color BORDER_COLOR = new Color(209, 213, 219);

    public static final Color P1_COLOR = new Color(37, 99, 235);   // Royal Blue
    public static final Color P2_COLOR = new Color(225, 29, 72);   // Crimson
    public static final Color TREASURE_COLOR = new Color(16, 185, 129); // Emerald Green

    public GameLogic(int xcoord, int ycoord) {
        super();
        this.xcoord = xcoord;
        this.ycoord = ycoord;
        this.marked = false;

        setLayout(new BorderLayout());
        setBackground(UNMARKED_BG);
        setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(BORDER_COLOR, 1, true),
            BorderFactory.createEmptyBorder(4, 4, 4, 4)
        ));

        boxLabel = new JLabel("", SwingConstants.CENTER);
        boxLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        boxLabel.setForeground(Color.WHITE);
        add(boxLabel, BorderLayout.CENTER);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (!marked) {
                    setBackground(HOVER_BG);
                    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!marked) {
                    setBackground(UNMARKED_BG);
                    setCursor(Cursor.getDefaultCursor());
                }
            }
        });
    }

    public void markCell(int player, int distance) {
        this.marked = true;
        setBackground(player == 1 ? P1_COLOR : P2_COLOR);
        boxLabel.setText(String.valueOf(distance));
        boxLabel.setForeground(Color.WHITE);
        boxLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
    }

    public void revealTreasure() {
        this.marked = true;
        setBackground(TREASURE_COLOR);
        boxLabel.setText("💎");
        boxLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 28));
    }

    public void resetCell() {
        this.marked = false;
        setBackground(UNMARKED_BG);
        boxLabel.setText("");
    }

    public boolean isMarked() { return marked; }
    public int getXcoord() { return xcoord; }
    public int getYcoord() { return ycoord; }
}