import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
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
    // Do the exact same thing for backslash [\] diagonals.
    // Starting point is the top left of the backslash [\] diagonal.
    for (int colNum = 0; colNum < NUM_COLUMNS - (NUM_TO_WIN - 1); colNum++) {
      for (int rowNum = NUM_ROWS - 1; rowNum >= NUM_ROWS - (NUM_TO_WIN - 1); rowNum--) {
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
    // If no wins were found, return false.
    return false;
  }

  /**
   * Color for the player's marker.
   */
  public static final String USER_COLOR = "\u001B[34m";

  /**
   * Color for the AI's marker.
   */
  public static final String AI_COLOR = "\u001B[31m";

  /**
   * Color for resetting the color.
   */
  public static final String RESET_COLOR = "\u001B[0m";

  /**
   * Function that displays the game grid.
   */
  public static void displayGameGrid() {
    // Print the column numbers.
    for (int colNum = 0; colNum < NUM_COLUMNS; colNum++) {
      System.out.print(" " + (colNum + 1) + " ");
    }
    // Newline after printing column numbers.
    System.out.println();
    // Go through every row [starting from the top].
    for (int rowNum = NUM_ROWS - 1; rowNum >= 0; rowNum--) {
      for (int colNum = 0; colNum < NUM_COLUMNS; colNum++) {
        char marker = gameGrid[colNum].charAt(rowNum);
        // Match the marker with the correct color.
        // If it's not a user or ai marker, it just prints a hashtag.
        if (marker == USER_MARKER) {
          System.out.print(" " + USER_COLOR + marker + RESET_COLOR);
        } else if (marker == AI_MARKER) {
          System.out.print(" " + AI_COLOR + marker + RESET_COLOR);
        } else {
          System.out.print(" " + "#" + " ");
        }
      }
      // Print a new line after every the end of every row,
      // to start a new row.
      System.out.println(RESET_COLOR);
    }
  }

  /**
   * Function that returns a list of unfilled column numbers.
   * @return ArrayList<Integer> of unfilled column numbers.
   */
  public static ArrayList<Integer> getUnfilledColumns() {
    // Create a list to hold unfilled column numbers.
    ArrayList<Integer> unfilledColumns = new ArrayList<>();
    // Go through every column.
    for (int colNum = 0; colNum < NUM_COLUMNS; colNum++) {
      // If the column is not full, add it to the list.
      if (gameGrid[colNum].charAt(NUM_ROWS - 1) == ' ') {
        unfilledColumns.add(colNum);
      }
    }
    // Return the list of unfilled column numbers. 
    return unfilledColumns;
  }

  /**
   * Number that defines the current turn number.
   * Starts at 1, increments by 1 every turn.
   */
  private static int turnNumber = 1;

  /**
   * Number that represents the total amount of grid spaces.
   * Can be used as the maximum limit number of turns.
   */
  private static final int TOTAL_GRID_SPACES = NUM_COLUMNS * NUM_ROWS;

  /**
   * Entrypoint of the program.
   * @param args UNUSED.
   */
  public static void main(final String[] args) {
    // Fill up the game grid with empty spaces.
    for (int colNum = 0; colNum < NUM_COLUMNS; colNum++) {
      gameGrid[colNum] = " ".repeat(NUM_ROWS);
    }
    // LOOP
    while (turnNumber <= TOTAL_GRID_SPACES) {
      // Display the game grid.
      displayGameGrid();
      // USER TURN
      if (turnNumber % 2 != 0) {
        // Try catch
        try {
          // Initialize Scanner for user input.
          Scanner scanner = new Scanner(System.in);
          // Get user input for column number.
          System.out.printf("Enter a column number [1-%d]: ", NUM_COLUMNS);
          int chosenColumn = scanner.nextInt() - 1;
          // Check if the input is valid.
          if (chosenColumn < 0 || chosenColumn >= NUM_COLUMNS) {
            // If it isn't, give an error message [IN RED]
            System.out.println("\033[0;31mERROR: INPUT OUT OF BOUNDS.");
            continue;
          } else if (gameGrid[chosenColumn].charAt(NUM_ROWS - 1) != ' ') {
            // If the column is full, give an error message [IN RED]
            System.out.println("\033[0;31mERROR: COLUMN IS FULL.");
            continue;
          } else {
            // If it is, place the user's marker in the chosen column.
            // Find the lowest empty space in the column.
            for (int rowNum = 0; rowNum < NUM_ROWS; rowNum++) {
              if (gameGrid[chosenColumn].charAt(rowNum) == ' ') {
                // Place the user's marker in the empty space.
                StringBuilder column = new StringBuilder(gameGrid[chosenColumn]);
                column.setCharAt(rowNum, USER_MARKER);
                gameGrid[chosenColumn] = column.toString();
                break;
              }
            }
            // Increment the turn number.
            turnNumber++;
          }
        } catch (Exception e) {
          // Error message for non-numeric input. [IN RED]
          System.out.println("\033[0;31mERROR: INPUT MUST BE NUMERIC.");
          continue;
        }
      } else {
        // AI TURN
        // Get a list of unfilled columns.
        ArrayList<Integer> unfilledColumns = getUnfilledColumns();
        // Pick a random column from the list.
        Random random = new Random();
        int chosenColumn = unfilledColumns.get(
            random.nextInt(unfilledColumns.size()));
        // Place the ai's marker in the chosen column.
        // Find the lowest empty space in the column.
        for (int rowNum = 0; rowNum < NUM_ROWS; rowNum++) {
          if (gameGrid[chosenColumn].charAt(rowNum) == ' ') {
            // Place the ai's marker in the empty space.
            StringBuilder column = new StringBuilder(gameGrid[chosenColumn]);
            column.setCharAt(rowNum, AI_MARKER);
            gameGrid[chosenColumn] = column.toString();
            break;
          }
        }
        // Increment the turn number.
        turnNumber++;
      }
      // Check if the user has won.
      if (checkForWin(USER_MARKER)) {
        break;
      }
      // Check if the ai has won.
      if (checkForWin(AI_MARKER)) {
        break;
      }
    }
    // Display the game grid one last time.
    displayGameGrid();
    // Check if the user has won.
    if (checkForWin(USER_MARKER)) {
      System.out.println("\033[0;34mYOU WIN! CONGRATULATIONS!");
    } else if (checkForWin(AI_MARKER)) {
      System.out.println("\033[0;31mAI WINS! BETTER LUCK NEXT TIME!");
    } else {
      System.out.println("\033[0;33mIT'S A TIE! NO ONE WINS!");
    }
  }
}
