package classes;

import java.util.List;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Block {

  private final int BLOCKWIDTH;
  private final int BLOCKHEIGHT;
  private final Point2D position;
  private final Rectangle block;
  private int health;
  private final Color color;
  private final Color outlineColor = Color.BLACK;
  private final int outlineWidth = 2;
  private boolean hasPowerUp = false;
  private PowerUp powerUp;
  private boolean isExplodingType = false;
  private final int ExplosionDamage = 2;


  /**
   * @author Billy McCune
   * Purpose: To Create a manage a game block object and create custom block features
   * Assumptions: N/A
   * Dependecies (classes or packages): javaFx
   * How to Use: Create a block and put it on the screen then get a ball and hit it.
   * Any Other Details: N/A
   */
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


  /*
      Purpose: Searches the list of blocks to find which ones are in the explosion
      Assumptions: N/A
      Parameters: blocks - the other blocks in the game at the time
      Exceptions: None
      return value: None
    */
  public void Exploding(List<Block> blocks) {
    if (this.isExplodingType) {
      for (Block tempblock : blocks) {
        checkBlockIntersectionForExploding(this, tempblock);
      }
    }
    this.setHealth(0);
  }


  /*
     Purpose: to check if the block is in the block explosion radius and do the proper damage
     Assumptions: the exploding area function is correct.
     Parameters: exploding block - the block that is exploding, tempblock - the block we are checking
     Exceptions: None
     return value: None
   */
  public void checkBlockIntersectionForExploding(Block explodingBlock, Block tempblock) {
    Rectangle ExplodingArea = CreateExplodingArea(explodingBlock);

    Shape intersect = Shape.intersect(ExplodingArea, tempblock.getBlock());
    if (intersect.getBoundsInLocal().getWidth() != -1) {
      tempblock.setHealth(tempblock.getHealth() - ExplosionDamage);
    }
  }

  /*
      Purpose: creates the explosion radius
      Assumptions: the exploding block is in the game
      Parameters: exploding block - the block that is exploding - helps with abstraction (might be redundant now that I think about it)
      Exceptions: None
      return value: rectangle - the area we are using to see if the other blocks are in.
    */
  public Rectangle CreateExplodingArea(Block explodingBlock) {
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
    return ExplodingArea;
  }
}
