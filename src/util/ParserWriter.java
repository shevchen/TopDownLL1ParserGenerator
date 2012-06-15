package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

public class ParserWriter {
	private Grammar g;
	private FirstFollowCounter ffc;
	private Set<Character> temp;
	private StringBuilder sb;

	public ParserWriter(Grammar g, FirstFollowCounter ffc) {
		this.g = g;
		this.ffc = ffc;
		temp = new HashSet<Character>();
		sb = new StringBuilder();
	}

	private void processRule(PrintWriter out, Rule r) {
		for (Pair<GrammarUnit, String> p : r.right) {
			if (p.first instanceof Terminal) {
				out.println("cur.addChild(new Node(\""
						+ ((Terminal) p.first).toString().replaceAll("\"",
								"\\\\\"") + "\"));");
			} else {

			}
		}
	}

	private void writeMethod(PrintWriter out, NonTerminal nt) {
		out.println("	private Node f_" + nt + "(C_" + nt
				+ " arg_0) throws ParseException {");
		out.println("		Node cur = new Node(\"" + nt + "\");");
		for (Rule r : g.rules.get(nt)) {
			temp.clear();
			ffc.addAllFirst(temp, r, 0);
			if (temp.remove(FirstFollowCounter.EPS)) {
				temp.addAll(ffc.follow.get(nt));
			}
			Character[] chars = temp.toArray(new Character[temp.size()]);
			sb.setLength(0);
			sb.append('{');
			for (int i = 0; i < chars.length; ++i) {
				if (i > 0) {
					sb.append(',');
				}
				sb.append(FileScanner.bestView(chars[i]));
			}
			sb.append('}');
			out.println("		for (char c : new char[]" + sb.toString() + ") {");
			out.println("			if (c == curChar) {");
			processRule(out, r);
			out.println("				return cur;");
			out.println("			}");
			out.println("		}");
		}
		out.println("		throw new ParseException(fs.getPosition(), curChar);");
		out.println("	}");
	}

	private void writeClass(PrintWriter out, NonTerminal nt) {
		out.println("class C_" + nt + " {");
		if (g.nonTermDefs.containsKey(nt)) {
			out.println(g.nonTermDefs.get(nt));
		}
		out.println("}");
	}

	public void writeFile(String className) throws FileNotFoundException {
		new File("src/gen").mkdirs();
		PrintWriter out = new PrintWriter("src/gen/" + className + ".java");
		out.println("package gen;");
		out.println();
		out.println("import java.io.FileNotFoundException;");
		out.println();
		out.println("import util.FileScanner;");
		out.println("import util.Node;");
		out.println("import util.ParseException;");
		out.println(g.header);
		out.println();
		out.println("public class " + className + " {");
		out.println("	private FileScanner fs;");
		out.println("	private char curChar;");
		out.println();
		out.println("	public " + className
				+ "(String fileName) throws FileNotFoundException {");
		out.println("		this.fs = new FileScanner(fileName);");
		out.println("		curChar = fs.nextChar();");
		out.println("	}");
		for (NonTerminal nt : g.rules.keySet()) {
			out.println();
			writeMethod(out, nt);
		}
		out.println();
		out.println("	public Node getTree() throws ParseException {");
		out.println("		C_" + g.start + " stNonTerm = new C_" + g.start + "();");
		out.println("		Node ans = f_" + g.start + "(stNonTerm);");
		out.println("		if (curChar != (char) -1) {");
		out
				.println("			throw new ParseException(fs.getPosition(), \"EOF\", FileScanner.quoted(\"\" + curChar));");
		out.println("		}");
		out.println("		return ans;");
		out.println("	}");
		out.println("}");
		for (NonTerminal nt : g.rules.keySet()) {
			out.println();
			writeClass(out, nt);
		}
		out.close();
	}
}
