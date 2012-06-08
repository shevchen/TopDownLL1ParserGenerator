import java.util.ArrayList;
import java.util.List;

/*
 * S -> E ';' S | eps
 * E -> St | U
 * St -> 'start:' N
 * U -> N ':' Ns
 * Ns -> N Ns | eps
 */

public class GrammarGrammar {
	public GrammarUnit getTree() {
		Terminal eps = new Terminal("");
		Terminal semi = new Terminal(";");
		Terminal colon = new Terminal(":");
		Terminal startT = new Terminal("start");
		NonTerminal N = new NonTerminal("N");
		NonTerminal Ns = new NonTerminal("Ns");
		NonTerminal U = new NonTerminal("U");
		NonTerminal St = new NonTerminal("St");
		NonTerminal E = new NonTerminal("E");
		NonTerminal S = new NonTerminal("S");
		List<GrammarUnit> list = new ArrayList<GrammarUnit>();
		list.add(E);
		list.add(semi);
		list.add(S);
		S.addChild(list);
		list = new ArrayList<GrammarUnit>();
		return eps;
	}
}
