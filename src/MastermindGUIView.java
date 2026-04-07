/**
 * @author Duc Tan Tran
 * Course: CSC335
 * File: MastermindGUIView.java
 * Purpose: The program simulates Mastermind, a similar game to Wordle. the computer randomly chooses 4 colored pegs 
 * -- each peg being one of 6 colors. The user then have to figure out what the computer guessed. The player will be interacting through a GUI
 * Implementations: takes in the user input, runs a loop that keep asking if the user wants to play, each play cycle consist 
 * of 10 guesses. 
 * 		GUI implementation: 3 clear segments, with a scrollable 10x4 peg board used for gameplay at the middle, the top center for the title, and the
 * 		bottom center for buttons. The implementations also include creating game controller and the mastermind model.
 */
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;

public class MastermindGUIView extends Application {
	
	// using list instead of hashmap for simplicity
    private static final char[]  COLORS      = {'r', 'o', 'y', 'g', 'b', 'p'};
    private static final Color[] COLOR_PAINT = {Color.RED, Color.ORANGE, Color.YELLOW,
                                                Color.GREEN, Color.BLUE, Color.PURPLE};

    private MastermindController controller;
    private GridPane board;
    private Circle[][] pegs;
    private List<Character> currentGuess = new ArrayList<>();
    private int currentRow = 0;

    /**
     * start() -- display the actual app by initializing the title, the board, the buttons and the layout
     * @param primaryStage - the javaFX window
     */
    @Override
    public void start(Stage primaryStage) {
        controller = new MastermindController(new MastermindModel());

        // Title
        Label title = new Label("MASTERMIND");
        title.setStyle("-fx-font-size: 26px; -fx-font-weight: bold;"); // make the text massive like the example

        // Board
        board = new GridPane();
        board.setHgap(10);
        board.setVgap(8);
        board.setAlignment(Pos.TOP_CENTER);
        displayPegBoard(); // call to display the board
        
        // make the board scrollable
        ScrollPane scroll = new ScrollPane(board);
        scroll.setPrefHeight(500);
        scroll.setFitToWidth(true); // center the scroll

        // Generates 6 circles at the bottom
        // create layout container, set alignment and spacing
        HBox colorRow = new HBox(10);
        colorRow.setAlignment(Pos.CENTER);
        colorRow.setPadding(new Insets(10));
        
        // for each circle color
        for (int i = 0; i < 6; i++) {
            Circle circleButton = new Circle(22, COLOR_PAINT[i]);
            final char color = COLORS[i]; // finals because lambda is called after
            circleButton.setOnMouseClicked(event -> selectColor(color));
            colorRow.getChildren().add(circleButton);
        }

        // action buttons
        Button submit  = new Button("Submit Guess");
        Button remove  = new Button("Remove Last Peg");
        Button restart = new Button("Restart");
        
        // set actions using lambda
        submit.setOnAction(e -> submit());
        remove.setOnAction(e -> removePeg());
        restart.setOnAction(e -> restart());

        // put the buttons into a row
        HBox buttonRow = new HBox(10, submit, remove, restart);
        buttonRow.setAlignment(Pos.CENTER);
        buttonRow.setPadding(new Insets(5, 0, 10, 0)); // add padding to look like the example
        
        // set all of the layout
        BorderPane window = new BorderPane();
        window.setTop(title);
        window.setCenter(scroll);
        // stack the 6 colors and the buttons correctly, then throw it to the bottom
        VBox colorRowOnTopButton = new VBox(colorRow, buttonRow);
        window.setBottom(colorRowOnTopButton);
        BorderPane.setAlignment(title, Pos.CENTER);

        primaryStage.setTitle("Mastermind");
        primaryStage.setScene(new Scene(window, 600, 700));
        primaryStage.show();
    }
    
    /**
     * displayPegBoard() -- reset everything and draw a new 10 rows board with empty pegs and score
     */
    private void displayPegBoard() {
    	// clear everything for a fresh display
        board.getChildren().clear();
        currentGuess.clear();
        pegs = new Circle[10][4]; // 10 rows of 4 cols to display
        currentRow = 0;


        // go through every row and set up the board
        for (int row = 0; row < 10; row++) {
        	// add indexing to the row
            board.add(new Label(String.valueOf(row + 1)), 0, row);
            // create 4 pegs
            for (int col = 0; col < 4; col++) {
                Circle peg = new Circle(20, Color.LIGHTGRAY);
                pegs[row][col] = peg;
                board.add(peg, col + 1, row); // col + 1 to account displacement from index col
            }
            // add the score box
            board.add(scoreGrid(0, 0), 5, row);
        }
    }
    
    /**
     * selectColor() -- Add the selected color to the current guess visually
     * 					Only works if the game is active or the row isn't full
     * @param color -- char representing the color
     */
    private void selectColor(char color) {
    	// After the game is over, do not allow any further guesses until “Restart” is clicked
        if (controller.isGameOver()) return;
        if (currentGuess.size() >= 4) return;
        
        // add the color selected to display and currentGuess
        currentGuess.add(color);
        int col = currentGuess.size() - 1;
        pegs[currentRow][col].setFill(charToColor(color));
    }

    /**
     * removePeg() -- removing the peg from the row.
     * 				  Only works if the game is active or the row isn't empty
     */
    private void removePeg() {
    	//After the game is over, do not allow any further guesses until “Restart” is clicked
        if (controller.isGameOver() || currentGuess.isEmpty()) return;

        // removing peg from display and currentGuess
        int col = currentGuess.size() - 1;
        currentGuess.remove(col);
        pegs[currentRow][col].setFill(Color.LIGHTGRAY);
    }
    /**
     * submit() -- score the user input, update the attempts count and update the mastermind model
     * 			   Only works if the game is active and its a full input
     */
    private void submit() {
    	// After the game is over, do not allow any further guesses until “Restart” is clicked
        if (controller.isGameOver()) return;
        if (currentGuess.size() < 4) return;
        
        // grade userInput
        String userInput = "";
        for (char c : currentGuess) {
        	userInput += c;
        }
        int[] score = controller.scoreUserInput(userInput);

        // remove everything in col 5 (score box), then add the updated one back
        board.getChildren().removeIf(n -> GridPane.getColumnIndex(n) == 5 && GridPane.getRowIndex(n) == currentRow);
        board.add(scoreGrid(score[0], score[1]), 5, currentRow);

        currentRow++;
        currentGuess.clear();
        
        // checking game status
        if (controller.playerWon()) {
            showAlert("You won!");
        } else if (controller.isGameOver()) {
            showAlert("You lost! The answer was: " + controller.getComputerGuess());
        }
    }
    
    /**
     * restart() -- reset every data, including the model for the controller and restart
     */
    private void restart() {
        controller.newGame();
        displayPegBoard();
    }
    
    /**
     * scoreGrid() -- generate a 2x2 box based on the correctness of the user input on that row
     * 				  There is a black peg for each “Right Color, Right Place” peg and white for “Right Color,Wrong Place”. 
     * @param black -- count of right color right place pegs
     * @param white -- count of right color wrong place pegs
     * @return a GridPane 2x2 box representing the score
     */
    private GridPane scoreGrid(int black, int white) {
        GridPane grid = new GridPane();
        grid.setHgap(2);
        grid.setVgap(2);
        grid.setPadding(new Insets(2));
        // black border outline to create a box
        grid.setStyle("-fx-border-color: black; -fx-border-width: 1;");

        List<Color> dots = new ArrayList<>();
        // I think the sequence doesn't matter so I am just adding stuff in the easiest way
        for (int i = 0; i < black; i++) {
        	dots.add(Color.BLACK);
        }
        for (int i = 0; i < white; i++) {
        	dots.add(Color.WHITE);
        }
        while (dots.size() < 4) {
        	dots.add(Color.LIGHTGRAY);
        }
        
        // placig the dots in 2x2
        int i = 0;
        for (int row = 0; row < 2; row++) {
            for (int col = 0; col < 2; col++) {
                Circle dot = new Circle(7, dots.get(i));
                i ++;
                dot.setStroke(Color.GRAY); // add in the outline for the dot
                grid.add(dot, col, row);
            }
        }
        return grid;
    }

    /**
     * showAlert() -- create a pop up alert window to showcase the message
     * @param message -- string of the message
     */
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Message");
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * charToColor() -- return the Color object when given a certain character, light gray if there is no matching color
     * @param c - color character
     * @return Color object of the character
     */
    private Color charToColor(char c) {
        for (int i = 0; i < 6; i++) {
            if (COLORS[i] == c) {
            	return COLOR_PAINT[i];
            }
        }
        return Color.LIGHTGRAY;
    }
}