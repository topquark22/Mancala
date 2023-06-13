package gtf.games.mancala.sample;

import gtf.games.mancala.IllegalMoveException;
import gtf.games.mancala.play.GameRunner;
import gtf.games.mancala.play.PlayerStrategy;

/**
 * Game play in which player 0 uses minimax and player 1 plays randomly
 * 
 * @author gtf
 *
 */
class GameTester {

  private static final int WIDTH = 6;
  private static final int STONES = 4;
  
  private final GameRunner runner;
  
  GameTester(PlayerStrategy player0strategy, PlayerStrategy player1strategy) {
    runner = new GameRunner(WIDTH, STONES, player0strategy, player1strategy);//, System.out);
  }
 
  public Integer run() throws IllegalMoveException {
    Integer winner;
    int[] calahCounts = runner.run();
    if (calahCounts[0] == calahCounts[1]) {
      winner = null;
    } else if (calahCounts[0] > calahCounts[1]) {
      winner = 0;
    } else {
      winner = 1;
    }
    return winner;
  }

}