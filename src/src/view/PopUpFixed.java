package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import view.ImageLoader.ImageID;

/**
 * PopUpButtons class extends {@link JDialog}.
 *
 * Class used to generate a PopUp fixed on Screen, that will only go away when clicking its only
 * button. The pop up serve as a warning.
 */
public class PopUpFixed extends JDialog {

  private int width;
  private int height;


  public PopUpFixed(String message, int xLoc, int yLoc, int width, int height) {
    this("Alert", message, xLoc, yLoc, width, height);
  }

  public PopUpFixed(String name, String message, int xLoc, int yLoc, int width, int height) {
    super();
    this.width = width;
    this.height = height;
    setTitle(name);
    JLabel label = new JLabel(message);
    label.setHorizontalAlignment(SwingConstants.CENTER);
    JButton button = new JButton("OK");
    setIconImage(ImageLoader.getBufImg(ImageID.AlertIcon));
    button.addActionListener(evt -> dispose());

    setLayout(new BorderLayout());
    add(label, BorderLayout.CENTER);
    add(button, BorderLayout.SOUTH);
    setLocation(xLoc, yLoc);
    configPopUp();
  }

  private void configPopUp() {
    setAlwaysOnTop(true);
    setModal(true);
    setModalityType(ModalityType.APPLICATION_MODAL);
    setMinimumSize(new Dimension(width, height));
    setSize(new Dimension(width, height));
    setMaximumSize(new Dimension(width, height));
    setResizable(false);
    setVisible(true);
    pack();
  }
}
