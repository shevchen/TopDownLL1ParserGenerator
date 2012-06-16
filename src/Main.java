import gen.GrammarFileParser;
import grammar_file.GrammarFileDefinitionParser;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import util.FileScanner;
import util.Grammar;
import util.Node;
import util.ParserGenerator;

public class Main {
	final static String gram = "grammarFile";

	static void genFileParser() {
		Grammar g;
		try {
			g = GrammarFileDefinitionParser
					.parseGrammarFileDefinition("grammarFile.g");
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

	static Node parseFile() {
		Node n;
		try {
			n = new GrammarFileParser(gram + ".g", FileScanner.whiteSpaces())
					.getTree();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		try {
			n.printAsDot(new PrintWriter(gram + ".dot"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return n;
	}

	static void genParser(Node n) {
		// Grammar g = Grammar.fromTree(n);
		// ParserGenerator pg = new ParserGenerator(g);
		// pg.printFirst();
		// pg.printFollow();
		// pg.printIsLL1();
		// pg.writeFile("Gen_" + gram);
	}

	public static void main(String[] args) {
		// genFileParser();
		genParser(parseFile());
	}
}
