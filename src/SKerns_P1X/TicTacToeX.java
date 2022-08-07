/*
 * Stewart Kerns
 * CPSC 5002, Seattle University
 * This is free and unencumbered software released into the public domain.
 */
package SKerns_P1X;

import java.util.Scanner;   //import the Scanner class

/**
 * This class builds all the fields and methods needed to create a game of
 * tic-tac-toe to be played by 2 users on a board of 3 rows/columns
 *
 * @author Stewart Kerns
 * @version 1.0
 */
public class TicTacToeX {
    //Create a field for the size of the board
    private int boardSize;
    //create a field for the array of the board that will hold player values
    private int[][] board;
    //create a field for the name of the first player
    private final String PLAYER_1_NAME = "Player X";
    //create a field for the name of the second player
    private final String PLAYER_2_NAME = "Player O";
    //create a field for the value of player 1 to go into the array
    private final int PLAYER_VAL_1 = 1;
    //create a field for the value of player 2 to go into the array
    private final int PLAYER_VAL_2 = -1;
    //create a field for the value when there is no winner
    private final int NO_WINNER = 0;
    //create a field for the element where the row request by the user will be
    private final int ROW = 0;
    //create a field for the element where the column request by the user
    // will be
    private final int COL = 1;

    //create a field for a count value of how many times players have played
    private int count;
    //create a field for a count of how many times player 1 has won
    private int player1Win;
    //create a field for a count of how many times player 2 has won
    private int player2Win;
    //create a field for a count of how many times the players have tied
    private int tie;

    private int[] userChoice;

    /**
     * This constructor sets up the board size and then creates a board of that
     * size, it also sets up arrays to hold the users choices and the number of
     * stat options
     */
    public TicTacToeX(){
        final int NUM_DIMENSIONS = 2;
        //set the board size to 3
        this.boardSize = 3;
        //set the count for number of turns to 0
        this.count = 0;
        //create an array of length and width of the board size
        this.board = new int[boardSize][boardSize];
        //create an array to be used for holding user input
        this.userChoice = new int[NUM_DIMENSIONS];
        //set the number of ties to 0
        this.tie = 0;
        //set the number of player 1 wins to 0
        this.player1Win = 0;
        //set the number of player 2 wins to 0
        this.player2Win = 0;
    }
    public TicTacToeX(int boardSize){
        final int NUM_DIMENSIONS = 2;
        //set the board size to 3
        this.boardSize = boardSize;
        //set the count for number of turns to 0
        this.count = 0;
        //create an array of length and width of the board size
        this.board = new int[boardSize][boardSize];
        //create an array to be used for holding user input
        this.userChoice = new int[NUM_DIMENSIONS];
        //set the number of ties to 0
        this.tie = 0;
        //set the number of player 1 wins to 0
        this.player1Win = 0;
        //set the number of player 2 wins to 0
        this.player2Win = 0;
    }


    /**
     * This method plays the game by alternating player turns until someone
     * wins or there is tie, it then congratulates the winner and displays the
     * winner and tie statistics before clearing the board
     * @param keyboardIn A Scanner object to take in user input
     */
    public void playGame(Scanner keyboardIn){
        //create an integer to hold the value of whom won
        int checkWin;
        //create an integer that will be watch for max amount of turns and is
        //the value of the board size squared
        int MAX_TURNS = (int)Math.pow(boardSize, 2);

        //create a do while loop to play each turn
        do {
            //use the userTurn method to do player 1s turn
            userTurn(keyboardIn, PLAYER_1_NAME,
                    PLAYER_VAL_1);
            //check for winner and set value to checkWin
            checkWin = checkWinner();
            //if player 1 didn't win and the game isn't over, let player 2 run
            if (count < MAX_TURNS && checkWin == NO_WINNER){
                //use the userTurn method to do player 2s turn
                userTurn(keyboardIn, PLAYER_2_NAME,
                        PLAYER_VAL_2);
                //check for winner and set value to checkWin
                checkWin = checkWinner();
            }
            //continue while the board isn't full and someone hasn't won
        } while(count < MAX_TURNS && checkWin == NO_WINNER);

        //congratulate the winner
        congratulateWinner(checkWin);
        //print the winner statistics
        printWinnerStats();
        //clear the board in case the game is run again
        clearBoard();
    }

    /**
     * This method takes in a row, column, and user value and assigns it to the
     * corresponding position on the board array
     *
     * @param row the row the value will be inserted into
     * @param column the column the value will be inserted into
     * @param userVal the value to be inserted
     */
    public void pieceOnBoard(int row, int column, int userVal){
        //place the users value to the board array
        board[row][column] = userVal;
        //increment the count to signify someone put a piece down
        incrementCount();
    }

    /**
     * This method checks to make sure there isn't already a piece on the board
     * and returns a boolean
     * @param row the row to be checked
     * @param column the column to be checked
     * @return boolean value of if the position is empty or not
     */
    public boolean checkPieceOnBoard(int row, int column) {
        //return true if the position is empty
        return board[row][column] == 0;
    }

    /**
     * This method checks if there was a winner and either returns the value of
     * the winner or return the value for NO_WINNER
     * @return int value of the winner or NO_WINNER
     */
    public int checkWinner(){
        //create an integer for the value of if someone won or not
        int rowsAndColumns = checkBoardStraights();
        //check if there was a winner in the main straights
        if (rowsAndColumns != NO_WINNER){
            //return the winner if someone won
            return rowsAndColumns;
        }
        //if no one won, check the diagonals
        else{
            //return the value of if someone via diagonals or if no one won
            return checkDiags();
        }
    }

    /**
     * This method uses the userChoose method to take in user input and then
     * check to make sure the position isn't already taken before placing the
     * value into the position
     * @param keyboardIn a Scanner object to take in input
     * @param PLAYER_NAME the name of the player whose turn it is
     * @param USER_VAL the value of the player whose turn it is
     */
    public void userTurn(Scanner keyboardIn,
                         final String PLAYER_NAME, final int USER_VAL){
        //print the board for the player to choose a space
        printBoard();
        //ask the user to input their requested position until they choose a
        //valid space, it doesn't display the board again for clarity
        do {
            userChoose(keyboardIn, PLAYER_NAME);
        } while (!checkPieceOnBoard(userChoice[ROW],
                userChoice[COL]));
        //place the value on the board
        pieceOnBoard(userChoice[ROW], userChoice[COL], USER_VAL);
    }

    /**
     * This method asks the user to input a row and a column and then verifies
     * that the input is in a valid range for the board as well as that it's an
     * integer
     * @param keyboardIn a Scanner object to take in user input
     * @param player the player name who will be choosing
     */
    public void userChoose(Scanner keyboardIn, String player) {
        //ask the user to choose a free space on the board
        System.out.println("\nChoose a free space on the board " + player +
                ".");
        //ask them to input a valid row until they input a row in range
        do {
            System.out.print("Please choose a valid row: ");
            //check that the input is an integer and prompt them to input a
            //valid input until they do
            while (!keyboardIn.hasNextInt()){
                System.out.print("That's not a valid input, please input " +
                        "a valid row number: ");
                keyboardIn.next();
            }
            //set the row of the user choice to the valid row input
            userChoice[ROW] = keyboardIn.nextInt();

        } while (userChoice[ROW] >= boardSize || userChoice[ROW] < 0);

        //ask them to input a valid column until they input a column in range
        do {
            System.out.print("Please choose a valid column: ");
            //check that the input is an integer and prompt them to input a
            //valid input until they do
            while (!keyboardIn.hasNextInt()){
                System.out.print("That's not a valid input, please input " +
                        "a valid column number: ");
                keyboardIn.next();
            }
            //set the column of the user choice to the valid column input
            userChoice[COL] = keyboardIn.nextInt();
        } while (userChoice[COL] >= boardSize || userChoice[COL] < 0);
    }

    /**
     * This method prints out the board with Xs and Os instead of the board
     * array of 1s and -1s
     */
    public void printBoard(){
        //create a final int for the number of spaces per element
        final int NUM_SPACES = 4;
        //create a final int for the number of rows per row (1 separator per)
        final int NUM_ROWS_PER_ROW = 2;
        //create an int for the number of dashes that will be between rows
        int numDash = boardSize * NUM_SPACES;
        //create an int for how many total rows there will be
        int printBoardLength = boardSize * NUM_ROWS_PER_ROW;
        //create a count to keep track of which row is being printed
        int countRow = 0;

        //print two spaces for the column labels
        System.out.print("  ");
        for (int i = 0; i < boardSize; i++) {
            //print each of the column labels for the first 9
            if (i <= 9){
                System.out.print(" " + i + "  ");
            }
            else{
                System.out.print(" " + i + " ");
            }
        }

        for (int i = 0; i < printBoardLength; i++){
            //move to the next row
            System.out.println();
            //this section creates the divider dashes
            if (i % 2 != 0) {
                //create a for loop to print the correct number of dashes
                for (int d = 0; d < numDash; d++) {
                    //print out two spaces for the first section
                    if (d == 0) {
                        System.out.print("  ");
                    }
                    //print a dash
                    System.out.print("-");
                }
            }
            //this section prints out the Xs and Os rows
            else{
                //create a for loop to print each column
                for (int j = 0; j < boardSize; j++) {
                    //print the label for the rows
                    if (j == 0) {
                        System.out.printf("%2d", countRow);
                    }

                    //use a switch statement to choose what to print in the row
                    switch (board[countRow][j]) {
                        //Print X if player 1 has the position
                        case PLAYER_VAL_1: {
                            System.out.print(" X |");
                            break;
                        }
                        //print O if player 2 has the position
                        case PLAYER_VAL_2: {
                            System.out.print(" O |");
                            break;
                        }
                        //print an empty cell if no on has chosen the position
                        case 0:{
                            System.out.print("   |");
                            break;
                        }
                    }

                }
                //increment the row count
                countRow++;
            }
        }
    }

    /**
     * This method checks the main straights of the board which are the rows
     * and columns
     * @return int value of the whoever won or NO_WINNER
     */
    public int checkBoardStraights(){
        //declare a sum to check against for rows
        int sumRow;
        //declare a sum to check against for columns
        int sumCol;
        //create a for loop to check each row and column for a winner
        for (int i = 0; i < boardSize; i++){
            //set both of the sums to 0
            sumRow = 0;
            sumCol = 0;
            //create a for loop to sum both rows and columns
            for (int j = 0; j < boardSize; j++){
                //add the value of the element to the sum
                sumRow += board[i][j];
                //add the value of the element to the sum
                sumCol += board[j][i];
            }
            //use the checkSum method to check if the row had a winner
            if (checkSum(sumRow)){
                //return the winner value if there was one
                return sumRow / boardSize;
            }
            //use the checkSum method to check if the column had a winner
            else if (checkSum(sumCol)){
                //return the winner value if there was one
                return sumCol / boardSize;
            }
        }
        //if no one won, return NO_WINNER
        return NO_WINNER;
    }

    /**
     * This method performs a check of both of the main diagonals to see if
     * there was a winner
     * @return int value of a winner if so and NO_WINNER if not
     */
    public int checkDiags(){
        //create a sum for the first main diagonal and initialize to 0
        int sumDiag = 0;
        //create a sum for the second main diagonal and initialize to 0
        int sumDiag2 = 0;

        //create a for loop to sum the first main diagonal
        for (int i = 0; i < boardSize; i++){
            //add the element to the sum
            sumDiag += board[i][i];
        }
        //use the checkSum method to check if the diagonal had a winner
        if (checkSum(sumDiag)){
            //return the value of the user who won
            return sumDiag / boardSize;
        }

        //create a for loop to sum the second main diagonal
        for (int i = 0; i < boardSize; i++){
            //add the element to the sum
            sumDiag2 += board[(boardSize - 1) - i][i];
        }
        //use the checkSum method to check if the diagonal had a winner
        if (checkSum(sumDiag2)){
            //return the value of the user who won
            return sumDiag2 / boardSize;
        }

        //return NO_WINNER if no one won
        return NO_WINNER;
    }

    /**
     * This method takes in a sum and then checks to see if it is the sum of
     * the number of pieces in a row needed for a win
     * @param sum an int value to be checked
     * @return boolean of it is a winning sum
     */
    public boolean checkSum(int sum){
        //if the sum is equal to the rows/columns of the board times the player
        //value, move into the if statement
        if (sum == (boardSize * PLAYER_VAL_1) || sum == (boardSize *
                PLAYER_VAL_2)) {
            //return true if it's a winning sum
            return true;
        }
        //return false if it's not a winning sum
        return false;
    }

    /**
     * This method takes in the value of the winner and congratulates them, if
     * there is no winner, it declares a tie and at the end displays the final
     * positions of the players on the board
     * @param winner int value for the player at the end of the game
     */
    public void congratulateWinner(int winner) {
        //create a string to be used for the congratulations
        String congrats = ", congrats!  You are the winner!  Here is the " +
                "final board:";
        //Use a switch statement to determine what to print
        switch (winner) {
            //print if player one won
            case PLAYER_VAL_1: {
                System.out.println("\n" + PLAYER_1_NAME + congrats);
                //increment player one's wins
                player1Win++;
                break;
            }
            //print if player two won
            case PLAYER_VAL_2: {
                System.out.println("\n" + PLAYER_2_NAME + congrats);
                //increment player two's wins
                player2Win++;
                break;
            }
            //print if no one won
            case NO_WINNER:
                System.out.println("\nNo one wins! It's a tie! Here is the " +
                        "final board: ");
                //increment the number of ties
                tie++;
                break;
        }
        //print the final board
        printBoard();

    }

    /**
     * This method prints our the wins of each player and the number of times
     * they've tied
     */
    public void printWinnerStats(){
        //Note: I spent a lot of time trying to make this so it didn't repeat
        //code, but in the end, all I did was end up making the rest of my code
        //more complicated and less readable in order to save one section of
        // repeating, so I reverted back to how I had it originally
        System.out.println("\n" + PLAYER_1_NAME + " has won " +
                player1Win + " times!");
        //print out how many times player 2 has won
        System.out.println(PLAYER_2_NAME + " has won " + player2Win +
                " times!");
        //print out how many ties there have been
        System.out.println("There have been " + tie + " tie " +
                "games.");
    }

    /**
     * This method is used to clear the board at the very end of the game so
     * that if the user requests to play another game, when the game starts,
     * the count and board will be clear
     */
    public void clearBoard(){
        //create a nested for loop to access each element in the array
        for (int i = 0; i < boardSize; i++){
            for (int j = 0; j < boardSize; j++){
                //set each element to 0
                board[i][j] = 0;
            }
        }
        //set the count of turns played to 0
        count = 0;
    }

    /**
     * This method is used to increment the count of turns played
     */
    private void incrementCount(){
        //increment the count
        count++;
    }
}
