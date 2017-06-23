package enemyAI.decisions;

import java.awt.Point;
import java.io.IOException;
import model.IBoard;
import model.Mark;

public abstract class Decision {
  final int UNDEFINED = -1;
  final int USER_HAS_TWO_MARKS_LINE = 0;
  final int USER_HAS_TWO_MARKS_COLUMN = 1;
  final int USER_HAS_TWO_MARKS_DIAGONAL = 2;
  final int AI_HAS_TWO_MARKS_LINE = 3;
  final int AI_HAS_TWO_MARKS_COLUMN = 4;
  final int AI_HAS_TWO_MARKS_DIAGONAL = 5;

  protected IBoard board;
  Mark aiMark;
  static Appendable app;
  static boolean log;

  public Point getDecision(){
    throw new IllegalArgumentException("This method must be overriden");
  }

  static public void setLog(Appendable append){
    app = append;
  }

  static public void toggleLog(){
    log = !log;
  }

  protected void writeLog(String s) {
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

  protected void breakLine() {
    writeLog("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-\n");
  }

  protected boolean twoMarksAndOneEmptyAtColumn(int n, Mark mark) {
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
  protected boolean twoMarksAndOneEmptyAtLine(int n, Mark mark) {
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
  protected boolean twoMarksAndOneEmptyAtDiagonal(Mark mark) {
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


  protected Point remaining(int x1, int y1, int x2, int y2) {
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


  protected String ponto(int x, int y) {
    return " [" + x + "," + y + "] ";
  }

  protected String ponto(Point p) {
    return " [" + p.x + "," + p.y + "] ";
  }

  protected int missingNumber(int n1, int n2) {
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

  protected Point remainingPointColumn(int n) {
    for (int i = 0; i < 3; i++) {
      if (board.isEmptyAt(i, n)) {
        writeLog("Only empty spot on column [" + n + "]. Returning point: " + ponto(i, n));
        return new Point(i, n);
      }
    }
    throw new IllegalArgumentException("AI attempting to create null Point at Column [" + n + "]");
  }

  protected Point remainingPointLine(int n) {
    for (int i = 0; i < 3; i++) {
      if (board.isEmptyAt(n, i)) {
        writeLog("Only empty spot on line [" + n + "]. Returning point: " + ponto(n, i));
        return new Point(n, i);
      }
    }
    throw new IllegalArgumentException("AI attempting to create null Point at Line [" + n + "]");
  }

  protected Point remainingPointDiagonal(boolean ascend) {
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

  protected boolean ascendDiagonal() {
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
}
