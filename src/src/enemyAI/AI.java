package enemyAI;

import java.awt.Point;
import java.util.Random;
import model.IBoard;
import model.Mark;

/**
 * Created by Alexandre on 16/06/2017.
 */
public class AI implements IArtificialIntelligence {

  private IBoard board;
  private Mark aiMark;

  public AI(Mark aiMark) {
    this.aiMark = aiMark;
  }

  public AI() {

  }


  @Override
  public void updateBoard(IBoard board) {
    this.board = board;
  }

  @Override
  public Point makeMove() {
    return randomDecision();
  }

  @Override
  public Mark getAIMark() {
    return aiMark;
  }

  @Override
  public void setAIMark(Mark aiMark) {
    this.aiMark = aiMark;
  }

  private IBoard bestDecision() {
    /*
     * Not yet implemented.
     */
    return null;
  }

  /**
   * Adds a Mark on a Random Valid Position.
   * Tries at least 10.000 times to find a Valid Position, otherwise it will throw an exception.
   *
   * @return the Point to add a Mark.
   */
  private Point randomDecision() {
    if (aiMark == null) {
      throw new IllegalArgumentException("Mark for AI was not set.");
    }
    Random rng = new Random();
    boolean found = false;
    int attempt = 0;
    int x = -1;
    int y = -1;
    while (!found) {
      x = Math.abs(rng.nextInt(3));
      y = Math.abs(rng.nextInt(3));
      found = board.isEmptyAt(x, y);
      attempt++;
      if (attempt > 10000) {
        throw new IllegalArgumentException("AI Stuck in Loop. Internal Error.");
      }
    }
    return new Point(x, y);
  }
}
