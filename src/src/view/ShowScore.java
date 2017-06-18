package view;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * ShowScore class extends {@link JPanel}.
 *
 * This class is responsible for showing the user the Score between the X mark and O mark.
 */
class ShowScore extends JPanel {

  private int scoreX;
  private int scoreO;
  private Font font;

  ShowScore() {
    scoreX = 0;
    scoreO = 0;
    font = new Font("Verdana", 1, 25);
    update();
  }

  /**
   * Rewrites the Score of both marks.
   *
   * @param xScore rewrites the score of the mark X with this.
   * @param oScore rewrites the score of the mark O with this.
   */
  void updateScore(int xScore, int oScore) {
    scoreX = xScore;
    scoreO = oScore;
    update();
  }

  private void update() {
    this.removeAll();
    JLabel label1 = new JLabel(" X    O");
    JLabel label2 = new JLabel(" " + scoreX + "    " + scoreO);
    label1.setFont(font);
    label2.setFont(font);
    this.setLayout(new BorderLayout());
    this.add(label1, BorderLayout.CENTER);
    this.add(label2, BorderLayout.SOUTH);
  }
}
