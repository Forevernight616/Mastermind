/**
 * @author Duc Tan Tran
 * Course: CSC335
 * File: Mastermind.java
 * Purpose: The program simulates Mastermind, a similiar game to Wordle. the computer randomly chooses 4 colored pegs 
 * -- each peg being one of 6 colors. The user then have to figure out what the computer guessed.
 * Implementations: takes in the user input, runs a loop that keep asking if the user wants to play, each play cycle consist 
 * of 10 guesses. When invoked with a command line argument of “-text”, it will launch the text-oriented UI. When invoked 
 * with a command line argument “-window” it will launch the GUI view. The default will be the GUI view.
 */

import javafx.application.Application;

public class Mastermind {

	/**
	 * This is the main method that deals with the program's flow
	 */
	public static void main(String[] args) {
		// Default launch or window launch
		if (args.length == 0 || args[0].equals("-window")) {
			Application.launch(MastermindGUIView.class, args);
		}
		
		// Text launch
		if (args.length == 1 && args[0].equals("-text")) {
			MastermindTextView textView = new MastermindTextView();
            textView.run();
		}
		
		// otherwise, don't do anything		
	}
}