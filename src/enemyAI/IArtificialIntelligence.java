package enemyAI;

import java.awt.Point;
import model.IBoard;
import model.Mark;

/**
 * Interface for ArtificialIntelligence on the TicTacToe game.
 * The IA need an updated board, and its mark to generate a point to add a Mark.
 */
public interface IArtificialIntelligence {

  /**
   * Updates the board for the AI.
   *
   * @param board the updates board.
   */
  void updateBoard(IBoard board);

  /**
   * Generates the point the AI will add its Mark.
   *
   * @return the Point where the AI will add its mark. IllegalArgumentException if the Mark for the
   * AI was not set yet.
   */
  Point makeMove();

  /**
   * Get method for the AI's mark.
   *
   * @return the AI's mark. It can be null if it was not set.
   */
  Mark getAIMark();

  /**
   * Set the AI's Mark.
   *
   * @param aiMark is mark for the AI.
   */
  void setAIMark(Mark aiMark);
}
