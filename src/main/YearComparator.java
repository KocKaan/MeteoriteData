package project5;

import java.util.Comparator;

/**
 * Compares Meteorite objects using according to their years. 
 * if their years are same sorted according to their alphanumerical order.
 * @author Kaan Karakas
 *
 */

public class YearComparator implements Comparator<Meteorite> {
	@Override
	public int compare(Meteorite o1, Meteorite o2) {
		//gets meteorite objects year by getYear method.
		if (o1.getYear() > o2.getYear()) {
			return 1;
		} else if (o1.getYear() < o2.getYear()) {
			return -1;
		}
		else {
			return o1.compareTo(o2);
		}
		
	}

}
