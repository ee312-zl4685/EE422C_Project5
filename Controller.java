package assignment5;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;


public class Controller {
	
	private static String myPackage;
	static {
		 myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}
	
	ObservableList<String> critterNameList = 
			(ObservableList<String>) FXCollections.observableArrayList("Algae","Craig","Critter1","Critter2","Critter3","Critter4");
	
    @FXML
    private ChoiceBox<String> cbMakeCritter;
    @FXML
    private TextField tfMakeCritter;
    @FXML
    private Button bMakeCritter;

    @FXML
    private TextField tfSteps;
    @FXML
    private Button bSteps;

    @FXML
    private ChoiceBox<String> cbRunStats;
    @FXML
    private Button bRunStats;

    @FXML
    private Slider SpeedSlider;
    @FXML
    private TextField Speed;
    @FXML
    private TextField tfTime;
    
    @FXML
    private TextArea RunStatsConsole;

    @FXML
    private void initialize(){
    	cbMakeCritter.setValue("Algae");
    	cbMakeCritter.setItems(critterNameList);
    	cbRunStats.setValue("Algae");
    	cbRunStats.setItems(critterNameList);
    	
    }

    @FXML
    private void goMakeCritter() throws IOException{
        String StrQuantity = tfMakeCritter.getText();
        String type = cbMakeCritter.getValue();
        Integer Quantity = 1;
        if(StrQuantity.length() > 0) {
            Quantity = Integer.parseInt(StrQuantity);
        }

        while(Quantity > 0) {
            try {
                Critter.makeCritter(type);
                Quantity --;
            } catch (InvalidCritterException e) {
                e.printStackTrace();
            }
        } 
    }
    
    @FXML
    private void timeStep() throws IOException{
        String StrQuantity = tfSteps.getText();
        Integer Quantity =1;
        if(StrQuantity.length() > 0){
            try {
                Quantity = Integer.parseInt(StrQuantity);
            } catch (Exception e){
               tfSteps.setText("INTEGER!");
            }
        }

        while(Quantity > 0){
            Critter.worldTimeStep();
            Quantity -= 1;
        }
    }

    
    @FXML
    private void goRunStats() throws IOException{
    	String type = cbRunStats.getValue();
    	String consoleMessage = "";
    	Class<?> class_name = null;

		try {
		    List<Critter> t = Critter.getInstances(type);
		    class_name = Class.forName(myPackage+"." + type);
		    Method runstats = class_name.getMethod("runStats", List.class);
		    consoleMessage = (String) runstats.invoke(class_name, t);

		}
		catch (Error e) {
		    e.printStackTrace();
		}

		catch (Exception e) {
		    e.printStackTrace();
		}
		
    	RunStatsConsole.setText(consoleMessage);
    	
    }


}