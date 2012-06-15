package util;

public class NonTerminal implements GrammarUnit, Comparable<NonTerminal> {
	public String name;

	public NonTerminal(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public int compareTo(NonTerminal o) {
		return name.compareTo(o.name);
	}
}
