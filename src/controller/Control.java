package controller;

import static java.awt.event.KeyEvent.VK_A;
import static java.awt.event.KeyEvent.VK_C;
import static java.awt.event.KeyEvent.VK_D;
import static java.awt.event.KeyEvent.VK_E;
import static java.awt.event.KeyEvent.VK_ESCAPE;
import static java.awt.event.KeyEvent.VK_LEFT;
import static java.awt.event.KeyEvent.VK_M;
import static java.awt.event.KeyEvent.VK_Q;
import static java.awt.event.KeyEvent.VK_R;
import static java.awt.event.KeyEvent.VK_RIGHT;
import static java.awt.event.KeyEvent.VK_S;
import static java.awt.event.KeyEvent.VK_W;
import static java.awt.event.KeyEvent.VK_X;
import static java.awt.event.KeyEvent.VK_Z;

import controller.KeyHandler.KeyEventType;
import controller.MouseHandler.ClickType;
import enemyAI.IArtificialIntelligence;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import model.IBoard;
import model.ImmutableBoard;
import model.Mark;
import sound.ISound;
import sound.SoundLabels;
import view.IView;
import view.PopUpFixed;

/**
 * Control Class. Implements {@link IControl inteface}, {@link ActionListener interface}
 *
 * Uses KeyListeners and MouseListeners for Keyboard and Mouse inputs
 * Uses ActionListeners for execution JButton actions.
 */
public final class Control implements IControl, ActionListener {

  private IBoard board;
  private IView view;
  private ISound sound;
  private IArtificialIntelligence ai;

  /**
   * Trigger Variable.
   * When True: user can make a play. AI can't
   * When False: user can't make a play. AI can.
   */
  private volatile boolean userTurn;

  /**
   * The Mark used by the user.
   * Whenever this variable is set for the user, the opposite variable is instantly set for the AI.
   */
  private volatile Mark userMark;

  /**
   * Variable used for calculating the Main Frame size.
   * The Main Frame has a Fixed non-Resizable Size, based on this value.
   */
  private int fixedSize;

  /**
   * Trigger Variable.
   * When this Variable is true -> The Control will exit, calling System.exit(0).
   */
  private volatile boolean wishToExit;

  /**
   * The Score for the X mark.
   */
  private int xScore;

  /**
   * The Score for the O mark.
   */
  private int oScore;

  /**
   * Delay in milliseconds between the user and the AI turns.
   */
  private long delayBetweenTurns;


  /**
   * Constructor for the Control class.
   *
   * @param board is the Board of the Game.
   * @param view is the Interface used to Visualize the Game.
   * @param sound is the sound manager of the game.
   * @param ai is the AI manager of the game.
   */
  public Control(IBoard board, IView view, ISound sound, IArtificialIntelligence ai) {
    if (board == null) {
      makePopUp("Board was not initialized.");
      System.exit(1);
    } else if (view == null) {
      makePopUp("MainViewFrame was not initialized.");
      System.exit(1);
    } else if (sound == null) {
      makePopUp("Sound was not initialized.");
      System.exit(1);
    } else if (ai == null) {
      makePopUp("AI was not initialized.");
      System.exit(1);
    }

    this.board = board;
    this.view = view;
    this.sound = sound;
    this.ai = ai;

    delayBetweenTurns = 700;
    fixedSize = 2;
    userTurn = true;
    wishToExit = false;

    xScore = 0;
    oScore = 0;

    /*
     * Put Control on hold and wait for User to choose which Mark it wants.
     */
    new ChooseMark(this, view, sound);

    ai.setAIMark(userMark.other());

    /*
     * Set up Listeners.
     */
    view.updateMouseListener(createMouseHandler());
    view.updateKeyListener(createKeyHandler());
    view.updateActionListener(this);

    //Start background music.
    sound.playLoop(SoundLabels.BG);

    /* End of Constructor */
  }

  @Override
  public void play() {
    if (board == null) {
      makePopUp("Board was not initialized.");
      System.exit(1);
    } else if (view == null) {
      makePopUp("MainViewFrame was not initialized.");
      System.exit(1);
    } else if (sound == null) {
      makePopUp("Sound was not initialized.");
      System.exit(1);
    } else if (ai == null) {
      makePopUp("AI was not initialized.");
      System.exit(1);
    }
    //Start game.
    board.startGame();

    /*
     * While user doesn't wish to exit.
     */
    while (!wishToExit) {
      /*
       * While game isn't over.
       */
      while (!board.isGameOver()) {

        view.refresh(); //Refresh View

        /*
         * If it is AI's turn.
         */
        if (!userTurn) {
          ai.updateBoard(board);
          view.refresh();
          try {
            /*
             * Add AI's Mark to the board.
             */
            board.addMark(ai.getAIMark(), ai.makeMove());
            Thread.sleep(delayBetweenTurns);
            sound.play(getSoundLabel(userMark.other()));
          } catch (IllegalArgumentException iae) {
            //userTurn = false here as a failSafe method.
            userTurn = false;
          } catch (InterruptedException e1) {
            e1.printStackTrace();
          }
          view.updateModel(new ImmutableBoard(board));
          /*
           * Finish AI's turn.
           */
          userTurn = true;

          /*
           * Check if the Game ended on AI's turn.
           */
          checkEndGame();
          try {
            Thread.sleep(delayBetweenTurns);
          } catch (InterruptedException e1) {
            e1.printStackTrace();
          }
        }/* End of IF */
      } /* End of While */
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    } /* End of While */
    System.exit(0);
  } /* End of Play() */

  @Override
  public Mark getUserMark() {
    return userMark;
  }

  @Override
  public void setUserMark(Mark m) {
    if (userMark != null) {
      throw new IllegalArgumentException("User Mark can't only be set once.");
    }
    if (m == null) {
      throw new IllegalArgumentException("Mark can't be null.");
    } else {
      userMark = m;
    }
  } /* End of setUserMark() */

  private synchronized MouseListener createMouseHandler() {
    MouseHandler mouse = new MouseHandler();
    ClickType click = ClickType.Clicked;

    /*
     * Creating Mouse Event.
     */
    Runnable mark = () -> {
      Point2D p = ClickToPoint.getPoint(view.getMouseLocation(), fixedSize);
      if (p != null) {
        makeMove_USER((int) p.getX(), (int) p.getY());
      }
    };
    /*
     * Adding MouseEvent to MouseHandler
     */
    mouse.addMouseEvent(click, 1, mark);

    return mouse;
  } /* End of createMouseHandler() */

  private synchronized KeyListener createKeyHandler() {
    KeyHandler key = new KeyHandler();

    /*
     * Command used for destroying the main frame and exiting the game.
     */
    Runnable exit = () -> {
      view.quit();
      makePopUp("Exiting game. Thank you for playing.");
      sound.stopLoop(SoundLabels.BG);
      wishToExit = true;
    };

    /*
     * Command used for resetting the game.
     */
    Runnable restart = () -> {
      board.resetBoard();
      view.updateModel(new ImmutableBoard(board));
    };

    /*
     * Command used for muting/unmuting all sounds.
     */
    Runnable mute = () -> sound.setMuteProperty(!sound.getMuteProperty());
    /*
     * Command used for increasing the screen's fixedSize(if possible).
     */
    Runnable increase = () -> {
      try {
        view.changeSize(++fixedSize);
      } catch (IllegalArgumentException iae) {
        makePopUp("Screen can't get any bigger.");
        fixedSize--;
      }
    };
    /*
     * Command used for decreasing the screen's fixedSize(if possible).
     */
    Runnable decrease = () -> {
      try {
        view.changeSize(--fixedSize);
      } catch (IllegalArgumentException iae) {
        makePopUp("Screen can't get any smaller.");
        fixedSize++;
      }
    };

    /*
     * Command used for making the User mark the Board.
     */
    Runnable a = () -> makeMove_USER(0, 0);
    Runnable b = () -> makeMove_USER(0, 1);
    Runnable c = () -> makeMove_USER(0, 2);
    Runnable d = () -> makeMove_USER(1, 0);
    Runnable e = () -> makeMove_USER(1, 1);
    Runnable f = () -> makeMove_USER(2, 0);
    Runnable g = () -> makeMove_USER(2, 1);
    Runnable h = () -> makeMove_USER(2, 2);
    Runnable i = () -> makeMove_USER(1, 2);

    /*
     * Add Key Commands to KeyHandler()
     */
    key.addKeyCommand(KeyEventType.KeyPressed, VK_ESCAPE, exit);
    key.addKeyCommand(KeyEventType.KeyPressed, VK_R, restart);
    key.addKeyCommand(KeyEventType.KeyPressed, VK_M, mute);
    key.addKeyCommand(KeyEventType.KeyPressed, VK_LEFT, decrease);
    key.addKeyCommand(KeyEventType.KeyPressed, VK_RIGHT, increase);
    key.addKeyCommand(KeyEventType.KeyPressed, VK_Q, a);
    key.addKeyCommand(KeyEventType.KeyPressed, VK_W, b);
    key.addKeyCommand(KeyEventType.KeyPressed, VK_E, c);
    key.addKeyCommand(KeyEventType.KeyPressed, VK_A, d);
    key.addKeyCommand(KeyEventType.KeyPressed, VK_S, e);
    key.addKeyCommand(KeyEventType.KeyPressed, VK_Z, f);
    key.addKeyCommand(KeyEventType.KeyPressed, VK_X, g);
    key.addKeyCommand(KeyEventType.KeyPressed, VK_C, h);
    key.addKeyCommand(KeyEventType.KeyPressed, VK_D, i);
    return key;
  } /* End of createKeyHandler() */

  /**
   * Method used to make the user's Move on a Point
   *
   * @param x is an x-coordinate inside the board.
   * @param y is an y-coordinate inside the board.
   */
  private synchronized void makeMove_USER(int x, int y) {
    if (userTurn) {
      try {
        /*
         * Add user Mark to the board.
         */
        board.addMark(userMark, x, y);

        /*
         * Play SFX for user's Mark.
         */
        sound.play(getSoundLabel(userMark));
        view.updateModel(new ImmutableBoard(board));

        /*
         * End user's Turn.
         */
        userTurn = false;
      } catch (IllegalArgumentException iae) {
        //Do nothing.
      }
    }
    checkEndGame();
  }

  /**
   * Creates a JDialog Popup Window, centered around the main JFrame. Non-resizable, and must be
   * focusable.
   *
   * @param s is the Message on the PopUp.
   */
  private void makePopUp(String s) {
    int ySize = (int) view.getDimension().getHeight() / 2;
    int xSize = (int) view.getDimension().getWidth() / 2;
    int popX = s.length() * 7;
    int popY = 100;
    int xLoc = (int) view.getScreenLocation().getX() + (xSize - (popX / 2));
    int yLoc = (int) view.getScreenLocation().getY() + (ySize - (popY / 2));
    sound.play(SoundLabels.PopUp);
    new PopUpFixed(s, xLoc, yLoc, popX, popY);
  }

  /**
   * Checks if the game is Over. If the game ends. It will update the ScoreBoard in the IView
   * interface and generate a PopUp message saying who the winner was, or if it was a draw.
   */
  private void checkEndGame() {
    if (board.isGameOver()) {
      Mark winner = board.getWinner();
      String winMessage;
      if (winner == userMark) {
        winMessage = "User won  ";
      } else if (winner == userMark.other()) {
        winMessage = "AI won  ";
      } else {
        winMessage = "It's a draw.  ";
      }
      updateScore(winner);
      makePopUp("  Game Over. " + winMessage);
      board.resetBoard();
      view.updateModel(new ImmutableBoard(board));
    }
  }

  @Override
  public synchronized void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      /*
       * Button used to Mute and Unmute all sound.
       */
      case "Mute": {
        sound.play(SoundLabels.Button);
        try {
          Thread.sleep(500);
        } catch (InterruptedException e1) {
          e1.printStackTrace();
        }
        sound.setMuteProperty(!sound.getMuteProperty());
        break;
      }
      /*
       * Button used to reset the Board, clearing it of all marks.
       */
      case "Restart": {
        board.resetBoard();
        break;
      }
      /*
       * Button used to increase the frame's fixedSize(if possible).
       */
      case "+": {
        try {
          view.changeSize(++fixedSize);
        } catch (IllegalArgumentException iae) {
          makePopUp("Screen can't get any bigger.");
          fixedSize--;
        }
        break;
      }
      /*
       * Button used to decrease the frame's fixedSize(if possible).
       */
      case "-": {
        try {
          view.changeSize(--fixedSize);
        } catch (IllegalArgumentException iae) {
          makePopUp("Screen can't get any smaller.");
          fixedSize++;
        }
        break;
      }
      /*
       * Button used to change the background of the Board.
       */
      case "chg BG": {
        view.cycleBackground();
        break;
      }
      default: {
        /*
         * Nothing.
         */
      }
    }
    sound.play(SoundLabels.Button);
  } /* End of actionPerformed */

  /**
   * Method used to get the correct Sound made by a mark for when it is placed on the board.
   */
  private SoundLabels getSoundLabel(Mark m) {
    if (m == Mark.X) {
      return SoundLabels.Cross;
    } else {
      return SoundLabels.Circle;
    }
  }

  /**
   * Updates score based on the Winner's Mark.
   *
   * @param winner is the Mark of the Winner. Can be null if it was a draw.
   */
  private void updateScore(Mark winner) {
    if (winner == Mark.X) {
      view.updateScore(++xScore, oScore);
    } else if (winner == Mark.O) {
      view.updateScore(xScore, ++oScore);
    }
  }
}
