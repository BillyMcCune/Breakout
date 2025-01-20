package classes;

import javafx.geometry.Point2D;
import javafx.scene.shape.Circle;


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


  public Ball(Paddle paddle) {
    this(paddle, 0, 10, 10, 3);
  }

  public Ball(Paddle paddle, double xDirection, double yDirection, double size, double speed) {
    myVelocity = new Point2D( speed * xDirection, speed * yDirection);
    ball = new Circle((int) paddle.getPaddle().getX() + (int) (paddle.getPaddle().getWidth() / 2),
        (int) paddle.getPaddle().getY() - (int) paddle.getPaddle().getHeight(), size);
    this.myPaddle = paddle;
  }

  public Circle getBall() {
    return this.ball;
  }

  public void move(double elapsedTime) {
    this.ball.setCenterX(ball.getCenterX() + speedUp * myVelocity.getX() * elapsedTime);
    this.ball.setCenterY(ball.getCenterY() + speedUp * myVelocity.getY() * elapsedTime);
  }

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

  public boolean BallHitBottom(int SCREEN_HEIGHT) {
    if (ball.getCenterY() > SCREEN_HEIGHT - ball.getBoundsInLocal().getHeight()) {
      return true;
    }
    return false;
  }

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


  public void blockVerticleBounce() {
    myVelocity = new Point2D(myVelocity.getX(), -myVelocity.getY());
  }

  public void changeSpeedUp(double newSpeedUp) {
    this.speedUp = newSpeedUp;
  }

  public double getSpeed() {
    return this.speed;
  }

  public void blockSideBounce() {
    myVelocity = new Point2D(-myVelocity.getX(), myVelocity.getY());
  }

  public double getSize(){
    return ball.getRadius();
  }

  public int getDamage() {
    return damage;
  }

  public void setDamage(int damage) {
    this.damage = damage;
  }

  public void changeSize(double size) {
    this.size = size;
    this.ball.setRadius(size);
  }

  @Override
  public Ball clone() {
    try {
      Ball clone = (Ball) super.clone();
      clone.myVelocity = new Point2D(this.myVelocity.getX(), this.myVelocity.getY());
      clone.ball = new Circle(this.ball.getCenterX(), this.ball.getCenterY(), this.size);
      return clone;
    } catch (CloneNotSupportedException e) {
      throw new AssertionError();
    }
  }

}
