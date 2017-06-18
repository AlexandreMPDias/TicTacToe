package controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;

/**
 * Class used for adding commands to Mouse Events.
 */
public class MouseHandler implements MouseListener {

  private Map<ClickType, Map<Integer, Runnable>> commands;

  MouseHandler() {
    this.commands = new HashMap<>();
  }

  /**
   * Adds a Command to a MouseEvent
   *
   * @param type MouseEvent
   * @param button MouseButton
   * @param event Command
   */
  void addMouseEvent(ClickType type, int button, Runnable event) {
    if (!commands.containsKey(type)) {
      commands.put(type, new HashMap<>());
    }
    commands.get(type).put(button, event);
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    if (commands.containsKey(ClickType.Clicked)) {
      if (commands.get(ClickType.Clicked).containsKey(e.getButton())) {
        commands.get(ClickType.Clicked).get(e.getButton()).run();
      }
    }
  }

  @Override
  public void mousePressed(MouseEvent e) {
    if (commands.containsKey(ClickType.Pressed)) {
      if (commands.get(ClickType.Pressed).containsKey(e.getButton())) {
        commands.get(ClickType.Pressed).get(e.getButton()).run();
      }
    }
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    if (commands.containsKey(ClickType.Released)) {
      if (commands.get(ClickType.Released).containsKey(e.getButton())) {
        commands.get(ClickType.Released).get(e.getButton()).run();
      }
    }
  }

  @Override
  public void mouseEntered(MouseEvent e) {
    if (commands.containsKey(ClickType.Entered)) {
      if (commands.get(ClickType.Entered).containsKey(e.getButton())) {
        commands.get(ClickType.Entered).get(e.getButton()).run();
      }
    }
  }

  @Override
  public void mouseExited(MouseEvent e) {
    if (commands.containsKey(ClickType.Exited)) {
      if (commands.get(ClickType.Exited).containsKey(e.getButton())) {
        commands.get(ClickType.Exited).get(e.getButton()).run();
      }
    }
  }

  public enum ClickType {
    Clicked, Pressed, Released, Entered, Exited
  }
}
