package tasklist;

import java.util.Comparator;




public class SortByWidth implements Comparator<Tasks>
{

	public int compare(Tasks a, Tasks b)
	{
		if(a.getWidth() < b.getWidth())
			return -1;
		else if(a.getWidth() > b.getWidth())
			return 1;
		else
			return 0;
	}
}
