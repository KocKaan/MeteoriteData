package project5;


/**
* This class is used for creating the Location object. The location is created 
* by the latitude and longitude of the meteorite. The location represents
* the location of the meteorite.
* 
* @author Kaan Karakas
*/

public class Location {
	
	private double latitude;
	private double longitude;

	/**
	* Creates the location object and initializes private latitude and longitude
	*
	* @param double double latitude and longitude
	*
	* @return returns nothing just initializes the latitude and longitude
	*
	* @throws IllegalArgumentException latitude not in range [-90.0, 90.0] or longitude not in range [-180.0, 180.0]
	*/
	public Location (double latitude, double longitude) {
		if( -90.0> latitude || latitude>90.0) {
	   	throw new IllegalArgumentException("latitude is not in the range of [-90.0, 90.0]");
		}
		if(-180.0> longitude ||longitude>180.0) {
			throw new IllegalArgumentException("longitude is not in the range of [-180.0, 180.0]");
		}
		this.latitude=latitude;
		this.longitude=longitude;
	}
	/**
	* This method calculates the distance between two location
	*
	* @param Location object
	*
	* @return the distance between this location and the one provided as the parameter.
	*
	* @throws IllegalArgumentException if Location object is null
	*/
	
	public double getDistance( Location loc ) {
		
		if(loc==null) {
			throw new IllegalArgumentException("the location is null check");
		}
		// distance between latitudes and longitudes
		double dLat = Math.toRadians(this.getLatitude() - loc.latitude);
		double dLon = Math.toRadians(this.getLongitude() - loc.longitude);
                                               //lon2  lon1
		// convert to radians
	     double lat2,lat1;
		 lat1 = Math.toRadians(loc.latitude);
		 lat2= Math.toRadians(this.getLatitude());
		// apply formulae
		 
		double a = Math.pow(Math.sin(dLat / 2), 2) +
				   Math.pow(Math.sin(dLon / 2), 2) *
				   Math.cos(lat1) *
				   Math.cos(lat2);
		double rad = 6371;
		double c = 2 * Math.asin(Math.sqrt(a));
		return rad * c;
	}
	
	/**
	* These are the mutator getters for the latitude and longitude.
	* Used for accessing private latitude and longitude
	*
	*
	* @return returns the latitude and longitude variables
	*
	*/
	
    public double getLatitude() {
    	return latitude;   	
    }
    
    public double getLongitude() {
    	return longitude;
    }
    /**
	* This methods overrides and sets what is considered as equal.
	* This method olso equalizes location if the distance between two location
	* is really close.
	*
	* @param Accepts any object but used for location pbject
	*
	* @return returns true all false to determine if two object is equal
	*
	*/

	@Override
	public boolean equals(Object obj) {
		Location loc= (Location) obj;
	//sets false if obj is null
		if(obj==null) {
			return false;
		}
		//gets the latitud and longitude
		double lati= loc.getLatitude();
		double longi= loc.getLongitude();
	
		//check whether two object is precisely at same location
		if(this.latitude==lati&&this.longitude==longi) {
			return true;
		}
		//determines the margin which two locaion will be considered as same
			if(Math.abs(this.latitude - lati) < 0.00001 &&
					Math.abs(this.longitude - longi) < 0.00001) {
				return true;
		}else {
			return false;	
	}
    
}
}
