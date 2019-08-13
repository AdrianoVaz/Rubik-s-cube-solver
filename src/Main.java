import java.util.Scanner;

/** 
 * @author Adriano Vaz
 * @author Nataliya Mykytas
 */

public class Main
{
	/**
	 * This client receives 5 rows. The first row has 3 characters. The second, third and fourth
	 * rows have 8 characters. The fifth row also has 3 characters.
	 * These rows, specify the cube configuration.
	 * The given characters represent the color (ex: O for orange, G for green, etc...) 
	 * The solved configuration can be:
	 * GGG
	 * WWWOYYYR
	 * WWWOYYYR
	 * WWWOYYYR
	 * BBB
	 * 
	 */
	public static void main(String [] args)
	{
		Scanner sc = new Scanner(System.in);
		String s = sc.next() + "00000" + sc.next() + sc.next() + sc.next() + sc.next() + "00000";
		sc.close();
		
		TCube tc = new TCube(s);
		Algorithm a = new Algorithm(tc);
		System.out.println(a.getMinSteps());
	}
}
