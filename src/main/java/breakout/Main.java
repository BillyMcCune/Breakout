package breakout;

import classes.Ball;
import classes.Block;
import classes.Paddle;
import javafx.application.Application;
import javafx.css.Size;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.shape.Circle;
import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;
import javafx.util.Duration;


/**
 * Feel free to completely change this code or delete it entirely.
 *
 * @author YOUR NAME HERE
 */
public class Main extends Application {

  // useful names for constant values used
  public static final String TITLE = "Example JavaFX Animation";
  public static final Color DUKE_BLUE = new Color(0, 0.188, 0.529, 1);
  public static final int FRAMES_PER_SECOND = 60;
  public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
  private Scene myScene;
  public static final int SIZE = 400;
  public static final int PADDLE_SPEED = 10;
  public static final int PADDLE_WIDTH = 100;
  public static final int PADDLE_HEIGHT = 15;
  public static final Color PADDLE_COLOR = Color.WHITE;
  public static final Color BALL_COLOR = Color.WHITE;
  public static final int BALL_SPEED = 150;
  public static final int BALL_SIZE = 10;
  public static final double BALL_XDIRECTION = 0.1;
  public static final double BALL_YDIRECTION = 1;
  public static final int BLOCK_HEIGHT = 50;
  public static final int BLOCK_WIDTH = 100;
  public static final Color BLOCK_COLOR = Color.WHITE;
  public static final int BLOCK_HEALTH = 1;
  public static int PLAYER_HEALTH = 1;

  private Paddle myPaddle;
  private Ball myBall;
  private Block myBlock;

  public Group root;

  /**
   * Initialize what will be displayed.
   */
  @Override
  public void start(Stage stage) {
    myScene = setupScene(SIZE, SIZE, DUKE_BLUE);
    stage.setScene(myScene);
    stage.setTitle(TITLE);
    stage.show();
    // attach "game loop" to timeline to play it (basically just calling step() method repeatedly forever)
    Timeline animation = new Timeline();
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames()
        .add(new KeyFrame(Duration.seconds(SECOND_DELAY), e -> step(SECOND_DELAY)));
    animation.play();
  }

  public Scene setupScene(int width, int height, Paint background) {
    myBlock = new Block(BLOCK_WIDTH, BLOCK_HEIGHT, BLOCK_HEALTH, width / 2, height / 4);
//    System.out.println(myBlock.getHealth());
    myBlock.getBlock().setFill(BLOCK_COLOR);
    root = new Group();
    SetUpBall(root);
    SetUpPaddle(root);
    root.getChildren().add(myBlock.getBlock());

    myScene = new Scene(root, width, height, background);
    // respond to input
    myScene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
    return myScene;
  }

  public void SetUpBall(Group root){
    myBall = new Ball(myPaddle, BALL_XDIRECTION, BALL_YDIRECTION, BALL_SIZE, BALL_SPEED);
    myBall.getBall().setFill(BALL_COLOR);
    root.getChildren().add(myBall.getBall());
  }

  public void SetUpPaddle(Group root){
    myPaddle = new Paddle(PADDLE_SPEED, PADDLE_HEIGHT, PADDLE_WIDTH, SIZE / 2, 3 * SIZE / 4);
    myPaddle.getPaddle().setFill(PADDLE_COLOR);
    root.getChildren().add(myPaddle.getPaddle());
  }

  private void step(double elapsedTime) {
    myBall.move(elapsedTime);
    myBall.bounce(SIZE, SIZE);
    if(myBall.BallHitBottom()){
      PLAYER_HEALTH -= 1;
      if (PLAYER_HEALTH <= 0) {
        DoReset();
      }
    }
    checkPaddleBallCollision(myPaddle, myBall);
    checkBallBlockCollision(myBlock, myBall);
  }

  private void DoReset() {
    root.getChildren().remove(myBall.getBall());
    root.getChildren().remove(myPaddle.getPaddle());

  }

  private void checkPaddleBallCollision(Paddle paddle, Ball ball) {
    Shape intersect = Shape.intersect(paddle.getPaddle(), ball.getBall());
    if (intersect.getBoundsInLocal().getWidth() != -1) {
      myBall.paddleBounce();
    }
  }

  private boolean BallBlockSideCollision(Block block, Ball ball) {
    double centerY = ball.getBall().getCenterY();
    double radius = ball.getBall().getRadius();
    double rectTop = block.getBlock().getY();
    double rectBottom = block.getBlock().getY() + block.getBlock().getHeight();

    return centerY + radius > rectTop && centerY - radius < rectBottom;
  }

  private boolean BallBlockVerticalCollision(Block block, Ball ball) {
    double centerX = ball.getBall().getCenterX();
    double rectLeft = block.getBlock().getX();
    double rectRight = block.getBlock().getX() + block.getBlock().getWidth();
    double radius = ball.getBall().getRadius();
    return centerX + radius > rectLeft && centerX - radius < rectRight;
  }

  private void checkBallBlockCollision(Block block, Ball ball) {
    if (!root.getChildren().contains(block.getBlock())) {
      return;
    }

    Shape intersect = Shape.intersect(block.getBlock(), ball.getBall());
    if (intersect.getBoundsInLocal().getWidth() != -1
        || intersect.getBoundsInLocal().getHeight() != -1) {
//            if (BallBlockSideCollision(block,ball)){
//                ball.blockSideBounce();
//                System.out.println("Ball block side bounce");
//            }
      if (BallBlockVerticalCollision(block, ball)) {
        ball.blockVerticleBounce();
        block.setHealth(block.getHealth() - 1);
        checkBlockHealth(block);
        System.out.println(block.getHealth());
      }
    }
  }

  private void checkBlockHealth(Block block) {
    if (block.getHealth() == 0) {
      delBlock(block);
    }
  }

  private void delBlock(Block block) {
    root.getChildren().remove(block.getBlock());
  }

  private void handleKeyInput(KeyCode code) {
    // NOTE new Java syntax that some prefer (but watch out for the many special cases!)
    //   https://blog.jetbrains.com/idea/2019/02/java-12-and-intellij-idea/
    switch (code) {
      case RIGHT -> {
        myPaddle.move(PADDLE_SPEED);
        myPaddle.checkEdges(SIZE);
      }
      case LEFT -> {
        myPaddle.move(-PADDLE_SPEED);
        myPaddle.checkEdges(SIZE);
      }
    }
  }
}