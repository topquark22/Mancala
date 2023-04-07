package gtf.games.mancala.solve;

import java.util.List;

import gtf.games.mancala.Board;
import gtf.games.mancala.IllegalMoveException;
import gtf.games.mancala.MoveResult;

public class MinimaxSolver implements Solver {

  private static final int NO_MOVE = -1;
  
  private final Cache cache;
  
  public MinimaxSolver() {
    cache = new Cache();
  }

  /*
   * (non-Javadoc)
   * 
   * @see gtf.games.mancala.solve.Solver#evaluate(gtf.games.mancala.Board, int,
   * int, int)
   */
  @Override
  public EvalResult evaluate(Board board, int player, int plies) {
    if (plies == 0) {
      return new EvalResult(NO_MOVE, board.getCalahStones(player) - board.getCalahStones(1 - player));
    }
    // EvalResult erc = cache.lookup(board, player, plies);
    // if (erc != null) {
    // return erc;
    // }
    int bestMove = NO_MOVE;
    int bestValue = Integer.MIN_VALUE;
    List<Integer> validMoves = board.getValidMoves(player);
    if (validMoves.isEmpty()) {
      // should not happen
      throw new IllegalStateException();
    }
    try {
      for (int move : validMoves) {
        Board board1 = Board.copy(board);
        MoveResult mr = board1.move(player, move); // mutates board1
        int nextPlayer = mr.isAnotherMove() ? player : 1 - player;
        int plies1 = mr.isGameOver() ? 0 : plies - 1;
        EvalResult er = evaluate(board1, nextPlayer, plies1);
        // cache.put(board1, nextPlayer, plies, er);
        int value1 = mr.isAnotherMove() ? er.getValue() : -er.getValue();
        if (value1 > bestValue) {
          bestMove = move;
          bestValue = value1;
        }
      }
    } catch (IllegalMoveException e) {
      // never happens
    }
    EvalResult er2 = new EvalResult(bestMove, bestValue);
    // cache.put(board, player, plies, er2);
    return er2;
  }
}
