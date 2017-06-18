package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import model.IBoard;
import view.ImageLoader.ImageID;


/**
 * MainViewFrame Class implements {@link IView} interface.
 *
 * Class created hold all the panels used to show all the information for the user.
 */
public class MainViewFrame implements IView {

  private final JFrame frame;
  private final ShowBoard showBoard;
  private final ShowScore score;
  private final ButtonsPanel buttons;
  private final int underBar;
  private int length;

  public MainViewFrame() {
    frame = new JFrame();
    underBar = 50;
    length = 500;
    frameConfiguration();
    showBoard = new ShowBoard();
    score = new ShowScore();
    buttons = new ButtonsPanel();

    frame.setIconImage(ImageLoader.getBufImg(ImageID.Icon));

    /*
     * Adding ShowBoard() on BorderLayout.Center.
     */
    frame.add(showBoard, BorderLayout.CENTER);
    JPanel panel = new JPanel();
    Color color = Color.GRAY;
    panel.setOpaque(true);
    panel.setBackground(color);
    panel.setLayout(new FlowLayout());
    panel.add(score);
    panel.add(buttons);
    score.setOpaque(true);
    score.setBackground(color);
    buttons.setOpaque(true);
    buttons.setBackground(color);

    /*
     * A Panel on BorderLayout.South with ShowScore() and ButtonsPanel() inside.
     */
    frame.add(panel, BorderLayout.SOUTH);

    frame.setResizable(false);
    frame.setVisible(true);
    frame.pack();
  }


  @Override
  public Dimension getDimension() {
    return frame.getSize();
  }

  @Override
  public void updateModel(IBoard board) {
    if (board == null) {
      throw new IllegalArgumentException("Board can't be null.");
    }
    showBoard.updateBoard(board);
    showBoard.repaint();
    frame.pack();
    frame.requestFocus();
  }

  @Override
  public void refresh() {
    showBoard.repaint();
    frame.repaint();
    frame.pack();
  }

  @Override
  public void updateKeyListener(KeyListener keys) {
    frame.addKeyListener(keys);
  }

  @Override
  public void updateMouseListener(MouseListener mouse) {
    frame.addMouseListener(mouse);
  }

  @Override
  public void updateActionListener(ActionListener action) {
    buttons.setActionListener(action);
  }

  @Override
  public void changeSize(int size) {
    if (size < 1 || size > 7) {
      throw new IllegalArgumentException("Invalid size.");
    }
    length = (size + 4) * 100;
    frame.getContentPane().setMinimumSize(new Dimension(length, length + underBar));
    frame.getContentPane().setMaximumSize(new Dimension(length, length + underBar));
    showBoard.reSize(size);
    frame.setLocationRelativeTo(null);
    frame.pack();
  }

  @Override
  public Point2D getMouseLocation() {
    return this.frame.getMousePosition();
  }

  @Override
  public Point2D getScreenLocation() {
    return this.frame.getLocation();
  }

  @Override
  public void updateScore(int xScore, int oScore) {
    score.updateScore(xScore, oScore);
  }

  private void frameConfiguration() {
    frame.setTitle("Tick Tack Toe");
    frame.setSize(new Dimension(500, 500 + underBar));
    frame.setFocusable(true);
    frame.setLayout(new BorderLayout());
    frame.setLocationRelativeTo(null);
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
  }

  @Override
  public void cycleBackground() {
    showBoard.updateBackground();
  }

  @Override
  public void quit() {
    frame.dispose();
  }
}
