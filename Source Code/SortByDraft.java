package tasklist;

import java.util.Comparator;



public class SortByDraft implements Comparator<Tasks>
{

	public int compare(Tasks a, Tasks b)
	{
		if(a.getDraft() < b.getDraft())
			return -1;
		else if(a.getDraft() > b.getDraft())
			return 1;
		else
			return 0;
	}
}
