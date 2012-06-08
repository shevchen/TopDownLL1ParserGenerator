import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestGrammar2 {
	public static void main(String[] args) {
		Map<NonTerminal, Rules> map = new HashMap<NonTerminal, Rules>();
		NonTerminal E = new NonTerminal("E");
		NonTerminal E2 = new NonTerminal("E2");
		NonTerminal T = new NonTerminal("T");
		NonTerminal T2 = new NonTerminal("T2");
		NonTerminal F = new NonTerminal("F");
		Terminal plus = new Terminal("+");
		Terminal mult = new Terminal("*");
		Terminal open = new Terminal("(");
		Terminal close = new Terminal(")");
		Terminal n = new Terminal("n");
		Terminal eps = new Terminal("");
		// E
		Rules r = new Rules();
		List<GrammarUnit> list = new ArrayList<GrammarUnit>();
		list.add(T);
		list.add(E2);
		r.add(list);
		map.put(E, r);
		// E2
		r = new Rules();
		list = new ArrayList<GrammarUnit>();
		list.add(plus);
		list.add(T);
		list.add(E2);
		r.add(list);
		list = new ArrayList<GrammarUnit>();
		list.add(eps);
		r.add(list);
		map.put(E2, r);
		// T
		r = new Rules();
		list = new ArrayList<GrammarUnit>();
		list.add(F);
		list.add(T2);
		r.add(list);
		map.put(T, r);
		// T2
		r = new Rules();
		list = new ArrayList<GrammarUnit>();
		list.add(mult);
		list.add(F);
		list.add(T2);
		r.add(list);
		list = new ArrayList<GrammarUnit>();
		list.add(eps);
		r.add(list);
		map.put(T2, r);
		// F
		r = new Rules();
		list = new ArrayList<GrammarUnit>();
		list.add(open);
		list.add(E);
		list.add(close);
		r.add(list);
		list = new ArrayList<GrammarUnit>();
		list.add(n);
		r.add(list);
		map.put(F, r);
		//
		ParserGenerator gen = new ParserGenerator(map, E);
		gen.printFirst();
		gen.printFollow();
		System.out.println("LL(1): " + gen.isLL1());
		try {
			gen.writeFile("TG2");
		} catch (NonLL1GrammarException e) {
			e.printStackTrace();
		}
	}
}
