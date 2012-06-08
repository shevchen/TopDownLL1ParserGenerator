import java.util.ArrayList;
import java.util.List;

public class Node {
	private String name;
	private List<Node> children;

	public Node(String name) {
		this.name = name;
		this.children = new ArrayList<Node>();
	}

	public String getName() {
		return name;
	}

	public List<Node> getChildren() {
		return children;
	}

	public void addChild(Node child) {
		children.add(child);
	}
}
