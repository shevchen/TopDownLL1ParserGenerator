import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Node {
	private String name;
	private List<Node> children;
	private int id;

	public Node(String name) {
		this.name = name;
		this.children = new ArrayList<Node>();
	}

	public String toString() {
		return name;
	}

	public List<Node> getChildren() {
		return children;
	}

	public void addChild(Node child) {
		children.add(child);
	}

	private int printDot(PrintWriter out, int id) {
		this.id = id;
		int maxNum = id;
		if (children.isEmpty()) {
			out.println("  subgraph {");
			out.println("    rank = max;");
			out.println("    v" + id + " [label=\"" + name
					+ "\" shape=box width=0.2];");
			out.println("  };");
		} else {
			out.println("  v" + id + " [label=\"" + name + "\"];");
			for (Node n : children) {
				maxNum = Math.max(maxNum, n.printDot(out, maxNum + 1));
				out.println("  v" + id + " -> v" + n.id + ";");
			}
		}
		return maxNum;
	}

	public void printAsDot(PrintWriter out) {
		out.println("digraph tree {");
		printDot(out, 0);
		out.println("}");
		out.close();
	}
}
