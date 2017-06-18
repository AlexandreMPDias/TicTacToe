package model;

import java.awt.Point;

/**
 * ImmutableBoard class extends {@link Board} class implements {@link IBoard} interface
 *
 * Class created for the IControl to pass the IBoard as an Object to the IView that can't be
 * modified by the IView.
 */
public class ImmutableBoard extends Board implements IBoard {

  ImmutableBoard() {
    throw new IllegalArgumentException("There's no default constructor for this class.");
  }

  public ImmutableBoard(IBoard model) {
    this.board = new Mark[3][3];
    this.gameOver = model.isGameOver();
    this.gameStarted = model.isGameStarted();
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        board[i][j] = model.markAt(i, j);
      }
    }
  }

  @Override
  public void addMark(Mark mark, Point position) {
    throw new IllegalArgumentException("This class can't modify the Board.");
  }

  @Override
  public void addMark(Mark mark, int x, int y) {
    throw new IllegalArgumentException("This class can't modify the Board.");
  }

  @Override
  public void resetBoard() {
    throw new IllegalArgumentException("This class can't modify the Board.");
  }
}
