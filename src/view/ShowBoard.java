package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Map.Entry;
import java.util.Random;
import javax.swing.JComponent;
import model.IBoard;
import model.Mark;
import view.ImageLoader.ImageID;

/**
 * ShowBoard class extends {@link JComponent}.
 *
 * Class used to create the Board to show the Game State to the user.
 * This class shows the area and all the marks currently on top of the area.
 */
class ShowBoard extends JComponent {

  private final int margin;
  private IBoard board;
  private int squareSize;
  private int panelSize;

  private BufferedImage bgImg;
  private ImageID bgID;

  ShowBoard() {
    super();
    setFocusable(true);
    board = null;
    margin = 10;
    Random random = new Random();
    bgID = ImageID.values()[Math.abs(random.nextInt(9))];
    bgImg = ImageLoader.getBufImg(bgID);
    panelSize = 500;
    this.setPreferredSize(new Dimension(panelSize, panelSize));
    this.setSize(new Dimension(panelSize, panelSize));
  }

  @Override
  public void paintComponent(Graphics g) {
    squareSize = (panelSize - (margin * 2)) / 3;
    g.drawImage(bgImg, 0, 0, panelSize, panelSize, null);
    if (board != null) {
      for (Entry<Point, Mark> entries : board.marksOnTheBoard().entrySet()) {
        drawMarkAt(g, entries.getValue(), entries.getKey().y, entries.getKey().x);
      }
    }
  }

  private void drawMarkAt(Graphics g, Mark mark, int a, int b) {
    BufferedImage img;
    if (mark == Mark.O) {
      img = ImageLoader.getBufImg(ImageID.O);
    } else {
      img = ImageLoader.getBufImg(ImageID.X);
    }
    int imgSize = squareSize - (margin);
    g.drawImage(img, (a * squareSize) + margin, (b * squareSize) + margin, imgSize, imgSize, null);

  }

  void updateBoard(IBoard board) {
    if (board == null) {
      throw new IllegalArgumentException("Board can't be null.");
    }
    this.board = board;
  }

  void updateBackground() {
    switch (bgID) {
      case BG1: {
        bgID = ImageID.BG2;
        break;
      }
      case BG2: {
        bgID = ImageID.BG3;
        break;
      }
      case BG3: {
        bgID = ImageID.BG4;
        break;
      }
      case BG4: {
        bgID = ImageID.BG5;
        break;
      }
      case BG5: {
        bgID = ImageID.BG6;
        break;
      }
      case BG6: {
        bgID = ImageID.BG7;
        break;
      }
      case BG7: {
        bgID = ImageID.BG8;
        break;
      }
      case BG8: {
        bgID = ImageID.BG9;
        break;
      }
      default: {
        bgID = ImageID.BG1;
      }
    }
    bgImg = ImageLoader.getBufImg(bgID);
  }

  void reSize(int size) {
    if (size < 1 || size > 10) {
      throw new IllegalArgumentException("Invalid size.");
    }
    panelSize = (size + 3) * 100;
    this.setPreferredSize(new Dimension(panelSize, panelSize));
    this.setSize(new Dimension(panelSize, panelSize));
  }
}
