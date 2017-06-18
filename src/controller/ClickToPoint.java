package controller;

import java.awt.Point;
import java.awt.geom.Point2D;

/**
 * Class used to calculate which Square the Mouse is currently clicking at.
 */
abstract class ClickToPoint {

  /**
   * Returns a point withs values ranging from {0,0} to {2,2} which are the mouse's coordinates'
   * equivalent to the coordinates inside the board.
   *
   * @param click is mouse's position inside the Frame.
   * @param size is the FixedSize { Explained at {@link Control } }
   * @return a point, equivalent to a coordinate inside the TicTacToe's Board.
   */
  static Point2D getPoint(Point2D click, int size) {
    int margin = 2 * (size + 3);
    int squareSize = ((size + 3) * 100 - (2 * margin)) / 3;

    int x = find((int) click.getX(), squareSize, margin + 1);
    int y = find((int) click.getY(), squareSize, margin + 23);
    if (x >= 0 && y >= 0) {
      return new Point(y, x);
    } else {
      return null;
    }
  }

  private static int find(int var, int squareSize, int margin) {
    if (var >= margin && var < squareSize + margin) {
      return 0;
    } else if (var < (squareSize * 2) + margin) {
      return 1;
    } else if (var < (squareSize * 3) + margin) {
      return 2;
    } else {
      return -1;
    }
  }
}
