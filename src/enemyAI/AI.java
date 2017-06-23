package enemyAI;

import enemyAI.decisions.Decision;
import enemyAI.decisions.TryToWin;
import enemyAI.decisions.WinBlock;
import java.awt.Point;
import java.io.IOException;
import java.util.Random;
import model.IBoard;
import model.Mark;

public class AI implements IArtificialIntelligence {

  protected IBoard board;
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
    Decision d = new TryToWin(this);
    Point p = d.getDecision();
    if (p == null) {
      d = new WinBlock(this);
      p = d.getDecision();
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

  @Override
  public IBoard getBoard() {
    return board;
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

  private String ponto(int x, int y) {
    return " [" + x + "," + y + "] ";
  }

  private String ponto(Point p) {
    return " [" + p.x + "," + p.y + "] ";
  }

}
