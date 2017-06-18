package view;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import model.IBoard;

/**
 * IView Interface.
 * Interface for showing the Board and all the Info necessary to the User.
 * This interface should show Board, its Marks and the score between the two marks.
 */
public interface IView {

  /**
   * Get method for the Dimension of the Window.
   *
   * @return the size of the Window
   */
  Dimension getDimension();

  /**
   * Update the Game model inside the Window.
   *
   * @param board is an Immutable IBoard object.
   */
  void updateModel(IBoard board);

  /**
   * Repaints the Frame and its components.
   */
  void refresh();

  /**
   * Adds a KeyListeners to the Window.
   *
   * @param keys is the KeyListener.
   */
  void updateKeyListener(KeyListener keys);

  /**
   * Adds a MouseListener to the Window.
   *
   * @param mouse is the MouseListener.
   */
  void updateMouseListener(MouseListener mouse);

  /**
   * Adds an ActionListener to the Window.
   *
   * @param action is the ActionListener.
   */
  void updateActionListener(ActionListener action);

  /**
   * Changes the screen Size based on a 1 to 7 code.
   *
   * @param size is code for the new screen Size.
   */
  void changeSize(int size);

  /**
   * Get method for the Mouse Location.
   *
   * @return a Point2D with the Mouse Location relative to the Window.
   */
  Point2D getMouseLocation();

  /**
   * Get method for the Screen Location.
   *
   * @return a Point2D with the Screen Location.
   */
  Point2D getScreenLocation();

  /**
   * Change the backGround of the ShowBoard.
   */
  void cycleBackground();

  /**
   * Change the Score.
   *
   * @param xScore is the Score for the X mark.
   * @param oScore is the Score for the O mark.
   */
  void updateScore(int xScore, int oScore);

  /**
   * Destroys the Window.
   */
  void quit();
}
