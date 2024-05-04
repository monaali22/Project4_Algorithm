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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TowPlayer extends Application{

	  private char currentPlayer = 'X';
	    private char[][] board = new char[3][3];
	    private Button[][] buttons = new Button[3][3];
	    private int roundsToPlay;
	    private int currentRound = 1;
	    private int scoreX = 0;
	    private int scoreO = 0;
	    private Stage primaryStage;
	    private TextArea resultTextArea = new TextArea();
	    private ComboBox<String> startPlayerComboBox = new ComboBox<>();
	    private TextField playerXNameField = new TextField();
	    private TextField playerONameField = new TextField();
	    private Text text = new Text();
	    private VBox option = new VBox(20);
	    private Button ok = new Button("OK");
	    private int yse = 0;
	    private Label  Lname = new Label ();
	    private Label  LnameO = new Label ();
	    private Label Loption = new Label("Start player");
	    private Label gName = new Label("PLYE WITH FRIND");

		VBox game = new VBox (10);
		VBox im = new VBox(10);


	    @Override
	    public void start(Stage primaryStage) throws Exception {
	        resultTextArea.setFont(Font.font("Time New Roman", FontWeight.BOLD, FontPosture.ITALIC, 20));
	        text.setFont(Font.font("Time New Roman", FontWeight.BOLD, FontPosture.ITALIC, 20));
	        resultTextArea.setPrefWidth(500);
	        resultTextArea.setMaxWidth(500);
	        resultTextArea.setPrefHeight(300);
	        this.primaryStage = primaryStage;

	        Image image1 = new Image("C:\\Users\\Lenovo\\Downloads\\TT.png"); // add image to the scene 
			ImageView imageView = new ImageView(image1);
			imageView.setFitWidth(200);
			imageView.setFitHeight(200);
			im.getChildren().add(gName);
			
			
			
	        startPlayerComboBox.getItems().addAll("Player X", "Player O");
	        startPlayerComboBox.setValue("Player X");

	        Lname.setText("Player X Name");
	        Lname.setFont(Font.font("Time New Roman", FontWeight.BOLD, FontPosture.ITALIC, 15));
	        gName.setFont(Font.font("Time New Roman", FontWeight.BOLD, FontPosture.ITALIC, 30));
	        gName.setStyle("-fx-text-fill:white");
	        Loption.setFont(Font.font("Time New Roman", FontWeight.BOLD, FontPosture.ITALIC, 15));
	        LnameO.setText("Player O Name");
	        LnameO.setFont(Font.font("Time New Roman", FontWeight.BOLD, FontPosture.ITALIC, 15));
	        
	        
	        
	        startPlayerComboBox.setOnAction(event -> {
	            // Handle ComboBox selection only if it's the initial choice
	                String selectedValue = startPlayerComboBox.getValue();
	                if ("Player X".equals(selectedValue)) 
	                	currentPlayer = 'X';
	                else 
	                	currentPlayer = 'O';
	                		                
	            
	        });

	        ok.setOnAction(e -> {
	            handleOkButton();
	        });

	        option.setAlignment(Pos.CENTER);
	        option.getChildren().addAll(Lname, playerXNameField, LnameO, playerONameField, ok);

	        roundsToPlay = askRoundsToPlay();

	        startNewRound();
	    }
	    
	    //__________________________________________________________________________________________________________________________________

	   
	    private void startNewRound() {
	        if (currentRound <= roundsToPlay) {
	            text.setText("Round " + currentRound + " / " + roundsToPlay);
	            GridPane gridPane = createGridPane();

	            // Check the selected player in the ComboBox and set currentPlayer accordingly
	            String startPlayer = startPlayerComboBox.getValue();
	            currentPlayer = (startPlayer.equals("Player X")) ? 'X' : 'O';

	            initializeBoard();

	            Scene scene = new Scene(createLayout(gridPane), 1000, 750);
	            

	            primaryStage.setTitle("Tic Tac Toe");
	            primaryStage.setScene(scene);
	            primaryStage.show();
	            //primaryStage.setFullScreen(true);
	            
	            
	            gridPane.setStyle("-fx-background-color:LIGHTSKYBLUE");

	            // Disable the ComboBox after the game starts
	            //startPlayerComboBox.setDisable(true);
	        } else {
	            showGameCompleteAlert();
	        }
	    }

	 
	    
	    //______________________________________________________________________________________________________________________

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

	   
	  //_______________________________________________________________________________________________________________________  
	    
	    private void onButtonClick(int row, int col) {
	        if (board[row][col] == ' ') {
	            buttons[row][col].setText(String.valueOf(currentPlayer));
	            board[row][col] = currentPlayer;

	            
	            if (checkWin()) {
	                announceWinner(currentPlayer, row, col);
	            } 
	            
	            else if (isBoardFull()) {
	                currentRound++;
	                startNewRound();
	            } 
	            
	            
	            else {
	            	if (currentPlayer == 'X') {
	            	    currentPlayer = 'O';
	            	} else {
	            	    currentPlayer = 'X';
	            	}	            }
	        }
	    }
	    
	  //________________________________________________________________________________________________________________________________  
	    
	    private void initializeBoard() {
	        for (int i = 0; i < 3; i++) {
	            for (int j = 0; j < 3; j++) {
	                board[i][j] = ' ';
	                buttons[i][j].setText("");
	                buttons[i][j].setStyle("-fx-background-color: white;");
	                buttons[i][j].setDisable(false);
	            }
	        }

	        
	        // Set the currentPlayer based on the selected player in the ComboBox
	        String selectedValue = startPlayerComboBox.getValue();
	        if (selectedValue.equals("Player X")) {
	            currentPlayer = 'X';
	        } else {
	            currentPlayer = 'O';
	        }
	    }
	    
	    
	    //____________________________________________________________________________________________________________________________

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


	    
	    //_____________________________________________________________________________________________________________________________

	    private void announceWinner(char winner, int row, int col) {
	    	   updateScore(winner);
	    	    Alert alert = new Alert(Alert.AlertType.INFORMATION);
	            alert.setTitle("Congratulations");
	           // alert.setHeaderText(null);
	            if (winner == 'X')
	                 alert.setContentText("Player "+playerXNameField.getText()+" is win");
	            else 
	            	alert.setContentText("Player "+playerONameField.getText()+" is win ");
	            
	            alert.showAndWait();	
	            
	           
	             disableButtons();
	    }
	    
	    //_______________________________________________________________________________________________________________________________

	    private void disableButtons() {
	        for (Button[] row : buttons) {
	            for (Button button : row) {
	                button.setDisable(true);
	            }
	        }
	    }

	    //______________________________________________________________________________________________________________________________
	    private boolean isBoardFull() {
	        for (int i = 0; i < 3; i++) {
	            for (int j = 0; j < 3; j++) {
	                if (board[i][j] == ' ') {
	                    return false;
	                }
	            }
	        }
	        return true;
	    }
	    
	    //______________________________________________________________________________________________________________________________

	    private void showGameCompleteAlert() {
	        Alert alert = new Alert(Alert.AlertType.INFORMATION);
	        alert.setTitle("Game Complete");

	        if (scoreX > scoreO) {
	            alert.setHeaderText(playerXNameField.getText() + " wins!");
	        } else if (scoreO > scoreX) {
	            alert.setHeaderText(playerONameField.getText() + " wins!");
	        } else {
	            alert.setHeaderText("It's a draw!");
	        }

	        alert.setContentText("Scores:\n" + playerXNameField.getText() + ": " + scoreX + "\n" + playerONameField.getText()
	                + ": " + scoreO);
	        alert.showAndWait();
	        //primaryStage.close();
	    }
	    
	    //__________________________________________________________________________________________________________________

	    private void updateScore(char winner) {
	        if (winner == 'X') {
	            scoreX++;
	        } else {
	            scoreO++;
	        }
	        updateScoreTextArea();
	    }

	    private void updateScoreTextArea() {
	        resultTextArea.setText("\t\t\t\tScores:\n Player X (" + playerXNameField.getText() + ")  : " + scoreX
	                + "\n\nPlayer O (" + playerONameField.getText() + ") : " + scoreO + "\n\n");
	    }
	    
	    //_________________________________________________________________________________________________________________________________
	    
	    private void handleOkButton() {
	        if (!playerXNameField.getText().trim().isEmpty() && !playerONameField.getText().trim().isEmpty()) {
	            resultTextArea.appendText("\t\t\t\tScores : \nPlayer X (" + playerXNameField.getText() + "): 0  \n\n Player O ("
	                    + playerONameField.getText() + "): 0  ");
	            
	            
	            option.setVisible(false);
	            playerXNameField.setDisable(true);
	            playerONameField.setDisable(true);

	            //name.setText(null);
	            startPlayerComboBox.setDisable(true);
	            //ok.setDisable(true);
	        } 
	        else {
	            Alert alert = new Alert(Alert.AlertType.WARNING);
	            alert.setTitle("Names Required");
	            alert.setHeaderText(null);
	            alert.setContentText("Please enter names for both Player X and Player O before starting the game.");
	            alert.showAndWait();
	        }
	    }
	    
	    //___________________________________________________________________________________________________________________________________

	    private int askRoundsToPlay() {
	        TextInputDialog dialog = new TextInputDialog();
	        dialog.setTitle("Number of Rounds");
	        dialog.setHeaderText("Enter the number of rounds you want to play:");
	        dialog.setContentText("Rounds:");

	        Optional<String> result = dialog.showAndWait();
	        return result.map(Integer::parseInt).orElse(1);
	    }

	    
	    
	    
	    
	    

	    private BorderPane createLayout(GridPane gridPane) {
	        BorderPane layout = new BorderPane();
	        VBox nameBox = new VBox(20);
	        HBox combox = new HBox(20);

	        nameBox.setAlignment(Pos.CENTER);
	        Lname.setText("Player X Name");
	        Lname.setFont(Font.font("Time New Roman", FontWeight.BOLD, FontPosture.ITALIC, 15));

	        LnameO.setText("Player O Name");
	        LnameO.setFont(Font.font("Time New Roman", FontWeight.BOLD, FontPosture.ITALIC, 15));

	        startPlayerComboBox.setPrefWidth(150);

	        nameBox.getChildren().addAll(Lname, playerXNameField, LnameO, playerONameField);
	        combox.getChildren().addAll(Loption, startPlayerComboBox , ok);
	        option.setAlignment(Pos.CENTER);
	        option.getChildren().addAll(nameBox, combox);
	        //game.getChildren().addAll(im , option);

	        Button nextRoundButton = new Button("Next Round");
	        nextRoundButton.setPrefWidth(150);
	        nextRoundButton.setFont(Font.font("Time New Roman", FontWeight.BOLD, 15));
	        
	        Button game = new Button("Next Game");
	        game.setPrefWidth(150);
	        game.setFont(Font.font("Time New Roman", FontWeight.BOLD, 15));
	        game.setOnAction(e->{
	        	 currentRound = 1;
	        	 scoreX = 0;
	        	 scoreO = 0;
		         resultTextArea.clear();
		         resultTextArea.appendText("\t\t\t\tScores : \nPlayer  ( "+playerXNameField.getText() +" ): 0  \n\n Player "+playerONameField.getText()+" : 0  ");
		          startNewRound();
	        });
	        
	        
	        Button Exit = new Button("EXIT");
	        Exit.setPrefWidth(100);
	        Exit.setFont(Font.font("Time New Roman", FontWeight.BOLD, 15));
	        Exit.setOnAction(E->{
	        	primaryStage.close();
	        });
	        
	        HBox bb = new HBox(20);
	        bb.getChildren().addAll(nextRoundButton , Exit ,game );
	        layout.setStyle("-fx-background-color:	LIGHTSKYBLUE");

	        VBox vbox = new VBox(10);
	        vbox.setAlignment(Pos.CENTER);
	        vbox.getChildren().addAll(text, gridPane, resultTextArea , bb);

	        layout.setCenter(vbox);
            layout.setLeft(option);
	        //layout.setBottom(bb);    
	        

	        layout.setPadding(new Insets(5, 5, 5, 5));
	        layout.setTop(im);

	        nextRoundButton.setOnAction(e -> {
	            currentRound++;
	            startNewRound();
	        });

	        return layout;
	    }
	    public static void main(String[] args) {
	        launch(args);
	    }
	

}
