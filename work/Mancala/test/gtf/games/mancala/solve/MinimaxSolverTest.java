package gtf.games.mancala.solve;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

import gtf.games.mancala.Board;

public class MinimaxSolverTest {

  @Ignore
  @Test
  public void test() {
    Board board = new Board(6, 1);
    Solver solver = new MinimaxSolver();
    solver.evaluate(board, 0, 2);
  }

}
