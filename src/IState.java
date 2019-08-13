/** 
 * @author Adriano Vaz
 * @author Nataliya Mykytas
 */

import java.util.Collection;
import java.util.List;
import java.util.PriorityQueue;

public interface IState
{
	boolean isSolved();
	int getStep();
	public int getWrong();
	public int aval();
	public void countWrong();
	public PriorityQueue<IState> getSuccessors(PriorityQueue<IState> queue);
}
