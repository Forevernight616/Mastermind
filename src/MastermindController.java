/**
 * @author Duc Tan Tran
 * Course: CSC335
 * File: MastermindController.java
 * Purpose: The program simulates Mastermind, a similiar game to Wordle. This class contains all of the game logic,
 *  and is shared by the textual and graphical UIs. This class contains mainly input/output methods. It also has a bunch
 *  of simple methods to validate the user inputs. It also acts as a gateway for the front-ends to grab data from the model.
 */
import java.util.Scanner;

public class MastermindController {
	private MastermindModel model;
	
	/**
	 * constructor for this class
	 * @param model - MastermindModel object
	 */
	public MastermindController(MastermindModel model) {
		this.model = model;
	}
	
	/**
	 * intentiontoInt(userProceeding) -- takes in a string and proceed the user's intention off of it
	 * @param userProceeding - string representing user's answer
	 * @returns 1 - int representing "yes"
	 * 			2 - int representing "no"
	 * 			3 - int representing an invalid response
	 */
	public int intentionToInt(String userProceeding) {
		if (userProceeding.equals("yes")) return 1;
		if (userProceeding.equals("no")) return 0;
		System.out.println("Please only enter <yes> or <no>.");
		return 2;
	}

	/**
	 * takesInInput(scanner) -- scans user input and clean it
	 * @param scanner -- scanner object
	 * @return string of the user input
	 */
	public String takesInInput(Scanner scanner) {
		return scanner.nextLine().trim().toLowerCase();
	}
	
	/**
	 * userProceedingInvalid(userInput) -- takes in an int of the userInput and see if its a valid input
	 * @param userInput - int representing their intention
	 * @return
	 */
	public boolean userProceedingInvalid(int userInput) {
		if (userInput == 2) {
			return true;
		}
		return false;
	}
	
	/**
	 * isValidInput(userInput) -- full validation process of the user input
	 * @param userInput -- string of the user input
	 * @return true or false based on the legality of the input
	 */
	public boolean isValidInput(String userInput) {
		try {
			validateLengthInput(userInput);
			validateColorInput(userInput);
			return true;
		}
		// length error
		catch(MastermindIllegalLengthException e) {
			System.out.println(e.getMessage());
			return false;
		}
		// color error
		catch(MastermindIllegalColorException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	/**
	 * validateLengthInput(userInput) -- If the user entered a string longer than 4, it throws MastermindIllegalLengthException
	 * @param userInput - String representing the color sequence that the user put in
	 * @throws MastermindIllegalLengthException
	 */
	public void validateLengthInput(String userInput) throws MastermindIllegalLengthException{
		if (userInput.length() != 4) {
			throw new MastermindIllegalLengthException("The length is 4, you have entered an improper length\n");
		}
	}
	
	/**
	 * colorDoesntExist(userLetter) -- checks a singular color to see if the user's color exist within the color range
	 * @param userLetter - char of the user's color choice
	 * @return true or false depending on whether the value doesn't exist or does exist
	 */
	public boolean colorDoesntExist(char userLetter) {
		return userLetter!='r' && userLetter!='o' && userLetter!='y' 
				&& userLetter!='g' && userLetter!='b' && userLetter!='p';
	}

	/**
	 * validateColorInput(userInput) -- If the user entered an improper color, it throws MastermindIllegalColorException
	 * @param userInput - String representing the color sequence that the user put in
	 * @throws MastermindIllegalColorException
	 */
	public void validateColorInput(String userInput) throws MastermindIllegalColorException{
		for (int i = 0; i< userInput.length(); i++) {
			char userLetter = userInput.charAt(i);
			if (colorDoesntExist(userLetter)) {
				throw new MastermindIllegalColorException("Invalid color used. Please only use r/o/y/g/b/p\n");
			}
		}
	}
	
	/*
	 * Various getters method. Mainly for relaying information from the model to the front ends.
	 */
	public boolean isCorrect(String userInput) {
		return model.isCorrect(userInput);
	}
	
	public String didUserLose(int attempts) {
		return model.didUserLose(attempts);
	}

    public boolean isGameOver(){ 
    	return model.isGameOver(); 
    }
    
    public boolean playerWon() {
    	return model.playerWon();
    }
    public String getComputerGuess(){
    	return model.stringComputerGuess(); 
    }
    
    public void newGame() {
    	this.model = new MastermindModel();
    }
    
    public int[] scoreUserInput(String userInput) {
    	return model.scoreUserInput(userInput);
    }
} 


	

