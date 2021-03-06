package util;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Node {
	private String name;
	private List<Node> children;
	private int id;
	private char realChar;

	public Node(String name, char realChar) {
		this.name = StringUtils.escape(name);
		this.realChar = realChar;
		this.children = new ArrayList<Node>();
	}
	
	public char getChar() {
		return realChar;
	}

	public String toString() {
		return name;
	}

	public void addChild(Node child) {
		children.add(child);
	}

	public Node getChild(int n) {
		return children.get(n);
	}

	public boolean isTerminal() {
		return children.isEmpty();
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

	public NonTerminal getFirst() {
		return new NonTerminal(children.get(1).children.get(0).toString());
	}
}