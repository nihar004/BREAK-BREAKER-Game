package game.map;

import java.awt.Graphics2D;
import java.awt.Color;
import game.bricks.Brick;

public class MapGenerator2 extends MapGenerator{
    public MapGenerator2() {
        int rows = 6;
        int cols = 11;
        bricks = new Brick[rows][cols];
        brickWidth = 56;
        brickHeight = 15; 
        brickSpacing = 8;

        for (int i = 0; i < bricks.length; i++) {
            for (int j = 0; j < bricks[i].length; j++) {
                int brickX = j * (brickWidth + brickSpacing) + 50;
                int brickY = i * (brickHeight + brickSpacing) + 50;
                bricks[i][j] = new Brick(brickX, brickY, brickWidth, brickHeight);

                if(j<i || j>=(cols-i) ){
                    bricks[i][j].setVisible(false);
                }
            } 
        }
    }

    @Override
    public void draw(Graphics2D g) {
        for (int i = 0; i < bricks.length; i++) {
            for (int j = 0; j < bricks[i].length; j++) {
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

