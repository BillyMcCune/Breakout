package classes;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


/**
 * Feel free to completely change this code or delete it entirely.
 *
 * @author Billy McCune
 * Purpose: To create and manage a Ball object for Breakout Assumptions: N/A
 * Dependecies (classes or packages): The other classes, Paddle, Block
 * How to Use: create a ball and use the step function to move it
 * Any Other Details: N/A
 */
public class Ball implements Cloneable {

  private double XDirection;
  private double YDirection;
  private double size;
  private double speed;
  private Circle ball;
  private Point2D myVelocity;
  private Paddle myPaddle;
  private int damage = 1;
  private double speedUp = 1;
  private Color color;


  public Ball(Paddle paddle) {
    this(paddle, 0, 10, 10, 60, Color.WHITE);
  }

  public Ball(Paddle paddle, double xDirection, double yDirection, double size, double speed,
      Color color) {
    myVelocity = new Point2D(speed * xDirection, speed * yDirection);
    ball = new Circle((int) paddle.getPaddle().getX() + (int) (paddle.getPaddle().getWidth() / 2),
        (int) paddle.getPaddle().getY() - (int) paddle.getPaddle().getHeight(), size);
    this.myPaddle = paddle;
    this.color = color;
    ball.setFill(color);
  }

  public Circle getBall() {
    return this.ball;
  }

  public void move(double elapsedTime) {
    this.ball.setCenterX(ball.getCenterX() + speedUp * myVelocity.getX() * elapsedTime);
    this.ball.setCenterY(ball.getCenterY() + speedUp * myVelocity.getY() * elapsedTime);
  }

  /*
      Purpose: Bounces the ball off the walls and ceiling
      Assumptions: The ball is within the walls and ceiling
      Parameters: Screen_Width and Screen_Height which are used to determine where the walls are
      Exceptions: None
      return value: None
    */
  public void bounce(int SCREEN_WIDTH, int SCREEN_HEIGHT) {
    if (ball.getCenterX() > SCREEN_WIDTH - ball.getBoundsInLocal().getWidth() ||
        ball.getCenterX() - ball.getBoundsInLocal().getWidth() < 0) {
      myVelocity = new Point2D(-myVelocity.getX(), myVelocity.getY());
    }
    if (ball.getCenterY() > SCREEN_HEIGHT - ball.getBoundsInLocal().getHeight()
        || ball.getCenterY() - ball.getBoundsInLocal().getHeight() < 0) {
      myVelocity = new Point2D(myVelocity.getX(), -myVelocity.getY());
    }
  }


  /*
    Purpose: To determine if a Ball object hit the bottom of the screen.
    Assumptions: The Ball object is on the screen.
    Parameters: The Screen height.
    Exceptions: None.
    return value: returns true is the ball hit the bottom of the screen.
  */
  public boolean BallHitBottom(int SCREEN_HEIGHT) {
    if (ball.getCenterY() > SCREEN_HEIGHT - ball.getBoundsInLocal().getHeight()) {
      return true;
    }
    return false;
  }

  /*
    Purpose: To bounce the ball from the paddle
    Assumptions: the ball has had a collision with the paddle
    Parameters: paddle object
    Exceptions: None
    return value: None
  */
  public void paddleBounce(Paddle paddle) {
    myVelocity = new Point2D(myVelocity.getX(), -myVelocity.getY());
    //break up the paddle into thirds
    if (ball.getCenterX() < paddle.getPaddle().getX() + paddle.getPaddle().getWidth() / 3) {
      myVelocity = new Point2D(-myVelocity.getX(), myVelocity.getY());
    } else if (ball.getCenterX()
        > paddle.getPaddle().getX() + 2 * paddle.getPaddle().getWidth() / 3) {
      myVelocity = new Point2D(-myVelocity.getX(), myVelocity.getY());
    }
    ball.setCenterY(paddle.getPaddle().getY() - ball.getBoundsInLocal().getHeight() / 2);
  }

  /*
   Purpose: To keep ball near paddle for Paddle holding ability
   Assumptions: Ball has hit the paddle and there is only one paddle
   Parameters: Paddle
   Exceptions: None
   return value: None
 */
  public void ballStayAbovePaddle(Paddle paddle) {
    ball.setCenterY(paddle.getPaddle().getY() - ball.getBoundsInLocal().getHeight() / 2);
    ball.setCenterX(paddle.getPaddle().getX() + paddle.getPaddle().getWidth() / 2);
  }

  /*
      Purpose: To bounce the ball off a Block
      Assumptions: Ball has hit a block
      Parameters: Non
      Exceptions: None
      return value: None
    */
  public void blockVerticleBounce() {
    myVelocity = new Point2D(myVelocity.getX(), -myVelocity.getY());
  }

  /*
      Purpose: To increase Ball speed
      Assumptions: Ball has gotten the speedup powerup
      Parameters: double NewSpeedUp - used to determine the speed increase
      Exceptions: None
      return value: None
    */
  public void changeSpeedUp(double newSpeedUp) {
    this.speedUp = newSpeedUp;
  }

  public double getSpeed() {
    return this.speed;
  }

  public void blockSideBounce() {
    myVelocity = new Point2D(-myVelocity.getX(), myVelocity.getY());
  }

  public double getSize() {
    return ball.getRadius();
  }

  public int getDamage() {
    return damage;
  }

  public Paddle getPaddle() {
    return myPaddle;
  }

  public void setDamage(int damage) {
    this.damage = damage;
  }

  public void changeSize(double size) {
    this.size = size;
    this.ball.setRadius(size);
  }

  /*
      Purpose: Clones the Ball
      Assumptions: The clone should spawn above the paddle and
      the new ball velocity should be the same as the old ones.
      Parameters: None
      Exceptions: None
      return value: None
    */
  @Override
  public Ball clone() {
    try {
      Ball clone = (Ball) super.clone();
      clone.myVelocity = new Point2D(this.myVelocity.getX(), this.myVelocity.getY());
      ball = new Circle(
          (int) myPaddle.getPaddle().getX() + (int) (myPaddle.getPaddle().getWidth() / 2),
          (int) myPaddle.getPaddle().getY() - (int) myPaddle.getPaddle().getHeight(), size);
      return clone;
    } catch (CloneNotSupportedException e) {
      throw new AssertionError();
    }
  }

}
