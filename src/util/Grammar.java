package util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Grammar {
	public String header;
	public NonTerminal start;
	public Map<NonTerminal, String> nonTermDefs;
	public Map<NonTerminal, List<Rule>> rules;

	public Grammar(String header, List<Pair<NonTerminal, String>> ntDefs,
			NonTerminal start, List<Rule> ruleList) {
		this.header = header == null ? "" : header;
		this.start = start;
		nonTermDefs = new TreeMap<NonTerminal, String>();
		if (ntDefs != null) {
			for (Pair<NonTerminal, String> p : ntDefs) {
				nonTermDefs.put(p.first, p.second);
			}
		}
		this.rules = new TreeMap<NonTerminal, List<Rule>>();
		if (ruleList != null) {
			for (Rule r : ruleList) {
				List<Rule> list = this.rules.get(r.left);
				if (list == null) {
					list = new ArrayList<Rule>();
				}
				list.add(r);
				this.rules.put(r.left, list);
			}
		}
	}

	public String toString() {
		String s = "GRAMMAR\n";
		s += "Header: {" + header + "}\n";
		s += "Start: " + start + "\n";
		s += "Definitions: " + nonTermDefs + "\n";
		s += "Rules: " + rules + "\n";
		return s;
	}

	public static void addDefaultRules(List<Rule> ruleList) {
		// WS
		for (char c : FileScanner.whiteSpaces()) {
			List<Pair<GrammarUnit, String>> right = new ArrayList<Pair<GrammarUnit, String>>();
			right.add(new Pair<GrammarUnit, String>(new Terminal(c), null));
			ruleList.add(new Rule(new NonTerminal("WS"), right, null));
		}
		// Eps
		List<Pair<GrammarUnit, String>> right = new ArrayList<Pair<GrammarUnit, String>>();
		right.add(new Pair<GrammarUnit, String>(new Terminal(
				FirstFollowCounter.EPS), null));
		ruleList.add(new Rule(new NonTerminal("Eps"), right, null));
	}

	private static String parseCode(Node code) {
		StringBuilder sb = new StringBuilder();
		Node maybeCodeChars = code.getChild(1);
		Node codeChar = maybeCodeChars.getChild(0);
		while (!"Eps".equalsIgnoreCase(codeChar.toString())) {
			Node term = codeChar.getChild(0);
			if (!term.isTerminal()) {
				// WhiteSpace
				term = term.getChild(0);
			}
			sb.append(term.getChar());
			maybeCodeChars = maybeCodeChars.getChild(1);
			codeChar = maybeCodeChars.getChild(0);
		}
		return sb.toString();
	}

	private static String getHeader(Node header) {
		Node son = header.getChild(0);
		if ("Eps".equalsIgnoreCase(son.toString())) {
			return null;
		}
		return parseCode(son);
	}

	private static NonTerminal getNonTerm(Node nonTerm) {
		StringBuilder sb = new StringBuilder();
		sb.append(nonTerm.getChild(0).getChild(0).getChar());
		Node maybeAlphanums = nonTerm.getChild(1);
		Node alphaNum = maybeAlphanums.getChild(0);
		while (!"Eps".equalsIgnoreCase(alphaNum.toString())) {
			sb.append(alphaNum.getChild(0).getChild(0).getChar());
			maybeAlphanums = maybeAlphanums.getChild(1);
			alphaNum = maybeAlphanums.getChild(0);
		}
		return new NonTerminal(sb.toString());
	}

	private static List<Pair<NonTerminal, String>> getNtDefs(Node nonTermDef) {
		List<Pair<NonTerminal, String>> ans = new ArrayList<Pair<NonTerminal, String>>();
		Node dollar = nonTermDef.getChild(0);
		while (!"Eps".equalsIgnoreCase(dollar.toString())) {
			NonTerminal def = getNonTerm(nonTermDef.getChild(1));
			String code = parseCode(nonTermDef.getChild(2));
			ans.add(new Pair<NonTerminal, String>(def, code));
			nonTermDef = nonTermDef.getChild(3);
			dollar = nonTermDef.getChild(0);
		}
		return ans;
	}

	private static NonTerminal getStart(Node start) {
		return getNonTerm(start.getChild(2));
	}

	private static Terminal getTerm(Node term) {
		Node type = term.getChild(0);
		if ("QuotedChar".equalsIgnoreCase(type.toString())) {
			return new Terminal(type.getChild(1).getChild(0).getChar());
		}
		// range
		return new Terminal(type.getChild(1).getChild(0).getChild(1)
				.getChild(0).getChar(), type.getChild(3).getChild(0)
				.getChild(1).getChild(0).getChar());
	}

	private static void addUnit(Node unit, List<Pair<GrammarUnit, String>> right) {
		Node smth = unit.getChild(0);
		if ("Term".equalsIgnoreCase(smth.toString())) {
			right.add(new Pair<GrammarUnit, String>(getTerm(smth), null));
		} else {
			NonTerminal nt = getNonTerm(smth);
			Node mbCode = unit.getChild(1).getChild(0);
			String code = null;
			if ("Code".equalsIgnoreCase(mbCode.toString())) {
				code = parseCode(mbCode);
			}
			right.add(new Pair<GrammarUnit, String>(nt, code));
		}
	}

	private static void parseRightSide(Node rightSide, NonTerminal left,
			List<Rule> ans) {
		List<Pair<GrammarUnit, String>> right = new ArrayList<Pair<GrammarUnit, String>>();
		addUnit(rightSide.getChild(0), right);
		Node mbUnits = rightSide.getChild(1);
		Node unit = mbUnits.getChild(0);
		while (!"Eps".equalsIgnoreCase(unit.toString())) {
			addUnit(unit, right);
			mbUnits = mbUnits.getChild(1);
			unit = mbUnits.getChild(0);
		}
		Node mbSynth = rightSide.getChild(2);
		String synthCode = null;
		if (!"Eps".equalsIgnoreCase(mbSynth.getChild(0).toString())) {
			synthCode = parseCode(mbSynth.getChild(1));
		}
		ans.add(new Rule(left, right, synthCode));
	}

	private static List<Rule> getRules(Node rules) {
		List<Rule> ans = new ArrayList<Rule>();
		Node son = rules.getChild(0);
		while (!"Eps".equalsIgnoreCase(son.toString())) {
			NonTerminal left = getNonTerm(son);
			// '-', '>'
			parseRightSide(rules.getChild(3), left, ans);
			Node maybeRightSides = rules.getChild(4);
			while (!"Eps".equalsIgnoreCase(maybeRightSides.getChild(0)
					.toString())) {
				parseRightSide(maybeRightSides.getChild(1), left, ans);
				maybeRightSides = maybeRightSides.getChild(2);
			}
			rules = rules.getChild(6);
			son = rules.getChild(0);
		}
		addDefaultRules(ans);
		return ans;
	}

	private static Set<Character> getDelims(Node delims) {
		Set<Character> set = FileScanner.whiteSpaces();
		if ("_".equals(delims.getChild(0).toString())) {
			Node quotedChars = delims.getChild(1);
			Node quotedChar = quotedChars.getChild(0);
			while (!"Eps".equalsIgnoreCase(quotedChar.toString())) {
				set.add(quotedChar.getChild(1).getChild(0).getChar());
				quotedChars = quotedChars.getChild(1);
				quotedChar = quotedChars.getChild(0);
			}
		}
		return set;
	}

	public static Pair<Grammar, Set<Character>> fromTree(Node root) {
		return new Pair<Grammar, Set<Character>>(new Grammar(getHeader(root
				.getChild(0)), getNtDefs(root.getChild(1)), getStart(root
				.getChild(2)), getRules(root.getChild(3))), getDelims(root
				.getChild(4)));
	}
}