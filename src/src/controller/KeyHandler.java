package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

/**
 * Class used to handle all KeyBoard inputs. This class works in two ways. One can add {@code
 * Runnable} to one of the each 3 type of {@code KeyEventType} or add one {@code Runnable} to all
 * and any Key typed, pressed or released.
 */
class KeyHandler implements KeyListener {

  private Runnable anyKeyEvent;
  private Map<KeyEventType, Map<Integer, Runnable>> commands;

  /**
   * Constructor for the KeyHandler class. Adding the runnable through this constructor is the only
   * form of making a command go explicitly to all keys.
   *
   * @param anyKey is the command for all keys.
   */
  KeyHandler(Runnable anyKey) {
    this();
    anyKeyEvent = anyKey;
  }

  /**
   * Default Constructor for the KeyHandler class.
   */
  KeyHandler() {
    commands = new HashMap<>();
  }

  /**
   * Add Commands to Handler.
   *
   * @param ket is KeyEvent Type { Pressed, Typed or Released }
   * @param key is the Key in question
   * @param command is the Command to be executed.
   */
  void addKeyCommand(KeyEventType ket, int key, Runnable command) {
    if (ket == null) {
      throw new IllegalArgumentException("KeyEventType was not initialized.");
    }
    if (key < 0) {
      throw new IllegalArgumentException("Invalid Key.");
    }
    if (command == null) {
      throw new IllegalArgumentException("Command was not initialized.");
    }
    if (!commands.containsKey(ket)) {
      commands.put(ket, new HashMap<>());
    }
    commands.get(ket).put(key, command);
  }

  @Override
  public void keyTyped(KeyEvent e) {
    if (commands.containsKey(KeyEventType.KeyTyped)) {
      if (commands.get(KeyEventType.KeyTyped).containsKey(e.getKeyCode())) {
        commands.get(KeyEventType.KeyTyped).get(e.getKeyCode()).run();
      }
    }
    if (anyKeyEvent != null) {
      anyKeyEvent.run();
    }
  }

  @Override
  public void keyPressed(KeyEvent e) {
    if (commands.containsKey(KeyEventType.KeyPressed)) {
      if (commands.get(KeyEventType.KeyPressed).containsKey(e.getKeyCode())) {
        commands.get(KeyEventType.KeyPressed).get(e.getKeyCode()).run();
      }
    }
    if (anyKeyEvent != null) {
      anyKeyEvent.run();
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    if (commands.containsKey(KeyEventType.KeyReleased)) {
      if (commands.get(KeyEventType.KeyReleased).containsKey(e.getKeyCode())) {
        commands.get(KeyEventType.KeyReleased).get(e.getKeyCode()).run();
      }
    }
    if (anyKeyEvent != null) {
      anyKeyEvent.run();
    }
  }

  public enum KeyEventType {
    KeyTyped, KeyPressed, KeyReleased
  }
}
