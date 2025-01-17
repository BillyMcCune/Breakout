package breakout;

import classes.Ball;
import classes.Block;
import classes.Paddle;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
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
  public static final int SIZE = 600;
  public static final int PADDLE_SPEED = 10;
  public static final int PADDLE_WIDTH = 100;
  public static final int PADDLE_HEIGHT = 15;
  public static final Color PADDLE_COLOR = Color.WHITE;
  public static boolean movePaddleLeft = false;
  public static boolean movePaddleRight = false;
  public static final Color BALL_COLOR = Color.WHITE;
  public static final int BALL_SPEED = 300;
  public static final int BALL_SIZE = 10;
  public static final double BALL_XDIRECTION = 0.1;
  public static final double BALL_YDIRECTION = 1;
  public static final int BLOCK_HEIGHT = 30;
  public static final int BLOCK_WIDTH = 100;
  public static final Color BLOCK_COLOR = Color.WHITE;
  public static final int BLOCK_HEALTH = 1;
  public static int PLAYER_HEALTH = 1;
  public static int MAX_BLOCKS_IN_ROW = 6;
  public static int MAX_BLOCKS_IN_COL = 20;
  public Group root;
  private Scene myScene;
  private Paddle myPaddle;
  private Ball myBall;
  private Block myBlock;
  private List<Block> myBlocks;

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
//   System.out.println(myBlock.getHealth());
    root = new Group();
    SetUpPaddle();
    SetUpBall();
    myBlocks = getBlocksForLevel(1);
    addBlocksToScene(myBlocks);
    myScene = new Scene(root, width, height, background);
    // respond to input
    myScene.setOnKeyPressed(e -> handleKeyPressed(e.getCode()));
    myScene.setOnKeyReleased(e -> handleKeyReleased(e.getCode()));
    return myScene;
  }

  public List<Block> getBlocksForLevel(int LEVEL_NUMBER) {
    List<Block> blocks = new ArrayList<>();
    try {
      InputStream in = getClass().getResourceAsStream("/levels/level1.txt");
      if (in == null) {
        throw new FileNotFoundException("Could not find level1.txt in resources.");
      }
      Scanner levelScanner = new Scanner(in);
      int current_spot = 0;
      int line_counter = 0;
      while (levelScanner.hasNextLine() && line_counter < MAX_BLOCKS_IN_COL) {
        line_counter++;
        String line = levelScanner.nextLine();
        String[] split = line.split(" ");
        if (split.length != MAX_BLOCKS_IN_ROW) {
          System.out.println("ERROR: Level " + LEVEL_NUMBER + " file format is incorrect");
        }
        for (int i = 0; i < split.length; i++) {
          current_spot = Integer.parseInt(split[i]);
          switch (current_spot) {
            case 0:
              break;
            case 1:
              blocks.add(new Block(BLOCK_WIDTH, BLOCK_HEIGHT,
                  1, SIZE / MAX_BLOCKS_IN_ROW * i, SIZE / MAX_BLOCKS_IN_COL * line_counter,
                  BALL_COLOR));
              break;

            case 2:
              break;
            case 3:
            default:
              break;
          }
        }
      }
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
    return blocks;
  }

  public void addBlocksToScene(List<Block> blocks) {
    for (Block block : blocks) {
      root.getChildren().add(block.getBlock());
    }
  }

  public void SetUpBall() {
    myBall = new Ball(myPaddle, BALL_XDIRECTION, BALL_YDIRECTION, BALL_SIZE, BALL_SPEED);
    myBall.getBall().setFill(BALL_COLOR);
    root.getChildren().add(myBall.getBall());
  }

  public void SetUpPaddle() {
    myPaddle = new Paddle(PADDLE_SPEED, PADDLE_HEIGHT, PADDLE_WIDTH, SIZE / 2, 7 * SIZE / 8);
    myPaddle.getPaddle().setFill(PADDLE_COLOR);
    root.getChildren().add(myPaddle.getPaddle());
  }

  private void step(double elapsedTime) {
    myBall.move(elapsedTime);
    if (myBall.BallHitBottom(SIZE)) {
      PLAYER_HEALTH -= 1;
      if (PLAYER_HEALTH <= 0) {
        DoReset();
      }
    }
    myBall.bounce(SIZE, SIZE);
    movePaddle(myPaddle);
    checkPaddleBallCollision(myPaddle, myBall);
    checkBallBlocksCollision(myBlocks, myBall);
  }

  private void movePaddle(Paddle paddle) {
    if (movePaddleLeft){
      paddle.move(-PADDLE_SPEED);
      paddle.checkEdges(SIZE);
    }
    if (movePaddleRight){
      paddle.move(PADDLE_SPEED);
      paddle.checkEdges(SIZE);
    }
  }

  private void checkBallBlocksCollision(List<Block> blocks, Ball myBall) {
    for (Block block : blocks) {
      checkBallBlockCollision(block, myBall);
    }
  }

  private void DoReset() {
    root.getChildren().remove(myBall.getBall());
    root.getChildren().remove(myPaddle.getPaddle());
    SetUpPaddle();
    SetUpBall();
  }

  private void checkPaddleBallCollision(Paddle paddle, Ball ball) {
    Shape intersect = Shape.intersect(paddle.getPaddle(), ball.getBall());
    if (intersect.getBoundsInLocal().getWidth() != -1) {
      myBall.paddleBounce(paddle);
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

  private void handleKeyPressed(KeyCode code) {
    // NOTE new Java syntax that some prefer (but watch out for the many special cases!)
    //   https://blog.jetbrains.com/idea/2019/02/java-12-and-intellij-idea/
    switch (code) {
      case RIGHT: movePaddleRight = true; break;
      case LEFT: movePaddleLeft = true; break;
    }
  }

  private void handleKeyReleased(KeyCode code) {
    switch (code) {
      case RIGHT: movePaddleRight = false; break;
      case LEFT: movePaddleLeft = false; break;
    }
  }
}