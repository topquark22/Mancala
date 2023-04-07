package gtf.games.mancala.sample;

import org.junit.Ignore;
import org.junit.Test;

import gtf.games.mancala.IllegalMoveException;
import gtf.games.mancala.play.MinimaxStrategy;
import gtf.games.mancala.play.RandomStrategy;

public class MancalaTest {

  @Ignore
  @Test
  public void LopsidedTest() throws IllegalMoveException {
    new GameTester(new MinimaxStrategy(4), new RandomStrategy()).run();
  }

  @Ignore
  @Test
  public void RandomTest() throws IllegalMoveException {
    new GameTester(new RandomStrategy(), new RandomStrategy()).run();
  }

  private static final int ITERATIONS = 1000;

  /**
   * Test different depths against each other in Minimax
   */
  // @Ignore
  @Test
  public void DepthsTest() throws IllegalMoveException {
    for (int depth = 1; depth <= 5; depth++) {
      runDepth(ITERATIONS, depth);
    }
  }

  private void runDepth(int iterations, int depth) throws IllegalMoveException {
    GameTester tester = new GameTester(new MinimaxStrategy(depth), new RandomStrategy());
    int tieCount = 0, player0winCount = 0, player1winCount = 0;
    for (int i = 0; i < iterations; i++) {
      Integer winner = tester.run();
      if (winner == null) {
        tieCount++;
      } else if (winner == 0) {
        player0winCount++;
      } else {
        player1winCount++;
      }
    }
    System.out.println("Using minimax depth " + depth + ", Player 0 won " + player0winCount + " games");
    System.out.println("Using random, Player 1 won " + player1winCount + " games");
    System.out.println("Tie games: " + tieCount);
    System.out.println();
  }

}
