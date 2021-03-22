package project5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * This class is the program which finds the fallen stars(meteorites) The
 * program is interactive When the program is executed the name of the input
 * file containing the list of all the fallen meteorites is provided as the
 * program's single line command line argument. The data in this file serves as
 * a database of all the fallen meteorites with their name, id, nametype,
 * recclass, mass,fall year,reclat,reclong and Geolocation. However this class
 * only reads and stores name, id, mass,reclat and reclong. In the interactive
 * part, the program should run in a loop that allows the user to issue
 * different queries. The three options are Location LATITUDE LONGITUDE, year
 * YEAR, mass MASS. The program will give back a a list meteorites with their
 * stored information if there is a valid meteorite for that.
 * 
 * @author Kaan Karakas
 *
 */
//meteoriteDataMisc
//getter setters
//bstmisc
//FallenStarsRUN
//
public class FallenStars {

	public static void main(String[] args) {

		// verify that the command line argument exists
		if (args.length == 0) {
			System.err.println("Usage Error: the program expects file name as an argument.\n");
			System.exit(1);
		}

		// verify that command line argument contains a name of an existing file
		File meteorFile = new File(args[0]);
		if (!meteorFile.exists()) {
			System.err.println("Error: the file " + meteorFile.getAbsolutePath() + " does not exist.\n");
			System.exit(1);
		}
		if (!meteorFile.canRead()) {
			System.err.println("Error: the file " + meteorFile.getAbsolutePath() + " cannot be opened for reading.\n");
			System.exit(1);
		}

		// open the file for reading
		Scanner meteData = null;

		// the catch is in case the file couldnt be read
		try {
			meteData = new Scanner(meteorFile, "UTF-8");
		} catch (FileNotFoundException e) {
			System.err.println("Error: the file " + meteorFile.getAbsolutePath() + " cannot be opened for reading.\n");
			System.exit(1);
		}

		// Creates an meteoriteData object thus the class has access to three BST and
		// their methods.
		MeteoriteData list = new MeteoriteData();
		String textLine = null;

		meteData.nextLine();
		ArrayList<String> lines;
		// this loop is used to parse through whole file while there is line left
		while (meteData.hasNext()) {

			try {
				textLine = meteData.nextLine();

				// the lines are converted to string and stored inside the arraylist
				lines = splitCSVLine(textLine);
			} catch (NoSuchElementException ex) {
				// catches NoSuchElementException
				System.err.println(textLine);
				continue;
			}

			try {
				// the name values of meteorites should be stored in the first index of the
				// array
				String name = lines.get(0);

				// the iid section of meteorites is stored in the second index of the array

				int id = Integer.parseInt(lines.get(1));

				// by using the name and id the meteorite object is created
				Meteorite meteo = new Meteorite(name, id);

				try {
					// these if statemenst are used to throw the decimal part of the number
					// because mass should be integer. Then it converted to a string to Set mass
					// I set mass 0 to if it is emty to handle 0 at my toString method at meteorite
					// class
					if (!lines.get(4).equals("")) {
						if (lines.get(4).contains(".")) {
							int ind = lines.get(4).indexOf(".");
							String sty = lines.get(4).substring(0, ind);
							meteo.setMass(Integer.parseInt(sty));
						} else {
							meteo.setMass(Integer.parseInt(lines.get(4)));
						}
					} else {
						meteo.setMass(0);
					}

				} catch (IllegalArgumentException ex) {

				}

				try {
					// this is used for getting the data out of the array and substring only the
					// year stating part.If it is empty I set it to 0 to handle it afterwards at
					// toString class
					String yearForm = lines.get(6);
					if (!yearForm.isEmpty()) {
						int year = Integer.parseInt(yearForm.substring(6, 10));
						meteo.setYear(year);
					} else {
						meteo.setYear(0);
					}
				} catch (IllegalArgumentException ex) {

				}

				// It is used to get the longitude and latitude out of arrayList.
				// if it is empty I sent it as null;
				String strLatitude = lines.get(7);
				String strLongitude = lines.get(8);
				if (!(strLatitude.isEmpty() && strLongitude.isEmpty())) {

					// the wanted parts of the line is parsed to double to set is as a location
					Double latitude = Double.parseDouble(lines.get(7));
					Double longitude = Double.parseDouble(lines.get(8));
					Location loc = new Location(latitude, longitude);
					meteo.setLocation(loc);
				}

				// In the end add the Meteorite meteo to the array List
				list.add(meteo);

			} catch (IllegalArgumentException ex) {

			}
		}

		// Interactive part starts from here

		Scanner userInput = new Scanner(System.in);
		String userValue = "";
		String firstPart = "";
		String secondPart = "";
		String thirdPart = "";

		// the text the user sees.
		do {
			System.out.println("Search the database by using one of the following queries.\n"
					+ " To search for meteorite nearest to a given goe-location, enter\n"
					+ "location LATITUDE LONGITUDE\n" + "To search for meteorites that fell in a given year, enter\n"
					+ "year YEAR\n" + "To search for meteorites with weights MASS +/- 10 grams, enter\n" + "mass MASS\n"
					+ "To finish the program, enter\n" + "quit\n\n\n" + "Enter your search query:");

			// this reads the user input
			userValue = userInput.nextLine();

			// this splits the sentence to their words bye detecting empty strings and add
			// tehm to array
			String[] sp = userValue.split(" ");

			// first part is the first index of array
			firstPart = sp[0];

			// trims the words
			String trim = userValue.trim();

			// counts the amount of words in the sentence
			int counter = trim.split("\\s+").length;

			// if there are two words only gets the second word
			if (counter == 2) {
				secondPart = sp[1];

			}
			// if there are three words gets the second and third element in the array
			if (counter == 3) {
				secondPart = sp[1];
				thirdPart = sp[2];
			}
			// checks for the validity of the query
			if (!(firstPart.equals("mass") || firstPart.equals("location") || firstPart.equals("year"))) {
				System.out.println("invalid query");
			}

			// this prints the list with the wanted mass searching for the second word
			// The meteorites in the range of delta is collected from BST by using getByMass
			// method
			// The obtained MeteoData will be iterated throught the usafe of BST iterator
			// created at BST class
			if (firstPart.equals("mass")) {

				// Iteratetor that iterates through meteorite holding BST. The getByMass returns
				Iterator<Meteorite> massItr = list.getByMass(Integer.parseInt(secondPart), 10).massIterator();
				// if the meteorite is not null by checking wheteher there is another element in
				// the BST the iterator iterates
				// throught the BST and prints out the next Meteorite
				if (massItr != null) {
					while (massItr.hasNext()) {

						System.out.println(massItr.next());
					}
				}
			}

			// this prints the list with the wanted year.
			else if (firstPart.equals("year")) {

				Iterator<Meteorite> yearItr = list.getByYear(Integer.parseInt(secondPart)).yearIterator();
				if (yearItr != null) {
					while (yearItr.hasNext()) {
						System.out.println(yearItr.next());
					}
				}
			}
			// this prints the list with the wanted location near the stated parameters
			else if (firstPart.equals("location")) {
				//the obtained input from user is converted to double to be used to create
				//Location object which is then used to use getByLocation method

				Meteorite meteo;
				Double latitude = Double.parseDouble(secondPart);
				Double longitude = Double.parseDouble(thirdPart);

				System.out.println(latitude + " " + longitude);
				Location loc = new Location(latitude, longitude);
				
				meteo = list.getByLocation(loc);
				System.out.println(meteo);
			}

			// if user enters quit the system closes
		} while (!userValue.equalsIgnoreCase("quit"));

		userInput.close();
	}

	/**
	 * Splits the given line of a CSV file according to commas and double quotes
	 * (double quotes are used to surround multi-word entries so that they may
	 * contain commas)
	 * 
	 * @author Joanna Klukowska
	 * @param textLine a line of text from a CSV file to be parsed
	 * @return an ArrayList object containing all individual entries found on that
	 *         line; any missing entries are indicated as empty strings; null is
	 *         returned if the textline passed to this function is null itself.
	 */

	public static ArrayList<String> splitCSVLine(String textLine) {

		if (textLine == null)
			return null;

		ArrayList<String> entries = new ArrayList<String>();
		int lineLength = textLine.length();
		StringBuffer nextWord = new StringBuffer();
		char nextChar;
		boolean insideQuotes = false;
		boolean insideEntry = false;

		// iterate over all characters in the textLine
		for (int i = 0; i < lineLength; i++) {
			nextChar = textLine.charAt(i);

			// handle smart quotes as well as regular quotes
			if (nextChar == '"' || nextChar == '\u201C' || nextChar == '\u201D') {

				// change insideQuotes flag when nextChar is a quote
				if (insideQuotes) {
					insideQuotes = false;
					insideEntry = false;
				} else {
					insideQuotes = true;
					insideEntry = true;
				}
			} else if (Character.isWhitespace(nextChar)) {
				if (insideQuotes || insideEntry) {
					// add it to the current entry
					nextWord.append(nextChar);
				} else { // skip all spaces between entries
					continue;
				}
			} else if (nextChar == ',') {
				if (insideQuotes) { // comma inside an entry
					nextWord.append(nextChar);
				} else { // end of entry found
					insideEntry = false;
					entries.add(nextWord.toString());
					nextWord = new StringBuffer();
				}
			} else {
				// add all other characters to the nextWord
				nextWord.append(nextChar);
				insideEntry = true;
			}

		}
		// add the last word ( assuming not empty )
		// trim the white space before adding to the list
		if (!nextWord.toString().equals("")) {
			entries.add(nextWord.toString().trim());
		}

		return entries;
	}
}
