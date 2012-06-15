import grammar_file.GrammarFileDefinitionParser;
import util.Grammar;
import util.ParserGenerator;

public class Main {
	public static void main(String[] args) {
		Grammar g;
		try {
			g = GrammarFileDefinitionParser
					.parseGrammarFileDefinition("grammarFile.gg");
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		ParserGenerator pg = new ParserGenerator(g);
		// pg.printFirst();
		// pg.printFollow();
		// pg.printIsLL1();
		pg.writeFile("Test");
	}
}
