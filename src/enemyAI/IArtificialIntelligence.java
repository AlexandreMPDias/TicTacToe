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
   * The AI will follow a set of strategies with a small chance of running a random, but valid,
   * move.
   *
   * @return the Point where the AI will add its mark. IllegalArgumentException if the Mark for the
   * AI was not set yet.
   */
  Point makeMove();

  /**
   * Generates the point the AI will add its Mark.
   *
   * The AI will follow a set of strategies, ensuring no random numbers take part.
   *
   * @return the Point where the AI will add its mark. IllegalArgumentException if the Mark for the
   * AI was not set yet. Beware. To ensure zero RNG involved, it may return null, if no available
   * decision is found.
   */
  Point makeMove_ensureNonRandom();

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

  /**
   * Turn a set of Outputs that can help manage what is happening on runtime inside the AI.
   *
   * @param appendable is the Appendable output. Not passing this parameter, or passing it as null,
   * is the same as passing System.out as an Appendable
   */
  void toggleLog(Appendable appendable);

  void toggleLog();
}
