package de.mabe.patternsolver;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.mabe.patternsolver.model.Line;
import de.mabe.patternsolver.model.Map;
import de.mabe.patternsolver.util.LineUtil;
import de.mabe.patternsolver.util.MapBuilder;

public class PossibilitiesForEachLineTest {
	//  |  1     2
	//          |  2  2  2  1  3
	//----------|-----------------
	//        2 |  -  X  X  -  -
	//        3 |  X  X  X  -  -
	//        1 |  -  -  -  -  X
	//  1  1  1 |  X  -  X  -  X
	//     1  3 |  X  -  X  X  X
	public static void main( String[] args ) {
		Map map = MapBuilder.parseSpecific( "5x5:1.2/2/2.2/1/3/2/3/1/1.1.1/1.3" );

		LineUtil.createPossibilities( prepareLine( 5, 1, 1, 1 ) );
		LineUtil.createPossibilities( prepareLine( 3, 1 ) );
		LineUtil.createPossibilities( prepareLine( 3, 2 ) );
		LineUtil.createPossibilities( prepareLine( 6, 2, 1 ) );
		LineUtil.createPossibilities( prepareLine( 5, 2, 1 ) );
		LineUtil.createPossibilities( prepareLine( 10, 2, 3, 1 ) );
		LineUtil.createPossibilities( prepareLine( 10, 2, 2 ) );
		LineUtil.createPossibilities( prepareLine( 15, 5, 6 ) );

		SimpleDateFormat format = new SimpleDateFormat( "dd.MM.yyyy HH:mm" );
		String formattedDate = format.format( new Date() );

	}

	private static Line prepareLine( int length, int... values ) {
		Line line = new Line( length );
		line.values = values;
		System.out.println( line );
		return line;
	}
}
