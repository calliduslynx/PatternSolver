package de.mabe.patternsolver.model;

public enum Status {
	SELECTED,
	DISABLED,
	UNKNOWN,
	UNCLEAR;

	public static Status get( boolean b ) {
		return b ? SELECTED : DISABLED;
	}

	@Override
	public String toString() {
		switch ( this ) {
		case SELECTED:
			return "X";
		case DISABLED:
			return "-";
		case UNKNOWN:
			return ".";
		case UNCLEAR:
			return "?";
		default:
			throw new RuntimeException();
		}
	}
}
