import java.util.ArrayList;
import java.util.List;

public class NonTerminal implements GrammarUnit, Comparable<NonTerminal> {
	private String name;
	private List<List<GrammarUnit>> children;

	public NonTerminal(String name) {
		this.name = name;
		children = new ArrayList<List<GrammarUnit>>();
	}

	@Override
	public String toString() {
		return name;
	}

	public List<List<GrammarUnit>> getChildren() {
		return children;
	}

	public void addChild(List<GrammarUnit> u) {
		children.add(u);
	}

	@Override
	public int compareTo(NonTerminal o) {
		return name.compareTo(o.name);
	}
}
