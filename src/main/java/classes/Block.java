package classes;

import java.util.List;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Block {

  private int BLOCKWIDTH;
  private int BLOCKHEIGHT;
  private Point2D position;
  private Rectangle block;
  private int health;
  private Color color;
  private Color outlineColor = Color.BLACK;
  private int outlineWidth = 2;
  private boolean hasPowerUp = false;
  private PowerUp powerUp;
  private boolean isExplodingType = false;
  private int ExplosionDamage = 2;

  public Block(int BLOCKWIDTH, int BLOCKHEIGHT, int health, int x, int y, Color color) {
    this.color = color;
    this.position = new Point2D(x, y);
    this.BLOCKWIDTH = BLOCKWIDTH;
    this.BLOCKHEIGHT = BLOCKHEIGHT;
    this.health = health;
    this.block = new Rectangle(x, y, BLOCKWIDTH, BLOCKHEIGHT);
    this.block.setFill(color);
    this.block.setStroke(outlineColor);
    this.block.setStrokeWidth(outlineWidth);
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

  public Color getColor() {
    return this.color;
  }

  public boolean hasPowerUp() {
    return this.hasPowerUp;
  }

  public void givePowerUp(PowerUp powerUp) {
    this.hasPowerUp = true;
    this.powerUp = powerUp;
  }

  public PowerUp getPowerUpForUse() {
    this.hasPowerUp = false;
    return this.powerUp;
  }

  public String getPowerUpName() {
    if (this.hasPowerUp) {
      return this.powerUp.getType();
    }
    return null;
  }

  public boolean isExplodingType() {
    return this.isExplodingType;
  }

  public void setToBeExplodingType() {
    this.isExplodingType = true;
  }

  public void Exploding(List<Block> blocks) {
    if (this.isExplodingType) {
      for (Block tempblock : blocks) {
        checkBlockIntersectionForExploding(this, tempblock);
      }
    }
    this.setHealth(0);
  }

  public void checkBlockIntersectionForExploding(Block explodingBlock, Block tempblock) {

    double ExplodingAreaWidth = explodingBlock.getBlock().getBoundsInLocal().getWidth() * 2;
    double ExplodingAreaHeight = explodingBlock.getBlock().getBoundsInLocal().getHeight() * 2;
    double ExplodingAreaX =
        explodingBlock.getBlock().getX()
            - explodingBlock.getBlock().getBoundsInLocal().getWidth() / 2;
    double ExplodingAreaY =
        explodingBlock.getBlock().getY()
            - explodingBlock.getBlock().getBoundsInLocal().getHeight() / 2;
    Rectangle ExplodingArea = new Rectangle(ExplodingAreaX, ExplodingAreaY, ExplodingAreaWidth,
        ExplodingAreaHeight);

    Shape intersect = Shape.intersect(ExplodingArea, tempblock.getBlock());
    if (intersect.getBoundsInLocal().getWidth() != -1) {
      tempblock.setHealth(tempblock.getHealth() - ExplosionDamage);
    }
  }
}
