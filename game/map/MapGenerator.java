package game.map;

import java.awt.Graphics2D;
import game.bricks.Brick;

public abstract class MapGenerator{
    public Brick[][] bricks;
    public int brickWidth;
    public int brickHeight;
    public int brickSpacing;

    public abstract void draw(Graphics2D g);
    public abstract int getTotalBricks();
} 

