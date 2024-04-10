package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.image.BufferedImage;
import game.levelselection.LevelSelectionPanel;


public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            createAndShowCoverPage();
        });
    }

    private static void createAndShowCoverPage() {
        JFrame frame = new JFrame("Game Cover Page");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        try {
            BufferedImage coverImage = ImageIO.read(new File("game/cover-start.jpg"));

            JLabel coverLabel = new JLabel(new ImageIcon(coverImage));
            coverLabel.setPreferredSize(new Dimension(frame.getWidth(), frame.getHeight()));

            frame.add(coverLabel);
            frame.setVisible(true);

            Timer timer = new Timer(1500, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.setVisible(false);
                    frame.dispose();
                    showLevelSelection();
                }
            });
            timer.setRepeats(false);
            timer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void showLevelSelection() {
        JFrame frame = new JFrame("Game Selection");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        LevelSelectionPanel levelSelectionPanel = new LevelSelectionPanel(frame);

        frame.add(levelSelectionPanel);
        frame.setVisible(true);
    }
}