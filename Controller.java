package assignment5;
/* CRITTERS Controller.java
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

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;
import assignment5.Critter.CritterShape;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.util.Duration;

import static assignment5.Params.*;

public class Controller {

	private static Double SIZE = 20.0;
	private static String myPackage;
	//private static Timeline timeline;
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}

	ObservableList<String> critterNameList = FXCollections.observableArrayList();

	@FXML
	private AnchorPane anchor;

	@FXML
	private SplitPane splitP;

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
	private GridPane gridpane;

	@FXML
	private RadioButton bAnime;
	
	@FXML
	private AnchorPane rightP;

	@FXML
	private void initialize() {
		getClassNames();		
		setSize();
		cbMakeCritter.setValue("Algae");
		cbMakeCritter.setItems(critterNameList);
		cbRunStats.setValue("Algae");
		cbRunStats.setItems(critterNameList);
		initializeGrid();
		initializeSlider();
	}
	
	
	
	@FXML
	private void initializeSlider() {
		SpeedSlider.setMin(1);
		SpeedSlider.setMin(0);
		SpeedSlider.setMax(100);
		SpeedSlider.setValue(1);
		SpeedSlider.setShowTickLabels(true);
		SpeedSlider.setShowTickMarks(true);
		SpeedSlider.setMajorTickUnit(50);
		SpeedSlider.setMinorTickCount(5);
		SpeedSlider.setBlockIncrement(10);
	}

	@FXML
	private void animationSpeed() {
		
		Speed.setText(Integer.toString(((int) SpeedSlider.getValue())));
	}
	
	@FXML
	private void checkAction(){
		if(bAnime.isSelected()){
			SpeedSlider.setDisable(true);
			cbMakeCritter.setDisable(true);
			bMakeCritter.setDisable(true);
			cbRunStats.setDisable(true);
			bRunStats.setDisable(true);
			tfSteps.setDisable(true);
			tfMakeCritter.setDisable(true);
			bSteps.setDisable(true);
			animationSpeed();
			animationAction();
		}
		else{
			SpeedSlider.setDisable(false);
			SpeedSlider.setDisable(false);
			cbMakeCritter.setDisable(false);
			bMakeCritter.setDisable(false);
			cbRunStats.setDisable(false);
			bRunStats.setDisable(false);
			tfSteps.setDisable(false);
			tfMakeCritter.setDisable(false);
			bSteps.setDisable(false);
		}
	}
	
	Timeline timeline = new Timeline();
	@FXML
	private void animationAction() {
		
		KeyFrame keyframe = new KeyFrame(Duration.seconds(0.4), ev->{
			int speed = Integer.parseInt(Speed.getText());
			while(speed != 0){
			if(!bAnime.isSelected()){
				timeline.stop();
				timeline.getKeyFrames().clear();
				Speed.clear();
				return;
	
			}
			try {
				timeStep();
			} catch (Exception e) {
				e.printStackTrace();
			}
			finally{
				speed--;
				
			}
			}
		});
		timeline.getKeyFrames().add(keyframe);
		timeline.setCycleCount(Animation.INDEFINITE);
		//timeline.play();
		timeline.playFromStart();
	}

	@FXML
	private void setSize() {
		if(world_height > 80 || world_width > 110){
			SIZE = 5.0;
		}
		else if((world_height > 50 && world_height <= 80) ||(world_height > 50 && world_height <= 80) || (world_width > 78 && world_width <= 110)){
			SIZE = 8.0;
		}
		else if((world_height > 30 && world_height <= 50) ||(world_height > 30 && world_height <= 50) || (world_width > 50 && world_width <= 78)){
			SIZE = 12.0;
		}
		else {
			SIZE = 20.0;
		}
	}

	private void initializeGrid() {

		int c = world_height;
		int r = world_width;
		int GAP = 2;
		gridpane.setVgap(GAP);
		gridpane.setHgap(GAP);
		gridpane.setGridLinesVisible(false);
		for (int i = 0; i < r; i++) {
			for (int j = 0; j < c; j++) {
				Rectangle rec = new Rectangle();
				rec.setHeight(SIZE);
				rec.setWidth(SIZE);
				rec.setFill(Color.WHITE);
				gridpane.add(rec, i, j);
			}
		}
	}

	private void displayGrid() {
		int c = world_height;
		int r = world_width;
		int GAP = 2;
		gridpane.setVgap(GAP);
		gridpane.setHgap(GAP);
		gridpane.setGridLinesVisible(false);

		ArrayList<Critter> critterList = new ArrayList<Critter>();
		critterList = (ArrayList<Critter>) Critter.getPop();
		Iterator<Critter> it = critterList.iterator();
		while (it.hasNext()) {
			Critter a = it.next();
			Color aFillColor = a.viewFillColor();
			Color aOutlineColor = a.viewOutlineColor();
			Shape shape = getShape(a.viewShape());
			shape.setFill(aFillColor);
			shape.setStroke(aOutlineColor);
			gridpane.add(shape, a.getX(), a.getY());
		}
	}

	private static final String path = "./src/assignment5";

	private void getClassNames() {
		File dir = new File(path);
		File[] files = dir.listFiles(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				if (name.endsWith(".java")) {
					return true;
				}
				return false;
			}
		});
		for (File f : files) {
			String name = f.getName().split(".java")[0];
			try {
				Critter.makeCritter(name);
				critterNameList.add(name);
			} catch (Exception e) {
			}
			Critter.getPop().clear();
		}

	}

	@FXML
	private void goMakeCritter() throws IOException {
		String StrQuantity = tfMakeCritter.getText();
		String type = cbMakeCritter.getValue();
		Integer Quantity = 1;
		if (StrQuantity.length() > 0) {
			Quantity = Integer.parseInt(StrQuantity);
		}

		while (Quantity > 0) {
			try {
				Critter.makeCritter(type);
				Quantity--;
			} catch (InvalidCritterException e) {
				e.printStackTrace();
			}
		}
		gridpane.getChildren().clear();
		initializeGrid();
		displayGrid();
		goRunStats();
	}

	private Shape getShape(CritterShape critterShape) {
		double size = SIZE - 2;
		switch (critterShape) {
		case CIRCLE: {
			Circle cir = new Circle();
			cir.setRadius((SIZE - 2) / 2);
			return cir;
		}
		case SQUARE: {
			Rectangle rect = new Rectangle();
			rect.setHeight(SIZE - 1);
			rect.setWidth(SIZE - 1);
			return rect;
		}
		case TRIANGLE: {
			Polygon triangle = new Polygon();
			triangle.getPoints().addAll(new Double[] { (SIZE - 2) / 2, 1.0, 1.0, SIZE - 2, SIZE - 2, SIZE - 2 });
			return triangle;
		}
		case STAR: {
			Polygon star = new Polygon();
			star.getPoints()
					.addAll(new Double[] { size / 2, 1.0, size * .65, size / 4, size - 1, size / 4, size * .70,
							size / 2, size - 1, size * .75, size * .65, size * .75, size / 2, size, size * .35,
							size * .75, 1.0, size * .75, size * .30, size / 2, 1.0, size / 4, size * .35, size / 4 });
			return star;
		}
		case DIAMOND: {
			Polygon diamond = new Polygon();
			diamond.getPoints().addAll(new Double[] { 1.0, (SIZE - 1) / 2, (SIZE - 1) / 2, 1.0, SIZE - 2,
					(SIZE - 1) / 2, (SIZE - 1) / 2, SIZE - 2 });
			return diamond;
		}
		default: {
			return new Circle();
		}
		}

	}

	@FXML
	private void timeStep() throws IOException {
		String StrQuantity = tfSteps.getText();
		Integer Quantity = 1;
		if (StrQuantity.length() > 0) {
			try {
				Quantity = Integer.parseInt(StrQuantity);
			} catch (Exception e) {
				tfSteps.setText("INTEGER!");
			}
		}

		while (Quantity > 0) {
			Critter.worldTimeStep();
			Quantity -= 1;
		}
		tfSteps.setText("");
		gridpane.getChildren().clear();
		initializeGrid();
		tfTime.setText(Critter.timestep + "s");
		displayGrid();
		goRunStats();
	}

	@FXML
	private void Exit() {
		System.exit(0);
	}

	@FXML
	private void goRunStats() throws IOException {
		String type = cbRunStats.getValue();
		String consoleMessage = "";
		Class<?> class_name = null;

		try {
			List<Critter> t = Critter.getInstances(type);
			class_name = Class.forName(myPackage + "." + type);
			Method runstats = class_name.getMethod("runStats", List.class);
			consoleMessage = (String) runstats.invoke(class_name, t);

		} catch (Error e) {
			e.printStackTrace();
		}

		catch (Exception e) {
			e.printStackTrace();
		}

		RunStatsConsole.setText(consoleMessage);

	}

	@FXML
	private void assignSeed() {
		TextInputDialog dialog = new TextInputDialog("");
		dialog.setTitle("Set Seed");
		dialog.setHeaderText("Set a seed value for randomization");
		dialog.setContentText("Enter:");

		Optional<String> input = dialog.showAndWait();
		if (input.isPresent()) {
			try {
				Integer seed = Integer.parseInt(input.get());
				Critter.setSeed(seed);
			} catch (Exception e) {
			}
		}

	}

}