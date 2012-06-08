import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ParserWriter {
	private Map<NonTerminal, Rules> rules;
	private NonTerminal start;
	private FirstFollowCounter ffc;

	private Set<Character> chars;

	public ParserWriter(Map<NonTerminal, Rules> rules, NonTerminal start,
			FirstFollowCounter ffc) {
		this.rules = rules;
		this.start = start;
		this.ffc = ffc;
		this.chars = new HashSet<Character>();
	}

	private void writeMethod(PrintWriter out, NonTerminal nt) {
		out.println("	private Node f_" + nt + "() throws ParseException {");
		out.println("		Node cur = new Node(\"" + nt + "\");");
		out.println("		switch (lex[curLex].text.charAt(0)) {");
		for (List<GrammarUnit> list : rules.get(nt)) {
			chars.clear();
			ffc.addAllFirst(chars, list, 0);
			if (chars.remove(FirstFollowCounter.EPS)) {
				chars.addAll(ffc.follow.get(nt));
			}
			for (char c : chars) {
				if (c == (char) -1) {
					out.println("		case (char) -1:");
				} else {
					out.println("		case '" + c + "':");
				}
			}
			for (GrammarUnit g : list) {
				if (g instanceof Terminal) {
					if (g.toString().isEmpty()) {
						continue;
					}
					out.println("			assertEquals(\"" + g + "\");");
					out.println("			cur.addChild(new Node(\"" + g + "\"));");
				} else {
					out.println("			cur.addChild(f_" + g + "());");
				}
			}
			out.println("			return cur;");
		}
		out.println("		default:");
		out.println("			throw new ParseException(lex[curLex]);");
		out.println("		}");
		out.println("	}");
	}

	public void writeFile(String className) throws FileNotFoundException {
		PrintWriter out = new PrintWriter("src/" + className + ".java");
		out.println("public class " + className + " {");
		out.println("	private Lexeme[] lex;");
		out.println("	private int curLex;");
		out.println();
		out.println("	public " + className + "(Lexeme[] lex) {");
		out.println("		this.lex = lex;");
		out.println("		this.curLex = 0;");
		out.println("	}");
		out.println();
		out
				.println("	public void assertEquals(String s) throws ParseException {");
		out.println("		if (lex[curLex].text.compareTo(s) != 0) {");
		out.println("			throw new ParseException(lex[curLex], s);");
		out.println("		}");
		out.println("		++curLex;");
		out.println("	}");
		for (NonTerminal nt : rules.keySet()) {
			out.println();
			writeMethod(out, nt);
		}
		out.println();
		out.println("	public Node getTree() throws ParseException {");
		out.println("		return f_" + start + "();");
		out.println("	}");
		out.println("}");
		out.close();
	}
}
