import java.util.ArrayList;
import java.util.List;

public class NonTerminal implements GrammarUnit {
	private String name;
	private List<List<GrammarUnit>> children;

	public NonTerminal(String name) {
		this.name = name;
		children = new ArrayList<List<GrammarUnit>>();
	}

	@Override
	public String getName() {
		return name;
	}

	public List<List<GrammarUnit>> getChildren() {
		return children;
	}

	public void addChild(List<GrammarUnit> u) {
		children.add(u);
	}
}
