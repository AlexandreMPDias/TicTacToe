package enemyAI.decisions;

import enemyAI.IArtificialIntelligence;
import java.awt.Point;
import model.IBoard;
import model.Mark;

public class TryToWin extends Decision {

  public TryToWin(IArtificialIntelligence ai) {
    board = ai.getBoard();
    aiMark = ai.getAIMark();
  }

  public TryToWin(IBoard b, Mark ai) {
    board = b;
    aiMark = ai;
  }

  @Override
  public Point getDecision() {
    int condition = UNDEFINED;
    int position = -1;
    for (int i = 0; i < 3; i++) {
      if (!board.isLineFull(i) || !board.isColumnFull(i)) {
        if (twoMarksAndOneEmptyAtLine(i, aiMark)) {
          condition = AI_HAS_TWO_MARKS_LINE;
          writeLog("Condition set to: [AI has 2 Marks on at least one line]");
          breakLine();
          position = i;
          break;
        } else if (twoMarksAndOneEmptyAtColumn(i, aiMark)) {
          condition = AI_HAS_TWO_MARKS_COLUMN;
          writeLog("Condition set to: [AI has 2 Marks on at least one column]");
          breakLine();
          position = i;
          break;
        }
      }
    }
    if (condition == UNDEFINED) {
      if (twoMarksAndOneEmptyAtDiagonal(aiMark)) {
        condition = AI_HAS_TWO_MARKS_DIAGONAL;
        writeLog("Condition set to: [AI has 2 Marks on at least one diagonal]");
        breakLine();
      }
    }
    return useCondition(condition, position);
  }


  Point useCondition(int condition, int pos) {
    if (aiMark == null) {
      throw new IllegalArgumentException("Mark for AI was not set.");
    }
    switch (condition) {
      case AI_HAS_TWO_MARKS_LINE: {
        writeLog("AI has two marks on line [" + pos + "]");
        breakLine();
        return remainingPointLine(pos);
      }
      case AI_HAS_TWO_MARKS_COLUMN: {
        writeLog("AI has two marks on column [" + pos + "]");
        breakLine();
        return remainingPointColumn(pos);
      }
      case AI_HAS_TWO_MARKS_DIAGONAL: {
        writeLog("AI has two marks on one diagonal");
        breakLine();
        return remainingPointDiagonal(ascendDiagonal());
      }
      default: {
        writeLog("[tryWin] AI found an invalid point. Returning Null [" + condition + "]");
        breakLine();
        return null;
      }
    }
  }
}
