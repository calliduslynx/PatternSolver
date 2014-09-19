package de.mabe.patternsolver.util;

import java.util.ArrayList;
import java.util.List;

import de.mabe.patternsolver.model.Field;
import de.mabe.patternsolver.model.Line;
import de.mabe.patternsolver.model.Possibility;
import de.mabe.patternsolver.model.Status;

public class PossibilityFinder {
	public static void createPossibilities( Line line ) {
		new PossibilityFinder( line ).run();
	}

	private Line line;
	private int[] values;
	private List<FieldWithBoolean> fieldValues = new ArrayList<FieldWithBoolean>();
	static int possibilityCount = 0;

	public PossibilityFinder( Line line ) {
		this.line = line;

		for ( Field field : line.fields ) {
			fieldValues.add( new FieldWithBoolean( field ) );
		}

		values = line.values;
	}

	public void run() {
		findPossibilitiesInternal( 0, 0 );
	}

	private void findPossibilitiesInternal( int currentPosition, int currentValueIndex ) {
		int currentValue = values[currentValueIndex];

		// calculate rest blocked by following values
		int blockedRest = 0;
		for ( int i = currentValueIndex + 1; i < values.length; i++ ) {
			blockedRest += 1 + values[i];
		}

		for ( int pos = currentPosition; pos < fieldValues.size() - currentValue - blockedRest + 1; pos++ ) {
			select( false, currentPosition, pos - currentPosition );
			select( true, pos, currentValue );

			if ( currentValueIndex + 1 == values.length ) { // this is the last value
				select( false, pos + currentValue, fieldValues.size() - pos - currentValue );
				addPossibilityToList();

			} else { // there is at least one more value
				select( false, pos + currentValue, 1 );
				findPossibilitiesInternal( pos + currentValue + 1, currentValueIndex + 1 );
			}
		}
	}

	private void select( boolean setTo, int startPos, int length ) {
		int endPos = startPos + length;
		for ( int i = startPos; i < endPos; i++ ) {
			fieldValues.get( i ).selected = setTo;
		}
	}

	private void addPossibilityToList() {
		Possibility possibility = new Possibility( line );
		int i = 0;
		for ( FieldWithBoolean fieldWB : fieldValues ) {
			possibility.selected[i] = Status.get( fieldWB.selected );
			i++;
		}
		line.possibilities.add( possibility );
	}

	class FieldWithBoolean {
		public FieldWithBoolean( Field field ) {
			this.field = field;
		}

		Field field;
		boolean selected = false;
	}
}
