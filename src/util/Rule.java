package util;

import java.util.List;

public class Rule {
	public NonTerminal left;
	public List<Pair<GrammarUnit, String>> right;
	public String synthCode;

	public Rule(NonTerminal left, List<Pair<GrammarUnit, String>> right,
			String synthCode) {
		this.left = left;
		this.right = right;
		this.synthCode = synthCode;
	}
}