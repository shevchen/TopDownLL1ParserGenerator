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

	public boolean isLL1() {
		return ffc.isLL1;
	}

	private void print(Map<NonTerminal, Set<Character>> what) {
		for (Map.Entry<NonTerminal, Set<Character>> e : what.entrySet()) {
			System.out.print(e.getKey() + ": ");
			boolean first = true;
			for (Character c : e.getValue()) {
				if (!first) {
					System.out.print(", ");
				} else {
					first = false;
				}
				if (c == FirstFollowCounter.EPS) {
					System.out.print("eps");
				} else if (c == FirstFollowCounter.EOL) {
					System.out.print("eol");
				} else {
					System.out.print(c);
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

	public void writeFile(String name) throws NonLL1GrammarException {
		if (!ffc.isLL1) {
			throw new NonLL1GrammarException();
		}
		try {
			pw.writeFile(name);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}