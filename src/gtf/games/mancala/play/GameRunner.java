package gtf.games.mancala.play;

import java.io.PrintStream;

import gtf.games.mancala.Board;
import gtf.games.mancala.IllegalMoveException;
import gtf.games.mancala.MoveResult;
import gtf.io.NullOutputStream;

public class GameRunner {

  private final int width;

  private final int stones;

  private final PlayerStrategy[] strategies;

  private final PrintStream console;

  public GameRunner(int width, int stones, PlayerStrategy player0strategy, PlayerStrategy player1strategy,
      PrintStream console) {
    this.width = width;
    this.stones = stones;
    strategies = new PlayerStrategy[2];
    strategies[0] = player0strategy;
    strategies[1] = player1strategy;
    this.console = console;
  }

  public GameRunner(int width, int stones, PlayerStrategy player0strategy, PlayerStrategy player1strategy) {
    this(width, stones, player0strategy, player1strategy, new PrintStream(new NullOutputStream()));
    //this(width, stones, player0strategy, player1strategy, System.out);
  }

  /**
   * 
   * @return array with calah count for players 0, 1
   */
  public int[] run() throws IllegalMoveException {
    Board board = new Board(width, stones);
    int player = 0;
    boolean gameOver = false;
    console.println("Player " + player + " goes first");
    console.println(board);
    while (!gameOver) {
      int move = strategies[player].getMove(board, player);
      console.println("Player " + player + " move: (" + player + ", " + move + ")");
      console.println();
      MoveResult mr = board.move(player, move);
      console.println(board);
      player = mr.isAnotherMove() ? player : 1 - player;
      gameOver = mr.isGameOver();
    }
    int[] calahCounts = new int[] { board.getCalahStones(0), board.getCalahStones(1) };
    //console.println(Arrays.toString(calahCounts));
    return calahCounts;
  }

}