package grammar_file;

import java.util.List;

import util.ParsedString;

class RuleString {
	public ParsedString left;
	public List<ParsedString> right;

	public RuleString(ParsedString left, List<ParsedString> right) {
		this.left = left;
		this.right = right;
	}
}