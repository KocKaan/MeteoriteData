package project5;

import java.util.ArrayList;
import java.util.Iterator;
/**
 * This class is responsible for creation of MeteoriteData object which when constructed
 * creates three BST with various comparatores. By creating three seperate BST the data 
 * is stored differently which will be used in case the user expects output according to 
 * mass, location or year. The methods that will be used it getByMass getByYear and 
 * getByLocation. 
 * 
 * @author Kaan Karakas
 *
 */

public class MeteoriteData {
	private BST<Meteorite> bst;
	
	//sorted according to mass
    private	BST<Meteorite> bst1;
	
	//sorted according to year.
	private BST<Meteorite> bst2;

	//constructor of the MeteoriteData
	MeteoriteData(){
		//alphanumerical order
		 bst= new BST<Meteorite>();
		
		//comparator with mass 
		 bst1= new BST<Meteorite>(new MassComparator());
		
		//comparator with year 
		 bst2= new BST<Meteorite>(new YearComparator());
	}
	/**
	 * Adds the specified element to three different trees if it is not already present. If this
	 * tree already contains the element, the call leaves the tree unchanged and
	 * returns false.This is done by calling the equals method at the BST Class.
	 * 
	 * @param data element to be added to this tree
	 * @return true if this tree did not already contain the specified element
	 * @throws NullPointerException if the specified element is null
	 */
	public boolean add ( Meteorite m) {
		if(m==null) {
			throw new NullPointerException("the meteorite is null");
		}
		//checks if the bst already contains the meteorite.
		if(bst.contains(m)||bst1.contains(m)||bst2.contains(m)) {
			return false;
		}else {
			bst.add(m);
		
			bst1.add(m);
			
			bst2.add(m);
		
		
		return true;
		}
	}
	
/////////////////////////////////////////////////
	/**
	* Compares the specified object with this tree for equality. Returns true if the give
	* object is also a tree, the two trees have the same size, and the inorder traversal of the
	* two trees returns the same nodes in the same order.
	* This operation is O(N). This is done by calling the equals method at the BST Class.
	* It first checks whether the obj is isntance of meteortireData
	*
	*
	* @param obj - object to be compared for equality with this tree
	*
	* @return true if the specified object is equal to this tree
	*
	*/
	
	public boolean equals ( Object obj) {
		//check fo rnull
		if(obj==null) {
			return false;
		}
		//check whether obj is isntance of MeteoriteData
		if (!(obj instanceof MeteoriteData)) {
			return false;
		}
		//converts to meteoData
		MeteoriteData meteodata = (MeteoriteData) obj;
		
		return this.bst.equals(meteodata.bst);
	}
/////////////////////////////////////////////////
	/**
	* This method should return a collection of all Meteorite objects with mass within delta grams of the specified mass.
	*Both values are specified in grams. The returned collection should be organized based on the mass from smallest 
	*to largest, and for meteorite objects with equal mass according to the natural ordering of the elements 
	*(i.e., dictated by the comparaTo method defined in the Mereorite class)This method should perform in O(K+H) in 
	* which K is the number of Meteorite objects in the returned collection and H is the height of the tree representing 
	* this collection
	*
	* @param mass- which is the desired mass entered by the user.
	* delta- is the range that the program will look after.
	*
	* @return If there are no elements in the collection that match the given criteria, this method should return null.
	* Otherwise returns MeteoriteData holding the BST.
	*
	* @throws IllegalArgumentException if delta<0 or mass<0
	*/
	public MeteoriteData getByMass ( int mass, int delta) {
		if(delta<0 ||mass<0) {
			throw new IllegalArgumentException("The delta or mass cant be below 10");
		}
		//check the bst for emptiness.
		if(bst.isEmpty()) {
			return null;
		}
		
		
		//create 2 dummy meteroite 
		Meteorite meteo1= new Meteorite("\u0000", 1);
		Meteorite meteo2= new Meteorite("\uffff", 99999999);
        
		//creares an MeteoriteData that will be used to set te dummy meteorites.
		MeteoriteData massData= new MeteoriteData();
		meteo1.setMass(mass-delta);
		meteo2.setMass(mass+delta);
	
	    //getRange will return the desired array list of meteorites
		ArrayList<Meteorite> list=bst1.getRange​(meteo1,meteo2);
		
		//if the list is empty that means no meteorites are qualified in the range
        if(list.size()==0) {
			return null;
		}
		//adds the meterotiees on the array list to massData trees.
		for(Meteorite m:list) {
			massData.add(m);
		}

		return massData;
	}
	
/////////////////////////////////////////////////
	/**
	* This method is responsible for giving back a meteorite object which is closest 
	* to the entered location. This method utilizes the getDistane() method which 
	* calculates the distance between two locations thus, the program could compare the 
	* distance between all location to the expected location.
	*
	* @param it accepts Location object 
	*
	* @throws IllegalArgumentException if Location object is null
	*/
	public Meteorite getByLocation ( Location loc) {
		if(loc==null) {
			throw new IllegalArgumentException("The location cant be null");
		}
		//creates two dummy meteos
		Meteorite meteo1= new Meteorite("\u0000", 3);
		Meteorite meteo2= new Meteorite("\uffff", 2);
		
		//the close meteo var is used to hold the smallest distance.
		Meteorite closeMeteorite = null;
		double closest=100000;
	
		//returns an array of the meteorirtes that fit the range 
		ArrayList<Meteorite> list=bst.getRange​(meteo1,meteo2);
		
		//for loop for iterating throught the list
		for(Meteorite meteo:list) {
			double currentDistance = 0;
			
			//if not null current distance will be given by the get distance.
			if(meteo.getLocation()!=null) {
			 currentDistance= loc.getDistance(meteo.getLocation());
			}
			
			//temporarrily hold the meteorite with shortest distance 
			if(closest>currentDistance ) {
				closest=currentDistance;
				closeMeteorite=meteo;

			}
		}
		//return meteo if not null
		if(closeMeteorite==null) {
			return null;
		}
		return closeMeteorite;
	}
		
	/**
	* This method should return a collection of all Meteorite objects that landed on Earth on the year specified.
	*  The returned collection should be organized based on the year from earliest to most recent, and for meteorite 
	*  objects with the same year according to the natural ordering of the elements (i.e., dictated by the comparaTo
	*   method defined in the Mereorite class). This method should perform in O(K+H) in which K is the number of Meteorite 
	*   objects in the returned collection and H is the height of the tree representing this collection
	*
	* @param int yaear wanted by the user.
	*
	* @return MeteoriteData with the expected meteroites.
	*
	* @throws IllegalArgumentException if the year is below 0
	*/
		
	public MeteoriteData getByYear( int year) {
		if(year<0)  {
			throw new IllegalArgumentException("THe year cant be below 0 ");
		}
		
		    //create 2 dummy meteroite 
				Meteorite meteo1= new Meteorite("\u0000", 1);
				Meteorite meteo2= new Meteorite("\uffff", 999999);
		        
				//creares an MeteoriteData that will be used to set te dummy meteorites.
				MeteoriteData yearData= new MeteoriteData();
			
				meteo1.setYear(year);
				meteo2.setYear(year);
			
	 	   //getRange will return the desired array list of meteorites
				ArrayList<Meteorite> list=bst2.getRange​(meteo1,meteo2);
			
                if(list.size()==0) {
					
					return null;
				}
				
              //adds the meterotiees on the array list to massData trees.
				for(Meteorite m:list) {
					yearData.add(m);
				}
				

				return yearData;
	}
	
	/**
	* iterator used for calling the iterator at BST class.
	* This particular iterator is for the alphanumerically sorted bst.
	*
	*
	* @return returns iterator called from the BST class.
	*
	*/
	public Iterator<Meteorite> iterator(){
		return bst.iterator();
	}
	/**
	* iterator used for calling the iterator at BST class.
	* This particular iterator is for the mass sorted bst.
	*
	*
	* @return returns iterator called from the BST class.
	*
	*/
	public Iterator<Meteorite> massIterator(){
		return bst1.iterator();
	}
	/**
	* iterator used for calling the iterator at BST class.
	* This particular iterator is for the year sorted bst.
	*
	*
	* @return returns iterator called from the BST class.
	*
	*/
	public Iterator<Meteorite> yearIterator(){
		return bst2.iterator();
	}
	
	/**
	* This method is responsible dor removing desire meteorite from all of the all of the bst's 
	* This is done by calling the remove method at BST class. 
	*
	* @param meteorite that wants to be removed
	*
	* @return returns true if meteo is deleted false if not
	*
	* @throws NullPointerException if the meteorite is null
	*/
	public boolean remove ( Meteorite m) {
		if(m==null) {
			throw new NullPointerException("the meteorite is null");
		}
		//checks if the metorite is present in the bst's 
		if(!(bst.contains(m)||bst1.contains(m)||bst2.contains(m))) {
			return false;
		}
		//remove from all of them
		 bst.remove(m);
		 bst1.remove(m);
		 bst2.remove(m);
		 
		 return true;
	
	}
	/**
	* This method is resbonsible for returning an string of a bst. 
	* This is done by calling the toString method at BSt class.
	*
	*
	* @return String of desired the bst's nodes
	*
	*/
	public String toString() {
		return bst.toString();
	}

}
