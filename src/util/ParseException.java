package util;

public class ParseException extends Exception {
	public ParseException(String position, String expected, String found) {
		super("Parse error: expected " + expected + ", found " + found + " at "
				+ position);
	}

	public ParseException(String position, char found) {
		super("Parse error: unexpected " + found + " at " + position);
	}
}