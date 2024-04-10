package game.gameplay;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.Timer;
import java.util.Random;
import game.map.*;
import game.bricks.Brick;
import game.levelselection.LevelSelectionPanel;


public class Gameplay extends JPanel implements ActionListener, KeyListener {
    private int paddleX;
    private int ballX, ballY;
    private int ballSpeedX, ballSpeedY;
    private Timer timer;
    private MapGenerator map;
    private int score;
    private boolean gameOver;
    private boolean enterPressed;
    private boolean win;
    private Random random;
    private int currentLevel;

    public Gameplay(int level) {
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        random = new Random();
        currentLevel = level;
    }

    public void initGame() {
        paddleX = 300;
        ballX = random.nextInt(100)+ 350;
        ballY = 300;
        ballSpeedX = random.nextBoolean() ? 3 : -3;
        ballSpeedY = 3;
        score = 0;
        gameOver = false;
        enterPressed = false;
        win = false;

        // Initialize the map based on the selected level
        switch (currentLevel) {
            case 1:
            map = new MapGenerator1();
            break;
            case 2:
            map = new MapGenerator2();
            break;
            case 3:
            map = new MapGenerator3();
            break;
            case 4:
            map = new MapGenerator4();
            break;
            default:
            map = new MapGenerator1(); // Default to level 1
        }
        
        timer = new Timer(1, this);
        timer.start();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver && !win) {
            ballX += ballSpeedX;
            ballY += ballSpeedY;


            if (ballX <= 0 || ballX >= 780) {
                ballSpeedX = -ballSpeedX;
            }

            if (ballY <= 0) {
                ballSpeedY = -ballSpeedY;
            }

            if (ballY >= 580) {
                // Game over when ball touches the bottom
                gameOver = true;
            }

            Rectangle ballRect = new Rectangle(ballX, ballY, 19, 19);
            Rectangle paddleRect = new Rectangle(paddleX, 550, 100, 10);

            if (ballRect.intersects(paddleRect)) {
                ballSpeedY = -ballSpeedY;
            }

            for (int i = 0; i < map.bricks.length; i++) {
                for (int j = 0; j < map.bricks[0].length; j++) {
                    Brick brick = map.bricks[i][j];
                    if (brick.isVisible()) {
                        Rectangle brickRect = brick.getBounds();

                        if (ballRect.intersects(brickRect)) {
                            brick.setVisible(false);
                            score += 10;
                            // Change ball direction when it hits a brick
                            ballSpeedX = random.nextBoolean() ? 3 : -3;
                            ballSpeedY = -ballSpeedY;
                        }
                    }
                }
            }

            // Check for win condition
            if (score == map.getTotalBricks() * 10) {
                win = true;
            }

            repaint();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (!gameOver && !win) {
            if (key == KeyEvent.VK_LEFT) { 
                if (paddleX > 0) {
                    paddleX -= 30;
                }
            } else if (key == KeyEvent.VK_RIGHT) {
                if (paddleX < 700) {
                    paddleX += 30; 
                }
            }
        } else {
            stopTimer();
            if (key == KeyEvent.VK_ENTER) {
                if (enterPressed) {
                    initGame();
                }
            }else if (key == KeyEvent.VK_ESCAPE) {
                    returnToLevelSelection();
            }
        }
    }

    private void returnToLevelSelection() {
        Window window = SwingUtilities.getWindowAncestor(this);
        window.dispose();

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Game Selection");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);

            LevelSelectionPanel levelSelectionPanel = new LevelSelectionPanel(frame);

            frame.add(levelSelectionPanel);
            frame.setVisible(true);
        });
    }   

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        setBackground(Color.BLACK);

        map.draw((Graphics2D) g);

        g.setColor(Color.BLUE);
        g.fillRect(paddleX, 550, 100, 10);

        g.setColor(Color.RED);
        g.fillOval(ballX, ballY, 20, 20);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("Score: " + score, 10, 30);

        if (gameOver) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("Game Over!", 305, 300);
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.drawString("Score: " + score, 380, 350);
            g.drawString("Press Enter to Restart the Game", 270, 400);
            g.drawString("Press ESC to Go Back", 315, 430);
            enterPressed = true;
        } else if (win) {
            g.setColor(Color.GREEN);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("You Win!", 335, 300);
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.drawString("Score: " + score, 380, 350);
            g.drawString("Press Enter to Restart the Game", 270, 400);
            g.drawString("Press ESC to Go Back", 315, 430);
            enterPressed = true;
        }
    }

    private void stopTimer() {
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }
    }
}
