package util;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Node {
	private String name;
	private List<Node> children;
	private int id;

	private static int termId;

	public Node(String name) {
		this.name = name;
		this.children = new ArrayList<Node>();
	}

	public String toString() {
		return name;
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

	private void addAllChildren(List<GrammarUnit> units) {
		Node left = children.get(0);
		if (left.children.isEmpty()) {
			return;
		}
		String uName = left.children.get(0).name;
		if ("Term".equals(left.name)) {
			uName = uName.substring(1, uName.length() - 1);
			if (uName.isEmpty()) {
				units.add(FirstFollowCounter.epsTerm);
			} else {
				units.add(new Terminal(termId++, uName, uName));
			}
		} else {
			units.add(new NonTerminal(uName));
		}
		if (children.size() > 1) {
			children.get(1).addAllChildren(units);
		}
	}

	private void addRules(Map<NonTerminal, Rules> map) {
		if (children.isEmpty()) {
			return;
		}
		if ("Rule".equals(name)) {
			NonTerminal left = new NonTerminal(children.get(0).children.get(0)
					.toString());
			Rules cur = map.get(left);
			if (cur == null) {
				cur = new Rules();
			}
			List<GrammarUnit> units = new ArrayList<GrammarUnit>();
			children.get(2).addAllChildren(units);
			cur.add(units);
			map.put(left, cur);
		}
		for (Node n : children) {
			n.addRules(map);
		}
	}

	public Map<NonTerminal, Rules> toRules() {
		Map<NonTerminal, Rules> map = new TreeMap<NonTerminal, Rules>();
		termId = 1;
		addRules(map);
		return map;
	}

	public NonTerminal getFirst() {
		return new NonTerminal(children.get(1).children.get(0).toString());
	}
}
