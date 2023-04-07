package gtf.games.mancala.solve;

import java.util.HashMap;
import java.util.Map;

import gtf.games.mancala.Board;

class Cache {

  private static class Key {
    Board board;
    int nextPlayer;

    Key(Board board, int nextPlayer) {
      this.board = board;
      this.nextPlayer = nextPlayer;
    }

    @Override
    public int hashCode() {
      return board.hashCode() << nextPlayer;
    }

    @Override
    public boolean equals(Object o) {
      Key k1 = (Key) o;
      return nextPlayer == k1.nextPlayer && board.equals(k1.board);
    }
  }

  private final Map<Key, EvalResult> map;

  Cache() {
    map = new HashMap<Key, EvalResult>();
  }

  // Value of a board is always stored from player 0's perspective.
  // For player 1 then negate the value
  public EvalResult lookup(Board board, int nextPlayer, int player) {
    Key key = new Key(board, nextPlayer);
    EvalResult erc = map.get(key);
    if (erc == null) {
      return null;
    }
    return adjust(erc, player);
  }

  private EvalResult adjust(EvalResult erc, int player) {
    // cache always contains value relative to player 0
    if (player == 0) {
      return erc;
    }
    return new EvalResult(erc.getBestMove(), -erc.getValue());
  }

  public void put(Board board, int nextPlayer, EvalResult er, int player) {
    // TODO Auto-generated method stub
    Key key = new Key(board, nextPlayer);
    map.put(key, adjust(er, player));
  }
}
