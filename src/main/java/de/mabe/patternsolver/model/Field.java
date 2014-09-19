package de.mabe.patternsolver.model;

public class Field {
	public Status status;
	public int x;
	public int y;

	public Field( int x, int y ) {
		status = Status.UNKNOWN;
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		return "" + status;
	}

}
