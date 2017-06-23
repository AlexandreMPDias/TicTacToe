package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
  protected Mark[][] area;
  boolean gameOver;
  boolean gameStarted;
  final int areaSize;
  /**
   * Number of Marks in the area.
   */
  private int nMarks;

  public Board() {
    gameStarted = false;
    nMarks = 0;
    area = new Mark[3][3];
    gameOver = false;
    areaSize = 3;
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
          "Position outside the range of the area. Position = [" + position.x + " , "
              + position.y + "].");
    }
    if (area[position.x][position.y] != null) {
      throw new IllegalArgumentException(
          "Can't add a Mark on top of another. Mark here: [" + area[position.x][position.y]
              .toString() + "]");
    }
    area[position.x][position.y] = mark;
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
    area = new Mark[3][3];
    gameOver = false;
  }

  @Override
  public Map<Point, Mark> marksOnTheBoard() {
    Map<Point, Mark> map = new HashMap<>();
    for (int i = 0; i < area.length; i++) {
      for (int j = 0; j < area.length; j++) {
        if (area[i][j] != null) {
          map.put(new Point(i, j), area[i][j]);
        }
      }
    }
    return map;
  }

  @Override
  public Map<Point, Mark> getBoard() {
    Map<Point, Mark> map = new HashMap<>();
    for (int i = 0; i < area.length; i++) {
      for (int j = 0; j < area.length; j++) {
        map.put(new Point(i, j), area[i][j]);
      }
    }
    return map;
  }

  @Override
  public Mark markAt(Point position) {
    if (pointOutsideOfBoard(position)) {
      throw new IllegalArgumentException(
          "Position outside the range of the area. Position = [" + position.getX() + " , "
              + position.getY() + "].");
    }
    return area[position.x][position.y];
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
          return area[i][0];
        }
        if (checkLine(i)) {
          return area[0][i];
        }
      }
      if (checkDiagonals()) {
        return area[1][1];
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
   * Checks if the point is outside the range of the area.
   *
   * @param point is the given point.
   * @return true if the point is inside the area, false if not.
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
      return (area[position.x][position.y] == null);
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
    return (area[0][n] == area[1][n] && area[1][n] == area[2][n] && area[0][n] != null);
  }

  /**
   * Check if anyone won at that line
   *
   * @param n is the Line
   * @return true if someone did, false if not
   */
  private boolean checkLine(int n) {
    return (area[n][0] == area[n][1] && area[n][1] == area[n][2] && area[n][0] != null);
  }

  /**
   * Check if anyone won at any diagonal
   *
   * @return true if someone did, false if not
   */
  private boolean checkDiagonals() {
    if (area[1][1] == area[2][2] && area[2][2] == area[0][0] && area[0][0] != null) {
      return true;
    }
    return (area[0][2] == area[1][1] && area[1][1] == area[2][0] && area[2][0] != null);
  }

  @Override
  public  boolean isColumnEmpty(int column){
    for(int i = 0; i < 3; i++){
      if(!isEmptyAt(i,column)) return false;
    }
    return true;
  }

  @Override
  public boolean isLineEmpty(int line){
    for(int i = 0; i < 3; i++){
      if(!isEmptyAt(line,i)) return false;
    }
    return true;
  }

  @Override
  public boolean isDiagonalEmpty(boolean ascend){
    for(int i = 0; i < 3; i++){
      int k = i;
      if(ascend) k = (areaSize - 1) - i;
      if(!isEmptyAt(k,i)) return false;
    }
    return true;
  }

  @Override
  public int numberOfMarksOnLine(Mark m, int line){
    int count = 0;
    for(int i = 0; i < 3; i++){
      if(markAt(line,i) == m){
        count++;
      }
    }
    return count;
  }

  @Override
  public int numberOfMarksOnColumn(Mark m, int column){
    int count = 0;
    for(int i = 0; i < 3; i++){
      if(markAt(i,column) == m){
        count++;
      }
    }
    return count;
  }

  @Override
  public int numberOfMarksOnDiagonal(Mark m, boolean ascend){
    int count = 0;
    for(int i = 0; i < 3; i++){
      int k = i;
      if(ascend) k = (areaSize - 1) - i;
      if(markAt(k,i) == m){
        count++;
      }
    }
    /*
    if(ascend){
      if(area[2][0] == m) count++;
      if(area[1][1] == m) count++;
      if(area[0][2] == m) count++;
    }
    else {
      if(area[0][0] == m) count++;
      if(area[1][1] == m) count++;
      if(area[2][2] == m) count++;
    }*/
    return count;
  }

  @Override
  public Map<Point,Mark> marksOnLine(int line){
    Map<Point,Mark> map = new HashMap<>();
    for(int i = 0; i < 3; i++){
      if(!isEmptyAt(line,i)){
        map.put(new Point(line,i), markAt(line,i));
      }
    }
    return map;
  }

  @Override
  public Map<Point,Mark> marksOnColumn(int column){
    Map<Point,Mark> map = new HashMap<>();
    for(int i = 0; i < 3; i++){
      if(!isEmptyAt(i,column)){
        map.put(new Point(i,column), markAt(i,column));
      }
    }
    return map;
  }

  @Override
  public Map<Point,Mark> marksOnDiagonal(boolean ascend){
    Map<Point,Mark> map = new HashMap<>();
    for(int i = 0; i < areaSize ; i++){
      int k = i;
      if(ascend) k = (areaSize - 1) - i;
      if(!isEmptyAt(k,i)){
        map.put(new Point(k,i), markAt(k,i));
      }
    }
    return map;
  }

  @Override
  public List<Point> listOfRemainingPositionsAtLine(int line) {
    List<Point> list = new ArrayList<>();
    for(int i = 0; i < areaSize; i++){
      if(isEmptyAt(line,i)){
        list.add(new Point(line, i));
      }
    }
    return list;
  }

  @Override
  public List<Point> listOfRemainingPositionsAtColumn(int column) {
    List<Point> list = new ArrayList<>();
    for(int i = 0; i < areaSize; i++){
      if(isEmptyAt(i,column)){
        list.add(new Point(i,column));
      }
    }
    return list;
  }

  @Override
  public List<Point> listOfRemainingPositionsAtDiagonal(boolean ascend) {
    List<Point> list = new ArrayList<>();
    for(int i = 0; i < areaSize; i++){
      int k = i;
      if(ascend) k = (areaSize - 1) - i;
      if(isEmptyAt(k,i)){
        list.add(new Point(k,i));
      }
    }
    return list;
  }

  @Override
  public boolean isColumnFull(int column) {
    for(int i = 0; i < areaSize; i++){
      if(isEmptyAt(i,column)){
        return false;
      }
    }
    return true;
  }

  @Override
  public boolean isLineFull(int line) {
    for(int i = 0; i < areaSize; i++){
      if(isEmptyAt(line,i)){
        return false;
      }
    }
    return true;
  }

  @Override
  public boolean isDiagonalFull(boolean ascend) {
    for(int i = 0; i < areaSize; i++){
      int k = i;
      if(ascend) k = 2 - i;
      if(isEmptyAt(k,i)) return false;
    }
    return true;
  }

}
