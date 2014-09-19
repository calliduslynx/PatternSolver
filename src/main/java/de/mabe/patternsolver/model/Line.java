package de.mabe.patternsolver.model;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Line {
	/**
	 * array with all numbers which are defined by game
	 */
	public int[] values;

	/**
	 * array which contains all fields contained by this line 
	 */
	public Field[] fields;

	/**
	 * list which contains all possibilities which are available for this line
	 */
	public List<Possibility> possibilities;

	public Line( int lenght ) {
		fields = new Field[lenght];
		possibilities = new LinkedList<Possibility>();
	}

	@Override
	public String toString() {
		return "Field: " + Arrays.toString( values ) + " ==> " + Arrays.toString( fields );
	}

}
