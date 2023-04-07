package gtf.games.mancala.play;

import gtf.games.mancala.Board;

public interface PlayerStrategy {

  /**
   * Choose a move for player on the given board.
   * 
   * @param board
   * @param player
   * @return move
   */
  int getMove(Board board, int player);
  
}
