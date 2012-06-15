package util;

public class ParseException extends Exception {
	public ParseException(String position, String found, String expected) {
		super("Parse error: expected " + StringUtils.quoted(expected)
				+ ", found " + StringUtils.quoted(found) + " at " + position);
	}

	public ParseException(String position, String found) {
		super("Parse error: unexpected " + StringUtils.quoted(found) + " at "
				+ position);
	}
}