import java.io.FileNotFoundException;
import java.io.PrintWriter;

import gen.Gen_math;
import gen.GrammarFileParser;
import grammar_file.GrammarFileDefinitionParser;
import util.Grammar;
import util.Node;
import util.ParserGenerator;

public class Main {
	final static String gram = "math";
	final static String expr = "math.in";

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
			n = new GrammarFileParser(gram + ".g").getTree();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		try {
			n.printAsDot(new PrintWriter("gram_" + gram + ".dot"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return n;
	}

	static void genParser(Node n) {
		Grammar g = Grammar.fromTree(n);
		// System.out.println(g);
		ParserGenerator pg = new ParserGenerator(g);
		// pg.printFirst();
		// pg.printFollow();
		// pg.printIsLL1();
		pg.writeFile("Gen_" + gram);
	}

	static void parseExpr() {
		Node n;
		try {
			n = new Gen_math(expr).getTree();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		try {
			n.printAsDot(new PrintWriter(expr + ".dot"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// genFileParser();
		// genParser(parseFile());
		parseExpr();
	}
}
