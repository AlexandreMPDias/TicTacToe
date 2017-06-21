package execution;

import controller.Control;
import controller.IControl;
import controller.WelcomeControl;
import enemyAI.AI;
import model.Board;
import sound.Sound;
import view.MainViewFrame;
import view.WelcomeFrame;

/**
 * @name Tic Tac Toe Game.
 * @author Alexandre Dias.
 * @version 1.0
 * @year 2017
 * @todo adjust frame size to actual 400/500/600/700/etc... and not numbers around those.
 *
 * Game created for the Gazeus as part of their evaluation.
 */


/**
 * Main Class for the Program. Declares the Welcome Control and plays it. Which generates a JFrame,
 * that will make the program stuck until the user presses any key. Then it declares the Control,
 * which generates a JFrame that will make the program stuck as well, until the user choose a mark.
 * Then it plays the control, starting to play the game.
 */
public class Main {

  public static void main(String[] args) {
    WelcomeControl welcome = new WelcomeControl(new WelcomeFrame(), new Sound());
    welcome.play();
    IControl control = new Control(new Board(), new MainViewFrame(), new Sound(), new AI());
    control.play();
  }
}
