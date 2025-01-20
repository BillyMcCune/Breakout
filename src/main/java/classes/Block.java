package classes;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

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
}
