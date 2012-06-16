package util;

import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Set;

public class ParserGenerator {
	private FirstFollowCounter ffc;
	private ParserWriter pw;

	public ParserGenerator(Grammar g) {
		ffc = new FirstFollowCounter(g.start, g.rules);
		pw = new ParserWriter(g, ffc);
	}

	private void print(Map<NonTerminal, Set<Character>> what) {
		for (Map.Entry<NonTerminal, Set<Character>> e : what.entrySet()) {
			System.out.print(e.getKey() + ": ");
			boolean first = true;
			for (char c : e.getValue()) {
				if (!first) {
					System.out.print(", ");
				} else {
					first = false;
				}
				if (c == FirstFollowCounter.EPS) {
					System.out.print("eps");
				} else if (c == FirstFollowCounter.EOF) {
					System.out.print("eof");
				} else if (c < 32) {
					System.out.print((int) c);
				} else {
					System.out.print("'" + c + "'");
				}
			}
			System.out.println();
		}
	}

	public void printFirst() {
		System.out.println("FIRST");
		print(ffc.first);
		System.out.println();
	}

	public void printFollow() {
		System.out.println("FOLLOW");
		print(ffc.follow);
		System.out.println();
	}

	public void printIsLL1() {
		System.out.println("Grammar is " + (ffc.isLL1 ? "" : "not ") + "LL(1)");
	}

	public void writeFile(String name) {
		try {
			pw.writeFile(name);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}