package gtf.games.mancala;

/**
 * Represents moving some number of stones from one Location.
 * 
 * In the strict rules of the game,
 * 1) location must be on the player's side of the board
 * 2) player must pick up ALL of the stones at that location.
 * This class does not enforce these rules.
 * 
 * @author gtf
 */
public class Move {

  /**
   * The player to move
   */
  private final int player;

  /**
   * The number of stones in the move.
   * If 0 then this is a terminal position.
   */
  private final int stones;

  /**
   * The location to start the move
   * 
   * @param player
   * @param stones
   */
  private final Location start;

  public int getPlayer() {
    return player;
  }

  public int getStones() {
    return stones;
  }
  
  Location getStart() {
    return start;
  }
  
  /**
   * Used in the recursion.
   * 
   * @param player
   * @param stones
   * @param location
   */
  Move(int player, int stones, Location start) {
    this.player = player;
    this.stones = stones;
    this.start = start;
  }
  
  /**
   * Apply this move
   * 
   * @return location after move
   */
  Location apply() {
    // initial start is always a Pit
    start.removeStones(stones);
    Move tail = step(stones == 1);
    while (tail.stones > 0) {
      tail = tail.step(tail.stones == 1);
    }
    return tail.getStart();
  }
  
  /**
   * Apply 1 step of this move to a (mutable) board
   * 
   * @param board
   * @return the next step in the move
   */
  private Move step(boolean isLast) {
    Location next = start.next(player);
    next.dropStone(player, isLast);
    return new Move(player, stones - 1, next);
  }

}
