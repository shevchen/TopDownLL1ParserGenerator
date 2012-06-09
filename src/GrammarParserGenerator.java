import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class GrammarParserGenerator {
	private static ParserGenerator grammarRulesParser() {
		// S -> A N ";" E
		// E -> R ";" E | ""
		// R -> N A O
		// O -> N Ns | T
		// A -> "->"
		// Ns -> N Ns | ""
		// N -> "\w+"
		// T -> \" .* \"
		Map<NonTerminal, Rules> map = new TreeMap<NonTerminal, Rules>();
		NonTerminal S = new NonTerminal("Start");
		NonTerminal E = new NonTerminal("Expr");
		NonTerminal R = new NonTerminal("Rule");
		NonTerminal A = new NonTerminal("Arrow");
		NonTerminal N = new NonTerminal("Nonterm");
		NonTerminal O = new NonTerminal("Right");
		NonTerminal Ns = new NonTerminal("Nonterms");
		NonTerminal T = new NonTerminal("Term");
		Terminal arrow = new Terminal(1, "->", "->");
		Terminal alphanum = new Terminal(2, "\\\\w+", "alphanum");
		Terminal term = new Terminal(3, "\\\'.*\\\'", "terminal");
		Terminal semi = new Terminal(4, ";", "semicolon");
		Terminal eps = FirstFollowCounter.epsTerm;
		Rules r;
		List<GrammarUnit> list;
		// S
		r = new Rules();
		list = new ArrayList<GrammarUnit>();
		list.add(A);
		list.add(N);
		list.add(semi);
		list.add(E);
		r.add(list);
		map.put(S, r);
		// E
		r = new Rules();
		list = new ArrayList<GrammarUnit>();
		list.add(R);
		list.add(semi);
		list.add(E);
		r.add(list);
		list = new ArrayList<GrammarUnit>();
		list.add(eps);
		r.add(list);
		map.put(E, r);
		// R
		r = new Rules();
		list = new ArrayList<GrammarUnit>();
		list.add(N);
		list.add(A);
		list.add(O);
		r.add(list);
		map.put(R, r);
		// O
		r = new Rules();
		list = new ArrayList<GrammarUnit>();
		list.add(N);
		list.add(Ns);
		r.add(list);
		list = new ArrayList<GrammarUnit>();
		list.add(T);
		r.add(list);
		map.put(O, r);
		// A
		r = new Rules();
		list = new ArrayList<GrammarUnit>();
		list.add(arrow);
		r.add(list);
		map.put(A, r);
		// Ns
		r = new Rules();
		list = new ArrayList<GrammarUnit>();
		list.add(N);
		list.add(Ns);
		r.add(list);
		list = new ArrayList<GrammarUnit>();
		list.add(eps);
		r.add(list);
		map.put(Ns, r);
		// N
		r = new Rules();
		list = new ArrayList<GrammarUnit>();
		list.add(alphanum);
		r.add(list);
		map.put(N, r);
		// T
		r = new Rules();
		list = new ArrayList<GrammarUnit>();
		list.add(term);
		r.add(list);
		map.put(T, r);
		//
		return new ParserGenerator(map, S);
	}

	public static Set<Character> delims() {
		Set<Character> ans = new TreeSet<Character>();
		ans.add(' ');
		ans.add('\n');
		ans.add('\t');
		return ans;
	}

	public static void main(String[] args) throws FileNotFoundException {
		ParserGenerator ruleParser = grammarRulesParser();
		ruleParser.printFirst();
		ruleParser.printFollow();
		ruleParser.writeFile("GrammarParser");
	}
}
