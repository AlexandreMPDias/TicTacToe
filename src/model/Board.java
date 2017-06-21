package model;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

/**
 * Board Class implements {@link IBoard}.
 * Creates a Board using an Array of Array.
 * Methods made for checking winning conditions were made in a very Hard Coded manner.
 */
public class Board implements IBoard {

  /**
   * Variable to hold the Marks.
   */
  protected Mark[][] board;
  boolean gameOver;
  boolean gameStarted;
  /**
   * Number of Marks in the board.
   */
  private int nMarks;

  public Board() {
    gameStarted = false;
    nMarks = 0;
    board = new Mark[3][3];
    gameOver = false;

  }

  @Override
  public void startGame() {
    gameStarted = true;
  }

  @Override
  public void addMark(Mark mark, Point position) {
    if (!gameStarted) {
      throw new IllegalArgumentException("Game have not been started yet.");
    }
    if (gameOver) {
      throw new IllegalArgumentException("Game is already over.");
    }
    if (pointOutsideOfBoard(position)) {
      throw new IllegalArgumentException(
          "Position outside the range of the board. Position = [" + position.x + " , "
              + position.y + "].");
    }
    if (board[position.x][position.y] != null) {
      throw new IllegalArgumentException(
          "Can't add a Mark on top of another. Mark here: [" + board[position.x][position.y]
              .toString() + "]");
    }
    board[position.x][position.y] = mark;
    nMarks++;
    gameOver = isThereAWinner() || (nMarks == 9);
  }

  @Override
  public void addMark(Mark mark, int x, int y) {
    addMark(mark, new Point(x, y));
  }

  @Override
  public void resetBoard() {
    nMarks = 0;
    board = new Mark[3][3];
    gameOver = false;
  }

  @Override
  public Map<Point, Mark> marksOnTheBoard() {
    Map<Point, Mark> map = new HashMap<>();
    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board.length; j++) {
        if (board[i][j] != null) {
          map.put(new Point(i, j), board[i][j]);
        }
      }
    }
    return map;
  }

  @Override
  public Map<Point, Mark> getBoard() {
    Map<Point, Mark> map = new HashMap<>();
    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board.length; j++) {
        map.put(new Point(i, j), board[i][j]);
      }
    }
    return map;
  }

  @Override
  public Mark markAt(Point position) {
    if (pointOutsideOfBoard(position)) {
      throw new IllegalArgumentException(
          "Position outside the range of the board. Position = [" + position.getX() + " , "
              + position.getY() + "].");
    }
    return board[position.x][position.y];
  }

  @Override
  public Mark markAt(int x, int y) {
    return markAt(new Point(x, y));
  }

  @Override
  public boolean isEmptyAt(Point position) {
    return containsPosition(position);
  }

  @Override
  public boolean isEmptyAt(int x, int y) {
    return isEmptyAt(new Point(x, y));
  }

  @Override
  public Mark getWinner() {
    if (!gameOver) {
      throw new IllegalArgumentException("Game is not Over yet.");
    }
    if (!gameStarted) {
      throw new IllegalArgumentException("Game have not started yet.");
    }
    if (!isThereAWinner()) {
      return null;
    } else {
      for (int i = 0; i < 3; i++) {
        if (checkColumn(i)) {
          return board[i][0];
        }
        if (checkLine(i)) {
          return board[0][i];
        }
      }
      if (checkDiagonals()) {
        return board[1][1];
      }
    }
    throw new IllegalArgumentException("Unexpected Behaviour.");
  }

  @Override
  public boolean isGameOver() {
    return gameOver;
  }

  @Override
  public boolean isGameStarted() {
    return gameStarted;
  }

  @Override
  public String boardState() {
    StringBuilder sb = new StringBuilder("");
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        char ID = (markAt(i, j) != null) ? markAt(i, j).toString().charAt(0) : ' ';
        sb.append(ID);
        if (j < 2) {
          sb.append("|");
        }
      }
      if (i < 2) {
        sb.append("\n-+-+-\n");
      }
    }
    return sb.toString() + "\n";
  }

  /**
   * Checks if the point is outside the range of the board.
   *
   * @param point is the given point.
   * @return true if the point is inside the board, false if not.
   */
  private boolean pointOutsideOfBoard(Point point) {
    if (point.getX() < 0 || point.getX() > 2) {
      return true;
    }
    return (point.getY() < 0 || point.getY() > 2);
  }

  /**
   * Checks if the Position contains a Mark.
   *
   * @param position is the given point.
   * @return true if there is a mark at the position, false if not.
   */
  private boolean containsPosition(Point position) {
    if (pointOutsideOfBoard(position)) {
      return false;
    } else {
      return (board[position.x][position.y] == null);
    }
  }

  /**
   * Check if there is a Winner
   *
   * @return true if there is, false if not.
   */
  private boolean isThereAWinner() {
    for (int i = 0; i < 3; i++) {
      if (checkColumn(i)) {
        return true;
      }
      if (checkLine(i)) {
        return true;
      }
    }
    return checkDiagonals();
  }

  /**
   * Check if anyone won at that column
   *
   * @param n is the Column
   * @return true if someone did, false if not
   */
  private boolean checkColumn(int n) {
    return (board[0][n] == board[1][n] && board[1][n] == board[2][n] && board[0][n] != null);
  }

  /**
   * Check if anyone won at that line
   *
   * @param n is the Line
   * @return true if someone did, false if not
   */
  private boolean checkLine(int n) {
    return (board[n][0] == board[n][1] && board[n][1] == board[n][2] && board[n][0] != null);
  }

  /**
   * Check if anyone won at any diagonal
   *
   * @return true if someone did, false if not
   */
  private boolean checkDiagonals() {
    if (board[1][1] == board[2][2] && board[2][2] == board[0][0] && board[0][0] != null) {
      return true;
    }
    return (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[2][0] != null);
  }
}
