package gtf.games.mancala;

interface Location {

  /**
   * Number of stones at this location.
   * 
   * @return
   */
  int getStones();
   
  /**
   * Next location to move to for the given player.
   * 
   * @param player
   * @return
   */
  Location next(int player);
  
  /**
   * Drop a stone at this location.
   * 
   * @param player
   * @param isLast This is the last stone of the move
   */
  void dropStone(int player, boolean isLast);
  
  /**
   * Remove stones (applicable only to Pit)
   * 
   * @param stones
   */
  void removeStones(int stones);
  
  /**
   * player gets another move if lands on his own calah
   * (Note we don't need to pass the player because a player
   * can never land in his opponent's calah)
   *
   * @return
   */
  boolean anotherMove();

}
