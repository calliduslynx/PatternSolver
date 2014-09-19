package de.mabe.patternsolver.model;

public class Possibility {
	public Line line;
	public Status[] selected;

	public Possibility( Line line ) {
		this.line = line;
		selected = new Status[line.fields.length];
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for ( Status selectedX : selected ) {
			builder.append( selectedX == null ? Status.UNCLEAR : selectedX );
		}
		return builder.toString();
	}
}
