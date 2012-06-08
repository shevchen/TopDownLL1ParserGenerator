import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FirstFollowCounter {
	private Map<NonTerminal, Rules> rules;
	private NonTerminal start;
	public Map<NonTerminal, Set<Character>> first, follow;
	public boolean isLL1;

	static final char EPS = 0;
	static final char EOL = (char) -1;

	public FirstFollowCounter(Map<NonTerminal, Rules> rules, NonTerminal start) {
		this.rules = rules;
		this.start = start;
		countFirst();
		countFollow();
		isLL1 = checkLL1();
	}

	boolean addAllFirst(Set<Character> to, List<GrammarUnit> from, int index) {
		if (index == from.size()) {
			return to.add(EPS);
		}
		GrammarUnit g = from.get(index);
		if (g instanceof Terminal) {
			String text = g.toString();
			if (text.isEmpty()) {
				return addAllFirst(to, from, index + 1);
			} else {
				return to.add(text.charAt(0));
			}
		} else {
			boolean ans = false;
			for (Character c : first.get((NonTerminal) g)) {
				if (c == EPS) {
					ans |= addAllFirst(to, from, index + 1);
				} else {
					ans |= to.add(c);
				}
			}
			return ans;
		}
	}

	private void countFirst() {
		first = new HashMap<NonTerminal, Set<Character>>();
		for (NonTerminal nt : rules.keySet()) {
			first.put(nt, new HashSet<Character>());
		}
		boolean action = true;
		while (action) {
			action = false;
			for (Map.Entry<NonTerminal, Rules> e : rules.entrySet()) {
				Set<Character> to = first.get(e.getKey());
				for (List<GrammarUnit> list : e.getValue()) {
					action |= addAllFirst(to, list, 0);
				}
			}
		}
	}

	private void countFollow() {
		follow = new HashMap<NonTerminal, Set<Character>>();
		for (NonTerminal nt : rules.keySet()) {
			follow.put(nt, new HashSet<Character>());
		}
		follow.get(start).add(EOL);
		boolean action = true;
		while (action) {
			action = false;
			for (Map.Entry<NonTerminal, Rules> e : rules.entrySet()) {
				Set<Character> leftFollow = follow.get(e.getKey());
				for (List<GrammarUnit> list : e.getValue()) {
					for (int i = 0; i < list.size(); ++i) {
						if (list.get(i) instanceof NonTerminal) {
							Set<Character> rightFollow = follow
									.get((NonTerminal) list.get(i));
							int prevSize = rightFollow.size();
							addAllFirst(rightFollow, list, i + 1);
							if (rightFollow.remove(EPS)) {
								rightFollow.addAll(leftFollow);
							}
							action |= rightFollow.size() > prevSize;
						}
					}
				}
			}
		}
	}

	private boolean checkLL1() {
		Set<Character> checkSet1 = new HashSet<Character>(), checkSet2 = new HashSet<Character>();
		for (Map.Entry<NonTerminal, Rules> e : rules.entrySet()) {
			Rules r = e.getValue();
			for (int i = 0; i < r.size(); ++i) {
				for (int j = i + 1; j < r.size(); ++j) {
					// 1
					checkSet1.clear();
					addAllFirst(checkSet1, r.get(i), 0);
					checkSet2.clear();
					addAllFirst(checkSet2, r.get(j), 0);
					for (char c : checkSet1) {
						if (checkSet2.contains(c)) {
							return false;
						}
					}
					// 2
					if (checkSet1.contains(EPS)) {
						for (char c : follow.get(e.getKey())) {
							if (checkSet2.contains(c)) {
								return false;
							}
						}
					}
				}
			}
		}
		return true;
	}
}
