/* CRITTERS Critter4.java
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

import static assignment5.Params.*;

/*
    Critter4 is also known as the Mama Critter
    Critter4 has the special ability to get pregnant and have up-to 8 children in a turn.
    Critter4 are specially loving and in support of their pacifist beliefs, they choose not to fight anyone.
* */
public class Critter4 extends Critter {

    private boolean isPregnant;
    private int lucky;
    private int dir;

    public Critter4(){
        isPregnant=false;
        dir = Critter.getRandomInt(8);
        lucky = Critter.getRandomInt(start_energy);
    }

    @Override
    public void doTimeStep() {
        walk(dir);


        if(lucky == Critter.getRandomInt(start_energy)) {
            isPregnant = true;
            lucky = lucky%8;

        }

        if(isPregnant) {
            while(lucky > 0){
                Critter4 child = new Critter4();
                reproduce(child, Critter.getRandomInt(8));
                walk(0);
            }

        }

        dir = Critter.getRandomInt(8);
        lucky = Critter.getRandomInt(this.getEnergy());
        isPregnant=false;

    }

    @Override
    public CritterShape viewShape() { return CritterShape.STAR; }

    @Override
    public boolean fight(String opponent) {
        return false;
    }

    public static void runStats(List<Critter> critters) {
        int preg =0;
        Critter4 temp;
        for(Object c : critters){
            temp = (Critter4) c;
            if(temp.isPregnant)
                preg += 1;
        }
        System.out.println(critters.size()+" MyCritter4s in the world.");
        System.out.println(preg + " are pregnant MyCritter4s.");
    }


    public String toString() {
        return "M";
    }

}