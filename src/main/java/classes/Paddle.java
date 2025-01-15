package classes;
import javafx.scene.shape.Rectangle;
import java.util.Random;
import javafx.geometry.Point2D;

public class Paddle {
  private int speed;
  private int height;
  private int width;
  private Rectangle paddle;
  private int x;
  private int y;
  private Point2D myVelocity;



  public Paddle(int speed, int height, int width, int x, int y) {
    this.speed = speed;
    this.height = height;
    this.width = width;
    this.paddle = new Rectangle(x,y,width,height);

  }

  public void move(int speed) {
    this.paddle.setX(this.paddle.getX() + speed);
  }

  public Rectangle getPaddle() {
    return this.paddle;
  }

}
