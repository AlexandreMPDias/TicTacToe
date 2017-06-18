package view;

import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * ButtonsPanel extends {@link JPanel} class.
 * Panel created to hold all the main buttons visible in the main frame.
 * Uses Control as the ActionListener.
 */
class ButtonsPanel extends JPanel {

  private JButton mute;
  private JButton restart;
  private JButton sizeMore;
  private JButton sizeLess;
  private volatile JButton changeBG;

  ButtonsPanel() {
    super();
    changeBG = new JButton("chg BG");
    mute = new JButton("Mute");
    restart = new JButton("Restart");
    sizeMore = new JButton("+");
    sizeLess = new JButton("-");
    this.add(mute);
    this.add(restart);
    this.add(sizeLess);
    this.add(sizeMore);
    this.add(changeBG);
  }

  void setActionListener(ActionListener action) {
    if (action == null) {
      throw new IllegalArgumentException("Action was not initialized.");
    }
    changeBG.addActionListener(action);
    mute.addActionListener(action);
    restart.addActionListener(action);
    sizeMore.addActionListener(action);
    sizeLess.addActionListener(action);

  }
}
