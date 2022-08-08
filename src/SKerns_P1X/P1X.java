/*
 * Stewart Kerns
 * CPSC 5002, Seattle University
 * This is free and unencumbered software released into the public domain.
 */
package SKerns_P1X;

import java.util.Scanner;   //import the Scanner class

/**
 * This program plays games of Tic-Tac-Toe boards of the size requested by the
 * user and will continue to play them until the user no longer wants
 *
 * @author Stewart Kerns
 * @version 1.0
 */
public class P1X {

    /**
     * The main method calls the welcome method, calls the playGame method from
     * the TicTacToe class, allows the user to play as many times as they want
     * and then calls a goodbye message
     *
     * @param args a String array containing the command line arguments
     */
    public static void main(String[] args){


        //Create a new Scanner object to read user input
        Scanner keyboardIn = new Scanner(System.in);
        //Prints a welcome message
        welcome();
        //create a new TicTacToe object
        TicTacToeX game = new TicTacToeX(sizeGame(keyboardIn));
        //Allow the user to play the game as many times as they want
        do {
            //play the game
            game.playGame(keyboardIn);
        } while(!noPlay(keyboardIn));
        //print a goodbye message
        goodbye();
        //close the scanner object
        keyboardIn.close();
    }

    /**
     * This method asks the user if they want to play on a larger than the
     * 3x3 sized board and prompts them to pick an odd number with a range of
     * 3 to 15
     *
     * @param keyboardIn A Scanner object to take in user input
     */
    public static int sizeGame(Scanner keyboardIn){
        //declare an int for boardsize
        int boardSize;
        //until a number in the specified range is provided, ask the user what
        //board size they want to play on
        do {
            System.out.print("\nWhat size of board would you like to play on?" +
                    "\n3 is the minimum, 15 is the largest, and the number " +
                    "must be odd: ");
            //verify that the input is an integer
            while (!keyboardIn.hasNextInt()){
                System.out.print("That's not a valid input, please input " +
                        "a valid board size number: ");
                keyboardIn.next();
            }
            //set the board size to the int chosen by the user
            boardSize = keyboardIn.nextInt();
        }while (boardSize < 3 || boardSize > 15 || boardSize % 2 == 0);
        //return the board size
        return boardSize;
    }

    /**
     * This method prints a welcome message to the user
     */
    public static void welcome(){
        System.out.println("\nWelcome to the P1 program, this program will " +
                "allow two users to play\na game of Tic-Tac-Toe for as many " +
                "rounds as they would like.\nPlayer X will go first and it " +
                "will alternate from there.\nYou can play on a standard 3x3 " +
                "board or larger (up to and including 15x15).\n");
    }

    /**
     * This method prints a goodbye message to the user
     */
    public static void goodbye(){
        System.out.println("Thanks for playing!");
    }

    /**
     * This method asks the user if they want to continue playing and requests
     * them to enter "no" if they no longer want to play
     * @param keyboardIn A scanner object to take in user input from the
     *                   keyboard
     * @return boolean value of if the user wants to continue
     */
    public static boolean noPlay(Scanner keyboardIn){
        //create a final String to match the user input to
        final String stringEnd = "NO";

        //prompt the user on if they want to play again
        System.out.print("\nWould you like to play again? (no to quit): ");
        //absorb the leftover \n from int inputs
        keyboardIn.nextLine();
        //take in the user input and assign it to a String
        String userAnswer = keyboardIn.nextLine();

        //return true if the string input by the user matches
        return userAnswer.equalsIgnoreCase(stringEnd);
    }
}
