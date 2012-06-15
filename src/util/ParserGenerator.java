package util;

import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Set;

public class ParserGenerator {
	private FirstFollowCounter ffc;
	private ParserWriter pw;

	public ParserGenerator(Map<NonTerminal, Rules> rules, NonTerminal start) {
		ffc = new FirstFollowCounter(rules, start);
		pw = new ParserWriter(rules, start, ffc);
	}

	private void print(Map<NonTerminal, Set<Terminal>> what) {
		for (Map.Entry<NonTerminal, Set<Terminal>> e : what.entrySet()) {
			System.out.print(e.getKey() + ": ");
			boolean first = true;
			for (Terminal t : e.getValue()) {
				if (!first) {
					System.out.print(", ");
				} else {
					first = false;
				}
				System.out.print("[" + t + "]");
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

	public void writeFile(String name) {
		try {
			pw.writeFile(name);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}