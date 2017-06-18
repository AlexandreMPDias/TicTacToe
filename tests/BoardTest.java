import static org.junit.Assert.assertEquals;

import java.awt.Point;
import model.Board;
import model.IBoard;
import model.Mark;
import org.junit.Test;

/**
 * Created by Alexandre on 16/06/2017.
 */
public class BoardTest {

  @Test
  public void test01() {
    IBoard b = new Board();
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        b.isEmptyAt(new Point(i, j));
      }
    }
  }

  @Test
  public void test02() {
    IBoard b = new Board();
    for (int i = -5; i < 6; i++) {
      for (int j = -10; j < 6; j++) {
        b.isEmptyAt(new Point(i, j));
      }
    }
  }

  @Test
  public void test03() {
    IBoard b = new Board();
    assertEquals(b.isEmptyAt(new Point(-5, 2)), false);
  }

  @Test
  public void test04() {
    IBoard b = new Board();
    //System.out.println(b.boardState());
  }

  @Test
  public void testCheckGameOver_01() {
    IBoard b = new Board();
    b.startGame();
    assertEquals(false, b.isGameOver());
    b.addMark(Mark.X, 0, 0);
    b.addMark(Mark.X, 1, 1);
    b.addMark(Mark.X, 2, 2);
    assertEquals(true, b.isGameOver());
  }

  @Test
  public void testCheckGameOver_02() {
    IBoard b = new Board();
    b.startGame();
    assertEquals(false, b.isGameOver());
    b.addMark(Mark.X, 0, 0);
    b.addMark(Mark.X, 0, 1);
    b.addMark(Mark.X, 0, 2);
    assertEquals(true, b.isGameOver());
  }

  @Test
  public void testCheckGameOver_03() {
    IBoard b = new Board();
    b.startGame();
    assertEquals(false, b.isGameOver());
    b.addMark(Mark.X, 1, 0);
    b.addMark(Mark.X, 1, 1);
    b.addMark(Mark.X, 1, 2);
    assertEquals(true, b.isGameOver());
    assertEquals(Mark.X, b.getWinner());
  }

  @Test
  public void testCheckGameOver_04() {
    IBoard b = new Board();
    b.startGame();
    assertEquals(false, b.isGameOver());
    b.addMark(Mark.X, 2, 0);
    b.addMark(Mark.X, 2, 1);
    b.addMark(Mark.X, 2, 2);
    assertEquals(true, b.isGameOver());
  }

  @Test
  public void testCheckGameOver_05() {
    IBoard b = new Board();
    b.startGame();
    assertEquals(false, b.isGameOver());
    b.addMark(Mark.X, 0, 2);
    b.addMark(Mark.X, 1, 1);
    b.addMark(Mark.X, 2, 0);
    assertEquals(true, b.isGameOver());
  }

  @Test
  public void testCheckGameOver_06() {
    IBoard b = new Board();
    b.startGame();
    assertEquals(false, b.isGameOver());
    b.addMark(Mark.X, 0, 0);
    b.addMark(Mark.X, 1, 0);
    b.addMark(Mark.X, 2, 0);
    assertEquals(true, b.isGameOver());
  }

  @Test
  public void testCheckGameOver_07() {
    IBoard b = new Board();
    b.startGame();
    assertEquals(false, b.isGameOver());
    b.addMark(Mark.X, 0, 1);
    b.addMark(Mark.X, 1, 1);
    b.addMark(Mark.X, 2, 1);
    assertEquals(true, b.isGameOver());
  }

  @Test
  public void testCheckGameOver_08() {
    IBoard b = new Board();
    b.startGame();
    assertEquals(false, b.isGameOver());
    b.addMark(Mark.X, 0, 2);
    b.addMark(Mark.X, 1, 2);
    b.addMark(Mark.X, 2, 2);
    assertEquals(true, b.isGameOver());
  }

  @Test
  public void testCheckGameOver_09() {
    IBoard b = new Board();
    b.startGame();
    assertEquals(false, b.isGameOver());
    b.addMark(Mark.X, 0, 0);
    b.addMark(Mark.X, 0, 1);
    b.addMark(Mark.O, 0, 2);
    b.addMark(Mark.O, 1, 0);
    b.addMark(Mark.O, 1, 1);
    b.addMark(Mark.X, 1, 2);
    b.addMark(Mark.X, 2, 0);
    b.addMark(Mark.X, 2, 1);
    b.addMark(Mark.O, 2, 2);
    assertEquals(true, b.isGameOver());
    assertEquals(null, b.getWinner());
  }
}
