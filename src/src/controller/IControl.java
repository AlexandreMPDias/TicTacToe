package controller;

import model.Mark;

/**
 * IControl interface.
 *
 * Responsible for the Trigger Events and Commands in the Game, such as Executing Key and Mouse
 * Commands, launching PopUps, and Starting, Restarting, Closing the Game.
 */

public interface IControl {

  /**
   * This method is responsible for the Turn management.
   */
  void play();

  /**
   * Get the Mark set for the User.
   *
   * @return the user's Mark.
   */
  Mark getUserMark();

  /**
   * This method can work only once. It will set a Mark as the User Mark.
   *
   * @param m the Mark for the User.
   * @throws IllegalArgumentException if a second(or more) attempt to set a Mark is made.
   */
  void setUserMark(Mark m);
}
