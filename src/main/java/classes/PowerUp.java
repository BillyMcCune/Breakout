package classes;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import javafx.scene.Group;

/**
 * Feel free to completely change this code or delete it entirely.
 *
 * @author Billy McCune
 * Purpose: To Have Power-Up's to improve the game and make it more interesting also to clean up code
 * Assumptions: You have a List of blocks, a ball, a list of extraballs, and a root
 * Dependecies (classes or packages): Javafx, List, Arrays, List, Random
 * How to Use: Create the power up and then when you want use the activiate power-up command
 * Any Other Details: N/A
 */
public class PowerUp {

  private final String type;
  private final int power;
  private final double BallSpeedUpRate = 1.5;
  private final double BallSizeIncrease = 1.2;
  private final int NumExtraBalls = 2;
  private final int BallDamageIncrease = 1;
  private final List<String> typeList = Arrays.asList("SpeedUp", "ExtraBalls", "BigBall");

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


  /*
     Purpose: activates a blocks power-up
     Assumptions: the block has a power-up
     Parameters: myBall, extrBall, myBlocks, root - all used for the powerups
     Exceptions: None
     return value: None
   */
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

    /*
      Purpose: changes the type from random to something that actually does something randomly
      Assumptions: The typeList size is not 0
      Parameters: None
      Exceptions: None
      return value: String
    */

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

  /*
  Purpose: Handles The Speed Up Power Up
  Assumptions: The Ball has no speed limit
  Parameters: myBall - the ball you want to increase the speed of
  Exceptions: None
  return value: None
*/
  public void handleSpeedUp(Ball myBall) {
    myBall.changeSpeedUp(BallSpeedUpRate);
  }

  /*
  Purpose: Handles the Extra Balls Power Up - creating an extra ball
  Assumptions: The Extraball list has to be empty for this to work
  Parameters: myBall - the original Ball, extraBall - the extraBall spot, and root - the root of the Scene
  Exceptions: None
  return value: None
*/
  public void handleExtraBalls(Ball myBall, List<Ball> extraBall, Group root) {
    if (extraBall.isEmpty()) {
      Ball newBall = new Ball(myBall.getPaddle());
      extraBall.add(newBall);
      root.getChildren().add(extraBall.getFirst().getBall());
    }
  }


  /*
    Purpose: Handles the Big Ball Power Up
    Assumptions: The Ball has no Size limit or Damage Limit
    Parameters: myBall - the ball you want to increase the size and damage of
    Exceptions: None
    return value: None
  */
  public void handleBigBall(Ball myBall) {
    myBall.changeSize(myBall.getSize() * BallSizeIncrease);
    myBall.setDamage(myBall.getDamage() + BallDamageIncrease);
  }


}
