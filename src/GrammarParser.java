import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GrammarParser {
	private String s, initial;
	private int lineNum, charNum;
	private Set<Character> delims;
	private List<Pattern> pattList;

	public GrammarParser(String s, Set<Character> delims) {
		this.initial = s + (char) -1;
		this.delims = delims;
		this.pattList = new ArrayList<Pattern>();
	}

	public void nextChar() {
		if (s.isEmpty()) {
			return;
		}
		if (s.charAt(0) == '\n') {
			lineNum++;
			charNum = 1;
		} else {
			charNum++;
		}
		s = s.substring(1);
	}

	public void skipDelims() {
		while (delims.contains(s.charAt(0))) {
			nextChar();
		}
	}

	public Node checkEquals(String regex) throws ParseException {
		Matcher m = Pattern.compile(regex).matcher(s);
		if (!m.find()) {
			throw new ParseException(lineNum, charNum, regex);
		}
		Node ans = new Node(m.group());
		for (int i = 0; i < m.end(); ++i) {
			nextChar();
		}
		return ans;
	}

	private Node f_Arrow() throws ParseException {
		Node cur = new Node("Arrow");
		skipDelims();
		pattList.clear();
		pattList.add(Pattern.compile("^->"));
		for (Pattern p : pattList) {
			Matcher m = p.matcher(s);
			if (m.find()) {
				cur.addChild(checkEquals("^->"));
				return cur;
			}
		}
		throw new ParseException(lineNum, charNum, s.charAt(0));
	}

	private Node f_Expr() throws ParseException {
		Node cur = new Node("Expr");
		skipDelims();
		pattList.clear();
		pattList.add(Pattern.compile("^\\w+"));
		for (Pattern p : pattList) {
			Matcher m = p.matcher(s);
			if (m.find()) {
				cur.addChild(f_Rule());
				cur.addChild(checkEquals("^;"));
				cur.addChild(f_Expr());
				return cur;
			}
		}
		pattList.clear();
		pattList.add(Pattern.compile("^￿"));
		for (Pattern p : pattList) {
			Matcher m = p.matcher(s);
			if (m.find()) {
				cur.addChild(checkEquals("^"));
				return cur;
			}
		}
		throw new ParseException(lineNum, charNum, s.charAt(0));
	}

	private Node f_Nonterm() throws ParseException {
		Node cur = new Node("Nonterm");
		skipDelims();
		pattList.clear();
		pattList.add(Pattern.compile("^\\w+"));
		for (Pattern p : pattList) {
			Matcher m = p.matcher(s);
			if (m.find()) {
				cur.addChild(checkEquals("^\\w+"));
				return cur;
			}
		}
		throw new ParseException(lineNum, charNum, s.charAt(0));
	}

	private Node f_Nonterms() throws ParseException {
		Node cur = new Node("Nonterms");
		skipDelims();
		pattList.clear();
		pattList.add(Pattern.compile("^\\w+"));
		for (Pattern p : pattList) {
			Matcher m = p.matcher(s);
			if (m.find()) {
				cur.addChild(f_Nonterm());
				cur.addChild(f_Nonterms());
				return cur;
			}
		}
		pattList.clear();
		pattList.add(Pattern.compile("^;"));
		for (Pattern p : pattList) {
			Matcher m = p.matcher(s);
			if (m.find()) {
				cur.addChild(checkEquals("^"));
				return cur;
			}
		}
		throw new ParseException(lineNum, charNum, s.charAt(0));
	}

	private Node f_Right() throws ParseException {
		Node cur = new Node("Right");
		skipDelims();
		pattList.clear();
		pattList.add(Pattern.compile("^\\w+"));
		for (Pattern p : pattList) {
			Matcher m = p.matcher(s);
			if (m.find()) {
				cur.addChild(f_Nonterm());
				cur.addChild(f_Nonterms());
				return cur;
			}
		}
		pattList.clear();
		pattList.add(Pattern.compile("^\'.*\'"));
		for (Pattern p : pattList) {
			Matcher m = p.matcher(s);
			if (m.find()) {
				cur.addChild(f_Term());
				return cur;
			}
		}
		throw new ParseException(lineNum, charNum, s.charAt(0));
	}

	private Node f_Rule() throws ParseException {
		Node cur = new Node("Rule");
		skipDelims();
		pattList.clear();
		pattList.add(Pattern.compile("^\\w+"));
		for (Pattern p : pattList) {
			Matcher m = p.matcher(s);
			if (m.find()) {
				cur.addChild(f_Nonterm());
				cur.addChild(f_Arrow());
				cur.addChild(f_Right());
				return cur;
			}
		}
		throw new ParseException(lineNum, charNum, s.charAt(0));
	}

	private Node f_Start() throws ParseException {
		Node cur = new Node("Start");
		skipDelims();
		pattList.clear();
		pattList.add(Pattern.compile("^->"));
		for (Pattern p : pattList) {
			Matcher m = p.matcher(s);
			if (m.find()) {
				cur.addChild(f_Arrow());
				cur.addChild(f_Nonterm());
				cur.addChild(checkEquals("^;"));
				cur.addChild(f_Expr());
				return cur;
			}
		}
		throw new ParseException(lineNum, charNum, s.charAt(0));
	}

	private Node f_Term() throws ParseException {
		Node cur = new Node("Term");
		skipDelims();
		pattList.clear();
		pattList.add(Pattern.compile("^\'.*\'"));
		for (Pattern p : pattList) {
			Matcher m = p.matcher(s);
			if (m.find()) {
				cur.addChild(checkEquals("^\'.*\'"));
				return cur;
			}
		}
		throw new ParseException(lineNum, charNum, s.charAt(0));
	}

	public Node getTree() throws ParseException {
		s = initial;
		lineNum = charNum = 1;
		return f_Start();
	}
}
