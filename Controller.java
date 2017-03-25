package assignment5;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

public class Controller {

    @FXML
    private ChoiceBox<String> cbMakeCritter;
    @FXML
    private TextField tfMakeCritter;
    @FXML
    private Button bMakeCritter;

    @FXML
    private TextField RunStatsConsole;
    @FXML
    private TextField tfTime;

    @FXML
    private TextField tfSteps;
    @FXML
    private Button bRunStats;

    @FXML
    private Slider SpeedSlider;

    private void goMakeCritter() throws IOException{
        try {
            String str = tfSteps.getText();
            Critter.makeCritter(str);
            RunStatsConsole.setText(str);
        } catch (InvalidCritterException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private String handleMakeCritter() {
        return tfSteps.getText();
    }


}
