import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class FirstFollowCounter {
	private Map<NonTerminal, Rules> rules;
	private NonTerminal start;
	public Map<NonTerminal, Set<Terminal>> first, follow;

	static final int EPS = 0, EOF = -1;

	public FirstFollowCounter(Map<NonTerminal, Rules> rules, NonTerminal start) {
		this.rules = rules;
		this.start = start;
		countFirst();
		countFollow();
	}

	boolean addAllFirst(Set<Terminal> to, List<GrammarUnit> from, int index) {
		if (index == from.size()) {
			return to.add(new Terminal(EPS, ""));
		}
		GrammarUnit g = from.get(index);
		if (g instanceof Terminal) {
			Terminal tg = (Terminal) g;
			if (tg.getId() == EPS) {
				return addAllFirst(to, from, index + 1);
			} else {
				return to.add(tg);
			}
		} else {
			boolean ans = false;
			for (Terminal c : first.get((NonTerminal) g)) {
				if (c.getId() == 0) {
					ans |= addAllFirst(to, from, index + 1);
				} else {
					ans |= to.add(c);
				}
			}
			return ans;
		}
	}

	private void countFirst() {
		first = new TreeMap<NonTerminal, Set<Terminal>>();
		for (NonTerminal nt : rules.keySet()) {
			first.put(nt, new TreeSet<Terminal>());
		}
		boolean action = true;
		while (action) {
			action = false;
			for (Map.Entry<NonTerminal, Rules> e : rules.entrySet()) {
				Set<Terminal> to = first.get(e.getKey());
				for (List<GrammarUnit> list : e.getValue()) {
					action |= addAllFirst(to, list, 0);
				}
			}
		}
	}

	private void countFollow() {
		follow = new TreeMap<NonTerminal, Set<Terminal>>();
		for (NonTerminal nt : rules.keySet()) {
			follow.put(nt, new TreeSet<Terminal>());
		}
		follow.get(start).add(new Terminal(EOF, "$"));
		boolean action = true;
		while (action) {
			action = false;
			for (Map.Entry<NonTerminal, Rules> e : rules.entrySet()) {
				Set<Terminal> leftFollow = follow.get(e.getKey());
				for (List<GrammarUnit> list : e.getValue()) {
					for (int i = 0; i < list.size(); ++i) {
						if (list.get(i) instanceof NonTerminal) {
							Set<Terminal> rightFollow = follow
									.get((NonTerminal) list.get(i));
							int prevSize = rightFollow.size();
							addAllFirst(rightFollow, list, i + 1);
							if (rightFollow.remove(new Terminal(EPS, ""))) {
								rightFollow.addAll(leftFollow);
							}
							action |= rightFollow.size() > prevSize;
						}
					}
				}
			}
		}
	}
}
