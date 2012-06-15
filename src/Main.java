import grammar_file.GrammarFileDefinitionParser;
import util.FirstFollowCounter;
import util.Grammar;

public class Main {
	public static void main(String[] args) {
		Grammar g;
		try {
			g = GrammarFileDefinitionParser
					.parseGrammarFileDefinition("grammarFile.g");
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		FirstFollowCounter ffc = new FirstFollowCounter(g.start, g.rules);
	}
}
