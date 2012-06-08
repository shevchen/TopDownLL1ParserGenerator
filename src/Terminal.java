public class Terminal implements GrammarUnit {
	private String text;

	public Terminal(String text) {
		this.text = text;
	}

	@Override
	public String getName() {
		return text;
	}
}
