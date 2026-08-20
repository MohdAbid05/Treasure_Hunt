/*
 *  GUI demonstration using swing.
 *  Here the GameLogic class extends JPanel and
 *  holds its coordinates in the grid as attributes.
 *  @author mAbid
 */
package treasure_hunt;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Main Entry Point for Treasure Hunt Game.
 */
public class Driver {
    public static void main(String[] args) {
        // Apply system native UI style
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> {
            GameScreen game = new GameScreen();
            game.setVisible(true);
        });
    }
}
