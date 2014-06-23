package ttt;

import java.util.Scanner;

/**
 * This is the main class that will be execute to play the game. 
 * The game is command line based so that the user will have to enter the row and column as described. 
 * The main method creates the instance of the TTT board, and the constructor of the TTT board runs the entire game.
 */
public class TTTMain {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		GameState continueGame = null;
		int choice = 0;
		do {
			showMenu();
			try {
				choice = input.nextInt();
			} catch (Exception e) {
				System.out.println("Invalid input");
			}
			
			while (choice <= 0 || choice > 3) {
				System.out.println("Please enter number between 1 to 3!");
				while (!input.hasNextInt()) {
					System.out.println("That's not a number!");
					input.next();
				}
				choice = input.nextInt();
			}

			switch (choice) {
			case 1:
				showInstructions();
				break;

			case 2:
				TTTBoard ttt = new TTTBoard();
				continueGame = ttt.getState();
				break;

			case 3:
				break;

			default:
				System.out.println("Enter a valid input");
				choice = 0;
			}
		} while (choice >= 0 && choice < 3 || continueGame != null);
	}

	public static void showMenu() {
		System.out.println("MENU:" 
				+ "\n--------------------------------------------"
				+ "\n1 - HowTo" 
				+ "\n2 - Play Game" 
				+ "\n3 - Exit"
				+ "\n--------------------------------------------");
	}

	public static void showInstructions() {
		System.out.println("HowTo:" 
				+ "\n============================================" 
				+ "\nThis is an unbeatable Tic-Tac-Toe game."
				+ "\nYour symbol is O and computer's symbol is X." 
				+ "\nTo play the game, enter the row and column number of the cell you want to mark your symbol on the grid, with space between the two numbers."
				+ "\nThe input should be (row[1-3] , column[1-3]) WITHOUT commas, and ONLY SPACES between two digits."
				+ "\nFor example, to mark the cell in the center, enter 2 2. (The index begins at 1)"
				+ "\nYou won't beat me. But you can try. Good luck!"
				+ "\n============================================");
	}
}
