package enemyAI.decisions;

import enemyAI.IArtificialIntelligence;
import java.awt.Point;
import model.IBoard;
import model.Mark;

public class WinBlock extends Decision{

  public WinBlock(IArtificialIntelligence ai){
    board = ai.getBoard();
    aiMark = ai.getAIMark();
  }

  public WinBlock(IBoard b, Mark ai) {
    board = b;
    aiMark = ai;
  }

  @Override
  public Point getDecision() {
    int condition = UNDEFINED;
    int position = -1;
    for (int i = 0; i < 3; i++) {
      if (twoMarksAndOneEmptyAtLine(i, aiMark.other())) {
        position = i;
        condition = USER_HAS_TWO_MARKS_LINE;
        writeLog("Condition set to: [User has 2 Marks on at least one line]");
        breakLine();
        break;
      } else if (twoMarksAndOneEmptyAtColumn(i, aiMark.other())) {
        position = i;
        condition = USER_HAS_TWO_MARKS_COLUMN;
        writeLog("Condition set to: [User has 2 Marks on at least one column]");
        breakLine();
        break;
      }
    }
    if (condition == UNDEFINED) {
      if (twoMarksAndOneEmptyAtDiagonal(aiMark.other())) {
        condition = USER_HAS_TWO_MARKS_DIAGONAL;
        writeLog("Condition set to: [User has 2 Marks on at least one diagonal]");
        breakLine();
      }
    }
    return useCondition(condition, position);
  }

  /**
   * If the user is one step away from winning, and prevent him from winning.
   *
   * @param condition is the condition the area is at.
   * @return the Point to add a Mark.
   */
  private Point useCondition(int condition, int pos) {
    if (aiMark == null) {
      throw new IllegalArgumentException("Mark for AI was not set.");
    }
    switch (condition) {
      case USER_HAS_TWO_MARKS_LINE: {
        writeLog("User have two marks on line [" + pos + "]");
        breakLine();
        return remainingPointLine(pos);
      }
      case USER_HAS_TWO_MARKS_COLUMN: {
        writeLog("User have two marks on column [" + pos + "]");
        breakLine();
        return remainingPointColumn(pos);
      }
      case USER_HAS_TWO_MARKS_DIAGONAL: {
        writeLog("User have two marks on one diagonal");
        breakLine();
        return remainingPointDiagonal(ascendDiagonal());
      }
      default: {
        writeLog("[WinBlock] AI found an invalid point. Returning Null [" + condition + "]");
        breakLine();
        return null;
      }
    }
  }

}
