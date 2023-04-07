package gtf.games.mancala;

public class MoveResult {

  private final boolean anotherMove;
  
  private final boolean gameOver;

  public boolean isAnotherMove() {
    return anotherMove;
  }

  public boolean isGameOver() {
    return gameOver;
  }  
  
  public MoveResult(boolean anotherMove, boolean gameOver) {
    this.anotherMove = anotherMove;
    this.gameOver = gameOver;
  }
  
  @Override
  public String toString() {
    return "[anotherMove=" + anotherMove + " gameOver=" + gameOver + "]";
  }
}
