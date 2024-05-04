package application;
	
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.*;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Button;
import javafx.scene.control.*;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Main extends Application {
	int numberOfRounds;
	Rounds Round ;
	RandomGame randomGame = new RandomGame();
	TextField text ;
	@Override
	public void start(Stage primaryStage) {
		Scene scene = new Scene (firstScreen () , 900 , 700);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Tic Tac Toe");
		primaryStage.show();
	
		TextField Rname ;
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	
	public Pane firstScreen () {
		Stage s = new Stage ();
		BorderPane pane = new BorderPane();
		Label T = new Label ();
		 text = new TextField();
		Label roundText = new Label ();
		Image image1 = new Image("C:\\Users\\Lenovo\\Downloads\\XO.png"); // add image to the scene 
		ImageView imageView = new ImageView(image1);
	//____________________________________________________________________________________
		Label RLabel = new Label ();
		
		
		
		T.setText("\t\t\t\t\tTic Tac Toe");
		T.setFont(Font.font("Time New Roman" , FontWeight.BOLD , FontPosture.ITALIC ,30)); // set font to the label
		T.setStyle("-fx-text-fill:RED");// set colour to the label font 
		
		roundText.setText("Please enter number of rounds");
		roundText.setFont(Font.font("Time New Roman" , FontWeight.BOLD , FontPosture.ITALIC ,17)); // set font to the label
		roundText.setStyle("-fx-text-fill:RED");// set colour to the label font 
		
		

		 Label label = new Label();
		 label.setText("Select the way you want to play : ");
		 label.setFont(Font.font("Time New Roman" , FontWeight.BOLD , FontPosture.ITALIC ,20));
		 label.setStyle("-fx-text-fill:BLUE");
		 
			
		 
	     RadioButton r1 = new RadioButton("Random moves");
	     RadioButton r2 = new RadioButton("Two human players to play against each other.");
	     RadioButton r3 = new RadioButton("Play with computer");
	     Button Start = new Button ("START GAME");
	     Start.setPrefWidth(200);
	     Start.setFont(Font.font("Time New Roman" , FontWeight.BOLD , FontPosture.ITALIC ,15));
	     
	     
	     ToggleGroup group = new ToggleGroup();
	     r1.setToggleGroup(group);
	     r2.setToggleGroup(group);
	     r3.setToggleGroup(group);
	     r1.setFont(Font.font("Time New Roman" , FontWeight.BOLD , FontPosture.ITALIC ,20));
	     r2.setFont(Font.font("Time New Roman" , FontWeight.BOLD , FontPosture.ITALIC ,20));
	     r3.setFont(Font.font("Time New Roman" , FontWeight.BOLD , FontPosture.ITALIC ,20));

	     
	     
	     
	     
	     HBox box1 = new HBox(20);
	     box1.setAlignment(Pos.CENTER);
	     box1.getChildren().addAll(Start );
	     
	     VBox vBox = new VBox(20);
	     vBox.setAlignment(Pos.CENTER);
	     //vBox.getChildren().addAll(r1 , r2 , r3 , box1 );
	     
	     label.setTranslateX(80);
	     label.setTranslateY(20);
	     r1.setTranslateX(80);
	     r1.setTranslateY(120);
	     r2.setTranslateX(80);
	     r2.setTranslateY(220);
	     r3.setTranslateX(80);
	     r3.setTranslateY(320);
	     
	     
	     Group root = new Group();
	     
	        root.getChildren().add(label);
	        root.getChildren().add(r1);
	        root.getChildren().add(r2);
	        root.getChildren().add(r3);
	       
	     
	     pane.setPadding(new Insets (5 ,5 ,70,5));
	     pane.setTop(T);
		 pane.setStyle("-fx-background-color:LIGHTSKYBLUE");
		pane.setLeft(root);
		pane.setBottom(box1);
		pane.setRight(imageView);
		  
	     
		Start.setOnAction(e -> {
            RadioButton selectedRadioButton = (RadioButton) group.getSelectedToggle();
            String selectedMode = selectedRadioButton.getText();

            try {
                handleGameStart(selectedMode);
            } catch (NumberFormatException ex) {
                System.out.println("Invalid number of rounds.");
            }
            r1.setSelected(false);
            r2.setSelected(false); 
            r3.setSelected(false);
            
        });

        return pane;
    }

	private void handleGameStart(String selectedMode) {
		

		if ("Random moves".equals(selectedMode)) {
		    // Assuming RandomGame is a subclass of Application
		    try {
		        RandomGame randomGame = new RandomGame();
		        randomGame.start(new Stage());
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		}

	       
	    
	else if ("Two human players to play against each other.".equals(selectedMode)) {
		 // Assuming RandomGame is a subclass of Application
	    try {
	        TowPlayer two = new TowPlayer();
	        two.start(new Stage());
	    } catch (Exception e) {
	        e.printStackTrace();
	    }	        
		
	    } else if ("Play with computer".equals(selectedMode)) {
	    	try {
		        Minimax min = new Minimax();
		        min.start(new Stage());
		    } catch (Exception e) {
		        e.printStackTrace();
		    }	       
	    	
	    } else {
	        System.out.println("Invalid selection or empty text field.");
	    }
	}

}
