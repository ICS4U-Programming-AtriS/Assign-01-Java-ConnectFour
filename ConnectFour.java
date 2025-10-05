/**
* Connect Four Game.
* The user plays against an ai that picks random moves.
*
* @author  Atri Sarker
* @version 1.0
* @since   2025-10-03
*/
public final class ConnectFour {
  /**
   * Constant for number of columns.
   */
  private static final int NUM_COLUMNS = 7;
  /**
   * Constant for number of rows.
   */
  private static final int NUM_ROWS = 6;
  /**
   * Number of pieces in a row to win.
   */
  private static final int NUM_TO_WIN = 4;
  
  /**
   * Private constructor to satisfy style checker.
   * @exception IllegalStateException for the utility class.
   * @see IllegalStateException
   */
  private ConnectFour() {
    // Prevents illegal states.
    throw new IllegalStateException("Utility class.");
  }

  /** 
   * Marker for the user.
  */
  public static final char USER_MARKER = 'O';
  
  /** 
   * Marker for the ai.
  */
  public static final char AI_MARKER = 'X';

  /** 
   * Variable to hold the gameGrid.
   */
  public static final String[] gameGrid = new String[NUM_COLUMNS];

  /**
   * function that checks if a marker has won
   * Looks for any matches of a markers {NUM_TO_WIN} in a row.
   * Orthogonally or diagonally.
   * @param marker the marker to check for a win.
   * @return true if the marker has won, false otherwise.
   */
  public static boolean checkForWin(final char marker) {
    String winString = String.valueOf(marker).repeat(NUM_TO_WIN);
    // Check for vertical wins.
    // Go through each column.
    for (int colNum = 0; colNum < NUM_COLUMNS; colNum++) {
      // Check if the column contains a win.
      if (gameGrid[colNum].contains(winString)) {
        return true;
      }
    }
    // Check for horizontal wins.
    // Go through each row.
    for (int rowNum = 0; rowNum < NUM_ROWS; rowNum++) {
      // Set variable to hold the row string.
      String row = "";
      for (int colNum = 0; colNum < NUM_COLUMNS; colNum++) {
        row += gameGrid[colNum].charAt(rowNum);
      }
      // Check if the row contains a win.
      if (row.contains(winString)) {
        return true;
      }
    }
    // Check for forward slash [/] diagonal wins.
    // Go through every square that can be considered a starting point.
    // No starting point can be in the last {NUM_TO_WIN - 1} columns or rows.
    // Starting point is the bottom left of the forward slash [/] diagonal.
    for (int colNum = 0; colNum < NUM_COLUMNS - (NUM_TO_WIN - 1); colNum++) {
      for (int rowNum = NUM_TO_WIN - 1; rowNum < NUM_ROWS; rowNum++) {
        // Set variable to hold the diagonal string.
        String diagonal = "";
        // Create the diagonal string.
        for (int squareNum = 0; squareNum < NUM_TO_WIN; squareNum++) {
          diagonal += gameGrid[colNum + squareNum].charAt(rowNum - squareNum);
        }
        // Check if the diagonal contains a win.
        if (diagonal.equals(winString)) {
          return true;
        }
      }
    }
    // Do the exact same thing for backslash [\] diagonals.
    // Starting point is the top left of the backslash [\] diagonal.
    for (int colNum = 0; colNum < NUM_COLUMNS - (NUM_TO_WIN - 1); colNum++) {
      for (int rowNum = 0; rowNum < NUM_ROWS - (NUM_TO_WIN - 1); rowNum++) {
        // Set variable to hold the diagonal string.
        String diagonal = "";
        // Create the diagonal string.
        for (int squareNum = 0; squareNum < NUM_TO_WIN; squareNum++) {
          diagonal += gameGrid[colNum + squareNum].charAt(rowNum + squareNum);
        }
        // Check if the diagonal contains a win.
        if (diagonal.equals(winString)) {
          return true;
        }
      }
    }
    // If no wins were found, return false.
    return false;
  }

  /**
   * Entrypoint of the program.
   * @param args UNUSED.
   */
  public static void main(final String[] args) {
    // Fill up the game grid with empty spaces.
    for (int colNum = 0; colNum < NUM_COLUMNS; colNum++) {
      gameGrid[colNum] = " ".repeat(NUM_ROWS);
    }
  }
}
