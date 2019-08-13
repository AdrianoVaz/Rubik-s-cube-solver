import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.PriorityQueue;

/** 
 * @author Adriano Vaz
 * @author Nataliya Mykytas
 */

public class TCube implements IState
{
	/**
	 * The char matrix cube, represents the color configuration of the floppy cube.
	 */
	private char[][] cube;

	/**
	 * The String config, represents the configuration of the floppy cube using a single line.
	 * The zeros represent non existent blocks.
	 * (Easier to read for the computer, harder to read for humans)
	 */
	private String config;

	/**
	 * The int step, represents the current step of the cube, when it trying to find the solution.
	 */
	private int step;

	/**
	 * The int wrong, represents the number of incorrect rows. 
	 * Will only count front rows if the sides of the cube are correct. 
	 */
	private int wrong;

	/**
	 * The solutions list contains, all the cube available solutions. (Rubick's cube solution)
	 */

	private static ArrayList <String> solutions;
	static
	{
		solutions= new ArrayList <String>();
		solutions.add("GGG00000WWWOYYYRWWWOYYYRWWWOYYYRBBB00000");
		solutions.add("OOO00000WWWBYYYGWWWBYYYGWWWBYYYGRRR00000");
		solutions.add("BBB00000WWWRYYYOWWWRYYYOWWWRYYYOGGG00000");
		solutions.add("RRR00000WWWGYYYBWWWGYYYBWWWGYYYBOOO00000");
		solutions.add("GGG00000YYYRWWWOYYYRWWWOYYYRWWWOBBB00000");
		solutions.add("RRR00000YYYBWWWGYYYBWWWGYYYBWWWGOOO00000");
		solutions.add("BBB00000YYYOWWWRYYYOWWWRYYYOWWWRGGG00000");
		solutions.add("OOO00000YYYGWWWBYYYGWWWBYYYGWWWBRRR00000");
	}

	/**
	 * The creation of a valid TCube implies that the initial configuration has exactly 40 characters.
	 * The constructor when not given a step number, assumes that the step is the first one (step zero).
	 * @param config Cube color configuration ("computer readable format")
	 * @see #setCube(String, int)
	 */
	public TCube(String config)
	{
		setCube(config, 0);
		countWrong();
	}

	/**
	 * The creation of a valid TCube implies that the initial configuration has exactly 40 characters.
	 * The constructor when not given a step number, assumes that the step is the first one (step zero).
	 * @param config Cube color configuration ("computer readable format")
	 * @see #setCube(String, int)
	 */
	public TCube (String config, int step)
	{
		setCube(config,step);
	}
	/**
	 * Allocates space for the cube color matrix.
	 */
	public void initCube()
	{
		this.cube = new char[5][8];
	}

	/**
	 * The creation of a valid TCube implies that the initial configuration has exactly 40 characters.
	 * The constructor when not given a step number, assumes that the step is the first one (step zero).
	 * @param config Cube color configuration ("computer readable format")
	 * @param step Cube current step (zero for the first step)
	 */
	public void setCube(String config, int step)
	{
		initCube();
		setStep(step);
		setConfig(config);
		for (int i = 0; i < 5; i++)
			for (int j = 0; j < 8; j++)
				this.cube[i][j] = config.charAt(i*8 + j);
	}

	/**
	 * Get current cube configuration in matrix of chars format.
	 * (Cube updates are done through cube moves)
	 * @return The matrix with the color configuration of the cube. (easier for humans and harder for computers).
	 */
	public char[][] getCube()
	{	
		return cube;
	}

	/**
	 * The creation of a valid TCube implies that the initial configuration has exactly 40 characters.
	 * @param config Cube color configuration ("computer readable format")
	 */
	public void setConfig(String config)
	{
		if(config.length() != 40)
			throw new IllegalArgumentException("Please insert a valid cube configuration!");
		else
			this.config = config;
	}

	/**
	 * Get current cube configuration in String format (ALWAYS update configuration before return, maybe a move was executed).
	 * @see #updateConfig()
	 * @return The configuration of the floppy cube using a single line (easier for computers and harder for humans).
	 */
	public String getConfig()
	{
		updateConfig();
		return config;
	}

	/**
	 * *USE WITH CAUTION*
	 * Set current cube configuration to an empty String.
	 */
	private void resetConfig()
	{
		config = "";
	}

	/**
	 * Update the current cube configuration (useful to execute after performing a cube move).
	 * @see #resetConfig()
	 */
	private void updateConfig()
	{
		resetConfig();
		for (int i=0; i<5; i++)
			for (int j=0; j<8; j++)
				this.config += cube[i][j];
	}

	/**
	 * Sets the current step of the cube. (The initial configuration is considered step zero)
	 * If the step to be set is bigger than 8, an exception is thrown, because the maximum number of steps
	 * needed to solve the cube is 8 (i.e. algorithm God's number)
	 */
	public void setStep(int step)
	{
		if(step > 8)
			throw new IllegalArgumentException("Error, the god's number for a troika cube is 8!");
		else
			this.step = step;
	}

	/**
	 * @return The current step of the cube. (The initial configuration is considered step zero)
	 */
	public int getStep()
	{
		return step;
	}

	/**
	 * Check if cube is in final configuration, this means that upper face has all positions with the 
	 * same color, this also applies to the lower face and sides of the cube.
	 * @see #updateConfig()
	 * @return True if the cube is in the solved configuration, else false.
	 */
	public boolean isSolved()
	{
		updateConfig();
		for(String i: solutions)
			if(config.equals(i))
				return true;
		return false;
	}

	/**
	 * Check how many incorrect rows the current state have.
	 * Only checks the front rows if the sides are correct.
	 */

	public void countWrong()
	{
		updateConfig();
		int count=0;
		this.toString();
		
		if(this.cube[0][0] != this.cube[0][1] || this.cube[0][0] != this.cube[0][2]){
			count++;
		}
		if(this.cube[1][7] != this.cube[2][7] || this.cube[1][7] != this.cube[3][7]){
			count++;
		}
		if(this.cube[1][0] != this.cube[2][0] || this.cube[1][0] != this.cube[3][0]){
			count++;
		}
		if(this.cube[1][1] != this.cube[2][1] || this.cube[1][1] != this.cube[3][1]){
			count++;
		}
		if(this.cube[1][2] != this.cube[2][2] || this.cube[1][2] != this.cube[3][2]){
			count++;
		}			

		setWrong(count);
	}

	/**
	 * @return Calculate the heuristics
	 */	

	public int getWrong() {
		return wrong;
	}

	/**
	 * Sets the number of incorrect colors of the cube compared to
	 *  the closest solution.
	 */

	public void setWrong(int wrong) {
		this.wrong = wrong;
	}

	/**
	 * @return The result of the function of evaluation using A* Algorithm.
	 */

	public int aval(){
		return wrong + step;
	}

	/**
	 * Makes all the possible moves (creating new cubes), for the current configuration and adds them all to the queue.
	 * @see #leftVertical()
	 * @see #midVertical()
	 * @see #rightVertical()
	 * @see #upHorizontal()
	 * @see #midHorizontal()
	 * @see #downHorizontal()
	 * @see #TCube(String, int)
	 * @return List of every possible next move configuration.
	 */

	public PriorityQueue<IState> getSuccessors(PriorityQueue<IState> queue) {
		TCube a = new TCube(config, step+1);
		TCube b = new TCube(config, step+1);
		TCube c = new TCube(config, step+1);
		TCube d = new TCube(config, step+1);
		TCube e = new TCube(config, step+1);
		TCube f = new TCube(config, step+1);

		a.leftVertical();
		a.countWrong();
		queue.add(a);
		b.midVertical();
		b.countWrong();
		queue.add(b);
		c.rightVertical();
		c.countWrong();
		queue.add(c);
		d.upHorizontal();
		d.countWrong();
		queue.add(d);
		e.midHorizontal();
		e.countWrong();
		queue.add(e);
		f.downHorizontal();
		f.countWrong();
		queue.add(f);

		return queue;
	}

	/**
	 * Returns a string representation of the object. In general,
	 * the toString method returns a string that "textually represents" this object.
	 * @return Current configuration of the Cube.
	 */
	@Override
	public String toString()
	{
		String s = new String();
		for (int i=0; i<5; i++)
		{
			for (int j=0; j<8; j++)
				if (cube[i][j] != '0')
					s += String.valueOf(cube[i][j]);
			s += "\n";
		}
		return s;
	}

	/**
	 * Swaps one color of the cube position in the matrix, with another color of the cube.
	 * The first color position in the matrix is in row i and column j.
	 * The first color position in the matrix is in row k and column l.
	 * 
	 * Please avoid swapping colors in the same position (i.e. i == k && j == l).
	 * @see #getCube()
	 */
	private void swap(int i, int j, int k, int l)
	{
		char[][] tempCube = getCube();
		char temp = tempCube[i][j];
		tempCube[i][j] = tempCube[k][l];
		tempCube[k][l] = temp;
	}

	/**
	 * Move 180 degrees left vertical lane of colors.
	 * @see #swap(int, int, int, int)
	 */
	public void leftVertical()
	{	
		swap(4,0, 0,0);
		swap(3,6, 1,0);
		swap(2,6, 2,0);
		swap(1,6, 3,0);
		swap(1,7, 3,7);
	}

	/**
	 * Move 180 degrees right vertical lane of colors.
	 * @see #swap(int, int, int, int)
	 */
	public void rightVertical()
	{
		swap(0,2, 4,2);
		swap(1,2, 3,4);
		swap(2,2, 2,4);
		swap(3,2, 1,4);
		swap(1,3, 3,3);
	}

	/**
	 * Move 180 degrees middle vertical lane of colors.
	 * @see #swap(int, int, int, int)
	 */
	public void midVertical()
	{	
		swap(0,1, 4,1);
		swap(1,1, 3,5);
		swap(2,1, 2,5);
		swap(3,1, 1,5);
	}

	/**
	 * Move 180 degrees top horizontal lane of colors.
	 * @see #swap(int, int, int, int)
	 */
	public void upHorizontal()
	{
		swap(1,0, 1,4);
		swap(1,1, 1,5);
		swap(1,2, 1,6);
		swap(1,3, 1,7);
		swap(0,0, 0,2);
	}

	/**
	 * Move 180 degrees middle horizontal lane of colors.
	 * @see #swap(int, int, int, int)
	 */
	public void midHorizontal()
	{
		swap(2,0, 2,4);
		swap(2,1, 2,5);
		swap(2,2, 2,6);
		swap(2,3, 2,7);
	}

	/**
	 * Move 180 degrees down horizontal lane of colors.
	 * @see #swap(int, int, int, int)
	 */
	public void downHorizontal()
	{
		swap(3,0, 3,4);
		swap(3,1, 3,5);
		swap(3,2, 3,6);
		swap(3,3, 3,7);
		swap(4,0, 4,2);
	}
}