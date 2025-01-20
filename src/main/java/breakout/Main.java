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
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;


/**
 * Feel free to completely change this code or delete it entirely.
 *
 * @author YOUR NAME HERE
 */
public class Main extends Application {

  // useful names for constant values used
  public static final String TITLE = "Blue Devil Breakout";
  public static final Color DUKE_BLUE = new Color(0, 0.188, 0.529, 1);
  public static final Color DUKE_DARK_BLUE = new Color(0.0039, 0.1294, 0.4118, 1.0);
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
  public static int BALL_SPEED;
  public static int BALL_SIZE;
  public static final double BALL_XDIRECTION = 0.1;
  public static final double BALL_YDIRECTION = 1;
  public static final int BLOCK_HEIGHT = 30;
  public static final int BLOCK_WIDTH = 100;
  public static final Color BLOCK_COLOR = Color.WHITE;
  public static final int BLOCK_HEALTH = 1;
  public static int MAX_BLOCKS_IN_ROW = 6;
  public static int MAX_BLOCKS_IN_COL = 20;
  public static int PLAYER_HEALTH;
  public static int PlAYER_SCORE;
  public Group root;
  private Scene myScene;
  private Paddle myPaddle;
  private Ball myBall;
  private Block myBlock;
  private List<Block> myBlocks;
  private int LevelNumber;
  private String winning_message = "You WON!!!";
  private String lost_message = "You Lost!!!";
  private final Text end_message = new Text(SIZE / 2, SIZE / 2, lost_message);
  private Text healthText = new Text("Health: " + PLAYER_HEALTH);
  private Text scoreText = new Text("Score: " + PlAYER_SCORE);
  private Text levelText = new Text("Level: " + LevelNumber);
  private int LEVEL_MESSAGE_OFFSET = SIZE / 2 - (int) levelText.getBoundsInLocal().getWidth();
  private int SCORE_MESSAGE_OFFSET = 10;
  private int HEALTH_MESSAGE_OFFSET =
      SIZE - (int) healthText.getBoundsInLocal().getWidth() * 2 - 20;
  private final Timeline animation = new Timeline();
  private boolean playerWon;
  private boolean gameEnded;
  private boolean OnStartingScreen = true;
  private Stage myStage;
  private String listOfRules = "\n * Don't let the ball touch the bottom "
      + "of the Screen \n * Use left and right keys to move the paddle "
      + "\n * Beat all the levels to win \n * Lose all your lives to lose \n * Have Fun!!!!";
  private int HIGH_SCORE = 0;

  private void initialize_variables() {
    LevelNumber = 1;
    BALL_SPEED = 600;
    BALL_SIZE = 10;
    PLAYER_HEALTH = 5;
    PlAYER_SCORE = 0;
    gameEnded = false;
    playerWon = false;
  }

  /**
   * Initialize what will be displayed.
   */
  @Override
  public void start(Stage stage) {
    myStage = stage;
    myScene = setupScene(SIZE, SIZE, DUKE_BLUE);
    stage.setScene(myScene);
    stage.setTitle(TITLE);
    stage.show();
    // attach "game loop" to timeline to play it (basically just calling step() method repeatedly forever)
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames()
        .add(new KeyFrame(Duration.seconds(SECOND_DELAY), e -> step(SECOND_DELAY)));
    animation.play();
  }

  public Scene setupScene(int width, int height, Paint background) {
//   System.out.println(myBlock.getHealth());
    initialize_variables();
    root = new Group();
    myScene = new Scene(root, width, height, background);
    // respond to input
    myScene.setOnKeyPressed(e -> handleKeyPressed(e.getCode()));
    myScene.setOnKeyReleased(e -> handleKeyReleased(e.getCode()));
    return myScene;
  }

  public void setUpLevelScene(){
    SetUpPaddle();
    SetUpBall();
    myBlocks = getBlocksForLevel(LevelNumber);
    addBlocksToScene(myBlocks);
    displayStats();
  }

  private void step(double elapsedTime) {
    if (OnStartingScreen) {
      displayStartingScreen();
      return;
    }
    myBall.move(elapsedTime);
    if (myBall.BallHitBottom(SIZE)) {
      decreaseHealth(1);
      ResetBallAndPaddle();
      if (PLAYER_HEALTH <= 0) {
        EndGame();
        return;
      }
    }
    myBall.bounce(SIZE, SIZE);
    movePaddle(myPaddle);
    checkPaddleBallCollision(myPaddle, myBall);
    checkBallBlocksCollision(myBlocks, myBall);
    if (checkBlocksGone(myBlocks)) {
      nextLevel();
    }
  }

  private void displayStartingScreen() {
    myScene.setFill(Color.WHITE);
    Text welcomeMessage = new Text("Welcome to " + TITLE);
    Text rules = new Text("Rules:" + listOfRules);
    welcomeMessage.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
    welcomeMessage.setFill(DUKE_BLUE);
    welcomeMessage.setX(SIZE / 2 - welcomeMessage.getBoundsInLocal().getWidth() / 2);
    welcomeMessage.setY(SIZE / 5 - welcomeMessage.getBoundsInLocal().getHeight() / 2);
    rules.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
    rules.setFill(DUKE_BLUE);
    rules.setX(SIZE / 2 - rules.getBoundsInLocal().getWidth() / 2);
    rules.setY(2*SIZE / 5 - rules.getBoundsInLocal().getHeight() / 2);
    rules.setTextAlignment(TextAlignment.CENTER);
    root.getChildren().add(welcomeMessage);
    root.getChildren().add(rules);
  }

  private void displayStats() {
    scoreText.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
    scoreText.setFill(Color.WHITE);
    scoreText.setText("Score: " + PlAYER_SCORE);
    scoreText.setX(SCORE_MESSAGE_OFFSET);
    scoreText.setY(scoreText.getBoundsInLocal().getHeight());

    healthText.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
    healthText.setFill(Color.WHITE);
    healthText.setText("Health: " + PLAYER_HEALTH);
    healthText.setX(HEALTH_MESSAGE_OFFSET);
    healthText.setY(healthText.getBoundsInLocal().getHeight());

    levelText.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
    levelText.setFill(Color.WHITE);
    levelText.setText("Level: " + LevelNumber);
    levelText.setX(LEVEL_MESSAGE_OFFSET);
    levelText.setY(levelText.getBoundsInLocal().getHeight());

    root.getChildren().add(levelText);
    root.getChildren().add(healthText);
    root.getChildren().add(scoreText);
  }

  private void increaseScore(int amount) {
    PlAYER_SCORE += amount;
    scoreText.setText("Score: " + PlAYER_SCORE);
  }

  private void decreaseHealth(int amount) {
    PLAYER_HEALTH -= amount;
    healthText.setText("Health: " + PLAYER_HEALTH);
  }

  private void increaseLevel(int amount) {
    LevelNumber += amount;
    levelText.setText("Level: " + LevelNumber);
  }


  private boolean checkBlocksGone(List<Block> blocks) {
    for (Block block : blocks) {
      if (root.getChildren().contains(block.getBlock())) {
        return false;
      }
    }
    return true;
  }

  private void EndGame() {
    gameEnded = true;
    animation.pause();
    root.getChildren().clear();
    DisplayEndMessage();
  }

  private void DisplayEndMessage() {
    end_message.setTextAlignment(TextAlignment.LEFT);
    end_message.setFont(Font.font("Verdana", FontWeight.BOLD, 40));
    end_message.setFill(DUKE_DARK_BLUE);
    end_message.setStroke(Color.WHITE);
    end_message.setStrokeWidth(2);
    end_message.setX(SIZE / 2 - end_message.getLayoutBounds().getWidth() / 2);
    end_message.setY(SIZE / 4 - end_message.getLayoutBounds().getHeight() / 2);
    if (playerWon) {
      end_message.setText(winning_message);
    }
    HIGH_SCORE = Math.max(HIGH_SCORE, PlAYER_SCORE);
    Text end_game_stats = new Text("Final Score: " + PlAYER_SCORE +
        "\n\nLives Remaining: " + PLAYER_HEALTH + "\n\nLevel Reached: "
        + LevelNumber + "\n\nHigh Score: " +  HIGH_SCORE + "\n\nPower-Ups Used: Todo");
    end_game_stats.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
    end_game_stats.setFill(Color.WHITE);
    end_game_stats.setX(SIZE / 2 - end_game_stats.getLayoutBounds().getWidth() / 2);
    end_game_stats.setY(end_message.getY() + end_game_stats.getBoundsInLocal().getHeight() / 2);
    end_game_stats.setTextAlignment(TextAlignment.CENTER);
    Text restartText = new Text("Click or Press a Button to Restart");
    restartText.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
    restartText.setFill(Color.WHITE);
    restartText.setTextAlignment(TextAlignment.CENTER);
    restartText.setX(SIZE / 2 - restartText.getLayoutBounds().getWidth() / 2);
    restartText.setY(end_game_stats.getY() + end_game_stats.getBoundsInLocal().getHeight() + restartText.getBoundsInLocal().getHeight());
    root.getChildren().add(end_message);
    root.getChildren().add(restartText);
    root.getChildren().add(end_game_stats);
    myScene.setFill(Color.BLACK);
    myScene.setOnKeyPressed(e -> handleKeyPressed(e.getCode()));
  }

  private void nextLevel() {
    increaseLevel(1);
    myBlocks = getBlocksForLevel(LevelNumber);
    addBlocksToScene(myBlocks);
    ResetBallAndPaddle();
  }

  private void playerWon() {
    playerWon = true;
    EndGame();
  }

  private List<Block> getBlocksForLevel(int LEVEL_NUMBER) {
    List<Block> blocks = new ArrayList<>();
    try {
      InputStream in = getClass().getResourceAsStream("/levels/level" + LEVEL_NUMBER + ".txt");
      if (in == null) {
        throw new FileNotFoundException();
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
                  Color.WHITE));
              break;
            case 2:
              blocks.add(new Block(BLOCK_WIDTH, BLOCK_HEIGHT,
                  1, SIZE / MAX_BLOCKS_IN_ROW * i, SIZE / MAX_BLOCKS_IN_COL * line_counter,
                  DUKE_DARK_BLUE));
            case 3:
            default:
              break;
          }
        }
      }
    } catch (FileNotFoundException e) {
      playerWon();
      return blocks;
    }
    return blocks;
  }

  private void addBlocksToScene(List<Block> blocks) {
    for (Block block : blocks) {
      root.getChildren().add(block.getBlock());
    }
  }

  private void restart() {
    animation.stop();
    myScene = setupScene(SIZE, SIZE, DUKE_BLUE);
    myStage.setScene(myScene);
    setUpLevelScene();
    animation.play();
  }

  private void SetUpBall() {
    myBall = new Ball(myPaddle, BALL_XDIRECTION, BALL_YDIRECTION, BALL_SIZE, BALL_SPEED);
    myBall.getBall().setFill(BALL_COLOR);
    root.getChildren().add(myBall.getBall());
  }

  private void SetUpPaddle() {
    myPaddle = new Paddle(PADDLE_SPEED, PADDLE_HEIGHT, PADDLE_WIDTH, SIZE / 2 - PADDLE_WIDTH / 2,
        7 * SIZE / 8);
    myPaddle.getPaddle().setFill(PADDLE_COLOR);
    root.getChildren().add(myPaddle.getPaddle());
  }

  private void movePaddle(Paddle paddle) {
    if (movePaddleLeft) {
      paddle.move(-PADDLE_SPEED);
      paddle.checkEdges(SIZE);
    }
    if (movePaddleRight) {
      paddle.move(PADDLE_SPEED);
      paddle.checkEdges(SIZE);
    }
  }

  private void checkBallBlocksCollision(List<Block> blocks, Ball myBall) {
    for (Block block : blocks) {
      checkBallBlockCollision(block, myBall);
    }
  }

  private void ResetBallAndPaddle() {
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
      increaseScore(1);
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
    if (gameEnded || OnStartingScreen) {
      OnStartingScreen = false;
      restart();
    } else {
      switch (code) {
        case RIGHT:
          movePaddleRight = true;
          break;
        case LEFT:
          movePaddleLeft = true;
          break;
        case L:
          decreaseHealth(-1);
          break;
        case R:
          ResetBallAndPaddle();
          break;
      }
    }
  }

  private void handleKeyReleased(KeyCode code) {
    switch (code) {
      case RIGHT:
        movePaddleRight = false;
        break;
      case LEFT:
        movePaddleLeft = false;
        break;
    }
  }

  private void mouseClicked(MouseEvent e) {
    if (gameEnded || OnStartingScreen) {
      OnStartingScreen = false;
      restart();
    }
  }
}