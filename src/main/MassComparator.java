package project5;

import java.util.*;


/**
 *Provides compariosn of Meteorite objects by checking their masses first. 
 *If their masses are same sorts according to their alphanumerical order.
 * 
 * @author Kaan Karakas
 *
 */
public class MassComparator implements Comparator<Meteorite> {
	@Override
	public int compare(Meteorite o1, Meteorite o2) {
		//uses getMass to get their mass to compare
		if (o1.getMass() > o2.getMass()) {
			return 1;
		} else if (o1.getMass() < o2.getMass()) {
			return -1;
		}
	    else {
			return o1.compareTo(o2);
		}
		
	}

}