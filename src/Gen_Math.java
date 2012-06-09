import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import util.Node;
import util.ParseException;

public class Gen_Math {
	private String s, initial;
	private int lineNum, charNum;
	private Set<Character> delims;
	private List<Pattern> pattList;

	public Gen_Math(String s, Set<Character> delims) {
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

	private Node f_E() throws ParseException {
		Node cur = new Node("E");
		skipDelims();
		pattList.clear();
		pattList.add(Pattern.compile("^\\d+"));
		pattList.add(Pattern.compile("^\\("));
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

	private Node f_E2() throws ParseException {
		Node cur = new Node("E2");
		skipDelims();
		pattList.clear();
		pattList.add(Pattern.compile("^\\+"));
		for (Pattern p : pattList) {
			Matcher m = p.matcher(s);
			if (m.find()) {
				cur.addChild(f_plus());
				cur.addChild(f_T());
				cur.addChild(f_E2());
				return cur;
			}
		}
		pattList.clear();
		pattList.add(Pattern.compile("^\\)"));
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
		pattList.add(Pattern.compile("^\\("));
		for (Pattern p : pattList) {
			Matcher m = p.matcher(s);
			if (m.find()) {
				cur.addChild(f_open());
				cur.addChild(f_E());
				cur.addChild(f_close());
				return cur;
			}
		}
		pattList.clear();
		pattList.add(Pattern.compile("^\\d+"));
		for (Pattern p : pattList) {
			Matcher m = p.matcher(s);
			if (m.find()) {
				cur.addChild(checkEquals("^\\d+"));
				return cur;
			}
		}
		throw new ParseException(lineNum, charNum, s.charAt(0));
	}

	private Node f_T() throws ParseException {
		Node cur = new Node("T");
		skipDelims();
		pattList.clear();
		pattList.add(Pattern.compile("^\\d+"));
		pattList.add(Pattern.compile("^\\("));
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
				cur.addChild(f_mult());
				cur.addChild(f_F());
				cur.addChild(f_T2());
				return cur;
			}
		}
		pattList.clear();
		pattList.add(Pattern.compile("^\\+"));
		pattList.add(Pattern.compile("^\\)"));
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

	private Node f_close() throws ParseException {
		Node cur = new Node("close");
		skipDelims();
		pattList.clear();
		pattList.add(Pattern.compile("^\\)"));
		for (Pattern p : pattList) {
			Matcher m = p.matcher(s);
			if (m.find()) {
				cur.addChild(checkEquals("^\\)"));
				return cur;
			}
		}
		throw new ParseException(lineNum, charNum, s.charAt(0));
	}

	private Node f_mult() throws ParseException {
		Node cur = new Node("mult");
		skipDelims();
		pattList.clear();
		pattList.add(Pattern.compile("^\\*"));
		for (Pattern p : pattList) {
			Matcher m = p.matcher(s);
			if (m.find()) {
				cur.addChild(checkEquals("^\\*"));
				return cur;
			}
		}
		throw new ParseException(lineNum, charNum, s.charAt(0));
	}

	private Node f_open() throws ParseException {
		Node cur = new Node("open");
		skipDelims();
		pattList.clear();
		pattList.add(Pattern.compile("^\\("));
		for (Pattern p : pattList) {
			Matcher m = p.matcher(s);
			if (m.find()) {
				cur.addChild(checkEquals("^\\("));
				return cur;
			}
		}
		throw new ParseException(lineNum, charNum, s.charAt(0));
	}

	private Node f_plus() throws ParseException {
		Node cur = new Node("plus");
		skipDelims();
		pattList.clear();
		pattList.add(Pattern.compile("^\\+"));
		for (Pattern p : pattList) {
			Matcher m = p.matcher(s);
			if (m.find()) {
				cur.addChild(checkEquals("^\\+"));
				return cur;
			}
		}
		throw new ParseException(lineNum, charNum, s.charAt(0));
	}

	public Node getTree() throws ParseException {
		s = initial;
		lineNum = charNum = 1;
		Node ans = f_E();
		if (s.charAt(0) != (char) -1) {
			throw new ParseException(lineNum, charNum, s.charAt(0));
		}
		return ans;
	}
}
