package classes;

import java.util.ArrayList;
import java.util.Random;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.shape.Circle;
import javax.swing.BoundedRangeModel;
import java.util.Random;
import javafx.geometry.Point2D;
import javafx.scene.shape.Shape;


public class Ball {
  private double XDirection;
  private double YDirection;
  private double size;
  private double speed;
  private Circle ball;
  private Point2D myVelocity;
  private int health;

  public Ball(Paddle paddle) {
    this(paddle,0,10,10,3);
  }

  public Ball(Paddle paddle, double xDirection, double yDirection, double size, double speed) {
    myVelocity = new Point2D(speed*xDirection, speed*yDirection);
    ball = new Circle((int)paddle.getPaddle().getX()  + (int)(paddle.getPaddle().getWidth()/2), (int)paddle.getPaddle().getY()-(int)paddle.getPaddle().getHeight(), size);
  }

  public Circle getBall(){
    return this.ball;
  }

 public void move(double elapsedTime) {
    this.ball.setCenterX(ball.getCenterX() + myVelocity.getX() * elapsedTime);
    this.ball.setCenterY(ball.getCenterY() + myVelocity.getY() * elapsedTime);
 }

 public void bounce(int SCREEN_WIDTH, int SCREEN_HEIGHT) {
   if (ball.getCenterX() > SCREEN_WIDTH - ball.getBoundsInLocal().getWidth()
       || ball.getCenterX() < 0) {
     myVelocity = new Point2D(-myVelocity.getX(), myVelocity.getY());
   }
   if (ball.getCenterY() > SCREEN_HEIGHT - ball.getBoundsInLocal().getHeight()
       || ball.getCenterY() - ball.getBoundsInLocal().getHeight() < 0) {
     myVelocity = new Point2D(myVelocity.getX(), -myVelocity.getY());
   }
 }
    public void paddleBounce() {
      myVelocity = new Point2D(myVelocity.getX(), -myVelocity.getY());
 }

 public void blockVerticleBounce() {
      myVelocity = new Point2D(myVelocity.getX(), -myVelocity.getY());
 }
 public void blockSideBounce() {
    myVelocity = new Point2D(-myVelocity.getX(), myVelocity.getY());
 }
}
