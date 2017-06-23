package model;

import java.awt.Point;
import java.util.List;
import java.util.Map;

/**
 * Board Interface.
 * Interface used for add and checking marks in a 3x3 matrix.
 * Keeps track of the GameState and the Game's Winner.
 * Can also reset itself.
 */
public interface IBoard {

  /**
   * Starts the Game.
   */
  void startGame();

  /**
   * Add a Mark to the Board.
   *
   * @param mark the mark to be added.
   * @param position the position to be added.
   * @throws IllegalArgumentException if game was not started.
   * @throws IllegalArgumentException if game is already over.
   * @throws IllegalArgumentException if position is outside the area.
   * @throws IllegalArgumentException if there is already a mark on this position.
   */
  void addMark(Mark mark, Point position);

  void addMark(Mark mark, int x, int y);

  /**
   * Resets the area, removing all marks from it, and setting the gameOver state to false.
   */
  void resetBoard();

  /**
   * Get method for all the Marks in the Board.
   *
   * @return a map of all the Marks in the Board, where the Keys are Positions, and Values are
   * Marks.
   */
  Map<Point, Mark> marksOnTheBoard();

  /**
   * Get method for all the Board.
   *
   * @return a map of all the Board, where the Keys are Positions, and Values are Marks, or null, if
   * there are no parks there.
   */
  Map<Point, Mark> getBoard();

  /**
   * Checks which Mark is at a position in the Board.
   *
   * @param position on the area to be checked.
   * @return the Mark at the given position, or null if there is no Mark there.
   */
  Mark markAt(Point position);

  Mark markAt(int x, int y);

  /**
   * Check if some position is empty or not.
   *
   * @param position on the area to be checked.
   * @return true if there is a Mark there, false if not.
   */
  boolean isEmptyAt(Point position);

  boolean isEmptyAt(int x, int y);

  /**
   * This method will return the Winner
   *
   * @return the Mark that corresponds for the Winner of the game. Or null if it was a draw.
   * @throws IllegalArgumentException if the game is not over.
   * @throws IllegalArgumentException if the game have not started.
   */
  Mark getWinner();

  /**
   * Checks if the Game is Over.
   *
   * @return true if game is over, false if not.
   */
  boolean isGameOver();

  /**
   * Checks if the Game has already started.
   *
   * @return true if it did, false if it did not.
   */
  boolean isGameStarted();

  /**
   * Get the Board state in a String form.
   *
   * @return the Board state in a String form, ready to be printed in the Console.
   */
  String boardState();


  boolean isColumnFull(int column);
  boolean isLineFull(int line);
  boolean isDiagonalFull(boolean ascend);

  boolean isColumnEmpty(int column);
  boolean isLineEmpty(int line);
  boolean isDiagonalEmpty(boolean ascend);

  int numberOfMarksOnLine(Mark m, int line);
  int numberOfMarksOnColumn(Mark m, int column);
  int numberOfMarksOnDiagonal(Mark m, boolean ascend);

  Map<Point,Mark> marksOnLine(int line);
  Map<Point,Mark> marksOnColumn(int column);
  Map<Point,Mark> marksOnDiagonal(boolean ascend);

  List<Point> listOfRemainingPositionsAtLine(int line);
  List<Point> listOfRemainingPositionsAtColumn(int column);
  List<Point> listOfRemainingPositionsAtDiagonal(boolean ascend);
}
