package application;
import javafx.scene.*;
import java.util.Optional;
import java.util.Random;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class RandomGame extends Application {
	
	    private char currentPlayer ;
	    private char PlayerChar = 'X';
	    private char ComputerChar = 'O';
	    private char[][] board = new char[3][3];
	    private Button[][] buttons = new Button[3][3];
	    private int roundsToPlay;
	    private int currentRound = 1;
	    private int playerScore = 0;
	    private int computerScore = 0;
	    private Stage primaryStage;
	    private TextArea resultTextArea = new TextArea();
	    private ComboBox<String> playerComboBox = new ComboBox<>();
	    private ComboBox<String> playerCharBox = new ComboBox<>();
	    private boolean initialChoiceMade = false;
	    private Text text = new Text();
	    private TextField name = new TextField();
	    private Label Lname = new Label();
	    private Label Loption = new Label();
        private VBox option = new VBox (30);
        Button ok = new Button ("OK");
        int yse = 0 ;  
        private Label Titil = new Label("Random Tic Tac Toe");
      

	    
	    
	    
	    

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		resultTextArea.setFont(Font.font("Time New Roman" , FontWeight.BOLD , FontPosture.ITALIC ,20));
		resultTextArea.setEditable(false);
		text.setFont(Font.font("Time New Roman" , FontWeight.BOLD  ,20));
		Titil.setFont(Font.font("Time New Roman" , FontWeight.BOLD  ,30));
		Titil.setStyle("-fx-text-fill:red");
		
		resultTextArea.setPrefWidth(500);
		resultTextArea.setMaxWidth(500);
		resultTextArea.setPrefHeight(300);
        this.primaryStage = primaryStage;
        //resultTextArea.appendText("\t\t\t\tScores :+ \n Player X : 0  \n\n Player O (Computer) : 0  ");
        
        playerComboBox.getItems().addAll("Player", "Computer");
        playerComboBox.setValue("Player"); // Set default value
        playerCharBox.getItems().addAll("X","O");
        playerCharBox.setValue("X");

        playerComboBox.setOnAction(event -> {
            // Handle ComboBox selection only if it's the initial choice
            if (!initialChoiceMade) {
                initialChoiceMade = true;
                
                String selectedValue = playerComboBox.getValue();
                if ("Computer".equals(selectedValue)) {
                    // Computer starts first
                    makeRandomMove();
                }
            }
        });
        
        
        playerCharBox.setOnAction(e->{
        	String value = playerCharBox.getValue();
        	if ("O".equals(value)) {
        		PlayerChar = 'O';
        		ComputerChar = 'X';
        		currentPlayer = 'O';
        		
        	}
        });
        
        
        Lname.setText("Player Name: ");
        Lname.setFont(Font.font("Time New Roman", FontWeight.BOLD, FontPosture.ITALIC, 15));
        Lname.setStyle("-fx-text-fill:red");
        
        ok.setOnAction(e -> {
            handleOkButton();
        });

        //option.setAlignment(Pos.CENTER);
       // option.getChildren().addAll( Lname, name  );
        Loption.setStyle("-fx-text-fill:red");
       // ok.setPrefWidth(200);
       
        // Ask the user for the number of rounds to play
        roundsToPlay = askRoundsToPlay();//round.RoundNum;
        
        

        startNewRound();
    }
	
	
    
	  
	  //______________________________________________________________________________________________________________
   
		// this method to Start New Round if can :-
	private void startNewRound() {     
        if (currentRound <= roundsToPlay) {
        	
        	//resultTextArea.appendText("Score : \n Player X : 0  \n Player O : 0  ");
        	text.setText("Round " + currentRound+" / "+roundsToPlay);
        	
        	//("Round " + currentRound);
            GridPane gridPane = createGridPane();
            
            initializeBoard();
            
            if ("Computer".equals(playerComboBox.getValue())) {
                currentPlayer = ComputerChar;
                makeRandomMove();
            }

            Scene scene = new Scene(createLayout(gridPane), 1000, 750);
            
            primaryStage.setTitle("Tic Tac Toe - Random Moves");
            primaryStage.setScene(scene);
            primaryStage.show();
            //primaryStage.setFullScreen(true);

	        gridPane.setStyle("-fx-background-color:LIGHTSKYBLUE");

            // Disable the ComboBox after the game has started
            //playerComboBox.setDisable(true);
        } 
        
        else {
        	
            showGameCompleteAlert();
        }
    }
	
	//________________________________________________________________________________________________________________________
	
	
	 // this method to great a Grid pane with all UL : - 
	
	 private BorderPane createLayout(GridPane gridPane) {
		 
	        BorderPane layout = new BorderPane();
	        HBox nameBox = new HBox (20);
	        HBox combox = new HBox (20);
	        //HBox All = new HBox (20);


	        nameBox.setAlignment(Pos.CENTER);
	        //Lname.setText("Player X Name");
	        Lname.setFont(Font.font("Time New Roman" , FontWeight.BOLD , FontPosture.ITALIC ,15));
	        Loption.setText("Start player");
	        Loption.setFont(Font.font("Time New Roman" , FontWeight.BOLD , FontPosture.ITALIC ,15));
	        playerComboBox.setPrefWidth(150);
	        
	        nameBox.getChildren().addAll(Lname , name , playerCharBox);    
	        combox.getChildren().addAll(Loption , playerComboBox ,ok );
	        option.setAlignment(Pos.CENTER);
	        option.getChildren().addAll(nameBox , combox  );
	        //layout.setAlignment(Pos.CENTER);
	        
	        Button nextRoundButton = new Button("Next Round");
	        nextRoundButton.setPrefWidth(150);
	        nextRoundButton.setFont(Font.font("Time New Roman" , FontWeight.BOLD  ,15));
	        
	        Button Exit = new Button("Exit");
	        Exit.setPrefWidth(100);
	        Exit.setFont(Font.font("Time New Roman" , FontWeight.BOLD  ,15));
	        Exit.setOnAction(e->{
	        	primaryStage.close();
	        });
	        
	        Button game = new Button("New Game");
	        game.setPrefWidth(100);
	        game.setFont(Font.font("Time New Roman" , FontWeight.BOLD  ,15));
	        game.setOnAction(e->{
	        	  currentRound = 1;
		          playerScore = 0;
		          computerScore = 0;
		          resultTextArea.clear();
		          resultTextArea.appendText("\t\t\t\tScores : \nPlayer  ( "+name.getText() +" ): 0  \n\n Player  (Computer) : 0  ");
		          startNewRound();
	        	
	        	
	        });
	        HBox buttonBox = new HBox (20);
	        buttonBox.setAlignment(Pos.CENTER);
	        buttonBox.getChildren().addAll(nextRoundButton , Exit , game );
	        
	       
	        layout.setStyle("-fx-background-color:LIGHTSKYBLUE");
	        
	        VBox vbox = new VBox(10);
	        vbox.setAlignment(Pos.CENTER);
	        vbox.getChildren().addAll(text ,gridPane, resultTextArea , buttonBox );

	        //layout.getChildren().add(vbox);
	        layout.setCenter(vbox);
	        //layout.setRight(text);
	        layout.setTop(Titil);
	        layout.setLeft(option);
	       
	       
	        layout.setPadding(new Insets (5 ,5 ,5 ,5));

	      
	        nextRoundButton.setOnAction(e -> {
	            currentRound++;
	            startNewRound();
	        });
	        
	        return layout;
	    }
	    
	 
	 
	 //________________________________________________________________________________________________________________________
	 
	  private GridPane createGridPane() {
		  
		  
	        GridPane gridPane = new GridPane();
	        gridPane.setAlignment(Pos.CENTER);
	        gridPane.setHgap(5);
	        gridPane.setVgap(5);

	        for (int i = 0; i < 3; i++) {
	            for (int j = 0; j < 3; j++) {
	                Button button = new Button();
	                button.setMinSize(100, 100);
	                button.setFont(Font.font("Time New Roman", FontWeight.BOLD, 40));
	                
	                
	                int finalI = i;
	                int finalJ = j;
	                
	                button.setOnAction(e -> onButtonClick(finalI, finalJ));
	                gridPane.add(button, j, i);
	                buttons[i][j] = button;
	            }
	        }

	        return gridPane;
	    }
	  
	  //______________________________________________________________________________________________________
	  
	  private void onButtonClick(int row, int col) {
		  
	        if (board[row][col] == ' ' && currentPlayer == PlayerChar) {
	        	
	            buttons[row][col].setText(Character.toString(PlayerChar));
	            board[row][col] = PlayerChar;
	            
	            if (checkWin()) {
	            	whoISWinner(PlayerChar);
	            } 
	            
	            else if (isBoardFull()) {
	                //resultTextArea.appendText("\nIt's a draw!\n");
	                currentRound++;
	                startNewRound();
	            } 
	            
	            else {
	                currentPlayer = ComputerChar;
	                makeRandomMove();
	            }
	        }
	    }
	  
	  
	  //__________________________________________________________________________________________________________________________
	
	  
	  private void initializeBoard() {
	        for (int i = 0; i < 3; i++) {
	            for (int j = 0; j < 3; j++) {
	                board[i][j] = ' ';
	                buttons[i][j].setText(""); // Clear the text on buttons
	                buttons[i][j].setStyle("-fx-background-color: white;"); // Reset button color
	                buttons[i][j].setDisable(false); // Enable buttons for the new round
	            }
	        }
	        currentPlayer = PlayerChar;
	    }
	    
	  
	  //_____________________________________________________________________________________________________________________________
	  
	  private void makeRandomMove() {
		  int[] move = random_option(board, currentPlayer);

	        if (move != null) {
	            int row = move[0];
	            int col = move[1];

	            buttons[row][col].setText(Character.toString(ComputerChar));
	            board[row][col] = ComputerChar;
	            
	            System.out.println("Computer move: Row " + row + ", Column " + col);


	            if (checkWin()) {
	            	whoISWinner(ComputerChar);
	            } 
	            
	            else if (isBoardFull()) {
	                resultTextArea.appendText("\n It's a draw!\n");
	                currentRound++;
	                startNewRound();
	            } 
	            
	            else {
	                currentPlayer = PlayerChar;
	            }
	        }
	  }
	  
	  
	  private int[] random_option(char[][] board, char currentPlayer) {
		  
	        // Count legal moves
	        int count = 0;
	        for (int i = 0; i < 3; i++) {  //  Initiates a loop to iterate over the rows of the board.
	            for (int j = 0; j < 3; j++) {   // Initiates a nested loop to iterate over the columns of the board.
	                if (board[i][j] == ' ') {    // Checks if the current cell in the board is empty.
	                    count++;
	                }
	            }
	        }

	        
	        
	        // Check if there are legal moves available
	        if (count > 0) {
	            // Choose a random move
	            Random random = new Random();
	            int randomIndex = random.nextInt(count);

	            System.out.println(randomIndex);
	            // Populate legal moves array
	            count = 0;
	            for (int i = 0; i < 3; i++) {
	                for (int j = 0; j < 3; j++) {
	                    if (board[i][j] == ' ') {
	                        if (count == randomIndex) {
	                            return new int[]{i, j};
	                        }
	                        count++;
	                    }
	                }
	            }
	        }

	        
	        // No legal moves available, return null to indicate an error
	        return null;
	    }

	  //______________________________________________________________________________________________________________________________
	  
	  
	  private boolean checkWin() {
	        // Check rows, columns, and diagonals for a win
	        for (int i = 0; i < 3; i++) {
	            if (board[i][0] == currentPlayer && board[i][1] == currentPlayer && board[i][2] == currentPlayer) {
	            	buttons[i][0].setStyle("-fx-background-color: GREY;");
	            	buttons[i][1].setStyle("-fx-background-color: GREY;");
	            	buttons[i][2].setStyle("-fx-background-color: GREY;");
	            
	                return true; // Check rows
	            }
	            
	            if (board[0][i] == currentPlayer && board[1][i] == currentPlayer && board[2][i] == currentPlayer) {
	            	buttons[0][i].setStyle("-fx-background-color: 	GREY;");
	            	buttons[1][i].setStyle("-fx-background-color: 	GREY;");
	            	buttons[2][i].setStyle("-fx-background-color: 	GREY;");
	            
	                return true; // Check columns
	            }
	        }
	        
	        
	        if (board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer) {
	        	buttons[0][0].setStyle("-fx-background-color: 	GREY;");
          	    buttons[1][1].setStyle("-fx-background-color: 	GREY;");
          	    buttons[2][2].setStyle("-fx-background-color: 	GREY;");
          
	            return true; // Check main diagonal
	        }
	        
	        if (board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer) {
	        	buttons[0][2].setStyle("-fx-background-color: 	GREY;");
             	buttons[1][1].setStyle("-fx-background-color: 	GREY;");                
             	buttons[2][0].setStyle("-fx-background-color: 	GREY;");
          
	            return true; // Check other diagonal
	        }
	        
	        return false;
	    }

	  //_______________________________________________________________________________________________________________________
	  
	  
	  private void whoISWinner(char winner) {
		  updateScore(winner);
  	    Alert alertt = new Alert(Alert.AlertType.INFORMATION);
        alertt.setTitle("Congratulations");
       // alert.setHeaderText(null);
        if (winner == PlayerChar)
             alertt.setContentText("Player "+name.getText()+" is win");
        else 
        	alertt.setContentText("Cmputer is win ");
        
        alertt.showAndWait();

	        
	        //highlightWinningButtons(row, col);
	        disableButtons();
	        //resultTextArea.appendText("Game Over. Player " + winner + " wins!\n");

	    }

	    //_______________________________________________________________________________________________________________
	    
	    private void disableButtons() {
	        for (Button[] row : buttons) {
	            for (Button button : row) {
	                button.setDisable(true); // Disable all buttons to prevent further moves
	            }
	        }
	    }
	    
	    //__________________________________________________________________________________________________________________
	    
	    private boolean isBoardFull() {
	        for (int i = 0; i < 3; i++) {
	            for (int j = 0; j < 3; j++) {
	                if (board[i][j] == ' ') {
	                    return false; // There is an empty space, the board is not full
	                }
	            }
	        }
	        return true; // Board is full, and no one has won
	    }
	    
	    

	    //______________________________________________________________________________________________________________
	    
	    
	    private void showGameCompleteAlert() {
	        Alert alert = new Alert(Alert.AlertType.INFORMATION);
	        alert.setTitle("Game Complete");

	        if (playerScore > computerScore) {
	            alert.setHeaderText("Player "+ name.getText()+" wins!");
	        } else if (computerScore > playerScore) {
	            alert.setHeaderText("Computer is  wins!");
	        } else {
	            alert.setHeaderText("It's a draw!");
	        }

	        alert.setContentText("Scores:\nPlayer "+ name.getText()+ " : " +playerScore + "\n Computer : " + computerScore);
	        alert.showAndWait();
	        //primaryStage.close();
	    }

	    //__________________________________________________________________________________________________________________
	    
	    private void updateScore(char winner) {
	    	
	        if (winner == PlayerChar) {
	            playerScore++;
	        } else {
	            computerScore++;
	        }
	        updateScoreTextArea();
	    }
	    
	    //________________________________________________________________________________________________________________
	    

	    private void updateScoreTextArea() {
	    	resultTextArea.setText("\t\t\t\tScores:\n Player  ("+ name.getText() +" )  : " + playerScore + "\n\nPlayer  ( Computer ) : " + computerScore+"\n\n");
	    }
	    
	    
	    
	    private void handleOkButton() {
	        if (!name.getText().trim().isEmpty()) {
	        	resultTextArea.appendText("\t\t\t\tScores : \nPlayer  ( "+name.getText() +" ): 0  \n\n Player  (Computer) : 0  ");
	            option.setVisible(false);
	            name.setDisable(true);
	            //name.setText(null);
	            playerCharBox.setDisable(true);
	            playerComboBox.setDisable(true);
	            ok.setDisable(true);

	        } else {
	            // Show an alert or message indicating that the name is required
	            Alert alert = new Alert(Alert.AlertType.WARNING);
	            alert.setTitle("Name Required");
	            alert.setHeaderText(null);
	            alert.setContentText("Please enter your name before starting the game.");
	            alert.showAndWait();
	        }
	    }
		
		//____________________________________________________________________________________________________________
		
		
		  private int askRoundsToPlay() {  // this method to determinate the Round number 
		    	
		        TextInputDialog dialog = new TextInputDialog();
		        dialog.setTitle("Number of Rounds");
		        dialog.setHeaderText("Enter the number of rounds you want to play:");
		        dialog.setContentText("Rounds:");

		        Optional<String> result = dialog.showAndWait();
		        return result.map(Integer::parseInt).orElse(1);
		    }
	  
	
	public static void main(String[] args) {
		launch(args);
	}
	
}

/*
 * private void showEndGameConfirmation() {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Game Over");
        confirmation.setHeaderText(null);
        confirmation.setContentText("One player has won more than half of the rounds. Do you want to continue or end the game?");

        ButtonType continueButton = new ButtonType("Continue");
        ButtonType endButton = new ButtonType("End Game");

        confirmation.getButtonTypes().setAll(continueButton, endButton);

        confirmation.showAndWait().ifPresent(response -> {
            if (response == continueButton) {
                currentRound++;
                startNewRound();
            } else if (response == endButton) {
                showGameCompleteAlert();
            }
        });
    }
 */
