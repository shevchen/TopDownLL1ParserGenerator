import java.io.FileNotFoundException;
import java.io.PrintWriter;

import gen.GrammarFileParser;
import grammar_file.GrammarFileDefinitionParser;
import util.Grammar;
import util.Node;
import util.ParserGenerator;

public class Main {
	static void genFileParser() {
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
		pg.writeFile("GrammarFileParser");
	}

	static void parseFile() {
		Node n;
		try {
			n = new GrammarFileParser("math.g").getTree();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		try {
			n.printAsDot(new PrintWriter("math.dot"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// genFileParser();
		parseFile();
	}
}
