package application;

import java.util.Optional;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.application.Platform;

public class Minimax extends Application{

	 private static final int SIZE = 3;
	    private static  String PLAYER = "X";   // player
	    private static  String PLAYER_com = "O";  // computer 
	    private String currentPlayer ;            // start player 
	    
	    private Button[][] board = new Button[SIZE][SIZE];  
	    private ComboBox<String> startingPlayerComboBox;   // {Player , Computer } 
	    private ComboBox<String> PlayerChr = new ComboBox<>();  //{X , O}

	    private int roundsToPlay;
	    private int currentRound = 1;
	    private int scoreForPlayer = 0;
	    private int scoreForComputer = 0;
	    private Stage primaryStage;
	    private TextArea resultTextArea = new TextArea();
	    private TextArea posibility = new TextArea();

	   // private boolean initialChoiceMade = false;
	    private Text text = new Text();             // for Round 
	    private TextField name = new TextField();    // Player Name 
	    private Label Lname = new Label();
	    private Label Loption = new Label();
	    private VBox option = new VBox (30);
	    private Button ok = new Button ("OK");
	    int yse = 0 ;
	    
	    
	    
     
    
    
    
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;

    	resultTextArea.setFont(Font.font("Time New Roman" , FontWeight.BOLD , FontPosture.ITALIC ,20));
		resultTextArea.setPrefWidth(500);
		resultTextArea.setMaxWidth(500);
		resultTextArea.setPrefHeight(300);
		
    	posibility.setFont(Font.font("Time New Roman" , FontWeight.BOLD , FontPosture.ITALIC ,15));
    	posibility.setPrefWidth(400);
    	posibility.setPrefHeight(300);
    	posibility.setMaxWidth(500);
    	posibility.setMaxHeight(300);

		
		text.setFont(Font.font("Time New Roman" , FontWeight.BOLD , FontPosture.ITALIC ,20));

        startingPlayerComboBox = new ComboBox<>();
        startingPlayerComboBox.getItems().addAll("Player", "Computer");
        startingPlayerComboBox.setValue("Player"); // Default value
        
        startingPlayerComboBox.setOnAction(e->{
        	String selectedPlayer = startingPlayerComboBox.getValue();
        	if ("Player".equals(selectedPlayer)) {
        	    currentPlayer = PLAYER;
        	} else {
        	    currentPlayer = PLAYER_com;
        	    makeComputerMove();
        	}

        });
        
        
        PlayerChr.getItems().addAll("X","O");
        PlayerChr.setValue("X");
        PlayerChr.setOnAction(e->{
        	if ("O".equals(PlayerChr.getValue())) {
        		PLAYER = "O";
        		PLAYER_com = "X";
        		currentPlayer = "O";
        		
        		
        		
        	}
        });
        
        
    	
        GridPane gridPane = createGridPane();
        //Label statusLabel = new Label("Player X's turn");
     
        Lname.setText("Enter Your Name: ");
        Lname.setFont(Font.font("Time New Roman", FontWeight.BOLD, FontPosture.ITALIC, 15));
        Lname.setStyle("-fx-text-fill:RED");

        
        ok.setOnAction(e -> {
            handleOkButton();
        });


        option.setAlignment(Pos.CENTER);
        option.getChildren().addAll( Lname, name , ok );
        roundsToPlay = askRoundsToPlay();//round.RoundNum;
              
        
        startNewRound();
    }
	
	
	
	private void startNewRound() {     
        if (currentRound <= roundsToPlay) {
        	
        	text.setText("Round " + currentRound+" / "+roundsToPlay);
        	
            GridPane gridPane = createGridPane();
            
            initializeBoard();
            
            if ("Computer".equals(startingPlayerComboBox.getValue())) {
                currentPlayer = PLAYER_com;
                makeComputerMove();
            }

            Scene scene = new Scene(createLayout(gridPane), 1000, 750);
            
            primaryStage.setTitle("Tic Tac Toe - Random Moves");
            primaryStage.setScene(scene);
            primaryStage.show();
            primaryStage.setFullScreen(true);

	        gridPane.setStyle("-fx-background-color:LIGHTSKYBLUE");

            // Disable the ComboBox after the game has started
            //playerComboBox.setDisable(true);
        } 
        
        else {
        	
            showGameCompleteAlert();
        }
    }
	
	
	private void initializeBoard() {
       for (int i = 0; i < 3; i++) {
           for (int j = 0; j < 3; j++) {
           	board[i][j].setText(""); // Clear the text on buttons
           	board[i][j].setStyle("-fx-background-color: white;"); // Reset button color
           	board[i][j].setDisable(false); // Enable buttons for the new round
           }
       }
       currentPlayer = PLAYER;
   }
	
	

	//______________________________________________________________________________________________________________________________
	
  


   private void onButtonClick(int row, int col) {
       if (board[row][col].getText().equals("") && !isGameFinish()) {
       	
       	
       	board[row][col].setText(currentPlayer);

           if (isGameFinish()) {
               announceWinner();
           } 
           
           else {
               switchPlayer();
               
               makeComputerMove();
           }
       }
   }

   private boolean isGameFinish() {
       return hasPlayerWon(PLAYER) || hasPlayerWon(PLAYER_com) || isBoardFull();
   }
   
   

   private boolean hasPlayerWon(String player) {
       // Check rows
       for (int i = 0; i < SIZE; i++) {
           if (board[i][0].getText().equals(player) &&  board[i][1].getText().equals(player) && board[i][2].getText().equals(player)) {
           	
   	
               return true;
           }
       }

       // Check columns
       for (int i = 0; i < SIZE; i++) {
           if (board[0][i].getText().equals(player) && board[1][i].getText().equals(player) && board[2][i].getText().equals(player)) {
           	
              return true;
           }
       }

       // Check diagonals
       if (board[0][0].getText().equals(player) && board[1][1].getText().equals(player) && board[2][2].getText().equals(player)) {
       	       	
           return true;
       }

       if (board[0][2].getText().equals(player) && board[1][1].getText().equals(player) &&  board[2][0].getText().equals(player)) {
    
           return true;
       }

       return false;
   }
   
   

   private boolean isBoardFull() {
       for (int i = 0; i < SIZE; i++) {
           for (int j = 0; j < SIZE; j++) {
               if (board[i][j].getText().equals("")) {
                   return false;
               }
           }
       }
       return true;
   }

  
   
   

   private void switchPlayer() {
   	
       if (currentPlayer.equals(PLAYER)) {
       	
           currentPlayer = PLAYER_com;
       } 
       
       else {
           currentPlayer = PLAYER;
       }
   }


   private void makeComputerMove() {
   	
   	// checks if the game is not finished and if the current player is the computer 
       if (!isGameFinish() && currentPlayer.equals(PLAYER_com)) { 
       	
           int[] bestMove = AIBestMove();
           int row = bestMove[0];
           int col = bestMove[1];
               
           board[row][col].fire();
         

           //board[bestMove[0]][bestMove[1]].fire();

           if (hasPlayerWon(PLAYER_com)) {
           	
               scoreForComputer++;
               resultTextArea.clear();
               posibility.clear();
   	    	   resultTextArea.setText("\t\t\t\tScores:\n Player  ("+ name.getText() +" )  : " + scoreForPlayer + "\n\nPlayer  ( Computer ) : " + scoreForComputer+"\n\n");
   	    	   
               highlightWinningButtons(PLAYER_com);

           }
       }
   }
   
   
   private int[] AIBestMove() { // 
		StringBuilder possibility = new StringBuilder();

     	//array to store the row and column of the best move
       int[] bestMove = { -1, -1 };
       
       int bestScore = Integer.MIN_VALUE;

       for (int i = 0; i < SIZE; i++) {
       	
           for (int j = 0; j < SIZE; j++) {
           	
           	
               if (board[i][j].getText().equals("")) {
                   board[i][j].setText(PLAYER_com);
                  
                   
                   int score = minimax(false);
                  
                 
                   board[i][j].setText("");
                   String s="";
                   if (score == 1   ) {
                	   System.out.print("  Win ");
                   	s="Win";
                   }
                   else if (score == -1   ) {
                	   System.out.print("  LOSS");
                   	s="Lose";
                   }
                   else {
                	   System.out.print("  Drow ");
                   	s="Draw";
                   }
                   
                   System.out.print("  " + i +" , "+ j);
                   posibility.setText(possibility.toString());
                   
                   if (score > bestScore) {
                      	s="Win";

                       bestScore = score;
                       bestMove[0] = i;
                       bestMove[1] = j;
                   }
                   
                   possibility.append("Scour " + i + ", " + j + ": " + s+"\t\t");

               }
               
                 
           }
           possibility.append("\n");
           System.out.println();
       }

       return bestMove;
   }

   private int minimax(boolean isMaximizing) {
   	
   	// base case
       if (isGameFinish()) {
           if (hasPlayerWon(PLAYER)) {
               return -1;
           }else if (hasPlayerWon(PLAYER_com)) {
               return 1;
           } 
           else {
               return 0;
           }
       }
       
       
       
       int bestScore;
       
       if (isMaximizing) {   // Maximising player (Computer)
       	  	
            bestScore = Integer.MIN_VALUE;
           
           for (int i = 0; i < SIZE; i++) {
               for (int j = 0; j < SIZE; j++) {
                   if (board[i][j].getText().equals("")) {
                       board[i][j].setText(PLAYER_com);
                       bestScore = Math.max(bestScore, minimax(false));
                       
                       board[i][j].setText("");
                   }
               }
           }
       } 
       
       
       
       
       else {      // Minimising player ( player ) 
       	
            bestScore = Integer.MAX_VALUE;
           
           for (int i = 0; i < SIZE; i++) {
               for (int j = 0; j < SIZE; j++) {
               	
                   if (board[i][j].getText().equals("")) {
                       board[i][j].setText(PLAYER);
                       
                       bestScore = Math.min(bestScore, minimax(true));
                       board[i][j].setText("");
                   }
               }
           }
       }
       
       return bestScore;

   }

   
 
   private void announceWinner() {
       String winner = currentPlayer.equals(PLAYER) ? PLAYER_com : PLAYER;
       if (hasPlayerWon(winner)) {
           System.out.println("Player " + winner + " wins!");
           
       } else {
           System.out.println("It's a draw!");
       }
   }
   
  
   private int askRoundsToPlay() {  // this method to determinate the Round number 
   	
       TextInputDialog dialog = new TextInputDialog();
       dialog.setTitle("Number of Rounds");
       dialog.setHeaderText("Enter the number of rounds you want to play:");
       dialog.setContentText("Rounds:");

       Optional<String> result = dialog.showAndWait();
       return result.map(Integer::parseInt).orElse(1);
   }
   
   private void handleOkButton() {
       if (!name.getText().trim().isEmpty()) {
       	resultTextArea.appendText("\t\t\t\tScores : \nPlayer  ( "+name.getText() +" ): 0  \n\n Player  (Computer) : 0  ");
           option.setVisible(false);
           
           name.setDisable(true);
           //name.setText(null);
           PlayerChr.setDisable(true);
           startingPlayerComboBox.setDisable(true);
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
   
   
   private void showGameCompleteAlert() {
       Alert alert = new Alert(Alert.AlertType.INFORMATION);
       alert.setTitle("Game Complete");

       if (scoreForPlayer > scoreForComputer) {
           alert.setHeaderText("Player "+ name.getText() + " wins!");
       } else if (scoreForComputer > scoreForPlayer) {
           alert.setHeaderText(" Computer is  wins!");
       } else {
           alert.setHeaderText("It's a draw!");
       }

       alert.setContentText("Scores:\nPlayer "+name.getText() +" : " + scoreForPlayer + "\nPlayer computer : " + scoreForComputer);
       alert.showAndWait();
       //primaryStage.close();
   }
   private void highlightWinningButtons(String player) {
       // Check rows
       for (int i = 0; i < SIZE; i++) {
           if (board[i][0].getText().equals(player) && board[i][1].getText().equals(player) && board[i][2].getText().equals(player)) {
               highlightButton(board[i][0]);
               highlightButton(board[i][1]);
               highlightButton(board[i][2]);
               return;
           }
       }

       // Check columns
       for (int i = 0; i < SIZE; i++) {
           if (board[0][i].getText().equals(player) && board[1][i].getText().equals(player) && board[2][i].getText().equals(player)) {
               highlightButton(board[0][i]);
               highlightButton(board[1][i]);
               highlightButton(board[2][i]);
               return;
           }
       }

       // Check diagonals
       if (board[0][0].getText().equals(player) && board[1][1].getText().equals(player) && board[2][2].getText().equals(player)) {
           highlightButton(board[0][0]);
           highlightButton(board[1][1]);
           highlightButton(board[2][2]);
           return;
       }

       if (board[0][2].getText().equals(player) && board[1][1].getText().equals(player) && board[2][0].getText().equals(player)) {
           highlightButton(board[0][2]);
           highlightButton(board[1][1]);
           highlightButton(board[2][0]);
       }
   }

   private void highlightButton(Button button) {
       button.setStyle("-fx-background-color: GREY; -fx-border-color: black;");
   }
   
   //____________________________________________________________________________________________________________________________________
     // interface method : -
   
   private BorderPane createLayout(GridPane gridPane) {
		 
       BorderPane layout = new BorderPane();
       HBox nameBox = new HBox (20);
       HBox combox = new HBox (20);
       //HBox All = new HBox (20);


       nameBox.setAlignment(Pos.CENTER);
       Lname.setText("Player Name");
       Lname.setFont(Font.font("Time New Roman" , FontWeight.BOLD , FontPosture.ITALIC ,15));
       Loption.setText("Start player");
       Loption.setFont(Font.font("Time New Roman" , FontWeight.BOLD , FontPosture.ITALIC ,15));
       Loption.setStyle("-fx-text-fill:RED");
       startingPlayerComboBox.setPrefWidth(150);
       
       nameBox.getChildren().addAll(Lname , name , ok);    
       combox.getChildren().addAll(Loption , startingPlayerComboBox , PlayerChr );
       option.setAlignment(Pos.CENTER);
       option.getChildren().addAll(nameBox , combox  );
       //layout.setAlignment(Pos.CENTER);
       
       Button nextRoundButton = new Button("Next Round");
       nextRoundButton.setPrefWidth(150);
       nextRoundButton.setFont(Font.font("Time New Roman" , FontWeight.BOLD  ,20));
       
       Button Exit = new Button("Exit");
       Exit.setPrefWidth(100);
       Exit.setFont(Font.font("Time New Roman" , FontWeight.BOLD  ,20));
       Exit.setOnAction(e->{
       	primaryStage.close();
       });
       
       Button NewGame = new Button("New Game ");
       NewGame.setPrefWidth(150);
       NewGame.setFont(Font.font("Time New Roman" , FontWeight.BOLD  ,20));
       NewGame.setOnAction(e->{
       	currentRound = 1;
	          scoreForComputer= 0;
	          scoreForPlayer = 0;
	          resultTextArea.clear();
	          resultTextArea.appendText("\t\t\t\tScores : \nPlayer  ( "+name.getText() +" ): 0  \n\n Player  (Computer) : 0  ");
	          startNewRound();
       });
       
       HBox buttonBox = new HBox(20);
       buttonBox.setAlignment(Pos.CENTER);
       buttonBox.getChildren().addAll(nextRoundButton , Exit , NewGame );
       
       
       layout.setStyle("-fx-background-color:LIGHTSKYBLUE");
       
       VBox vbox = new VBox(10);
       vbox.setAlignment(Pos.CENTER);
       vbox.getChildren().addAll(text , gridPane, resultTextArea , buttonBox );

       //layout.getChildren().add(vbox);
       layout.setCenter(vbox);
       
       
       	 layout.setLeft(option);
       	 //resultTextArea.appendText("\t\t\t\tScores :+ \nPlayer X "+name.getText() +" : 0  \n\n Player O (Computer) : 0  ");
       	 //option.setVisible(false);
       	layout.setRight(posibility);
       
      
       layout.setPadding(new Insets (5 ,5 ,5 ,5));

     
       nextRoundButton.setOnAction(e -> {
           currentRound++;
           startNewRound();
       });
       
       
       
       return layout;
   }
   
	private GridPane createGridPane() { // this method to do Button Pane :- 
       GridPane gridPane = new GridPane();
       gridPane.setAlignment(Pos.CENTER);
       gridPane.setHgap(10);
       gridPane.setVgap(10);

       for (int row = 0; row < SIZE; row++) {
           for (int col = 0; col < SIZE; col++) {
               Button button = createButton(row, col);
               board[row][col] = button;
               button.setMinSize(100, 100);
               button.setFont(Font.font("Time New Roman", FontWeight.BOLD, 40));
               gridPane.add(button, col, row);
           }
       }

       return gridPane;
   }

	
   private Button createButton(int row, int col) { // Button Action 
       Button button = new Button();
       button.setMinSize(100, 100);
       button.setOnAction(event -> onButtonClick(row, col));
       return button;
   }
   
	
	public static void main(String[] args) {
		launch(args);
	}

	/*
	private void showGameCompleteAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Complete");

        if (scoreForComputer > computerScore) {
            alert.setHeaderText("Player "+ name.getText()+" wins!");
        } else if (scoreForComputer > scoreForPlayer) {
            alert.setHeaderText("Computer is  wins!");
        } else {
            alert.setHeaderText("It's a draw!");
        }

        alert.setContentText("Scores:\nPlayer "+ name.getText()+ " : " +playerScore + "\n Computer : " + computerScore);
        alert.showAndWait();
        //primaryStage.close();
    }
    */
}
