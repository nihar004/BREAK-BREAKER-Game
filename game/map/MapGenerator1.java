package game.map;

import java.awt.Graphics2D;
import java.awt.Color;
import game.bricks.Brick;

public class MapGenerator1 extends MapGenerator{
    public MapGenerator1() {
        int rows = 4;
        int cols = 10;
        bricks = new Brick[rows][cols];
        brickWidth = 60;
        brickHeight = 20; 
        brickSpacing = 10; 

        for (int i = 0; i < bricks.length; i++) {
            for (int j = 0; j < bricks[0].length; j++) {
                int brickX = j * (brickWidth + brickSpacing) + 50;
                int brickY = i * (brickHeight + brickSpacing) + 50;
                bricks[i][j] = new Brick(brickX, brickY, brickWidth, brickHeight);
            }
        }
    }

    @Override
    public void draw(Graphics2D g) {
        for (int i = 0; i < bricks.length; i++) {
            for (int j = 0; j < bricks[0].length; j++) {
                Brick brick = bricks[i][j];
                if (brick.isVisible()) {
                    g.setColor(Color.YELLOW);
                    g.fillRect(brick.getX(), brick.getY(), brick.getWidth(), brick.getHeight());
                }
            }
        }
    }

    @Override
    public int getTotalBricks() {
        return bricks.length * bricks[0].length;
    }
}
