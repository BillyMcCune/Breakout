package classes;
import java.util.Random;
import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;

public class Block {
    private int BLOCKWIDTH;
    private int BLOCKHEIGHT;
    private Point2D position;
    private Rectangle block;
    private int health;

    public Block(int BLOCKWIDTH, int BLOCKHEIGHT, int health, int x, int y) {
      this.position = new Point2D(x, y);
      this.BLOCKWIDTH = BLOCKWIDTH;
      this.BLOCKHEIGHT = BLOCKHEIGHT;
      this.health = health;
      this.block = new Rectangle(x, y, BLOCKWIDTH, BLOCKHEIGHT);
    }

    public Rectangle getBlock() {
      return this.block;
    }

    public int getHealth() {
      return this.health;
    }

    public void setHealth(int health) {
      this.health = health;
    }
}
