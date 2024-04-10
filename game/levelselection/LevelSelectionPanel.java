package game.levelselection;

import javax.swing.*;
import java.awt.Color;
import java.awt.event.*;
import java.awt.Font;
import game.gameplay.Gameplay;

public class LevelSelectionPanel extends JPanel {
    private JButton level1Button;
    private JButton level2Button;
    private JButton level3Button;
    private JButton level4Button;
    private int selectedLevel = 1;

    public LevelSelectionPanel(JFrame frame) {
        setLayout(null);

        JLabel HeadingLabel = new JLabel("SELECT LEVEL");
        HeadingLabel.setBounds(200, 50, 400, 60);
        HeadingLabel.setForeground(Color.WHITE);
        Font labelFont = new Font("Chiller", Font.BOLD, 70);
        HeadingLabel.setFont(labelFont);
        HeadingLabel.setHorizontalAlignment(SwingConstants.CENTER);        
        add(HeadingLabel);

        int buttonWidth = 200;
        int buttonHeight = 50;

        level1Button = createLevelButton("Level 1", 1, buttonWidth, buttonHeight);
        level2Button = createLevelButton("Level 2", 2, buttonWidth, buttonHeight);
        level3Button = createLevelButton("Level 3", 3, buttonWidth, buttonHeight);
        level4Button = createLevelButton("Level 4", 4, buttonWidth, buttonHeight);

        add(level1Button);
        add(level2Button);
        add(level3Button);
        add(level4Button);

        setBackground(Color.BLACK);

        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_DOWN) {
                    selectedLevel = Math.min(selectedLevel + 1, 4);
                    updateButtonStyles();
                } else if (key == KeyEvent.VK_UP) {
                    selectedLevel = Math.max(selectedLevel - 1, 1);
                    updateButtonStyles();
                } else if (key == KeyEvent.VK_ENTER) {
                    frame.setVisible(false);
                    startGame(selectedLevel);
                }
            }
        });

        updateButtonStyles();
    }

    private JButton createLevelButton(String text, int level, int width, int height) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setBackground(Color.BLACK);
        button.setForeground(Color.YELLOW);
        button.setFont(new Font("Arial", Font.PLAIN, 20));
        button.setBounds(300, 150 + (level - 1) * 80, width, height);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectedLevel = level;
                updateButtonStyles();
            }
        });
        return button;
    }

    private void updateButtonStyles() {
        level1Button.setForeground(selectedLevel == 1 ? Color.RED : Color.YELLOW);
        level2Button.setForeground(selectedLevel == 2 ? Color.RED : Color.YELLOW);
        level3Button.setForeground(selectedLevel == 3 ? Color.RED : Color.YELLOW);
        level4Button.setForeground(selectedLevel == 4 ? Color.RED : Color.YELLOW);
    }

    private void startGame(int level) {
        SwingUtilities.invokeLater(() -> {    
            JFrame frame = new JFrame("Breakout Ball Game");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);

            Gameplay gameplay = new Gameplay(level);
            frame.add(gameplay);

            frame.setVisible(true);

            gameplay.initGame();  
        });
    }
}

