package assignment4;

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

    public static void runStats(java.util.List<Critter> Critter1) {

        int starving = 0;
        for (Object obj : Critter1) {
            Critter1 c = (Critter1) obj;
            if(c.getEnergy() < 10 && c.getEnergy() > 0){
                starving ++;
            }
        }
        System.out.println("There are total number of " + Critter1.size() + " Critter1 in the world.");
        System.out.println("There are " + starving + " starving and weak Critter1.");
    }


    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

}