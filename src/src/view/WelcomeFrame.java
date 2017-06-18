package view;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import model.IBoard;
import view.ImageLoader.ImageID;

/**
 * WelcomeFrame class implements {@link IView}.
 *
 * Class created for showing the User a Welcome Screen.
 */
public class WelcomeFrame implements IView {

  private JFrame frame;

  public WelcomeFrame() {
    frame = new JFrame();
    frame.setTitle("Welcome");
    frame.setIconImage(ImageLoader.getBufImg(ImageID.Icon));
    frame.getContentPane().add(new JLabel(new ImageIcon("data\\image\\Front.gif")));
    frame.setResizable(false);
    frame.setSize(new Dimension(700, 700));
    frame.setFocusable(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
    frame.pack();
  }

  @Override
  public synchronized void quit() {
    frame.setFocusable(false);
    frame.setVisible(false);
    this.frame.dispose();
  }

  @Override
  public Dimension getDimension() {
    throw new IllegalArgumentException("This class does not support this method.");
  }

  @Override
  public void updateModel(IBoard board) {
    throw new IllegalArgumentException("This class does not support this method.");
  }

  @Override
  public void refresh() {
    frame.repaint();
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
    throw new IllegalArgumentException("This class does not support this method.");
  }

  @Override
  public void changeSize(int size) {
    throw new IllegalArgumentException("This class does not support this method.");
  }

  @Override
  public Point2D getMouseLocation() {
    throw new IllegalArgumentException("This class does not support this method.");
  }

  @Override
  public Point2D getScreenLocation() {
    throw new IllegalArgumentException("This class does not support this method.");
  }

  @Override
  public void cycleBackground() {
    throw new IllegalArgumentException("This class does not support this method.");
  }

  @Override
  public void updateScore(int xScore, int oScore) {
    throw new IllegalArgumentException("This class does not support this method.");
  }
}
