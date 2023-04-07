package gtf.games.mancala.solve;

import gtf.games.mancala.Board;

public interface Solver {

  /**
   * Minimax algorithm
   * 
   * @param board Board to evaluate
   * @param nextPlayer Player who plays next on this board
   * @param player evaluate board relative to this player
   * @param plies recursion depth
   * @return
   */
  EvalResult evaluate(Board board, int player, int plies);

}