package de.mabe.patternsolver.util;

import de.mabe.patternsolver.model.Align;
import de.mabe.patternsolver.model.Line;
import de.mabe.patternsolver.model.Map;
import de.mabe.patternsolver.model.Status;

public class MapToStringUtil {
	public static MapToStringUtil instance = new MapToStringUtil();
	private Map map;
	private char[][] chars;
	private int maxValuesOnLeft;
	private int maxValuesOnTop;

	//          |  1     2
	//          |  2  2  2  1  3
	//----------|-----------------
	//        2 |  -  X  X  -  -
	//        3 |  X  X  X  -  -
	//        1 |  -  -  -  -  X
	//  1  1  1 |  X  -  X  -  X
	//     1  3 |  X  -  X  X  X
	public String mapToString( Map map ) {
		this.map = map;

		maxValuesOnLeft = getMaxCountOfValuesInLine( map.lines[Align.waag] );
		maxValuesOnTop = getMaxCountOfValuesInLine( map.lines[Align.senk] );
		createCharMap( getCharMapWidth(), getCharMapHeight() );

		printValues();
		printXs();
		printFrame();

		return charMapToString();
	}

	private void printValues() {
		// top
		for ( int x = 0; x < map.lines[Align.senk].length; x++ ) {
			Line line = map.lines[Align.senk][x];
			int y = maxValuesOnTop - line.values.length;
			for ( int value : line.values ) {
				char[] valueChar = get3CharInt( value );
				chars[maxValuesOnLeft * 3 + 2 + 3 * x + 0][y] = valueChar[0];
				chars[maxValuesOnLeft * 3 + 2 + 3 * x + 1][y] = valueChar[1];
				chars[maxValuesOnLeft * 3 + 2 + 3 * x + 2][y] = valueChar[2];
				y++;
			}
		}

		// left
		for ( int y = 0; y < map.lines[Align.waag].length; y++ ) {
			int x = 1 + 3 * (maxValuesOnLeft - map.lines[Align.waag][y].values.length);
			for ( int value : map.lines[Align.waag][y].values ) {
				char[] valueChar = get3CharInt( value );
				chars[x + 0][y + 1 + maxValuesOnTop] = valueChar[0];
				chars[x + 1][y + 1 + maxValuesOnTop] = valueChar[1];
				chars[x + 2][y + 1 + maxValuesOnTop] = valueChar[2];
				x += 3;
			}

		}
	}

	private char[] get3CharInt( int value ) {
		String str = "" + value;
		char[] valueChars = new char[3];
		valueChars[0] = valueChars[1] = valueChars[2] = ' ';
		switch ( str.length() ) {
		case 1:
			valueChars[2] = str.charAt( 0 );
			break;
		case 2:
			valueChars[1] = str.charAt( 0 );
			valueChars[2] = str.charAt( 1 );
			break;
		case 3:
			valueChars[0] = str.charAt( 0 );
			valueChars[1] = str.charAt( 1 );
			valueChars[2] = str.charAt( 2 );
			break;
		}

		return valueChars;
	}

	private void printXs() {
		for ( int x = 0; x < map.fields.length; x++ ) {
			for ( int y = 0; y < map.fields[0].length; y++ ) {
				char sign = ' ';
				if ( map.fields[x][y].status != Status.SELECTED ) sign = '-';
				if ( map.fields[x][y].status == Status.SELECTED ) sign = 'x';

				chars[maxValuesOnLeft * 3 + 4 + 3 * x][maxValuesOnTop + 1 + y] = sign;
			}
		}
	}

	private void printFrame() {
		// horizontal
		for ( int x = 0; x < chars.length; x++ ) {
			chars[x][maxValuesOnTop] = '-';
		}

		// vertical
		for ( int y = 0; y < chars[0].length; y++ ) {
			chars[maxValuesOnLeft * 3 + 1][y] = '|';
		}
	}

	private String charMapToString() {
		StringBuffer buffer = new StringBuffer();
		for ( int y = 0; y < chars[0].length; y++ ) {
			for ( int x = 0; x < chars.length; x++ ) {
				buffer.append( chars[x][y] );
			}
			buffer.append( "\n" );
		}
		return buffer.toString();
	}

	private void createCharMap( int charMapWidth, int charMapHeight ) {
		chars = new char[charMapWidth][];
		for ( int x = 0; x < charMapWidth; x++ ) {
			chars[x] = new char[charMapHeight];
			for ( int y = 0; y < charMapHeight; y++ ) {
				chars[x][y] = ' ';
			}
		}
	}

	private int getCharMapWidth() {
		return 2 + map.fields.length * 3 + 1 + 3 * maxValuesOnLeft;
	}

	private int getCharMapHeight() {
		return map.fields[0].length + 1 + maxValuesOnTop;
	}

	private int getMaxCountOfValuesInLine( Line[] lines ) {
		int max = 0;
		for ( Line line : lines ) {
			int sizeOfLine = line.values.length;
			if ( sizeOfLine > max ) max = sizeOfLine;
		}
		return max;
	}
}
