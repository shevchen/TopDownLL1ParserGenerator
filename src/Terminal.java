public class Terminal implements GrammarUnit, Comparable<Terminal> {
	private int id;
	private String name, regex;

	public Terminal(int id, String regex, String name) {
		this.id = id;
		this.name = name;
		this.regex = (regex == null) ? null : ("^" + regex);
	}

	public int getId() {
		return id;
	}

	public String getRegex() {
		return regex;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public int compareTo(Terminal o) {
		return id - o.id;
	}
}
