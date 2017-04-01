package assignment5;
/* CRITTERS Critter1.java
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

import java.util.*;

/*
 * Critter1 created by Zhaofeng Liang
 * Critter1 is a custom critter. Critter1 is really weak, it will move
 * every 3 turns, and always lose the fight against other critters.
 * Critter1 will always eat Algae
 * Critter1 is represented as "E"
 *
 */

public class Critter1 extends Critter {

    private int turn;

    @Override
    public void doTimeStep() {
        //Moves every 3 timesteps
        int shouldWalk = 0;
        setTurn(getTurn() + 1);
        shouldWalk = getTurn();
        if (shouldWalk % 3 == 0) {
            walk(Critter.getRandomInt(8));
        }
    }

    @Override
    public boolean fight(String opponent) {
        // Only eats Algae and fight its own type
        if(opponent.toString().equals("@")){
            return true;
        }
        else if(opponent.toString().equals("E")){
            return true;
        }
        else{
            return false;
        }
    }

    public String toString() {
        return "E";
    }

    public static String runStats(java.util.List<Critter> Critter1) {
    	
    	String str = "";
    	
        int starving = 0;
        for (Object obj : Critter1) {
            Critter1 c = (Critter1) obj;
            if(c.getEnergy() < 10 && c.getEnergy() > 0){
                starving ++;
            }
        }
        str = str.concat("There are total number of " + Critter1.size() + " Critter1 in the world.");
        str = str.concat("\n");
        str = str.concat("\n");
        str = str.concat("There are " + starving + " starving and weak Critter1.");
        return str;
    }


    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

	@Override
	public CritterShape viewShape() {
		return CritterShape.TRIANGLE;
	}
	@Override
	public javafx.scene.paint.Color viewOutlineColor() { 
		return javafx.scene.paint.Color.CRIMSON; 
	}
	@Override
	public javafx.scene.paint.Color viewFillColor(){
		return javafx.scene.paint.Color.BLACK;
	}

}