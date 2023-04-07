package gtf.games.mancala.play;

import java.util.Random;

import gtf.games.mancala.Board;

public class RandomStrategy implements PlayerStrategy {

  private final Random random;
  
  public RandomStrategy(Random random) {
    this.random = random;
  }
  
  public RandomStrategy() {
    this(new Random());
  }
  
  @Override
  public int getMove(Board board, int player) {
    return (int)(random.nextDouble() * board.getWidth());
  }

}
