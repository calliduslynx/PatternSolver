package de.mabe.patternsolver;

import java.util.ArrayList;
import java.util.List;

import de.mabe.patternsolver.model.Align;
import de.mabe.patternsolver.model.Field;
import de.mabe.patternsolver.model.Line;
import de.mabe.patternsolver.model.Map;
import de.mabe.patternsolver.model.Possibility;
import de.mabe.patternsolver.model.Status;
import de.mabe.patternsolver.util.LineUtil;

public class PatternSolver {
	private Map map;

	public PatternSolver( Map map ) {
		this.map = map;
	}

	public void solve() {
		createPossibilities();
		findDecidedFields();
	}

	private void createPossibilities() {
		for ( int align : Align.both ) {
			for ( Line line : map.lines[align] ) {
				LineUtil.createPossibilities( line );
			}
		}
	}

	private void findDecidedFields() {
		boolean somethingChanged;

		do {
			somethingChanged = false;
			for ( int align : Align.both ) {
				for ( Line line : map.lines[align] ) {
					somethingChanged |= findDecidedFieldsForLine( line );
				}
			}
		} while ( somethingChanged );
	}

	private boolean findDecidedFieldsForLine( Line line ) {
		boolean somethingChanged = false;

		fieldIteration: for ( int fieldIndex = 0; fieldIndex < line.fields.length; fieldIndex++ ) {
			Field field = line.fields[fieldIndex];
			if ( field.status != Status.UNKNOWN ) continue;

			Status tempStatus = Status.UNKNOWN;

			// ***** fill temp status based on possibilities
			for ( Possibility possibility : line.possibilities ) {
				Status targetStatus = possibility.selected[fieldIndex];

				if ( tempStatus == Status.UNKNOWN ) { // if no set before set to given one
					tempStatus = targetStatus;
				} else if ( tempStatus != targetStatus ) { // if not the same (UNCLEAR or other) set to unclear
					continue fieldIteration;
				} // otherwise keep it
			}

			// ***** set field status
			setFieldStatus( field, tempStatus );
			somethingChanged = true;
		}

		return somethingChanged;
	}

	private void setFieldStatus( Field field, Status tempStatus ) {
		// set field
		field.status = tempStatus;

		List<Possibility> possibilitiesToRemove = new ArrayList<Possibility>();

		// remove vertical possibilities
		for ( Possibility possibility : map.lines[Align.senk][field.x].possibilities ) {
			if ( possibility.selected[field.y] != field.status ) {
				possibilitiesToRemove.add( possibility );
			}
		}
		map.lines[Align.senk][field.x].possibilities.removeAll( possibilitiesToRemove );

		// remove horizontal possibilities
		possibilitiesToRemove.clear();
		for ( Possibility possibility : map.lines[Align.waag][field.y].possibilities ) {
			if ( possibility.selected[field.x] != field.status ) {
				possibilitiesToRemove.add( possibility );
			}
		}
		map.lines[Align.waag][field.y].possibilities.removeAll( possibilitiesToRemove );

	}
}
