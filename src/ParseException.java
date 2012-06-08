public class ParseException extends Exception {
	public ParseException(int lineNum, int charNum, String regex) {
		super("Parse error: pattern [" + regex + "] not found at line "
				+ lineNum + ", character " + charNum);
	}

	public ParseException(int lineNum, int charNum, char c) {
		super("Parse error: unexpected symbol '"
				+ (c == FirstFollowCounter.EOF ? "eof" : c) + "' at line "
				+ lineNum + ", character " + charNum);
	}
}