package gtf.games.mancala;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class BoardTest {

  @Test
  public void captureTest() throws IllegalMoveException {
    Board board = new Board(6, 0);
    board.dropStonesInPit(1,  4, 1);
    board.dropStonesInPit(0, 5, 1);
    MoveResult mr = board.move(1, 4);
    assertEquals(2, board.getCalahStones(1));
    assertEquals(0, board.getCalahStones(0));
    assertEquals(0, board.getPitStones(1, 4));
  }

  @Test
  public void anotherMoveTest() throws IllegalMoveException {
    Board board = new Board(6, 1);
    MoveResult mr = board.move(0,  0);
    assertTrue(mr.isAnotherMove());
  }
}
