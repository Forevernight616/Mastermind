/**
 * @author Duc Tan Tran
 * Course: CSC335
 * File: MastermindTextView.java
 * Purpose: The program simulates Mastermind, a similiar game to Wordle. the computer randomly chooses 4 colored pegs 
 * - each peg being one of 6 colors. The user then have to figure out what the computer guessed. The player interact with the game
 * through the terminal.
 * Implementations: takes in the user input, runs a loop that keep asking if the user wants to play, each play cycle consist 
 * of 10 guesses. 
 */
import java.util.Scanner;

public class MastermindTextView {
	
	private MastermindController controller;
	private MastermindModel model;
	private Scanner scanner;
	
	public MastermindTextView() {
        this.model      = new MastermindModel();
        this.controller = new MastermindController(model);
        this.scanner    = new Scanner(System.in);
    }
	
	/**
	 * run() - This is the main method that deals with the program's flow
	 * 			The player will be interacting with the program through the terminal.
	 */
	public void run() {
		System.out.println("Welcome to Mastermind!");
	
		while(true) {
			System.out.print("Would you like to play? ");
			int userIntention = controller.intentionToInt(controller.takesInInput(scanner));
			// if user answered something invalid
			if (controller.userProceedingInvalid(userIntention)) continue;
			// if user answered "no"
			if (userIntention==0) break;
	
			int attempt = 0;
			model.generateRandomGuess();
			while (attempt < 10) {
				System.out.print("Enter guess number " + String.valueOf(attempt+1) + ": ");
				String userInput = controller.takesInInput(scanner);
				
				//handles invalid user input
				if (controller.isValidInput(userInput) == false) continue;

				//winning condition
				if (controller.isCorrect(userInput)) break;
				
				attempt+=1;
			}
			System.out.println(controller.didUserLose(attempt));
			
		}
		System.out.println("Exiting...");
	}
}
