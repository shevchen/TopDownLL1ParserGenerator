package util;

public class Terminal implements GrammarUnit {
	public char from, to;
	
	public Terminal(char c) {
		from = to = c;
	}

	public Terminal(char from, char to) {
		this.from = from;
		this.to = to;
	}

	@Override
	public String toString() {
		if (from == to) {
			if (from == FirstFollowCounter.EOF) {
				return "eof";
			}
			if (from == FirstFollowCounter.EPS) {
				return "eps";
			}
			return StringUtils.bestView(from, false);
		}
		return StringUtils.bestView(from, false) + " — "
				+ StringUtils.bestView(to, false);
	}
}
