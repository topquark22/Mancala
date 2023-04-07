package gtf.games.mancala.play;

import gtf.games.mancala.Board;
import gtf.games.mancala.solve.EvalResult;
import gtf.games.mancala.solve.MinimaxSolver;
import gtf.games.mancala.solve.Solver;

public class MinimaxStrategy implements PlayerStrategy {

  private final Solver solver;

  private final int depth;

  public MinimaxStrategy(int depth) {
    if (depth <= 0) {
      throw new IllegalArgumentException("invalid depth " + depth);
    }
    solver = new MinimaxSolver();
    this.depth = depth;
  }

  @Override
  public int getMove(Board board, int player) {
    EvalResult er = solver.evaluate(board, player, depth);
    return er.getBestMove();
  }

}
