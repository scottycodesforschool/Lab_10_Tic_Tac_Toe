import java.util.Scanner; // Needed for SafeInput methods

/**
 * Main class for the Tic Tac Toe game from Lab10 of 1090C.
 * This class contains the game logic, board representation,
 * and helper methods for game operations.
 */
public class TicTacToe {

    // Class-level variables for the board and its dimensions
    private static final int ROWS = 3; // Number of rows on the Tic Tac Toe board
    private static final int COLS = 3; // Number of columns on the Tic Tac Toe board
    // The game board, initialized with ROWS and COLS. Each cell will store " " (empty), "X", or "O".
    private static final String[][] board = new String[ROWS][COLS];

    // Main method where the game execution will start
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in); // Create a single Scanner object for all inputs

        // --- Pseudo Code Outline for Tic Tac Toe Game ---
        // --- End of Pseudo Code Outline ---


        // --- Game Implementation ---
        boolean playAgain;
        String currentPlayer;
        boolean gameOver;
        int turnCount;
        int rowMove;
        int colMove;
        int actualRow;
        int actualCol;

        do { // Outer loop for playing multiple games
            clearBoard(); // Clear the board for a new game
            currentPlayer = "X"; // X always starts
            gameOver = false;
            turnCount = 0; // Reset turn counter for a new game

            SafeInput.prettyHeader("Welcome to Tic Tac Toe!"); // Display a nice header
            display(); // Display the initial empty board

            do { // Inner loop for a single game (turns)
                // Prompt current player for their move
                System.out.println("It's " + currentPlayer + "'s turn.");
                do {
                    // Get row input (1-3)
                    rowMove = SafeInput.getRangedInt(in, "Enter row (1-" + ROWS + ")", 1, ROWS);
                    // Get col input (1-3)
                    colMove = SafeInput.getRangedInt(in, "Enter column (1-" + COLS + ")", 1, COLS);

                    // Convert 1-3 coordinates to 0-2 array indices
                    actualRow = rowMove - 1;
                    actualCol = colMove - 1;

                    // Validate move
                    if (isValidMove(actualRow, actualCol)) {
                        System.out.println("Invalid move! That spot is already taken or out of bounds. Please choose an empty spot.");
                    }
                } while (isValidMove(actualRow, actualCol)); // Loop until a valid move is entered

                // Record the valid move on the board
                board[actualRow][actualCol] = currentPlayer;
                turnCount++; // Increment turn counter

                display(); // Display the board after the move

                // Check for win or tie conditions
                if (turnCount >= 5) { // Minimum 5 moves needed for a win
                    if (isWin(currentPlayer)) {
                        SafeInput.prettyHeader(currentPlayer + " WINS! Congratulations!");
                        gameOver = true;
                    }
                }

                if (!gameOver && turnCount == 9) { // If the board is full and no win, it's a tie
                    if (isTie()) { // isTie checks if the board is full and no win
                        SafeInput.prettyHeader("It's a TIE! Good game!");
                        gameOver = true;
                    }
                }

                // If the game is not over, toggle player
                if (!gameOver) {
                    currentPlayer = (currentPlayer.equals("X")) ? "O" : "X";
                }

            } while (!gameOver); // End of inner game loop

            // Prompt players to play again
            playAgain = SafeInput.getYNConfirm(in, "Do you want to play again?");

        } while (playAgain); // End of outer play again loop

        System.out.println("Thanks for playing Tic Tac Toe!");
        in.close(); // Close the scanner when the program is done
    }

    // Helper methods for Tic Tac Toe game logic (private static)

    /**
     * Clears the Tic Tac Toe board by setting all elements to a single space character "".
     * This prepares the board for a new game.
     */
    private static void clearBoard() {
        // Iterate through each row
        for (int r = 0; r < ROWS; r++) {
            // Iterate through each column in the current row
            for (int c = 0; c < COLS; c++) {
                board[r][c] = " "; // Set the cell to a space character
            }
        }
    }

    /**
     * Displays the current state of the Tic Tac Toe board to the console.
     * It formats the board with lines and pipes for readability.
     */
    private static void display() {
        // Outer loop for rows
        for (int r = 0; r < ROWS; r++) {
            // Inner loop for columns to print cell content
            for (int c = 0; c < COLS; c++) {
                // Print the content of the current cell, ensuring a consistent width for single characters
                System.out.print(" " + board[r][c] + " ");
                if (c < COLS - 1) { // If it's not the last column, print a pipe separator
                    System.out.print("|");
                }
            }
            System.out.println(); // Move to the next line after printing a row
            if (r < ROWS - 1) { // If it's not the last row, print a horizontal line separator
                System.out.println("---+---+---"); // Adjusted for 3-char wide cells
            }
        }
        System.out.println(); // Add an extra newline for spacing after the board
    }

    /**
     * Checks if a proposed move (row, col) is valid.
     * A move is valid if the specified cell on the board is currently empty (" ").
     *
     * @param row The row index of the proposed move.
     * @param col The column index of the proposed move.
     * @return true if the cell is empty and thus a legal move, false otherwise.
     */
    private static boolean isValidMove(int row, int col) {
        // Check if the cell at the given coordinates is a space (empty)
        // Bounds checking is implicitly handled by getRangedInt for user input (1-3 converted to 0-2),
        // but it's good practice to keep it for robustness if this method were called directly.
        return row < 0 || row >= ROWS || col < 0 || col >= COLS || !board[row][col].equals(" ");
    }

    /**
     * Checks if the specified player (X or O) has won the game.
     * This method calls helper methods to check for row, column, and diagonal wins.
     *
     * @param player The player symbol ("X" or "O") to check for a win.
     * @return true if the player has won, false otherwise.
     */
    private static boolean isWin(String player) {
        // A player wins if they have a row win, a column win, or a diagonal win
        return isRowWin(player) || isColWin(player) || isDiagonalWin(player);
    }

    /**
     * Checks if the specified player has a win condition in any row.
     *
     * @param player The player symbol ("X" or "O") to check for a row win.
     * @return true if the player has three in a row, false otherwise.
     */
    private static boolean isRowWin(String player) {
        // Iterate through each row
        for (int r = 0; r < ROWS; r++) {
            // Check if all cells in the current row match the player's symbol
            if (board[r][0].equals(player) && board[r][1].equals(player) && board[r][2].equals(player)) {
                return true; // Found a row win
            }
        }
        return false; // No row win found
    }

    /**
     * Checks if the specified player has a win condition in any column.
     *
     * @param player The player symbol ("X" or "O") to check for a column win.
     * @return true if the player has three in a column, false otherwise.
     */
    private static boolean isColWin(String player) {
        // Iterate through each column
        for (int c = 0; c < COLS; c++) {
            // Check if all cells in the current column match the player's symbol
            if (board[0][c].equals(player) && board[1][c].equals(player) && board[2][c].equals(player)) {
                return true; // Found a column win
            }
        }
        return false; // No column win found
    }

    /**
     * Checks if the specified player has a win condition in any diagonal.
     * There are two possible diagonals: top-left to bottom-right, and top-right to a bottom-left.
     *
     * @param player The player symbol ("X" or "O") to check for a diagonal win.
     * @return true if the player has three in a diagonal, false otherwise.
     */
    private static boolean isDiagonalWin(String player) {
        // Check top-left to bottom-right diagonal
        if (board[0][0].equals(player) && board[1][1].equals(player) && board[2][2].equals(player)) {
            return true;
        }
        // Check top-right to bottom-left diagonal
        return board[0][2].equals(player) && board[1][1].equals(player) && board[2][0].equals(player);// No diagonal win found
    }

    /**
     * Checks for a tie condition in the Tic Tac Toe game.
     * A tie occurs if all cells on the board are filled and no player has won.
     * (This implementation checks if the board is full. The alternative of checking
     * if all win vectors are blocked is more complex and not implemented here,
     * but could be added if needed.)
     *
     * @return true if the game is a tie, false otherwise.
     */
    private static boolean isTie() {
        // Iterate through all cells to see if any are still empty
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                if (board[r][c].equals(" ")) {
                    return false; // Found an empty space, so it's not a tie yet
                }
            }
        }
        return true; // No empty spaces found, so the board is full.
        // If isWin() was also false, then it's a tie.
    }
}
