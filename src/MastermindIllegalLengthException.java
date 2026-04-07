/**
 * @author Duc Tan Tran
 * Course: CSC335
 * File: MastermindIllegalColorException.java
 * Purpose: special exception made to handle the user's invalid input, which is entering an illegal string length
 */
public class MastermindIllegalLengthException extends Exception{
	/**
	 * constructor for the MastermindIllegalLengthException
	 */
	public MastermindIllegalLengthException(String message) {
		super(message);
	}
}
