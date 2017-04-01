/* CRITTERS Critter4.java
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
package assignment5;
import java.util.*;

import assignment5.Critter.CritterShape;
import javafx.scene.paint.Color;

import static assignment5.Params.*;



public class Critter4 extends Critter {

    private boolean step;
    private int dir;

    public Critter4(){
        step=true;
        dir = Critter.getRandomInt(8);
    }

    @Override
    public void doTimeStep() {
        if(getEnergy() < 50) {
            run(dir);
            step=false;
            return;
        } else if(getEnergy() >= 50 && getEnergy() <85) {
            walk(dir);
            step = false;
        } else if(getEnergy() >= 85 && getEnergy() <= 150) {
            walk(getRandomInt(8));
        } else if(getEnergy() > 150) {
            dir = getRandomInt(8);
            Critter4 child = new Critter4();
            reproduce(child, dir);
        }
    }

    @Override
    public boolean fight(String opponent) {
        if (opponent.equals("@")) return true;
        if(step) {
            String walkAttempt = look(dir, false);
            String runAttempt = look(dir, true);
            if(walkAttempt != null && walkAttempt.equals("@")) {
                walk(dir);
                step = false;
                return true;
            }
            if(runAttempt != null &&  runAttempt.equals("@")){
                run(dir);
                step = false;
                return true;
            }
            else {
                run(dir);
                step = false;
            }
        }
        return true;
    }

    public static String runStats(List<Critter> critters) {
    	int Elite=0;
        int nearDead=0;
        String str = "";
        Critter4 temp;
        for(Object c : critters){
            temp = (Critter4) c;
            if(temp.getEnergy()>150) Elite++;
            if(temp.getEnergy()<30) nearDead++;
        }
        str = str.concat(critters.size()+" MyCritter4s in the world.");
        str = str.concat("\n");
        str = str.concat("\n");
        str = str.concat(Elite + " are thriving and might become parents.");
        str = str.concat("\n");
        str = str.concat("\n");
        str = str.concat(nearDead + " are almost dead.");
        return str;
    }


    public String toString() {
        return "S";
    }

    @Override
	public CritterShape viewShape() {
		return CritterShape.CIRCLE;
	}
	@Override
	public javafx.scene.paint.Color viewFillColor() { 
		return Color.GOLD;
	}
	@Override
	public javafx.scene.paint.Color viewOutlineColor() { 
		return Color.RED;
	}

}