public class ParseException extends Exception {
	public ParseException(Lexeme l) {
		super("Parse error at '" + l.text + "': line " + l.lineNumber
				+ ", character " + l.charNumber);
	}
}