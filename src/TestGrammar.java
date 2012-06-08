import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestGrammar {
	public static void main(String[] args) {
		Map<NonTerminal, Rules> map = new HashMap<NonTerminal, Rules>();
		NonTerminal E = new NonTerminal("E");
		NonTerminal T = new NonTerminal("T");
		NonTerminal F = new NonTerminal("F");
		Terminal plus = new Terminal(1, "+");
		Terminal mult = new Terminal(2, "*");
		Terminal open = new Terminal(3, "(");
		Terminal close = new Terminal(4, ")");
		Terminal n = new Terminal(5, "n");
		// E
		Rules r = new Rules();
		List<GrammarUnit> list = new ArrayList<GrammarUnit>();
		list.add(T);
		r.add(list);
		list = new ArrayList<GrammarUnit>();
		list.add(E);
		list.add(plus);
		list.add(T);
		r.add(list);
		map.put(E, r);
		// T
		r = new Rules();
		list = new ArrayList<GrammarUnit>();
		list.add(F);
		r.add(list);
		list = new ArrayList<GrammarUnit>();
		list.add(T);
		list.add(mult);
		list.add(F);
		r.add(list);
		map.put(T, r);
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
	}
}
