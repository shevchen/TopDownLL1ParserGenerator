package grammar_file;

import java.util.List;

import util.ParsedString;

public class RawGrammar {
	public List<RuleString> rules;
	public ParsedString start;

	public RawGrammar(List<RuleString> rules, ParsedString first) {
		this.rules = rules;
		this.start = first;
	}
}
