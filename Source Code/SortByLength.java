package tasklist;
import java.util.Comparator;


public class SortByLength implements Comparator<Tasks>
{

	public int compare(Tasks a, Tasks b)
	{
		if(a.getLength() < b.getLength())
			return -1;
		else if(a.getLength() > b.getLength())
			return 1;
		else
			return 0;
	}
}
