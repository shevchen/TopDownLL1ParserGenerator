public class NonLL1GrammarException extends Exception {
	public NonLL1GrammarException() {
		super("Error: grammar is not LL(1).");
	}
}
