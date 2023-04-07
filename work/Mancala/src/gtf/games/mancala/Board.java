package gtf.games.mancala;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents data about where stones are on the board.
 * 
 * @author gtf
 *
 */
public class Board {

  private static final int DEFAULT_PITS = 6;

  private static final int DEFAULT_STONES = 4;

  /**
   * This is controversial. If set to true, landing in an empty pit
   * on your side will send your piece to your calah even if the opposite
   * pit is empty.
   */
  public static final boolean ALLOW_EMPTY_CAPTURE = false;

  private final int width;

  /**
   * int[2][width]
   */
  private final int[][] pits;

  private final int[] calahs;


  /**
   * Constructs a board in the initial state with default values.
   */
  public Board() {
    this(DEFAULT_PITS, DEFAULT_STONES);
  }

  /**
   * Constructs a board in the initial state.
   * 
   * @param width number of pits on each side
   * @param stones initial number of stones per pit
   */
  public Board(int width, int stones) {
    this.width = width;
    pits = new int[2][width];
    calahs = new int[2];
    for (int side = 0; side < 2; side++) {
      Arrays.fill(pits[side], stones);
    }
  }

  public int getWidth() {
    return width;
  }

  /**
   * 
   * @param side which side of the board
   * @param pit index of the pit
   * @return number of stones in the pit
   */
  public int getPitStones(int side, int pit) {
    return pits[side][pit];
  }

  /**
   * 
   * @param player
   * @return number of stones in the player's calah
   */
  public int getCalahStones(int player) {
    return calahs[player];
  }

  /**
   * drop stones into a pit (usually 1).
   * 
   * @param side
   * @param pit
   * @param stones
   */
  public void dropStonesInPit(int side, int pit, int stones) {
    pits[side][pit] += stones;
  }

  /**
   * remove stones from a pit (usually all of them)
   */
  private void removeStonesFromPit(int side, int pit, int stones) {
    pits[side][pit] -= stones;
  }
  
  /**
   * Drop stone(s) into a player's calah.
   * 
   * @param player
   * @param stones number of stones
   */
  public void dropStonesInCalah(int player, int stones) {
    calahs[player] += stones;
  }

  /**
   * 
   * @param player
   * @return list of valid moves for player
   */
  public List<Integer> getValidMoves(int player) {
    List<Integer> validMoves = new ArrayList<Integer>();
    for (int pit = 0; pit < width; pit++) {
      if (getPitStones(player, pit) > 0) {
        validMoves.add(pit);
      }
    }
    return validMoves;
  }
  
  /**
   * Empty a pit
   * 
   * @param side
   * @param pit
   */
  public void clear(int side, int pit) {
    pits[side][pit] = 0;
  }

  /**
   * Constructs a copy of the board with the data from the
   * original board. The copy can be modified without changing
   * the original board.
   * 
   * @param board0
   */
  public static Board copy(Board board0) {
    Board board = new Board(board0.width, 0);
    for (int side = 0; side < 2; side++) {
      System.arraycopy(board0.pits[side], 0, board.pits[side], 0, board0.width);
    }
    for (int player = 0; player < 2; player++) {
      board.calahs[player] = board0.calahs[player];
    }
    return board;
  }

  /*
   * (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  public String toString() {
    StringBuffer buf = new StringBuffer();
    for (int player = 0; player < 2; player++) {
      buf.append("player " + player + " calah=" + calahs[player] + "\n");
    }
    buf.append("\n");
    for (int side = 0; side < 2; side++) {
      for (int pit = 0; pit < width; pit++) {
        buf.append("" + pits[side][pit] + " ");
      }
      buf.append("\n");
    }
    return buf.toString();
  }

  /**
   * This method should be called at the end of each move.
   * 
   * "The game ends when all of the pits on one side of the
   * board are empty." If this is the case, this method
   * captures all of the pieces for the player who has
   * pieces on his side of the board. Otherwise it does
   * nothing.
   * 
   * @return true if the game has ended
   */
  private boolean afterMove() {
    boolean ended = false;
    int side;
    for (side = 0; side < 2; side++) {
      boolean sideEmpty = true;
      for (int pit = 0; pit < width; pit++) {
        if (pits[side][pit] > 0) {
          sideEmpty = false;
          break;
        }
      }
      if (sideEmpty) {
        ended = true;
        break;
      }
    }
    if (!ended) {
      return false;
    }
    // capture stones
    int capturingPlayer = 1 - side;
    for (int pit = 0; pit < width; pit++) {
      dropStonesInCalah(capturingPlayer, getPitStones(capturingPlayer, pit));
      clear(capturingPlayer, pit);
    }
    return true;
  }
  
  @Override
  public int hashCode() {
    int hash = 0;
    for (int side = 0; side < 2; side++) {
      hash ^= pits[side].hashCode() << side;
    }
    return hash;
  }
  
  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Board)) {
      return false;
    }
    Board board1 = (Board)o;
    for (int side = 0; side < 2; side++) {
      for (int pit = 0; pit < width; pit++) {
        if (pits[side][pit] != board1.pits[side][pit]) {
          return false;
        }
      }
    }
    //ignore calahs
    return true;
  }
  
  /**
   * Used by client code to make a move on the board
   * 
   * @param player
   * @param pit
   * @return
   */
  public MoveResult move(int player, int pit) throws IllegalMoveException {
    if (getPitStones(player, pit) == 0) {
      throw new IllegalMoveException();
    }
    Board.Pit start = new Pit(player, pit);
    Move move = new Move(player, start.getStones(), start);
    Location end = move.apply();
    boolean gameOver = afterMove();
    return new MoveResult(end.anotherMove(), gameOver);
  }

  /**
   * A Pit on a Board instance.
   * 
   * @author gtf
   *
   */
  class Pit implements Location {

    private final int side;

    private final int pit;

    Pit(int side, int pit) {
      this.side = side;
      this.pit = pit;
    }

    /*
     * (non-Javadoc)
     * @see gtf.games.mancala.Location#getStones()
     */
    @Override
    public int getStones() {
      return Board.this.getPitStones(side, pit);
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see gtf.games.mancala.Location#next(int)
     */
    @Override
    public Location next(int player) {
      if (side == 0 && pit == 0) {
        if (side == player) {
          return new Calah(0);
        } else {
          // skip opponent's calah
          return new Pit(1, 0);
        }
      } else if (side == 1 && pit == width - 1) {
        if (side == player) {
          return new Calah(1);
        } else {
          // skip opponent's calah
          return new Pit(0, width - 1);
        }
      } else if (side == 0) {
        return new Pit(side, pit - 1);
      } else {
        return new Pit(side, pit + 1);
      }
    }

    /*
     * (non-Javadoc)
     * 
     * @see gtf.games.mancala.Location#updateBoard(gtf.games.mancala.Board,
     * gtf.games.mancala.Move)
     */
    @Override
    public void dropStone(int player, boolean isLast) {
      if (isLast && side == player && Board.this.getPitStones(side, pit) == 0
          && (ALLOW_EMPTY_CAPTURE || Board.this.getPitStones(1 - side, pit) > 0)
          ) {
        // capture
        int numStonesCaptured = Board.this.getPitStones(1 - side, pit);
        Board.this.dropStonesInCalah(player, 1 + numStonesCaptured);
        clear(1 - side, pit);
      } else {
        Board.this.dropStonesInPit(side, pit, 1);
      }
    }

    /*
     * (non-Javadoc)
     * 
     * @see gtf.games.mancala.Location#anotherMove(int)
     */
    @Override
    public boolean anotherMove() {
      return false;
    }

    /**
     * Used when starting a move
     * @param stones
     */
    @Override
    public void removeStones(int stones) {
      if (stones > getStones()) {
        throw new IllegalArgumentException("Not enough stones to remove");
      }
      Board.this.removeStonesFromPit(side, pit, stones);
    }
  }

  /**
   * A Calah on a Board instance.
   * 
   * @author gtf
   *
   */
   class Calah implements Location {

    /**
     * which calah (ie. which player's calah) this is
     */
    private final int calah;

    Calah(int calah) {
      this.calah = calah;
    }
    
    /*
     * (non-Javadoc)
     * @see gtf.games.mancala.Location#getStones()
     */
    @Override
    public int getStones() {
      return Board.this.getCalahStones(calah);
    }
    
    /*
     * (non-Javadoc)
     * @see gtf.games.mancala.Location#next(int)
     */
    @Override
    public Location next(int player) {
      if (calah == 0) {
        return new Pit(1, 0);
      } else {
        return new Pit(0, width - 1);
      }
    }
    
    /*
     * (non-Javadoc)
     * @see gtf.games.mancala.Location#dropStone(int)
     */
    @Override
    public void dropStone(int player, boolean isLast) {
      Board.this.dropStonesInCalah(calah, 1);
    }

    /*
     * (non-Javadoc)
     * @see gtf.games.mancala.Location#removeStones(int)
     */
    @Override
    public void removeStones(int stones) {
      throw new UnsupportedOperationException();
    }
    
    /*
     * (non-Javadoc)
     * @see gtf.games.mancala.Location#anotherMove()
     */
    @Override
    public boolean anotherMove() {
      // Always another move because a player can only ever
      // land in his own calah.
      return true;
    }
  }

}