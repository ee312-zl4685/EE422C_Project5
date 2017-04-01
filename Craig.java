package assignment5;
/* CRITTERS Craig.java
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

public class Craig extends Critter {
	
	@Override
	public String toString() { return "C"; }
	
	private static final int GENE_TOTAL = 24;
	private int[] genes = new int[8];
	private int dir;
	
	public Craig() {
		for (int k = 0; k < 8; k += 1) {
			genes[k] = GENE_TOTAL / 8;
		}
		dir = Critter.getRandomInt(8);
	}
	
	public boolean fight(String not_used) { return true; }

	@Override
	public void doTimeStep() {
		/* take one step forward */
		walk(dir);
		
		if (getEnergy() > 150) {
			Craig child = new Craig();
			for (int k = 0; k < 8; k += 1) {
				child.genes[k] = this.genes[k];
			}
			int g = Critter.getRandomInt(8);
			while (child.genes[g] == 0) {
				g = Critter.getRandomInt(8);
			}
			child.genes[g] -= 1;
			g = Critter.getRandomInt(8);
			child.genes[g] += 1;
			reproduce(child, Critter.getRandomInt(8));
		}
		
		/* pick a new direction based on our genes */
		int roll = Critter.getRandomInt(GENE_TOTAL);
		int turn = 0;
		while (genes[turn] <= roll) {
			roll = roll - genes[turn];
			turn = turn + 1;
		}
		assert(turn < 8);
		
		dir = (dir + turn) % 8;
	}

	public static String runStats(java.util.List<Critter> craigs) {
		String str = "";
		int total_straight = 0;
		int total_left = 0;
		int total_right = 0;
		int total_back = 0;
		for (Object obj : craigs) {
			Craig c = (Craig) obj;
			total_straight += c.genes[0];
			total_right += c.genes[1] + c.genes[2] + c.genes[3];
			total_back += c.genes[4];
			total_left += c.genes[5] + c.genes[6] + c.genes[7];
		}
		str = str.concat("" + craigs.size() + " total Craigs    ");
		str = str.concat("\n");
		str = str.concat("\n");
		str = str.concat("" + total_straight / (GENE_TOTAL * 0.01 * craigs.size()) + "% straight   ");
		str = str.concat("\n");
		str = str.concat("\n");
		str = str.concat("" + total_back / (GENE_TOTAL * 0.01 * craigs.size()) + "% back   ");
		str = str.concat("\n");
		str = str.concat("\n");
		str = str.concat("" + total_right / (GENE_TOTAL * 0.01 * craigs.size()) + "% right   ");
		str = str.concat("\n");
		str = str.concat("\n");
		str = str.concat("" + total_left / (GENE_TOTAL * 0.01 * craigs.size()) + "% left   ");
		return str;
	}
	
	@Override
	public CritterShape viewShape() { return CritterShape.SQUARE; }

	@Override
	public javafx.scene.paint.Color viewOutlineColor() { return javafx.scene.paint.Color.BLUE; }

}
