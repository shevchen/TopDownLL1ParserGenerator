package util;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class ParserWriter {
	private Map<NonTerminal, Rules> rules;
	private NonTerminal start;
	private FirstFollowCounter ffc;

	private Set<Terminal> terms;

	public ParserWriter(Map<NonTerminal, Rules> rules, NonTerminal start,
			FirstFollowCounter ffc) {
		this.rules = rules;
		this.start = start;
		this.ffc = ffc;
		this.terms = new TreeSet<Terminal>();
	}

	private void writeMethod(PrintWriter out, NonTerminal nt) {
		out.println("	private Node f_" + nt + "() throws ParseException {");
		out.println("		Node cur = new Node(\"" + nt + "\");");
		out.println("		skipDelims();");
		for (List<GrammarUnit> list : rules.get(nt)) {
			terms.clear();
			ffc.addAllFirst(terms, list, 0);
			if (terms.remove(FirstFollowCounter.epsTerm)) {
				terms.addAll(ffc.follow.get(nt));
			}
			out.println("		pattList.clear();");
			for (Terminal t : terms) {
				out.println("		pattList.add(Pattern.compile(\"" + t.getRegex()
						+ "\"));");
			}
			out.println("		for (Pattern p : pattList) {");
			out.println("			Matcher m = p.matcher(s);");
			out.println("			if (m.find()) {");
			for (GrammarUnit g : list) {
				if (g instanceof Terminal) {
					Terminal tg = (Terminal) g;
					out.println("				cur.addChild(checkEquals(\""
							+ tg.getRegex() + "\"));");
				} else {
					out.println("				cur.addChild(f_" + g + "());");
				}
			}
			out.println("				return cur;");
			out.println("			}");
			out.println("		}");
		}
		out
				.println("		throw new ParseException(lineNum, charNum, s.charAt(0));");
		out.println("	}");
	}

	public void writeFile(String className) throws FileNotFoundException {
		PrintWriter out = new PrintWriter("src/" + className + ".java");
		out.println("import java.util.ArrayList;");
		out.println("import java.util.List;");
		out.println("import java.util.Set;");
		out.println("import java.util.regex.Matcher;");
		out.println("import java.util.regex.Pattern;");
		out.println();
		out.println("import util.Node;");
		out.println("import util.ParseException;");
		out.println();
		out.println("public class " + className + " {");
		out.println("	private String s, initial;");
		out.println("	private int lineNum, charNum;");
		out.println("	private Set<Character> delims;");
		out.println("	private List<Pattern> pattList;");
		out.println();
		out.println("	public " + className
				+ "(String s, Set<Character> delims) {");
		out.println("		this.initial = s + (char) -1;");
		out.println("		this.delims = delims;");
		out.println("		this.pattList = new ArrayList<Pattern>();");
		out.println("	}");
		out.println();
		out.println("	public void nextChar() {");
		out.println("		if (s.isEmpty()) {");
		out.println("			return;");
		out.println("		}");
		out.println("		if (s.charAt(0) == '\\n') {");
		out.println("			lineNum++;");
		out.println("			charNum = 1;");
		out.println("		} else {");
		out.println("			charNum++;");
		out.println("		}");
		out.println("		s = s.substring(1);");
		out.println("	}");
		out.println();
		out.println("	public void skipDelims() {");
		out.println("		while (delims.contains(s.charAt(0))) {");
		out.println("			nextChar();");
		out.println("		}");
		out.println("	}");
		out.println();
		out
				.println("	public Node checkEquals(String regex) throws ParseException {");
		out.println("		Matcher m = Pattern.compile(regex).matcher(s);");
		out.println("		if (!m.find()) {");
		out.println("			throw new ParseException(lineNum, charNum, regex);");
		out.println("		}");
		out.println("		Node ans = new Node(m.group());");
		out.println("		for (int i = 0; i < m.end(); ++i) {");
		out.println("			nextChar();");
		out.println("		}");
		out.println("		return ans;");
		out.println("	}");
		for (NonTerminal nt : rules.keySet()) {
			out.println();
			writeMethod(out, nt);
		}
		out.println();
		out.println("	public Node getTree() throws ParseException {");
		out.println("		s = initial;");
		out.println("		lineNum = charNum = 1;");
		out.println("		return f_" + start + "();");
		out.println("	}");
		out.println("}");
		out.close();
	}
}
