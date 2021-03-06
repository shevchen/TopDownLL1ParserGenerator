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
		if (r.initCode != null) {
			out.println(r.initCode.replaceAll("\\$", "arg_"));
		}
		for (int i = 0; i < r.right.size(); ++i) {
			Pair<GrammarUnit, String> p = r.right.get(i);
			if (p.first instanceof Terminal) {
				Terminal t = (Terminal) p.first;
				if (t.from == FirstFollowCounter.EPS) {
					out.println("C__Terminal arg_" + (i + 1)
							+ " = new C__Terminal((char) -1);");
					out.println("cur.addChild(new Node(\"\", (char) -1));");
				} else {
					out.println("while (curChar < (char) " + (int) t.from
							+ " || curChar > (char) " + (int) t.to + ") {");
					out.println("	if (fs.ignore(curChar)) {");
					out.println("		curChar = fs.read();");
					out.println("	} else {");
					out
							.println("		throw new ParseException(fs.getPosition(), \"\" + curChar, \""
									+ StringUtils.escape(t.toString()) + "\");");
					out.println("	}");
					out.println("}");
					out.println("C__Terminal arg_" + (i + 1)
							+ " = new C__Terminal(curChar);");
					out
							.println("cur.addChild(new Node(\"\" + StringUtils.bestView(curChar, false), curChar));");
					out.println("curChar = fs.read();");
				}
			} else {
				NonTerminal nt = (NonTerminal) p.first;
				out.println("C_" + nt + " arg_" + (i + 1) + " = new C_" + nt
						+ "();");
				if (p.second != null) {
					out.println(p.second.replaceAll("\\$", "arg_"));
				}
				out.println("cur.addChild(f_" + nt + "(arg_" + (i + 1) + "));");
			}
		}
		if (r.synthCode != null) {
			out.println(r.synthCode.replaceAll("\\$", "arg_"));
		}
	}

	private void writeMethod(PrintWriter out, NonTerminal nt) {
		out.println("	private Node f_" + nt + "(C_" + nt
				+ " arg_0) throws ParseException {");
		out.println("		Node cur = new Node(\"" + nt + "\", (char) -1);");
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
				sb.append(StringUtils.quoted(StringUtils.bestView(chars[i],
						true)));
			}
			sb.append('}');
			out.println("		for (char c : new char[]" + sb.toString() + ") {");
			out.println("			if (c == curChar) {");
			processRule(out, r);
			out.println("				return cur;");
			out.println("			}");
			out.println("		}");
		}
		out.println("		if (fs.ignore(curChar)) {");
		out.println("			curChar = fs.read();");
		out.println("			return f_" + nt + "(arg_0);");
		out.println("		}");
		out
				.println("		throw new ParseException(fs.getPosition(), \"\" + curChar);");
		out.println("	}");
	}

	private void writeClass(PrintWriter out, NonTerminal nt) {
		out.println("	class C_" + nt + " {");
		if (g.nonTermDefs.containsKey(nt)) {
			out.println(g.nonTermDefs.get(nt));
		}
		out.println("	}");
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
		out.println("import util.StringUtils;");
		out.println(g.header);
		out.println();
		out.println("@SuppressWarnings(\"unused\")");
		out.println("public class " + className + " {");
		out.println("	private FileScanner fs;");
		out.println("	private char curChar;");
		out.println();
		out.println("	public " + className
				+ "(String fileName) throws FileNotFoundException {");
		out.println("		this.fs = new FileScanner(fileName);");
		out.println("		curChar = fs.read();");
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
				.println("			throw new ParseException(fs.getPosition(), \"\" + curChar, \"eof\");");
		out.println("		}");
		out.println("		return ans;");
		out.println("	}");

		out.println();
		out.println("	class C__Terminal {");
		out.println("		public char c;");
		out.println();
		out.println("		public C__Terminal(char c) {");
		out.println("			this.c = c;");
		out.println("		}");
		out.println("	}");
		for (NonTerminal nt : g.rules.keySet()) {
			out.println();
			writeClass(out, nt);
		}
		out.println("}");
		out.close();
	}
}
