package util;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

public class FirstFollowCounter {
	private NonTerminal start;
	private Map<NonTerminal, List<Rule>> rules;
	public Map<NonTerminal, Set<Character>> first, follow;
	public boolean isLL1;

	public static final char EPS = (char) -2;
	public static final char EOF = (char) -1;

	public FirstFollowCounter(NonTerminal start,
			Map<NonTerminal, List<Rule>> rules) {
		this.start = start;
		this.rules = rules;
		countFirst();
		countFollow();
		isLL1 = checkLL1();
	}

	public boolean addAllFirst(Set<Character> to, Rule r, int index) {
		if (index == r.right.size()) {
			return to.add(EPS);
		}
		GrammarUnit g = r.right.get(index).first;
		if (g instanceof Terminal) {
			Terminal tg = (Terminal) g;
			if (tg.from == EPS) {
				if (tg.to != EPS) {
					throw new RuntimeException();
				}
				return addAllFirst(to, r, index + 1);
			} else {
				boolean ans = false;
				for (char c = tg.from; c <= tg.to && c < EPS && c < EOF; ++c) {
					ans |= to.add(c);
				}
				return ans;
			}
		} else {
			boolean ans = false;
			for (Character c : first.get((NonTerminal) g)) {
				if (c == EPS) {
					ans |= addAllFirst(to, r, index + 1);
				} else {
					ans |= to.add(c);
				}
			}
			return ans;
		}
	}

	private void countFirst() {
		first = new TreeMap<NonTerminal, Set<Character>>();
		for (NonTerminal nt : rules.keySet()) {
			first.put(nt, new HashSet<Character>());
		}
		boolean action = true;
		while (action) {
			action = false;
			for (Map.Entry<NonTerminal, List<Rule>> e : rules.entrySet()) {
				Set<Character> to = first.get(e.getKey());
				for (Rule r : e.getValue()) {
					action |= addAllFirst(to, r, 0);
				}
			}
		}
	}

	private void countFollow() {
		follow = new TreeMap<NonTerminal, Set<Character>>();
		for (NonTerminal nt : rules.keySet()) {
			follow.put(nt, new HashSet<Character>());
		}
		follow.get(start).add(EOF);
		boolean action = true;
		while (action) {
			action = false;
			for (Map.Entry<NonTerminal, List<Rule>> e : rules.entrySet()) {
				Set<Character> leftFollow = follow.get(e.getKey());
				for (Rule r : e.getValue()) {
					for (int i = 0; i < r.right.size(); ++i) {
						GrammarUnit g = r.right.get(i).first;
						if (g instanceof NonTerminal) {
							Set<Character> rightFollow = follow
									.get((NonTerminal) g);
							int prevSize = rightFollow.size();
							addAllFirst(rightFollow, r, i + 1);
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
		Set<Character> testSet1 = new HashSet<Character>(), testSet2 = new HashSet<Character>();
		for (Entry<NonTerminal, List<Rule>> e : rules.entrySet()) {
			List<Rule> cur = e.getValue();
			for (int i = 0; i < cur.size(); ++i) {
				for (int j = i + 1; j < cur.size(); ++j) {
					// 1
					testSet1.clear();
					addAllFirst(testSet1, cur.get(i), 0);
					testSet2.clear();
					addAllFirst(testSet2, cur.get(j), 0);
					String error = "Grammar is not LL(1)\nA: " + cur.get(i)
							+ "\nB: " + cur.get(j) + "\n";
					for (char c : testSet1) {
						if (testSet2.contains(c)) {
							System.err.println(error);
							System.err.println("'" + c
									+ "' is in FIRST of A and B");
							return false;
						}
					}
					// 2
					if (testSet1.contains(EPS)) {
						for (char c : follow.get(e.getKey())) {
							if (testSet2.contains(c)) {
								System.err.println(error);
								System.err.println("EPS is in FIRST of A; '"
										+ c
										+ "' is in FIRST of B and FOLLOW of A");
								return false;
							}
						}
					}
					// 3
					if (testSet2.contains(EPS)) {
						for (char c : follow.get(e.getKey())) {
							if (testSet1.contains(c)) {
								System.err.println(error);
								System.err.println("EPS is in FIRST of B; '"
										+ c
										+ "' is in FIRST of A and FOLLOW of B");
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
