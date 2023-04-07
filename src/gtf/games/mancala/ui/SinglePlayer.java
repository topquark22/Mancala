package gtf.games.mancala.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

import gtf.games.mancala.Board;
import gtf.games.mancala.IllegalMoveException;
import gtf.games.mancala.MoveResult;
import gtf.games.mancala.solve.EvalResult;
import gtf.games.mancala.solve.MinimaxSolver;
import gtf.games.mancala.solve.Solver;

public class SinglePlayer {

  public static void main(String[] args) throws IOException {

    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    PrintStream out = System.out;

    int depth = readInt(in, out, 1, "Enter recursion depth (>= 1): ");
    int player = readInt(in, out, 0, 1, "You are player 1, opponent is player 0. Which player moves first? ");

    Solver solver = new MinimaxSolver();
    Board board = new Board();

    boolean gameOver = false;
    while (!gameOver) {
      System.out.println(board.toString());
      System.out.println("It is " + hr(player) + " turn");

      MoveResult mr;
      if (player == 0) {
        mr = handleOpponent(in, out, board);
      } else {
        mr = handleYou(out, board, solver, depth);
      }

      gameOver = mr.isGameOver();
      if (gameOver) {
        break;
      }

      if (mr.isAnotherMove()) {
        System.out.println(hr1(player) + " another move");
      } else {
        player = 1 - player;
      }
    }
  }

  private static MoveResult handleOpponent(BufferedReader in, PrintStream out, Board board) throws IOException {
    while (true) {
      try {
        int opponentMove = readInt(in, out, 0, 5, "Enter opponent's move: ");
        out.println("Opponent moves " + opponentMove);
        return board.move(0, opponentMove);
      } catch (IllegalMoveException e) {
        out.println(e.getMessage());
      }
    }
  }

  private static MoveResult handleYou(PrintStream out, Board board, Solver solver, int depth) {
    while (true)
      try {
        EvalResult er = solver.evaluate(board, 1, depth);
        int bestMove = er.getBestMove();
        out.println("You should play " + bestMove);
        return board.move(1, bestMove);
      } catch (IllegalMoveException e) {
        out.println(e.getMessage());
      }
  }

  private static int readInt(BufferedReader in, PrintStream out, int min, int max, String prompt) {
    Integer result = null;
    while (result == null) {
      try {
        out.print(prompt);
        String line = in.readLine();
        result = Integer.parseInt(line.trim());
        if (result < min || result > max) {
          result = null;
        }
      } catch (NumberFormatException | IOException | NullPointerException e) {
        // try again
      }
    }
    return result;
  }

  private static int readInt(BufferedReader in, PrintStream out, int min, String prompt) {
    return readInt(in, out, min, Integer.MAX_VALUE, prompt);
  }

  private static String hr(int player) {
    if (player == 0) {
      return "opponent's";
    } else {
      return "your";
    }
  }

  private static String hr1(int player) {
    if (player == 0) {
      return "opponent gets";
    } else {
      return "you get";
    }
  }
}
