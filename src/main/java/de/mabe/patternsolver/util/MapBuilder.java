package de.mabe.patternsolver.util;

import de.mabe.patternsolver.model.Align;
import de.mabe.patternsolver.model.Map;

public class MapBuilder {
	// 5x5:1.2/2/2.2/1/3/2/3/1/1.1.1/1.3
	public static Map parseSpecific( String specificMap ) {
		String size = specificMap.split( ":" )[0];
		int x = Integer.valueOf( size.split( "x" )[0] );
		int y = Integer.valueOf( size.split( "x" )[1] );

		// create map of correct size
		Map map = new Map( x, y );

		// fill in values
		String[] values = specificMap.split( ":" )[1].split( "/" );
		for ( int i = 0; i < x; i++ ) {
			map.lines[Align.senk][i].values = getIntArr( values[i] );
		}
		for ( int i = 0; i < y; i++ ) {
			map.lines[Align.waag][i].values = getIntArr( values[x + i] );
		}

		return map;
	}

	private static int[] getIntArr( String pointSeparatedValues ) {
		String[] values = pointSeparatedValues.split( "\\." );
		int[] arr = new int[values.length];
		for ( int i = 0; i < values.length; i++ ) {
			arr[i] = Integer.parseInt( values[i] );
		}

		return arr;
	}
}
