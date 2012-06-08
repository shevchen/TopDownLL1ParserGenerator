public class ParseException extends Exception {
	public ParseException(Lexeme l, String s) {
		super("Parse error: expected '" + s + "', found '" + l.text
				+ "' at line " + l.lineNumber + ", character " + l.charNumber);
	}

	public ParseException(Lexeme l) {
		super("Parse error: unexpected '" + l.text + "' at line "
				+ l.lineNumber + ", character " + l.charNumber);
	}
}