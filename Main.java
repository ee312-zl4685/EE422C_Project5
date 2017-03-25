package assignment5;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		
		
		try {
			Parent root = FXMLLoader.load(getClass().getResource("FXML.fxml"));
		    
	        Scene scene = new Scene(root);
	    
	        primaryStage.setScene(scene);
	        primaryStage.show();
			// Paints the icons. Painter.paint();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}