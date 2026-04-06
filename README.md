## Description
This project implements Mastermind, you can play the game through compiling then when invoked with a command line argument of “-text”, you will launch the text-oriented UI. When invoked with a command line argument 0f “-window” you’ll launch the GUI view. The default will be the GUI view.

![board.png](board.png)

## How To Play
- The goal is to guess a generated sequence of 4 pegs of these available colors: red, blue, green, purple, yellow and orange
- You have 10 tries to guess the sequence of 4 pegs, repititions can happen.
- For the GUI, the box with 4 circle indicates:
	- A black peg for each “Right Color, Right Place” peg .
	- A white peg for “Right Color, Wrong Place”.

### MVC - Implementation
1.	`Mastermind` – This is the main class. 
2.	`MastermindGUIView` – This is the JavaFX GUI as shown above
3.	`MastermindTextView` – This is the UI that we built in project 2
4.	`MastermindController` – This class contains all of the game logic, and must be shared by the textual and graphical UIs. You may not call into different controllers from the different UIs and all methods provided must be useful to both front ends.
5.	`MastermindModel` – This class contains all of the game state and must be also shared between the two front ends.