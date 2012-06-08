public class Terminal implements GrammarUnit, Comparable<Terminal> {
	private int id;
	private String regex;

	public Terminal(int id, String regex) {
		this.id = id;
		this.regex = (regex == null) ? null : ("^" + regex);
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return regex;
	}

	@Override
	public int compareTo(Terminal o) {
		return id - o.id;
	}
}
