package util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Grammar {
	public String header;
	public NonTerminal start;
	public Map<NonTerminal, String> nonTermDefs;
	public Map<NonTerminal, List<Rule>> rules;

	public Grammar(String header, List<Pair<NonTerminal, String>> ntDefs,
			NonTerminal start, List<Rule> ruleList) {
		this.header = header;
		this.start = start;
		nonTermDefs = new HashMap<NonTerminal, String>();
		for (Pair<NonTerminal, String> p : ntDefs) {
			nonTermDefs.put(p.first, p.second);
		}
		this.rules = new HashMap<NonTerminal, List<Rule>>();
		for (Rule r : ruleList) {
			List<Rule> list = this.rules.get(r.left);
			if (list == null) {
				list = new ArrayList<Rule>();
			}
			list.add(r);
		}
	}
}