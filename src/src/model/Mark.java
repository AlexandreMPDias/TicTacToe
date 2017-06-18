package model;

/**
 * Enumeration for the Marks on the Board.
 * Each Mark is a Symbol that represents a Player as an Icon.
 */
public enum Mark {
  X, O;

  public Mark other() {
    if (this == X) {
      return O;
    } else {
      return X;
    }
  }
}
