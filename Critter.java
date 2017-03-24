package assignment5;

import java.util.*;
import java.lang.reflect.*;
import static assignment5.Params.*;

public abstract class Critter {
	/* NEW FOR PROJECT 5 */
	public enum CritterShape {
		CIRCLE,
		SQUARE,
		TRIANGLE,
		DIAMOND,
		STAR
	}
	
	
	/* the default color is white, which I hope makes critters invisible by default
	 * If you change the background color of your View component, then update the default
	 * color to be the same as you background 
	 * 
	 * critters must override at least one of the following three methods, it is not 
	 * proper for critters to remain invisible in the view
	 * 
	 * If a critter only overrides the outline color, then it will look like a non-filled 
	 * shape, at least, that's the intent. You can edit these default methods however you 
	 * need to, but please preserve that intent as you implement them. 
	 */
	public javafx.scene.paint.Color viewColor() { 
		return javafx.scene.paint.Color.WHITE; 
	}
	
	public javafx.scene.paint.Color viewOutlineColor() { return viewColor(); }
	public javafx.scene.paint.Color viewFillColor() { return viewColor(); }
	
	public abstract CritterShape viewShape();
	
	private static int timestep; //Variable to keep track of time (used in the future)
	private static String myPackage;
	private	static List<Critter> population = new java.util.ArrayList<Critter>();
	private static List<Critter> babies = new java.util.ArrayList<Critter>();
	private static List<Critter> deadList = new java.util.ArrayList<Critter>();
	private boolean dead; //Variable to keep track of life (prevent dead encounters)
	private boolean hasMoved; //Variable to keep track of movement (prevent moving twice in a timestep())
	
	// Gets the package name.  This assumes that Critter and its subclasses are all in the same package.
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}
	
	protected final String look(int direction, boolean steps) {
		
		Critter tempCritter = new Algae();
		tempCritter.x_coord = this.x_coord;
		tempCritter.y_coord = this.y_coord;
		tempCritter.energy  = start_energy;
		
		if(steps == true){
			tempCritter.run(direction);
		}
		else{
			tempCritter.walk(direction);
		}
		
		
		
		
		
		
		
		
		return "";
	}
	
	/* rest is unchanged from Project 4 */
	
	

	private static java.util.Random rand = new java.util.Random();
	/**
	 * Return a random number from [0,max) , max is exclusive
	 * @param max a exclusive maxium for the random number
	 * @return a int value that contains random number
	 */
	public static int getRandomInt(int max) {
		return rand.nextInt(max);
	}
	
	/**
	 * Provides a seed that force simulation to repeat the same sequence of 
	 * random numbers during testing
	 * @param new_seed is a exclusive maximum number for random
	 */
	public static void setSeed(long new_seed) {
		rand = new java.util.Random(new_seed);
	}
	
	
	/* a one-character long string that visually depicts your critter in the ASCII interface */
	public String toString() { return ""; }
	
	private int energy = 0;
	protected int getEnergy() { return energy; }
	
	private int x_coord;
	private int y_coord;
	
	/**
	 * Handle walk movements for a Critter subclass
	 * Must ensure Critter doesn't move twice, carry out the move, and energy is updated accordingly.
	 * @param direction
	 * 			int direction must one of 0-7 
	 * */
	protected final void walk(int direction) {
		if(hasMoved){
			energy-=walk_energy_cost;
			return;
		}
		switch (direction) {
			case 0: {
				x_coord++;
				if (x_coord == world_width) {
					x_coord = 0;
				}
				break;
			}

			case 1: {
				x_coord++;
				y_coord--;
				if (x_coord == world_width)
					x_coord = 0;
				if (y_coord < 0)
					y_coord = world_height - 1;
				break;
			}
			case 2: {
				y_coord--;
				if (y_coord < 0)
					y_coord = world_height - 1;
				break;
			}

			case 3: {
				x_coord--;
				y_coord--;
				if (x_coord < 0)
					x_coord = world_width - 1;
				if (y_coord < 0)
					y_coord = world_height - 1;
				break;
			}
			case 4: {
				x_coord--;
				if (x_coord < 0)
					x_coord = world_width - 1;
				break;
			}

			case 5: {
				x_coord--;
				y_coord++;
				if (x_coord < 0)
					x_coord = world_width - 1;
				if (y_coord == world_height)
					y_coord = 0;
				break;
			}
			case 6: {
				y_coord++;
				if (y_coord == world_height)
					y_coord = 0;
				break;
			}
			case 7: {
				x_coord++;
				y_coord++;
				if (x_coord == world_width)
					x_coord = 0;
				if (y_coord == world_height)
					y_coord = 0;
				break;
			}

		}
		energy -= walk_energy_cost;
		if (energy <= 0) {
			dead = true;
			deadList.add(this);
		}
		hasMoved = true;

	}
	

	/**
	 * Handle run movements for a Critter subclass.
	 * Must ensure Critter doesn't move twice, carry out the move, and energy is updated accordingly.
	 * @param direction
	 * 			int direction must one of 0-7 
	* */	
	protected final void run(int direction) {
		if(hasMoved){
			energy -= run_energy_cost;
			return;
		}
		switch (direction) {
			case 0: {
				x_coord += 2;
				if (x_coord > world_width - 1) {
					x_coord %= world_width;
				}
				break;
			}

			case 1: {
				x_coord += 2;
				y_coord -= 2;
				if (x_coord > world_width - 1)
					x_coord %= world_width;
				if (y_coord < 0)
					y_coord += world_height;
				break;
			}
			case 2: {
				y_coord -= 2;
				if (y_coord < 0)
					y_coord += world_height;
				break;
			}

			case 3: {
				x_coord -= 2;
				y_coord -= 2;
				if (x_coord < 0)
					x_coord += world_width;
				if (y_coord < 0)
					y_coord += world_height;
				break;
			}
			case 4: {
				x_coord -= 2;
				if (x_coord < 0)
					x_coord += world_width;
				break;
			}

			case 5: {
				x_coord -= 2;
				y_coord += 2;
				if (x_coord < 0)
					x_coord += world_width;
				if (y_coord > world_height - 1)
					y_coord %= world_height;
				break;
			}
			case 6: {
				y_coord += 2;
				if (y_coord > world_height - 1)
					y_coord %= world_height;
				break;
			}
			case 7: {
				x_coord += 2;
				y_coord += 2;
				if (x_coord > world_width - 1)
					x_coord %= world_width;
				if (y_coord > world_height - 1)
					y_coord %= world_height;
				break;
			}

		}
		energy -= run_energy_cost;
		if (energy <= 0) {
			dead = true;
			deadList.add(this);
		}
		hasMoved = true;
	}
	
	/**
	 * Handle reproduction for a Critter subclass
	 * Initialize a child Critter based on the direction and parent coordinates.
	 * @param direction
	 * 			int direction must one of 0-7 
	 * @param offspring
	 * 			The child of a Critter
	 * */
	protected final void reproduce(Critter offspring, int direction) {
		if (this.energy <= Params.min_reproduce_energy) {
			return;
		}
		offspring.energy = (int) Math.ceil((double) (this.energy) / 2);
		this.energy = (int) Math.ceil((double) (this.energy) / 2);
		switch (direction) {
			case 0: {
				offspring.x_coord = this.x_coord + 1;
				offspring.y_coord = this.y_coord;
				if (offspring.x_coord == world_width) {
					offspring.x_coord = 0;
				}
				break;
			}
			case 1: {
				offspring.x_coord = this.x_coord + 1;
				offspring.y_coord = this.y_coord - 1;
				if (offspring.x_coord == world_width) {
					offspring.x_coord = 0;
				}
				if (offspring.y_coord < 0) {
					offspring.y_coord = world_height - 1;
				}
				break;
			}
			case 2: {
				offspring.x_coord = this.x_coord;
				offspring.y_coord = this.y_coord - 1;
				if (offspring.y_coord < 0) {
					offspring.y_coord = world_height - 1;
				}
				break;
			}
			case 3: {
				offspring.x_coord = this.x_coord - 1;
				offspring.y_coord = this.y_coord - 1;
				if (offspring.x_coord < 0) {
					offspring.x_coord = world_width - 1;
				}
				if (offspring.y_coord < 0) {
					offspring.y_coord = world_height - 1;
				}
				break;
			}
			case 4: {
				offspring.x_coord = this.x_coord - 1;
				offspring.y_coord = this.y_coord;
				if (offspring.x_coord < 0) {
					offspring.x_coord = world_width - 1;
				}
				break;
			}
			case 5: {
				offspring.x_coord = this.x_coord - 1;
				offspring.y_coord = this.y_coord + 1;
				if (offspring.x_coord < 0) {
					offspring.x_coord = world_width - 1;
				}
				if (offspring.y_coord == world_height) {
					offspring.y_coord = 0;
				}
				break;
			}
			case 6: {
				offspring.x_coord = this.x_coord;
				offspring.y_coord = this.y_coord + 1;
				if (offspring.y_coord == world_height) {
					offspring.y_coord = 0;
				}
				break;
			}
			case 7: {
				offspring.x_coord = this.x_coord + 1;
				offspring.y_coord = this.y_coord + 1;
				if (offspring.x_coord == world_width) {
					offspring.x_coord = 0;
				}
				if (offspring.y_coord == world_height) {
					offspring.y_coord = 0;
				}
				break;
			}

		}
		babies.add(offspring);

	}

	public abstract void doTimeStep();
	public abstract boolean fight(String oponent);
	
	/** isOccupied method checks if the Critter has a collision with any others
	 * @param a
	 * 		pass in Critter
	 * @return 
	 * 		true if Critter a is in collision with any live critters.
	 * */
	private static boolean isOccupied(Critter a){

		Iterator it = population.iterator();
		while(it.hasNext()){
			Critter b = (Critter) it.next();
			if((a != b) && a.dead == false && b.dead == false && (a.x_coord == b.x_coord) && (a.y_coord == b.y_coord)){
				return true;
			}
		}
		return false;
	}

	/**
	 * Handles all collisions within a timestep of the world
	 * If multiple Critters are in the same location, there can only be one remaining
	 * */
	private static void doEncounter(){
		int aDice;
		int bDice;
		for(Critter a:population){
			for(Critter b:population){
				if((a != b) && a.dead == false && b.dead == false && (a.x_coord == b.x_coord) && (a.y_coord == b.y_coord)) {
					int ax = a.x_coord;
					int ay = a.y_coord;
					boolean aResult = a.fight(b.toString());
					if(!aResult) {
						if (isOccupied(a)) {
							a.x_coord = ax;
							a.y_coord = ay;
						}
					}

					int bx = b.x_coord;
					int by = b.y_coord;
					boolean bResult = b.fight(a.toString());
					if(!bResult) {
						if (isOccupied(b)) {
							b.x_coord = bx;
							b.y_coord = by;
						}
					}
					if((a != b) && a.dead == false && b.dead == false && (a.x_coord == b.x_coord) && (a.y_coord == b.y_coord)){
						if(aResult == false && bResult == false){
							if(a.getRandomInt(a.energy)>b.getRandomInt(b.energy)){
								a.energy += b.energy/2;
								b.dead=true;
								deadList.add(b);
							} else if(a.getRandomInt(a.energy)<b.getRandomInt(b.energy)){
								b.energy += a.energy/2;
								a.dead=true;
								deadList.add(a);
							} else{
								int coinflip = Critter.getRandomInt(2);
								if(coinflip == 0){
									a.energy += (b.energy/2);
									b.dead = true;
									deadList.add(b);
								}
								else {
									b.energy += (a.energy / 2);
									a.dead = true;
									deadList.add(a);
								}
							}
						}
						if(aResult == true || bResult == true){
							if(aResult == false){
								aDice = 0;
							}
							else{
								aDice = Critter.getRandomInt(a.energy);
							}
							if(bResult == false){
								bDice = 0;
							}
							else{
								bDice = Critter.getRandomInt(b.energy);
							}
							if(aDice > bDice){
								a.energy += (b.energy/2);
								b.dead = true;
								deadList.add(b);
							} else if(aDice < bDice){
								b.energy += (a.energy/2);
								a.dead = true;
								deadList.add(a);
							}
							else{
								int coinflip = Critter.getRandomInt(2);
								if(coinflip == 0){
									a.energy += (b.energy/2);
									b.dead = true;
									deadList.add(b);
								}
								else{
									b.energy += (a.energy/2);
									a.dead = true;
									deadList.add(a);
								}
							}
						}

					}
				}
			}
		}
		population.removeAll(deadList);
		deadList.clear();

	}
	
	/** This method generates int refresh_algae_count number of Algae
	 * */
	private static void genAlgae(){
		for(int i = 0; i < refresh_algae_count; i++){
			try {
				makeCritter("Algae");
			} catch (InvalidCritterException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	/** Handles actions for each Critter in the world 
	 * */
	public static void worldTimeStep() {
		timestep += 1;
		
		Iterator<Critter> it = population.iterator();
		it.next().look(2, true);
		while (it.hasNext()) {
			it.next().doTimeStep();	//run each Critters TimeStep
		}

		doEncounter();	//check collisions

		Iterator<Critter> it2 = population.iterator();
		while (it2.hasNext()) {
			Critter c = it2.next();
			c.energy -=rest_energy_cost;		//Update energy
			if(c.energy <= 0)	
				it2.remove();					//Handle Critters who died due to lack of energy
			c.hasMoved = false;					//hasMoved for each Critter is reset at the end of a TimeStep
		}

		genAlgae();								//Generate refresh Algae
		population.addAll(babies);				//Add new babies into the world
		babies.clear();

	}
	
	public static void displayWorld(Object pane) {} 
	/* Alternate displayWorld, where you use Main.<pane> to reach into your
	   display component.
	   // public static void displayWorld() {}
	*/
	
	/**
	 * create and initialize a Critter subclass.
	 * critter_class_name must be the unqualified name of a concrete subclass of Critter, if not,
	 * an InvalidCritterException must be thrown.
	 * (Java weirdness: Exception throwing does not work properly if the parameter has lower-case instead of
	 * upper. For example, if craig is supplied instead of Craig, an error is thrown instead of
	 * an Exception.)
	 * @param critter_class_name a String that is taken from input
	 * @throws InvalidCritterException if input is not a valid critter class
	 */
	public static void makeCritter(String critter_class_name) throws InvalidCritterException {
		char[] temp = critter_class_name.toCharArray();
		if(temp[0] < 123 && temp[0] > 96)
			temp[0] += 32;
		critter_class_name = new String(temp);

		Critter newCritter = getCritterFromString(critter_class_name);
		newCritter.x_coord = getRandomInt(world_width);
		newCritter.y_coord = getRandomInt(world_height);
		newCritter.energy = start_energy;
		newCritter.dead = false;
		newCritter.hasMoved = false;
		population.add(newCritter);
	}
	
	/** 
	 * Helper method for makeCritter.
	 * @param  critter_class_name
	 * 		Appropriate name for a Critter subclass
	 * @return A Critter object of type critter_class_name		
	 * 	
	* */
	private static Critter getCritterFromString (String critter_class_name) throws InvalidCritterException {
		Class<?> myCritter = null;
		Constructor<?> constructor = null;
		Object instanceOfMyCritter = null;

		try {
			myCritter = Class.forName(myPackage +"." +critter_class_name); 	// Class object of specified name
		} catch (ClassNotFoundException e) {
			throw new InvalidCritterException(critter_class_name);
		}
		try {
			constructor = myCritter.getConstructor();		// No-parameter constructor object
			instanceOfMyCritter = constructor.newInstance();	// Create new object using constructor
		} catch (NoSuchMethodException e){// various exceptions ) {
			// Do whatever is needed to handle the various exceptions here -- e.g. rethrow Exception
		}
		catch (Exception e){

		}

		Critter me = (Critter)instanceOfMyCritter;		// Cast to Critter
		return me;
	}
	
	
	/**
	 * Gets a list of critters of a specific type.
	 * @param critter_class_name What kind of Critter is to be listed.  Unqualified class name.
	 * @return List of Critters.
	 * @throws InvalidCritterException if input is not a valid critter class
	 */
	public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException {
		List<Critter> result = new java.util.ArrayList<Critter>();

		Critter temp = getCritterFromString(critter_class_name); // Generate a temporary Critter of type critter_class_name
		Iterator it = population.iterator();
		while (it.hasNext()) {
			Critter a = (Critter) it.next();
			if (a.getClass().isInstance(temp))	//if Critter a matches the temporary created earlier, add to results List
				result.add(a);
		}

		return result;
	}

	/**
	 * Prints out how many Critters of each type there are on the board.
	 * @param critters List of Critters.
	 */
	public static void runStats(List<Critter> critters) {
		System.out.print("" + critters.size() + " critters as follows -- ");
		java.util.Map<String, Integer> critter_count = new java.util.HashMap<String, Integer>();
		for (Critter crit : critters) {
			String crit_string = crit.toString();
			Integer old_count = critter_count.get(crit_string);
			if (old_count == null) {
				critter_count.put(crit_string, 1);
			} else {
				critter_count.put(crit_string, old_count.intValue() + 1);
			}
		}
		String prefix = "";
		for (String s : critter_count.keySet()) {
			System.out.print(prefix + s + ":" + critter_count.get(s));
			prefix = ", ";
		}
		System.out.println();
	}
	
	
	
	/* the TestCritter class allows some critters to "cheat". If you want to 
	 * create tests of your Critter model, you can create subclasses of this class
	 * and then use the setter functions contained here. 
	 * 
	 * NOTE: you must make sure thath the setter functions work with your implementation
	 * of Critter. That means, if you're recording the positions of your critters
	 * using some sort of external grid or some other data structure in addition
	 * to the x_coord and y_coord functions, then you MUST update these setter functions
	 * so that they correctup update your grid/data structure.
	 */
	static abstract class TestCritter extends Critter {
		protected void setEnergy(int new_energy_value) {
			super.energy = new_energy_value;
		}
		
		protected void setX_coord(int new_x_coord) {
			super.x_coord = new_x_coord;
		}
		
		protected void setY_coord(int new_y_coord) {
			super.y_coord = new_y_coord;
		}
		
		protected int getX_coord() {
			return super.x_coord;
		}
		
		protected int getY_coord() {
			return super.y_coord;
		}
		

		/*
		 * This method getPopulation has to be modified by you if you are not using the population
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.
		 */
		protected static List<Critter> getPopulation() {
			return population;
		}
		
		/*
		 * This method getBabies has to be modified by you if you are not using the babies
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.  Babies should be added to the general population 
		 * at either the beginning OR the end of every timestep.
		 */
		protected static List<Critter> getBabies() {
			return babies;
		}
	}
	
	/**
	 * Clear the world of all critters, dead and alive
	 */
	public static void clearWorld() {
		// Complete this method.
		population.clear();
		deadList.clear();
		babies.clear();
		population.clear();
	}
	
}
