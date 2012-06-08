public class Lexeme {
	public String text;
	public int lineNumber, charNumber;

	public Lexeme(String text, int lineNumber, int charNumber) {
		this.text = text;
		this.lineNumber = lineNumber;
		this.charNumber = charNumber;
	}
}
