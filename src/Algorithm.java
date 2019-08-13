
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.PriorityQueue;

/** 
 * @author Adriano Vaz
 * @author Nataliya Mykytas
 */
public class Algorithm 
{	
	Comparator<IState> comparator = new stateComparator();
	PriorityQueue<IState> queue = new PriorityQueue<IState>(comparator);

	/**
	 * The constructor adds the state given by the user to the queue.
	 * @param Input from user that's supposed to be solved by the algorithm.
	 */
	public Algorithm(IState state)
	{
		queue.add(state);
	}

	/**
	 * Finds the minimum number of steps to achieve a desired solution, using a god's algorithm.
	 * @return Minimum number of steps required to achieve the desired solution (i.e. God's number). Returning -1 means solution not found.
	 */
	public int getMinSteps()
	{
		IState state;
		int result = -1;
		//int count = 0;
		while(true)
		{
			if (queue.isEmpty())
			{
				result = -1;
				break;
			}
			//count++;
			state = queue.poll();
			if (state.isSolved())
			{
				result = state.getStep();
				break;
			}
			else{
				if(state.getStep()+1<=8){
					queue = state.getSuccessors(queue);
				}
			}
		}
		//System.out.println(count);
		return result;
	}
}