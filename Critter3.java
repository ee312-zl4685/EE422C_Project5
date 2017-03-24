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
    public CritterShape viewShape() { return CritterShape.SQUARE; }

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


    public static void runStats(List<Critter> critters) {
        Critter3 max = (Critter3) critters.get(0);
        Critter3 temp;
        for(Object c : critters){
            temp = (Critter3) c;
            if(temp.dir > max.dir)
                max=temp;
        }
        history -= 1;
        System.out.println(critters.size() + " Critter3s in the world right now.");
        System.out.println("There have been "+ (history)+ " Critter3s ever.");
        System.out.println("The longest surviving Critter3 right now: " +max.dir);
    }

    public String toString() {
        return "W";
    }

}