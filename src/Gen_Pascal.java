import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import util.Node;
import util.ParseException;

public class Gen_Pascal {
	private String s, initial;
	private int lineNum, charNum;
	private Set<Character> delims;
	private List<Pattern> pattList;

	public Gen_Pascal(String s, Set<Character> delims) {
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

	private Node f_A() throws ParseException {
		Node cur = new Node("A");
		skipDelims();
		pattList.clear();
		pattList.add(Pattern.compile("^,"));
		for (Pattern p : pattList) {
			Matcher m = p.matcher(s);
			if (m.find()) {
				cur.addChild(f_comma());
				cur.addChild(f_var());
				cur.addChild(f_A());
				return cur;
			}
		}
		pattList.clear();
		pattList.add(Pattern.compile("^:"));
		for (Pattern p : pattList) {
			Matcher m = p.matcher(s);
			if (m.find()) {
				cur.addChild(checkEquals("^"));
				return cur;
			}
		}
		throw new ParseException(lineNum, charNum, s.charAt(0));
	}

	private Node f_E() throws ParseException {
		Node cur = new Node("E");
		skipDelims();
		pattList.clear();
		pattList.add(Pattern.compile("^n"));
		for (Pattern p : pattList) {
			Matcher m = p.matcher(s);
			if (m.find()) {
				cur.addChild(f_F());
				cur.addChild(f_semi());
				cur.addChild(f_E());
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

	private Node f_F() throws ParseException {
		Node cur = new Node("F");
		skipDelims();
		pattList.clear();
		pattList.add(Pattern.compile("^n"));
		for (Pattern p : pattList) {
			Matcher m = p.matcher(s);
			if (m.find()) {
				cur.addChild(f_var());
				cur.addChild(f_A());
				cur.addChild(f_colon());
				cur.addChild(f_var());
				return cur;
			}
		}
		throw new ParseException(lineNum, charNum, s.charAt(0));
	}

	private Node f_S() throws ParseException {
		Node cur = new Node("S");
		skipDelims();
		pattList.clear();
		pattList.add(Pattern.compile("^var"));
		for (Pattern p : pattList) {
			Matcher m = p.matcher(s);
			if (m.find()) {
				cur.addChild(f_varW());
				cur.addChild(f_F());
				cur.addChild(f_semi());
				cur.addChild(f_E());
				return cur;
			}
		}
		throw new ParseException(lineNum, charNum, s.charAt(0));
	}

	private Node f_colon() throws ParseException {
		Node cur = new Node("colon");
		skipDelims();
		pattList.clear();
		pattList.add(Pattern.compile("^:"));
		for (Pattern p : pattList) {
			Matcher m = p.matcher(s);
			if (m.find()) {
				cur.addChild(checkEquals("^:"));
				return cur;
			}
		}
		throw new ParseException(lineNum, charNum, s.charAt(0));
	}

	private Node f_comma() throws ParseException {
		Node cur = new Node("comma");
		skipDelims();
		pattList.clear();
		pattList.add(Pattern.compile("^,"));
		for (Pattern p : pattList) {
			Matcher m = p.matcher(s);
			if (m.find()) {
				cur.addChild(checkEquals("^,"));
				return cur;
			}
		}
		throw new ParseException(lineNum, charNum, s.charAt(0));
	}

	private Node f_semi() throws ParseException {
		Node cur = new Node("semi");
		skipDelims();
		pattList.clear();
		pattList.add(Pattern.compile("^;"));
		for (Pattern p : pattList) {
			Matcher m = p.matcher(s);
			if (m.find()) {
				cur.addChild(checkEquals("^;"));
				return cur;
			}
		}
		throw new ParseException(lineNum, charNum, s.charAt(0));
	}

	private Node f_var() throws ParseException {
		Node cur = new Node("var");
		skipDelims();
		pattList.clear();
		pattList.add(Pattern.compile("^n"));
		for (Pattern p : pattList) {
			Matcher m = p.matcher(s);
			if (m.find()) {
				cur.addChild(checkEquals("^n"));
				return cur;
			}
		}
		throw new ParseException(lineNum, charNum, s.charAt(0));
	}

	private Node f_varW() throws ParseException {
		Node cur = new Node("varW");
		skipDelims();
		pattList.clear();
		pattList.add(Pattern.compile("^var"));
		for (Pattern p : pattList) {
			Matcher m = p.matcher(s);
			if (m.find()) {
				cur.addChild(checkEquals("^var"));
				return cur;
			}
		}
		throw new ParseException(lineNum, charNum, s.charAt(0));
	}

	public Node getTree() throws ParseException {
		s = initial;
		lineNum = charNum = 1;
		return f_S();
	}
}
