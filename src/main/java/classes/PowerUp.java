package classes;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class PowerUp {
  private String type;
  private int power;
  private Block block;
  private double BallSpeedUpRate = 1.2;
  private double BallSizeIncrease = 1.2;
  private int NumExtraBalls = 2;
  private int BallDamageIncrease = 1;
  private List<String> typeList = Arrays.asList("Random", "SpeedUp", "ExtraBalls", "BigBall", "Exploding");

  public PowerUp(String type, int power) {
    type = type.toLowerCase();
    switch (type){
      case "random": this.type = "Random"; break;
      case "speedup": this.type = "SpeedUp"; break;
      case "extraballs": this.type = "ExtraBalls"; break;
      case "bigball":  this.type = "BigBall"; break;
      case "exploding": this.type = "Exploding"; break;
      default: this.type = "None"; break;
    }
    this.power = power;
  }

  public void ActivatePowerUp(Ball myBall, List<Ball> TemporaryBalls, List<Block> myBlocks) {
    switch (type){
      case "SpeedUp":
        handleSpeedUp(myBall,TemporaryBalls);
        break;
      case "ExtraBalls":
        handleExtraBalls(myBall,TemporaryBalls);
        break;
      case "BigBall":
        handleBigBall(myBall, TemporaryBalls);
        break;
      case "Exploding":
        handleExploding(myBlocks);
        break;
      case "Random":
        handleRandom(myBall, TemporaryBalls, myBlocks);
        break;
    }
  }

  public void handleRandom(Ball myBall, List<Ball> TemporaryBalls, List<Block> myBlocks){
    Random rand = new Random();
    int randnum = rand.nextInt(0,typeList.size()-1);
    String chosenType = typeList.get(randnum);
    this.type = chosenType.toLowerCase();
    ActivatePowerUp(myBall,TemporaryBalls,myBlocks);
  }

  public String getType(){
    return type;
  }

  public int getPower(){
    return power;
  }

  public void handleSpeedUp(Ball myBall, List<Ball> TemporaryBalls){
      myBall.changeSpeed(myBall.getSpeed()* BallSpeedUpRate);
      for (Ball ball : TemporaryBalls){
        ball.changeSpeed(myBall.getSpeed()* BallSpeedUpRate);
      }
  }

  public void handleExtraBalls(Ball myBall, List<Ball> TemporaryBalls){
      for (int i = 0; i < NumExtraBalls; i++){
        TemporaryBalls.add(myBall.clone());
      }
  }

  public void handleBigBall(Ball myBall, List<Ball> TemporaryBalls){
      myBall.changeSize(myBall.getSize()*BallSizeIncrease);
      myBall.setDamage(myBall.getDamage()+BallDamageIncrease);
    for (Ball ball : TemporaryBalls){
      ball.changeSize(myBall.getSize()*BallSizeIncrease);
      ball.setDamage(ball.getDamage()+BallDamageIncrease);
    }
  }

  public void handleExploding(List<Block> myBlocks){

  }

}
