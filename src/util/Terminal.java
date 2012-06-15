package util;

public class Terminal implements GrammarUnit {
	public char from, to;

	public Terminal(char from, char to) {
		this.from = from;
		this.to = to;
	}

	@Override
	public String toString() {
		return (int) from + "-" + (int) to;
	}
}
