package util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Grammar {
	public String header;
	public NonTerminal start;
	public Map<NonTerminal, String> nonTermDefs;
	public Map<NonTerminal, List<Rule>> rules;

	public Grammar(String header, List<Pair<NonTerminal, String>> ntDefs,
			NonTerminal start, List<Rule> ruleList) {
		this.header = header == null ? "" : header;
		this.start = start;
		nonTermDefs = new TreeMap<NonTerminal, String>();
		if (ntDefs != null) {
			for (Pair<NonTerminal, String> p : ntDefs) {
				nonTermDefs.put(p.first, p.second);
			}
		}
		this.rules = new TreeMap<NonTerminal, List<Rule>>();
		if (ruleList != null) {
			for (Rule r : ruleList) {
				List<Rule> list = this.rules.get(r.left);
				if (list == null) {
					list = new ArrayList<Rule>();
				}
				list.add(r);
				this.rules.put(r.left, list);
			}
		}
	}

	public static Grammar fromTree(Node n) {
		// TODO Auto-generated method stub
		return null;
	}
}