package assignment5;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;
import assignment5.Critter.CritterShape;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

import static assignment5.Params.*;


public class Controller {

    private static Double SIZE = 20.0;
    private static String myPackage;
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
    private void initialize(){
    	getClassNames();
        cbMakeCritter.setValue("Algae");
        cbMakeCritter.setItems(critterNameList);
        cbRunStats.setValue("Algae");
        cbRunStats.setItems(critterNameList);
        initializeGrid();
    }
    
    @FXML
    private void setSize(){
    	double width = gridpane.getWidth();
    	double height = gridpane.getHeight();
    	if(width > splitP.getWidth())
    	{
    		splitP.resize(width, splitP.getHeight());
    	}
    	if(height > splitP.getHeight()){
    		splitP.resize(width, height);
    	}
    }
    
    private void initializeGrid() {
    	
        int c = world_height;
        int r = world_width;
        int GAP = 2;
        gridpane.setVgap(GAP);
        gridpane.setHgap(GAP);
        gridpane.setGridLinesVisible(false);
        for(int i =0; i< r; i++) {
            for(int j=0; j<c; j++){
                Rectangle rec = new Rectangle();
                rec.setHeight(SIZE);
                rec.setWidth(SIZE);
                rec.setFill(Color.WHITE);
                gridpane.add(rec,i,j);
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
        while(it.hasNext()){
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
     private void getClassNames(){
    	File dir = new File(path);
    	File[] files = dir.listFiles(new FilenameFilter(){

			@Override
			public boolean accept(File dir, String name) {
				if(name.endsWith(".java")){
					return true;
				}
				return false;
			}
    	});
    		for(File f : files){
    			String name = f.getName().split(".java")[0];
    			try{
    				Critter.makeCritter(name);
    				critterNameList.add(name);
    			}
    			catch (Exception e){
    			}
    			Critter.getPop().clear();
    	}
    	
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
        gridpane.getChildren().clear();
        initializeGrid();
        displayGrid();
    }

    private Shape getShape(CritterShape critterShape){
        switch (critterShape){
            case CIRCLE: {
                Circle cir = new Circle();
                cir.setRadius(SIZE/2);
                return cir;
            }
            case SQUARE: {
                Rectangle rect = new Rectangle();
                rect.setHeight(SIZE);
                rect.setWidth(SIZE);
                return rect;
            }
            case TRIANGLE: {
                Polygon triangle = new Polygon();
                triangle.getPoints().addAll(new Double[]{
                    SIZE/2, 0.0,
                    0.0, SIZE,
                    SIZE, SIZE
                });
                return triangle;
            }
            case STAR:{
                Polygon star = new Polygon();
                return star;
            }
            case DIAMOND:{
                Polygon diamond = new Polygon();
                diamond.getPoints().addAll(new Double[]{
                    0.0, SIZE/2,
                    SIZE/2, 0.0,
                    SIZE, SIZE/2,
                    SIZE/2, SIZE});
                return diamond;
            }
            default: {
                return new Circle();
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
        gridpane.getChildren().clear();
        initializeGrid();
        displayGrid();
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