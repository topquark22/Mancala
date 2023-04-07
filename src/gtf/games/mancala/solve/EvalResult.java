package gtf.games.mancala.solve;

/**
 * Result of evaluating a board. It gives the best move (for some player) and the
 * value of the move for that player. Should be used in context of the next
 * player.
 * 
 * @author gtf
 *
 */
public class EvalResult {

  private final int bestMove;

  private final int value;

  public int getBestMove() {
    return bestMove;
  }

  public int getValue() {
    return value;
  }

  EvalResult(int bestMove, int value) {
    this.bestMove = bestMove;
    this.value = value;
  }

  @Override
  public String toString() {
    return "[bestMove=" + bestMove + ", value=" + value + "]";
  }
}
