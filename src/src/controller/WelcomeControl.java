package controller;

import java.awt.event.KeyListener;
import sound.ISound;
import sound.SoundLabels;
import view.IView;

/**
 * Class created for maintaining the WelcomeFrame. A Frame that comes before the the MainFrame, and
 * only have one gif, that plays in loop, and will close if any key is pressed.
 */
public class WelcomeControl {

  private IView view;
  private ISound sound;

  private volatile boolean locked;

  public WelcomeControl(IView view, ISound sound) {
    if (view == null) {
      throw new IllegalArgumentException("'MainViewFrame not initialized.");
    } else if (sound == null) {
      throw new IllegalArgumentException("Sound not initialized.");
    }
    this.view = view;
    this.sound = sound;
    view.updateKeyListener(getKeyHandler());
    sound.playLoop(SoundLabels.Front);
    locked = true;
  }

  public void play() {
    while (locked) {
      /*
       * Will lock execution until any key is pressed.
       */
      //Do nothing.
    }
    view.quit();
    sound.stopLoop(SoundLabels.Front);
  }

  private KeyListener getKeyHandler() {

    /*
     * Any key press will break the lock on the execution.
     */
    Runnable end = () -> locked = false;
    return new KeyHandler(end);

  }
}
