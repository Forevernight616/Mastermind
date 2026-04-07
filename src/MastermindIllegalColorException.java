/**
 * @author Duc Tan Tran
 * Course: CSC335
 * File: MastermindIllegalColorException.java
 * Purpose: special exception made to handle the user's invalid input, which is entering an illegal color
 */
public class MastermindIllegalColorException extends Exception{
	/**
	 * constructor for the MastermindIllegalColorException
	 */
	public MastermindIllegalColorException(String message) {
		super(message);
	}
}
