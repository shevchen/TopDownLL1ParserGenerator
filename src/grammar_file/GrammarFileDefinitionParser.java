package grammar_file;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import util.FileScanner;
import util.FirstFollowCounter;
import util.Grammar;
import util.GrammarUnit;
import util.NonTerminal;
import util.Pair;
import util.ParseException;
import util.ParsedString;
import util.Rule;
import util.Terminal;

public class GrammarFileDefinitionParser {
	private static List<ParsedString> parseRight(FileScanner fs) {
		List<ParsedString> right = new ArrayList<ParsedString>();
		ParsedString cur = fs.nextToken();
		while (!";".equals(cur)) {
			right.add(cur);
			cur = fs.nextToken();
		}
		return right;
	}

	private static RawGrammar getRawGrammar(FileScanner fs)
			throws ParseException {
		// starting nonterminal
		fs.assertEquals("->");
		ParsedString first = fs.nextToken();
		fs.assertEquals(";");
		// rules
		List<RuleString> rules = new ArrayList<RuleString>();
		ParsedString left = fs.nextToken();
		while (!left.str.isEmpty()) {
			fs.assertEquals("->");
			rules.add(new RuleString(left, parseRight(fs)));
		}
		return new RawGrammar(rules, first);
	}

	private static void assertEquals(String exp, ParsedString s)
			throws ParseException {
		if (!exp.equals(s.str)) {
			throw new ParseException(s.pos, '"' + exp + '"', '"' + s.str + '"');
		}
	}

	private static void addRange(Iterator<ParsedString> it,
			List<Pair<GrammarUnit, String>> dest) throws ParseException {
		char from = getChar(it.next());
		assertEquals("-", it.next());
		char to = getChar(it.next());
		assertEquals("]", it.next());
		dest.add(new Pair<GrammarUnit, String>(new Terminal(from, to), null));
	}

	private static char getChar(ParsedString parsed) throws ParseException {
		String s = parsed.str;
		ParseException e = new ParseException(parsed.pos, "character",
				'"' + s + '"');
		if (s.charAt(0) == '"' || s.charAt(0) == '\'') {
			if (s.length() == 2) {
				if (s.charAt(1) == s.charAt(0)) {
					return FirstFollowCounter.EPS;
				} else {
					throw e;
				}
			} else if (s.length() == 3) {
				if (s.charAt(2) == s.charAt(0)) {
					return s.charAt(1);
				} else {
					throw e;
				}
			} else {
				throw e;
			}
		}
		try {
			return (char) Integer.parseInt(s);
		} catch (NumberFormatException e1) {
			throw e;
		}
	}

	public static Grammar parseGrammarFileDefinition(String fileName)
			throws FileNotFoundException, ParseException {
		Set<Character> ignore = new HashSet<Character>();
		ignore.add(' ');
		ignore.add('\n');
		ignore.add('\t');
		RawGrammar raw = getRawGrammar(new FileScanner(fileName, ignore));
		String header = null;
		List<Pair<NonTerminal, String>> ntDefs = null;
		NonTerminal start = new NonTerminal(raw.start.str);
		List<Rule> ruleList = new ArrayList<Rule>(raw.rules.size());
		for (RuleString r : raw.rules) {
			NonTerminal left = new NonTerminal(r.left.str);
			List<Pair<GrammarUnit, String>> right = new ArrayList<Pair<GrammarUnit, String>>();
			for (Iterator<ParsedString> it = r.right.iterator(); it.hasNext();) {
				ParsedString s = it.next();
				if ("[".equals(s.str)) {
					addRange(it, right);
				} else if (s.str.charAt(0) == '"' || s.str.charAt(0) == '\'') {
					char only = getChar(s);
					right.add(new Pair<GrammarUnit, String>(new Terminal(only,
							only), null));
				} else {
					right.add(new Pair<GrammarUnit, String>(new NonTerminal(
							s.str), null));
				}
			}
			ruleList.add(new Rule(left, right, null));
		}
		return new Grammar(header, ntDefs, start, ruleList);
	}
}