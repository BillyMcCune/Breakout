package classes;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import javafx.scene.Group;

public class PowerUp {

  private String type;
  private int power;
  private double BallSpeedUpRate = 1.5;
  private double BallSizeIncrease = 1.2;
  private int NumExtraBalls = 2;
  private int BallDamageIncrease = 1;
  private List<String> typeList = Arrays.asList("SpeedUp", "ExtraBalls", "BigBall");

  public PowerUp(String type, int power) {
    type = type.toLowerCase();
    switch (type) {
      case "random":
        this.type = getRandom();
        break;
      case "speedup":
        this.type = "SpeedUp";
        break;
      case "extraballs":
        this.type = "ExtraBalls";
        break;
      case "bigball":
        this.type = "BigBall";
        break;
      default:
        this.type = "None";
        break;
    }
    this.power = power;
  }

  public void ActivatePowerUp(Ball myBall, List<Ball> extraBall, List<Block> myBlocks, Group root) {
    switch (type) {
      case "SpeedUp":
        handleSpeedUp(myBall);
        break;
      case "ExtraBalls":
        handleExtraBalls(myBall, extraBall, root);
        break;
      case "BigBall":
        handleBigBall(myBall);
        break;
    }
  }

  public String getRandom() {
    Random rand = new Random();
    int randnum = rand.nextInt(0, typeList.size());
    String chosenType = typeList.get(randnum);
    return chosenType;
  }

  public String getType() {
    return type;
  }

  public int getPower() {
    return power;
  }

  public void handleSpeedUp(Ball myBall) {
    myBall.changeSpeedUp(BallSpeedUpRate);
  }

  public void handleExtraBalls(Ball myBall, List<Ball> extraBall, Group root) {
    if (extraBall.isEmpty()) {
      Ball newBall = new Ball(myBall.getPaddle());
      extraBall.add(newBall);
      root.getChildren().add(extraBall.getFirst().getBall());
    }
  }

  public void handleBigBall(Ball myBall) {
    myBall.changeSize(myBall.getSize() * BallSizeIncrease);
    myBall.setDamage(myBall.getDamage() + BallDamageIncrease);
  }


}
