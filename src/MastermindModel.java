/**
 * @author Duc Tan Tran
 * Course: CSC335
 * File: MastermindModel.java
 * Purpose: The program simulates Mastermind, a similiar game to Wordle.  This class contains all of the game state and is shared between 
 * the two front ends. This class contains all the logical operations necessary to run the game, and it also host all the data of the game.
 * It can only be interacted through the MastermindControll class.
 */
import java.util.HashMap;
import java.util.Random;

public class MastermindModel {
	
	private String stringComputerGuess;
	private HashMap <String, Integer> computerGuess;
	private int attempt;
	
	private boolean playerWon;
	
	/**
	 * Constructor of this class
	 */
	public MastermindModel() {
		this.stringComputerGuess = null;
		this.computerGuess = null;
		this.playerWon = false;
		this.attempt = 0;
		generateRandomGuess();
	}
	
	/**
	 * generateRandomGuess() -- generates a random string representing 4 positions with each position a singular peg
	 * 							store the secret sequence in stringComputerGuess and its hashmap representation in computerGuess
	 */
	public void generateRandomGuess() {
		// Generate a basic dictionary, where random int will be converted to a color
		HashMap<Integer, String> numberConversion = generateIntToColorMap();
		
		String computerGuess = "";
		Random random = new Random();
		for (int i = 0; i < 4; i++) {
			String color = numberConversion.get(random.nextInt(6));
			computerGuess += color;
		}
		
		this.stringComputerGuess = computerGuess;
		this.computerGuess = HMComputerGuess(stringComputerGuess);
	}
	
	/**
	 * generateIntToColorMap() -- creates a hashmap where int can be converted to colors
	 * @return numberConversion - hashmap connecting the int to color
	 */
	public HashMap<Integer,String> generateIntToColorMap(){
		HashMap<Integer,String> numberConversion = new HashMap<>();
		numberConversion.put(0, "r");
		numberConversion.put(1, "o");
		numberConversion.put(2, "y");
		numberConversion.put(3, "g");
		numberConversion.put(4, "b");
		numberConversion.put(5, "p");
		return numberConversion;
	}
	
	/**
	 * isCorrect() -- check if the user input is correct with the computer guess. This is exclusively used for the text based interaction.
	 * @param userInput - string representing the user input
	 * @return true or false depending on its correctness
	 */
	public boolean isCorrect(String userInput) {
		int[] score = scoreUserInput(userInput);
		System.out.println("Colors in the correct place: " + score[0]);
		System.out.println("Colors correct but in wrong position: " + score[1] + "\n");
		if (score[0]==4) {
			System.out.println("You Won, the answer was: " + stringComputerGuess);
			return true;
		}
		return false;
	}
	
	/**
	 * scoreUserInput(userInput) -- takes in the user input, weights it against the computer guess and output the result
	 * It goes through the user's input, and subtract the user's peg from the pre-existing hashmap. Negative values usually indicates duplicated
	 * count, and get passed through a max of 0 or the total sum of the hashmap values.
	 * @param userInput - user color sequence
	 * @returns score - int[] or size 2, where the first index is properColorAndPosition, the second is properColorIncorrectPosition
	 */
	public int[] scoreUserInput (String userInput){
		
		// mainly to keep track for GUI view
		attempt += 1;
		
		int[] score = {0,0};
		// properColorAndPosition is score[0]
		// properColorIncorrectPosition is score[1]
		
		for (int i=0; i<4;i++) {
			String userPegColor = String.valueOf(userInput.charAt(i));
			String computerPegColor = String.valueOf(stringComputerGuess.charAt(i));
			
			// case of properColorAndPosition
			if (userPegColor.equals(computerPegColor)) {
				score[0] += 1;
				computerGuess.put(userPegColor, computerGuess.get(userPegColor)-1);
			}
			// case of properColorIncorrectPosition
			else if (computerGuess.containsKey(userPegColor) && computerGuess.get(userPegColor) > 0 ) {
				score[1] += 1;
				computerGuess.put(userPegColor, computerGuess.get(userPegColor)-1);
			}	
		}
		//removing double counting for properColorIncorrectPosition
		score[1] = Math.max(0,score[1] + sumComputerGuess());
		
		// check if player have won
		if (score[0] == 4) {
			playerWon = true;
		}
		return score;
	}
	
	/**
	 * HMComputerGuess(string) -- convert a string of pegs into a hashmap representation with keys as the color
	 * and values as the frequency of that color and return that hashmap
	 * @param stringComputerGuess - string representing the computer-generated color sequence
	 * @returns computerGuess - computer generated peg sequence represented as a HashMap of colors and their frequency
	 */
	public HashMap<String,Integer> HMComputerGuess(String stringComputerGuess){
		HashMap <String, Integer> computerGuess = new HashMap<>();
		// begin conversion
		for (int i = 0; i<stringComputerGuess.length();i++) {
			String color = String.valueOf(stringComputerGuess.charAt(i));
			if (computerGuess.containsKey(color)) {
				computerGuess.put(color, computerGuess.get(color) + 1);
			}
			else {
				computerGuess.put(color, 1);
			}
		}
		return computerGuess;
	}

	/**
	 * sumComputerGuess() -- summation of all values in the hashmap computer guess (the original computer generated peg 
	 * sequence represented as a HashMap of colors and their frequency) and return the sum as an integer
	 */
	public int sumComputerGuess() {
		int result = 0;
		// go through all values
		for (int value:computerGuess.values()) {
			if (value < 0) {
				result += value;
			}
		}
		return result;
	}
	
	/**
	 * didUserLose() -- check if the user lost
	 * @return string representing the message
	 */
	public String didUserLose() {
		if (attempt == 10) {
			// reset attempts
			attempt = 0;
			return ("You Lost, the answer was: " + stringComputerGuess + "\n");}
		return "";	
	}
	
	/**
	 * didUserLose(attempt) -- check if the user lost
	 * @para attempt - int representing the attempt made
	 * @return string representing the message
	 */
	public String didUserLose(int attempt) {
		if (attempt == 10) {
			return ("You Lost, the answer was: " + stringComputerGuess + "\n");}
		return "";	
	}
	/**
	 * isGameOver() -- check if the game is over by looking at the attempts instead of the flag
	 * @return
	 */
	public boolean isGameOver() {
		if (attempt >= 10) {
			attempt = 0;
			return true;
		}
		return false;
	}
	
	/*
	 * Various getter methods
	 */
	public boolean playerWon() {
		return playerWon;
	}
	
	public String stringComputerGuess() {
		return stringComputerGuess;
	}
	
	
}
