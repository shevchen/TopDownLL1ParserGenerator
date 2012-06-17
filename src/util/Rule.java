package util;

import java.util.List;

public class Rule {
	public NonTerminal left;
	public String initCode;
	public List<Pair<GrammarUnit, String>> right;
	public String synthCode;

	public Rule(NonTerminal left, String initCode,
			List<Pair<GrammarUnit, String>> right, String synthCode) {
		this.left = left;
		this.initCode = initCode;
		this.right = right;
		this.synthCode = synthCode;
	}

	@Override
	public String toString() {
		String ans = left + " -> ";
		if (initCode != null) {
			ans += "{ " + initCode + " } ";
		}
		for (Pair<GrammarUnit, String> p : right) {
			ans += p.first;
			if (p.second != null) {
				ans += "{ " + p.second + " }";
			}
			ans += ' ';
		}
		if (synthCode != null) {
			ans += " : { " + synthCode + " }";
		}
		return ans;
	}
}