import java.util.Comparator;

/** 
 * @author Adriano Vaz
 * @author Nataliya Mykytas
 */

/**
 * Comparator that will decide which State will have priority over others.
 */

public class stateComparator implements Comparator<IState>
{
	/**
	 * Once a state is added to the queue this function will decide
	 * the best position in the queue based on the function aval.
	 */
    @Override
    public int compare(IState x, IState y)
    {
        if (x.aval() < y.aval())
        {
            return -1;
        }
        if (x.aval() > y.aval())
        {
            return 1;
        }
        return 0;
    }
}