/* CRITTERS Critter3.java
 * EE422C Project 4 submission by
 * Zhaofeng Liang
 * zl4685
 * 16230
 * Zohaib Imam
 * szi58
 * 16230
 * Slip days used: 0
 * https://github.com/ee312-zl4685/EE422C_Project4
 * Spring 2017
 */
package assignment5;
import java.util.*;

import assignment5.Critter.CritterShape;

/*  Critter3 is also known as Bolt Critter, this critter runs in a circle.
    It cannot fight itself due to self-respect, but it loves to fight others.
    Critter3s are rare in the world, so we keep track of how many ever existed.
* */

public class Critter3 extends Critter {

    private int dir;
    private static int history;

    public Critter3(){
        dir = 0;
        history += 1;
    }



    @Override
    public void doTimeStep() {
        run(dir%8);
        dir++;

    }

    @Override
    public boolean fight(String opponent) {

        if(opponent.equals("W"))
            return false;
        else return true;
    }


    public static String runStats(List<Critter> critters) {
    	String str = "";
    	if(critters.size() < 1){
    		str = str.concat(critters.size() + " Critter3s in the world right now.");
            str = str.concat("\n");
            str = str.concat("\n");
            str = str.concat("There have been 0 Critter3s ever.");
            return str;
    	}
        Critter3 max = (Critter3) critters.get(0);
        Critter3 temp;
        for(Object c : critters){
            temp = (Critter3) c;
            if(temp.dir > max.dir)
                max=temp;
        }
        history -= 1;
        str = str.concat(critters.size() + " Critter3s in the world right now.");
        str = str.concat("\n");
        str = str.concat("\n");
        str = str.concat("There have been "+ (history)+ " Critter3s ever.");
        str = str.concat("\n");
        str = str.concat("\n");
        str = str.concat("The longest surviving Critter3 right now: " +max.dir);
        return str;
    }

    public String toString() {
        return "W";
    }



    @Override
	public CritterShape viewShape() {
		return CritterShape.STAR;
	}
	@Override
	public javafx.scene.paint.Color viewFillColor() { 
		return javafx.scene.paint.Color.GREY; 
	}
	@Override
	public javafx.scene.paint.Color viewOutlineColor() { 
		return javafx.scene.paint.Color.CYAN; 
	}

}