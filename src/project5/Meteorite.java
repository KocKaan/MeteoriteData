package project5;

import java.text.DecimalFormat;
/**
* This class is responsible for the creation of Meteorite objects.
* After the creation of the mateorite it olso compares between each 
* other to sort the meteorites. It is olso responsible for the output
* structure of the meteorites when the que is created. This class 
* implements Comparabel<> interface to use to compareTo method.
* 
* @author Kaan Karakas
*/

public class Meteorite implements Comparable<Meteorite> {
	private String name;
	private int id;
	private int mass;
	private int year;
	private Location loc;
	
	/**
	* This methos is responsible of the creation of Meteorite object with only
	* name and id of it. It olso initializes the private name and id.
	*
	* @param It accepts string name and int id.
	*
	*
	* @throws IllegalArgumentException when name is null or empty and id is below 0;
	*/
	public Meteorite( String name, int id )throws IllegalArgumentException {
		if(name.equals(null)||name.isEmpty()) {
			throw new IllegalArgumentException("name is null or empty ");
	}else if(id<=0) {
		throw new IllegalArgumentException("id is less than 0");
	}	
		this.name=name;
		this.id=id;
	}
	/**
	* These methods are getters and setters for Meteorites variables such as
	* id, name , mass, year, location. 
	*
	* @param the setters parameter depend on the variable they need to assign
	*
	*
	* @throws IllegalArgumentException if mass is below 0 or year is over 2020
	*/
	    public String getName() {
	    	return name;
	    }
	    public int getId() {
	    	return id;
	    }
	    
		public void setMass(int mass) throws IllegalArgumentException{
			if(mass<=0) {
				throw new IllegalArgumentException("mass shouldn't be below 0");
			}
			this.mass=mass;
		}
		public void setYear(int year) throws IllegalArgumentException{
			if(year>=2020 || year<=0 ){
				throw new IllegalArgumentException("year should be below the current year 2020");
			}
			this.year= year;
		}
		public int getMass() {
			return mass;
		}
		public int getYear() {
			return year;
		}
		
		public void setLocation(Location loc) {
			this.loc=loc;
		
		}
		public Location getLocation() {
			return loc;
		}
		
		
		@Override
		public int compareTo(Meteorite o) {
			if(name!=o.name ) {
				return name.compareToIgnoreCase(o.name);
			}else {
				return Integer.compare(id,o.id);
			}
		}
		/**
		* This method is used for checking whether two object is same
		* If it is same it return false
		* If it is not same returns false
		*
		* @param Object in this case expected to be Meteorite
		*
		*/
		
		@Override
		public boolean equals(Object obj) {
			if(this==obj) 
				return true;
			//if obj null returns false
			if(obj==null)
				return false;
			//if obj is not isntance of Meteorite retuns false
			if(!(obj instanceof Meteorite))
				return false;
			
			//Instanciate obj as Meteorite object
			Meteorite gg= (Meteorite) obj;
			
			//Checks if name and id equals for both Meteorite if yes returns true
			if(this.name.equals(gg.getName().toLowerCase()) && this.id== gg.getId()) {
				return true;
			}else {
				return false;
			}	
		}
		
		/**
		* This is a override method and converts anything to String
		* The way the Meteorite objects are printed is formatted here.
		* By using getters the variables are formatted
		*0 is the key value for mass and year. Meteorites that were initialized with 
		*0 mass or year is empty on that section.
		*
		*/
		
	//name,id,year,mass,latitude, langtitude
		@Override
		public String toString() {
			String mass;
			String year;
			String latitude;
			String longitude;
			
			//if mass is 0 converts it to empty string
			if(getMass()==0) {
				 mass= String.valueOf(getMass());
				 mass="";
			}else {
				mass=String.valueOf(getMass());
			}
		
			//if the year is 0 converts it to empty string
			if(getYear()==0) {
				year= String.valueOf(getYear());
				year="";
			}else {
				year=String.valueOf(getYear());
			}
			
			//if Location object loc is null returns empty string for latitude and longitude
			if(loc==null ) {
			
				latitude="";	
				longitude="";
				
			}else {
				//to print atleast 5 more decimal points longitude and latitude.
				double lat=loc.getLatitude();
				double lon=loc.getLongitude();
				 DecimalFormat df= new DecimalFormat("#.00000");
				    latitude= df.format(lat);
				    longitude= df.format(lon);
				
			}
			//this is the format which the Meteorite will be printed
			return String.format("%-20s %4d %4s %6s %10s %10s",name,id,year,mass,latitude,longitude);
			
		}
		
		
		
		
		
		
	}

