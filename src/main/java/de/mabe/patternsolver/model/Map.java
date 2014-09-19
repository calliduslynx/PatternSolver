package de.mabe.patternsolver.model;

import de.mabe.patternsolver.util.MapToStringUtil;

public class Map {
	public Line[][] lines;
	public Field[][] fields;

	public Map( int x, int y ) {
		// ***** create fields
		fields = new Field[x][];
		for ( int i = 0; i < x; i++ ) {
			fields[i] = new Field[y];
			for ( int j = 0; j < y; j++ ) {
				fields[i][j] = new Field( i, j );
			}
		}

		// ***** create lines
		lines = new Line[2][];
		lines[Align.senk] = new Line[x];
		lines[Align.waag] = new Line[y];

		// ***** create senk lines
		for ( int i = 0; i < x; i++ ) {
			Line line = new Line( y );
			lines[Align.senk][i] = line;
			for ( int j = 0; j < y; j++ ) {
				line.fields[j] = fields[i][j];
			}
		}

		// ***** create waag lines
		for ( int i = 0; i < y; i++ ) {
			Line line = new Line( x );
			lines[Align.waag][i] = line;
			for ( int j = 0; j < x; j++ ) {
				line.fields[j] = fields[j][i];
			}
		}
	}

	@Override
	public String toString() {
		return MapToStringUtil.instance.mapToString( this );
	}

}
