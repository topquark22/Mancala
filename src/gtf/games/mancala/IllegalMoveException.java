package gtf.games.mancala;

public class IllegalMoveException extends Exception {

  private static final long serialVersionUID = 1L;

  IllegalMoveException() {
    super("IllegalMove");
  }
}
