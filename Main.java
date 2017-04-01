package assignment5;
/* CRITTERS Main.java
 * EE422C Project 5 submission by
 * Zhaofeng Liang
 * zl4685
 * 16230
 * Zohaib Imam
 * szi58
 * 16230
 * Slip days used: 0
 *  https://github.com/ee312-zl4685/EE422C_Project5.git
 * Spring 2017
 */

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
	        primaryStage.setTitle("CritterSimulator Ver.1.0");
	        primaryStage.setScene(scene);
	        primaryStage.show();
			// Paints the icons. 
	        // Painter.paint();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}