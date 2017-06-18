package view;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import view.ImageLoader.ImageID;

/**
 * PopUpButtons class extends {@link JFrame}.
 *
 * Class used to generate a PopUp with only 2 Buttons as options.
 * This popUp can't be closed. So Setting Up these buttons to close this class is mandatory.
 */
public class PopUpButtons extends JFrame {

  private List<JButton> buttons;

  public PopUpButtons(int xLoc, int yLoc) {
    super();
    int width = 140;
    int height = 280 + 40;
    buttons = new ArrayList<>();
    setIconImage(ImageLoader.getBufImg(ImageID.Ask));
    this.setTitle("Choose Mark");
    JButton X = new JButton(ImageLoader.getIcon(ImageID.X));
    JButton O = new JButton(ImageLoader.getIcon(ImageID.O));
    X.setActionCommand("X");
    O.setActionCommand("O");
    buttons.add(X);
    buttons.add(O);
    BoxLayout box = new BoxLayout(getContentPane(), BoxLayout.Y_AXIS);
    setLayout(box);

    add(X);
    add(O);
    setLocation(xLoc - width / 2, yLoc - height / 2);
    setSize(width, height);
    setAlwaysOnTop(true);
    setResizable(false);
    setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    setVisible(true);
    pack();
  }

  public void addActionListener(ActionListener action) {
    for (JButton button : buttons) {
      button.addActionListener(action);
    }
  }

  public void quit() {
    dispose();
  }

  public void addButton(JButton button) {
    add(button);
    buttons.add(button);
  }

}
