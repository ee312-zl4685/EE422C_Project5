package assignment4;

/*
 * Critter2 created by Zhaofeng Liang
 * Critter2 is a type of critter that decides to either rest, walk, run base on
 * their age.
 * Age 0 to 5 or bigger than 60 will rest
 * Age 6 to 18 or 50 to 60 will walk
 * Age 19 to 49 will run and reproduce
 * Age 0 to 10 or bigger than 60 will return false in fight
 * Age 11 to 59 will always fight, hence return true
 * Critter2 has 25% of birthrate
 * Critter2 is represented as "Z" in the world
 */

public class Critter2 extends Critter {

    private int age;

    public Critter2() {
        // Initialize age as 0;
        age = 0;
    }

    @Override
    public void doTimeStep() {
        // Increment age every timestep and rest, walk, run based on age
        age++;
        if (age <= 5 && age > 60) {
            return;
        }
        if (age >= 6 && age <= 18 && age >= 50 && age <= 60) {
            walk(Critter.getRandomInt(8));
        }
        if (age >= 19 && age <= 49) {
            run(Critter.getRandomInt(8));
            //25% of getting babies
            if (Critter.getRandomInt(4) == 0) {
                Critter2 child = new Critter2();
                reproduce(child, Critter.getRandomInt(8));
            }
        }

    }

    @Override
    public boolean fight(String opponent) {
        // Always eats algae, but children and elder dont fight
        if (opponent.toString().equals("@")) {
            return true;
        }
        if (age <= 10 && age >= 60) {
            return false;
        }
        if (age >= 11 && age < 60) {
            return true;
        }

        return false;
    }

    public String toString() {
        return "Z";
    }

    public static void runStats(java.util.List<Critter> Critter2) {
        int children = 0;
        int teenager = 0;
        int adult = 0;
        int middleage = 0;
        int elder = 0;
        for (Object obj : Critter2) {
            Critter2 c = (Critter2) obj;
            if (c.age < 10) {
                children++;
            }
            if (c.age >= 10 && c.age <= 18) {
                teenager++;
            }
            if (c.age >= 19 && c.age <= 40) {
                adult++;
            }
            if (c.age >= 41 && c.age <= 60) {
                middleage++;
            }
            if (c.age >= 61) {
                elder++;
            }
        }
        System.out.println("There are " + Critter2.size() + " Critter2 in the world.");
        System.out.println("There are " + children + " children Critter2 in the world.");
        System.out.println("There are " + teenager + " teenager Critter2 in the world.");
        System.out.println("There are " + adult + " adult Critter2 in the world.");
        System.out.println("There are " + middleage + " middle age Critter2 in the world.");
        System.out.println("There are " + elder + " elder Critter2 in the world.");
    }

}