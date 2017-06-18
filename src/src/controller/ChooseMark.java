package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.Mark;
import sound.ISound;
import sound.SoundLabels;
import view.IView;
import view.PopUpButtons;

/**
 * Class used to generate a Pop Up where the user will have to buttons to click. One being X, and
 * the other being O. This class should hold the Controller from running until any of those buttons
 * are clicked. When clicking those any of those buttons the user is choosing which mark it is going
 * to use during its gameplay.
 */
public class ChooseMark implements ActionListener {

  private IControl control;

  /**
   * Constructor for the ChooseMark class. Creating this class will keep the program from running
   * any further while the IControl interface returns null at the getUserMark() method.
   *
   * @param control is the IControl interface used to run the game.
   * @param view is the IView interface used to show the game. It must be the same as IView used
   * inside the IController received.
   * @param sound is the sound manager.
   */
  ChooseMark(IControl control, IView view, ISound sound) {
    if (control == null) {
      throw new IllegalArgumentException("Control was not initialized.");
    }
    if (view == null) {
      throw new IllegalArgumentException("MainViewFrame was not initialized.");
    }
    if (sound == null) {
      throw new IllegalArgumentException("Sound was not initialized.");
    }
    this.control = control;
    sound.playLoop(SoundLabels.Epic, 0.3);
    int ySize = (int) view.getDimension().getHeight() / 2;
    int xSize = (int) view.getDimension().getWidth() / 2;
    int xLoc = (int) view.getScreenLocation().getX() + (xSize);
    int yLoc = (int) view.getScreenLocation().getY() + (ySize);
    PopUpButtons pop = new PopUpButtons(xLoc, yLoc);
    pop.addActionListener(this);
    while (control.getUserMark() == null) {
      try {
        Thread.sleep(500);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    sound.stopLoop(SoundLabels.Epic);
    pop.quit();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand().equals("X")) {
      try {
        control.setUserMark(Mark.X);
        System.out.println("UserMark set to [X]. AI Mark set to [O].");
      } catch (IllegalArgumentException iae) {
      }
    } else {
      if (e.getActionCommand().equals("O")) {
        try {
          control.setUserMark(Mark.O);
          System.out.println("UserMark set to [O]. AI Mark set to [X].");
        } catch (IllegalArgumentException iae) {
        }
      }
    }
  }
}

