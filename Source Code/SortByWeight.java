package tasklist;
import java.util.Comparator;



public class SortByWeight implements Comparator<Tasks>
{
	

	public int compare(Tasks a, Tasks b)
	{
		if(a.getWeight() < b.getWeight())
			return -1;
		else if(a.getWeight() > b.getWeight())
			return 1;
		else
			return 0;
	}
}
