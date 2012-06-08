import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TG2 {
	private String s, initial;
	private int lineNum, charNum;
	private Set<Character> delims;
	private List<Pattern> pattList;

	public TG2(String s, Set<Character> delims) {
		this.initial = s + (char) -1;
		this.delims = delims;
		this.pattList = new ArrayList<Pattern>();
	}

	public void nextChar() {
		if (s.charAt(0) == (char) FirstFollowCounter.EOF) {
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

	public void assertEquals(String regex) throws ParseException {
		Matcher m = Pattern.compile(regex).matcher(s);
		if (!m.find()) {
			throw new ParseException(lineNum, charNum, regex);
		}
		for (int i = 0; i < m.end(); ++i) {
			nextChar();
		}
	}

	private Node f_E() throws ParseException {
		Node cur = new Node("E");
		skipDelims();
		pattList.clear();
		pattList.add(Pattern.compile("^\\("));
		pattList.add(Pattern.compile("^n"));
		for (Pattern p : pattList) {
			Matcher m = p.matcher(s);
			if (m.find()) {
				cur.addChild(f_T());
				cur.addChild(f_E2());
				return cur;
			}
		}
		throw new ParseException(lineNum, charNum, s.charAt(0));
	}

	private Node f_T() throws ParseException {
		Node cur = new Node("T");
		skipDelims();
		pattList.clear();
		pattList.add(Pattern.compile("^\\("));
		pattList.add(Pattern.compile("^n"));
		for (Pattern p : pattList) {
			Matcher m = p.matcher(s);
			if (m.find()) {
				cur.addChild(f_F());
				cur.addChild(f_T2());
				return cur;
			}
		}
		throw new ParseException(lineNum, charNum, s.charAt(0));
	}

	private Node f_T2() throws ParseException {
		Node cur = new Node("T2");
		skipDelims();
		pattList.clear();
		pattList.add(Pattern.compile("^\\*"));
		for (Pattern p : pattList) {
			Matcher m = p.matcher(s);
			if (m.find()) {
				assertEquals("^\\*");
				cur.addChild(new Node("*"));
				cur.addChild(f_F());
				cur.addChild(f_T2());
				return cur;
			}
		}
		pattList.clear();
		pattList.add(Pattern.compile("^￿"));
		pattList.add(Pattern.compile("^\\+"));
		pattList.add(Pattern.compile("^\\)"));
		for (Pattern p : pattList) {
			Matcher m = p.matcher(s);
			if (m.find()) {
				assertEquals("^");
				cur.addChild(new Node(""));
				return cur;
			}
		}
		throw new ParseException(lineNum, charNum, s.charAt(0));
	}

	private Node f_E2() throws ParseException {
		Node cur = new Node("E2");
		skipDelims();
		pattList.clear();
		pattList.add(Pattern.compile("^\\+"));
		for (Pattern p : pattList) {
			Matcher m = p.matcher(s);
			if (m.find()) {
				assertEquals("^\\+");
				cur.addChild(new Node("+"));
				cur.addChild(f_T());
				cur.addChild(f_E2());
				return cur;
			}
		}
		pattList.clear();
		pattList.add(Pattern.compile("^￿"));
		pattList.add(Pattern.compile("^\\)"));
		for (Pattern p : pattList) {
			Matcher m = p.matcher(s);
			if (m.find()) {
				assertEquals("^");
				cur.addChild(new Node(""));
				return cur;
			}
		}
		throw new ParseException(lineNum, charNum, s.charAt(0));
	}

	private Node f_F() throws ParseException {
		Node cur = new Node("F");
		skipDelims();
		pattList.clear();
		pattList.add(Pattern.compile("^\\("));
		for (Pattern p : pattList) {
			Matcher m = p.matcher(s);
			if (m.find()) {
				assertEquals("^\\(");
				cur.addChild(new Node("("));
				cur.addChild(f_E());
				assertEquals("^\\)");
				cur.addChild(new Node(")"));
				return cur;
			}
		}
		pattList.clear();
		pattList.add(Pattern.compile("^n"));
		for (Pattern p : pattList) {
			Matcher m = p.matcher(s);
			if (m.find()) {
				assertEquals("^n");
				cur.addChild(new Node("n"));
				return cur;
			}
		}
		throw new ParseException(lineNum, charNum, s.charAt(0));
	}

	public Node getTree() throws ParseException {
		s = initial;
		lineNum = charNum = 1;
		return f_E();
	}
}
