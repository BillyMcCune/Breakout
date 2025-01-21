package classes;

import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;

/**
 * Feel free to completely change this code or delete it entirely.
 *
 * @author Billy McCune
 * Purpose: To create and manage the Paddle object for Breakout
 * Assumptions: There are walls in the game
 * Dependecies (classes or packages): Javafx
 * How to Use: create the paddle where you want it on the screen and create keylisteners to move the paddle left or right
 * Any Other Details: N/A
 */
public class Paddle {

  private final int speed;
  private final int height;
  private final int width;
  private final Rectangle paddle;
  private int x;
  private int y;
  private Point2D myVelocity;


  public Paddle(int speed, int height, int width, int x, int y) {
    this.speed = speed;
    this.height = height;
    this.width = width;
    this.paddle = new Rectangle(x, y, width, height);

  }

  public void move(int speed) {
    this.paddle.setX(this.paddle.getX() + speed);
  }

  public Rectangle getPaddle() {
    return this.paddle;
  }


  /*
     Purpose: Checks to see if the paddle is going to move past the edges and stop it from doing so
     Assumptions: the paddle only moves side to side
     Parameters: SCREEN_WIDTH - to determine where the walls are
     Exceptions: None
     return value: None
   */
  public void checkEdges(int SCREEN_WIDTH) {
    if (this.paddle.getX() + this.paddle.getBoundsInLocal().getWidth() > SCREEN_WIDTH) {
      this.paddle.setX(SCREEN_WIDTH - this.paddle.getBoundsInLocal().getWidth());
    }
    if (this.paddle.getX() < 0) {
      this.paddle.setX(0);
    }
  }

}
