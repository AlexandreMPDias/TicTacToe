package enemyAI;

import java.awt.Point;
import java.io.IOException;
import java.util.Random;
import model.IBoard;
import model.Mark;

public class AI implements IArtificialIntelligence {

  private final int UNDEFINED = -1;
  private final int USER_HAS_TWO_MARKS_LINE = 0;
  private final int USER_HAS_TWO_MARKS_COLUMN = 1;
  private final int USER_HAS_TWO_MARKS_DIAGONAL = 2;
  private final int AI_HAS_TWO_MARKS_LINE = 3;
  private final int AI_HAS_TWO_MARKS_COLUMN = 4;
  private final int AI_HAS_TWO_MARKS_DIAGONAL = 5;
  private IBoard board;
  private Mark aiMark;
  private Appendable app;
  private boolean log;
  private int difficulty;


  public AI(Mark aiMark) {
    this.aiMark = aiMark;
    log = false;
    Random rng = new Random();
    difficulty = rng.nextInt(20) + 10;
  }

  public AI() {
    this(null);
  }

  @Override
  public void updateBoard(IBoard board) {
    this.board = board;
  }

  @Override
  public Point makeMove() {
    try {
      Random rng = new Random();
      int index = Math.abs(rng.nextInt(100));
      writeLog("Choosing Move. 10% Chance of going random.");
      if (index < difficulty) {
        Point randomMove = randomDecision();
        writeLog("Random move chosen. Returning random move" + ponto(randomMove));
        return randomMove;
      } else {
        Point p = makeMove_ensureNonRandom();
        if (p == null) {
          writeLog(
              "Treating makeMove_ensureNonRandom returning Null, and returning random valid point instead.");
          p = randomDecision();
        }
        writeLog("Returning Point" + ponto(p) + "\n");
        return p;
      }
    } catch (IllegalArgumentException iae) {
      System.out.println(
          "Error: " + iae.getMessage() + ".\nTreating that exception and now resuming runtime.");
    }
    return randomDecision();
  }

  @Override
  public Point makeMove_ensureNonRandom() {
    Point p = tryWin();
    if (p == null) {
      p = winBlock();
      if (p == null) {
        if (board.isEmptyAt(1, 1)) {
          writeLog("Strategy Chosen: [AI: mark center]\nAdding mark to" + ponto(1, 1));
          return new Point(1, 1);
        }
        writeLog("makeMove_ensureNonRandom returning Null");
        return null;
      }
    }
    return p;
  }

  @Override
  public Mark getAIMark() {
    return aiMark;
  }

  @Override
  public void setAIMark(Mark aiMark) {
    writeLog("AI Mark set to " + aiMark);
    this.aiMark = aiMark;
  }

  @Override
  public void toggleLog(Appendable appendable) {
    this.app = appendable;
    if (log) {
      log = false;
    } else {
      log = true;
    }
  }

  @Override
  public void toggleLog() {
    if (log) {
      log = false;
    } else {
      log = true;
    }
  }

  private Point tryWin() {
    int condition = UNDEFINED;
    int position = -1;
    for (int i = 0; i < 3; i++) {
      if (!isLineFull(i) || !isColumnFull(i)) {
        if (twoMarksAtLine(i, aiMark)) {
          condition = AI_HAS_TWO_MARKS_LINE;
          writeLog("Condition set to: [AI has 2 Marks on at least one line]");
          breakLine();
          position = i;
          break;
        } else if (twoMarksAtColumn(i, aiMark)) {
          condition = AI_HAS_TWO_MARKS_COLUMN;
          writeLog("Condition set to: [AI has 2 Marks on at least one column]");
          breakLine();
          position = i;
          break;
        }
      }
    }
    if (condition == UNDEFINED) {
      if (twoMarksAtDiagonal(aiMark)) {
        condition = AI_HAS_TWO_MARKS_DIAGONAL;
        writeLog("Condition set to: [AI has 2 Marks on at least one diagonal]");
        breakLine();
      }
    }
    return tryWinUseCondition(condition, position);
  }

  private Point winBlock() {
    int condition = UNDEFINED;
    int position = -1;
    for (int i = 0; i < 3; i++) {
      if (twoMarksAtLine(i, aiMark.other())) {
        position = i;
        condition = USER_HAS_TWO_MARKS_LINE;
        writeLog("Condition set to: [User has 2 Marks on at least one line]");
        breakLine();
        break;
      } else if (twoMarksAtColumn(i, aiMark.other())) {
        position = i;
        condition = USER_HAS_TWO_MARKS_COLUMN;
        writeLog("Condition set to: [User has 2 Marks on at least one column]");
        breakLine();
        break;
      }
    }
    if (condition == UNDEFINED) {
      if (twoMarksAtDiagonal(aiMark.other())) {
        condition = USER_HAS_TWO_MARKS_DIAGONAL;
        writeLog("Condition set to: [User has 2 Marks on at least one diagonal]");
        breakLine();
      }
    }
    return winBlockUseCondition(condition, position);
  }

  /**
   * If the user is one step away from winning, and prevent him from winning.
   *
   * @param condition is the condition the board is at.
   * @return the Point to add a Mark.
   */
  private Point winBlockUseCondition(int condition, int pos) {
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
        writeLog("[winBlock] AI found an invalid point. Returning Null [" + condition + "]");
        breakLine();
        return null;
      }
    }
  }

  /**
   * If the AI is one step away from victory.
   *
   * @param condition is the condition the board is at.
   * @return the Point to add a Mark.
   */
  private Point tryWinUseCondition(int condition, int pos) {
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

  private boolean twoMarksAtColumn(int n, Mark mark) {
    writeLog("\tChecking for any two marks on Column [" + n + "] from " + mark);
    boolean ret = false;
    for (int i = 0; i < 3; i++) {
      boolean a = (board.markAt(i, n) == board.markAt((i + 1) % 2, n));
      boolean c = !board.isEmptyAt(i, n) && !board.isEmptyAt((i + 1) % 2, n);
      boolean d = board.isEmptyAt(remaining(i, n, (i + 1) % 2, n));
      boolean b = board.markAt(i, n) == mark;
      ret = a && b && c && d;
      writeLog("\t\t[Column] if point" + ponto(i, n) + "and" + ponto((i + 1) % 2, n)
          + "are the same == [" + a + "]");
      writeLog("\t\t[Column] if both points are not empty == [" + c + "]");
      writeLog("\t\t[Column] if the remaining point " + ponto(remaining(i, n, (i + 1) % 2, n))
          + "is empty == [" + d + "]");
      writeLog(
          "\t\t[Column] if point" + ponto(i, n) + "contains mark [" + mark + "] == [" + b + "]");
      writeLog("\t\tAll of the above conditions must be met. == [" + ret + "]\n");
      if (ret) {
        writeLog("Returning [" + ret + "]");
        return ret;
      }
    }
    writeLog("Returning [" + ret + "]");
    return ret;
  }

  private boolean twoMarksAtLine(int n, Mark mark) {
    writeLog("\tChecking for any two marks on Line [" + n + "] from " + mark);
    boolean ret = false;
    for (int i = 0; i < 3; i++) {
      boolean a = (board.markAt(n, i) == board.markAt(n, (i + 1) % 2));
      boolean b = board.markAt(n, i) == mark;
      boolean d = board.isEmptyAt(remaining(n, i, n, (i + 1) % 2));
      boolean c = !board.isEmptyAt(n, i) && !board.isEmptyAt(n, (i + 1) % 2);
      ret = a && b && c && d;
      writeLog("\t\t[Line] if point" + ponto(n, i) + "and" + ponto(n, (i + 1) % 2)
          + "are the same == [" + a + "]");
      writeLog("\t\t[Line] if both points are not empty == [" + c + "]");
      writeLog("\t\t[Line] if the remaining point " + ponto(remaining(n, i, n, (i + 1) % 2))
          + "is empty == [" + d + "]");
      writeLog(
          "\t\t[Line] if point" + ponto(n, i) + "contains mark [" + mark + "] == [" + b + "]");
      writeLog("\t\tAll of the above conditions are met. == [" + ret + "]\n");
      if (ret) {
        return ret;
      }
    }
    return ret;
  }

  private boolean twoMarksAtDiagonal(Mark mark) {
    writeLog("\tChecking for any two marks on diagonals.");
    boolean ret = false;
    int[][] diags;
    for (int j = 0; j < 2; j++) {
      if (j == 0) {
        diags = new int[][]{{0, 0}, {1, 1}, {2, 2}, {0, 0}, {2, 2}, {1, 1}, {0, 0}};
      } else {
        diags = new int[][]{{2, 0}, {1, 1}, {0, 2}, {2, 0}, {0, 2}, {1, 1}, {2, 0}};
      }
      for (int i = 0; i < diags.length; i++) {
        int[] a = diags[(i)];
        int[] b = diags[(i + 1) % diags.length];
        int[] c = diags[(i + 2) % diags.length];
        boolean h = (a[0] != b[0]) && (a[1] != b[1]);
        if (h) {
          boolean x = board.markAt(a[0], a[1]) == board.markAt(b[0], b[1]);
          boolean y = board.isEmptyAt(c[0], c[1]);
          boolean z = board.markAt(a[0], a[1]) == mark;
          ret = x && y && z;
          writeLog("\t\t[Diagonals] if point" + ponto(a[0], a[1]) + "and" + ponto(b[0], b[1])
              + "are the same == [" + x + "]");
          writeLog("\t\t[Diagonals] if the point " + ponto(c[0], c[1]) + "is empty == [" + y + "]");
          writeLog(
              "\t\t[Diagonals] if point" + ponto(a[0], a[1]) + "contains mark [" + mark + "] == ["
                  + z
                  + "]");
          writeLog("\t\tAll of the above conditions are met == [" + ret + "]\n");
          if (ret) {
            return ret;
          }
        }
      }
    }
    return ret;
  }

  private Point remainingPointColumn(int n) {
    for (int i = 0; i < 3; i++) {
      if (board.isEmptyAt(i, n)) {
        writeLog("Only empty spot on column [" + n + "]. Returning point: " + ponto(i, n));
        return new Point(i, n);
      }
    }
    throw new IllegalArgumentException("AI attempting to create null Point at Column [" + n + "]");
  }

  private Point remainingPointLine(int n) {
    for (int i = 0; i < 3; i++) {
      if (board.isEmptyAt(n, i)) {
        writeLog("Only empty spot on line [" + n + "]. Returning point: " + ponto(n, i));
        return new Point(n, i);
      }
    }
    throw new IllegalArgumentException("AI attempting to create null Point at Line [" + n + "]");
  }

  private Point remainingPointDiagonal(boolean ascend) {
    for (int i = 0; i < 3; i++) {
      if (ascend) {
        if (board.isEmptyAt(2 - i, i)) {
          return new Point(2 - i, i);
        }
      } else {
        if (board.isEmptyAt(i, i)) {
          return new Point(i, i);
        }
      }
    }
    throw new IllegalArgumentException("AI attempting to create null Point");
  }

  private boolean ascendDiagonal() {
    boolean a = board.isEmptyAt(0, 0);
    boolean b = board.isEmptyAt(0, 2);
    boolean c = board.isEmptyAt(2, 0);
    boolean d = board.isEmptyAt(2, 2);
    if (a && b) {
      return d;
    } else if (c && d) {
      return a;
    } else if (c && a) {
      return false;
    } else if (b && d) {
      return false;
    } else if (a) {
      return false;
    } else if (b) {
      return true;
    } else if (c) {
      return true;
    } else if (d) {
      return false;
    }
    return true;
  }

  private void writeLog(String s) {
    if (log) {
      if (app != null) {
        try {
          app.append(s.subSequence(0, s.length()));
        } catch (IOException e) {
          e.printStackTrace();
        }
      } else {
        System.out.println(s);
      }
    }
  }

  private String ponto(int x, int y) {
    return " [" + x + "," + y + "] ";
  }

  private String ponto(Point p) {
    return " [" + p.x + "," + p.y + "] ";
  }

  private void breakLine() {
    writeLog("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-\n");
  }

  private boolean isLineFull(int n) {
    return (!board.isEmptyAt(n, 0) && !board.isEmptyAt(n, 1) && !board.isEmptyAt(n, 2));
  }

  private boolean isColumnFull(int n) {
    return (!board.isEmptyAt(0, n) && !board.isEmptyAt(1, n) && !board.isEmptyAt(2, n));
  }

  private Point remaining(int x1, int y1, int x2, int y2) {
    if (x1 == x2) {
      //Same column
      return new Point(x1, missingNumber(y1, y2));
    } else if (y1 == y2) {
      //Same Line
      return new Point(missingNumber(x1, x2), y1);
    } else if (x1 + x2 == 3 && y1 + y2 == 3) {
      //Ascending Diagonal
      return new Point(missingNumber(x1, x2), missingNumber(y1, y2));
    } else {
      //Descending Diagonal
      return new Point(missingNumber(x1, x2), missingNumber(y1, y2));
    }
  }


  private int missingNumber(int n1, int n2) {
    int a, b;
    if (n1 < n2) {
      a = n1;
      b = n2;
    } else {
      a = n2;
      b = n1;
    }
    if (a == 0 && b == 1) {
      return 2;
    } else if (a == 1 && b == 2) {
      return 0;
    } else {
      return 1;
    }
  }
}
