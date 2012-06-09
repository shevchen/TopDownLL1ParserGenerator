import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import util.GrammarParserGenerator;
import util.Node;
import util.NonTerminal;
import util.ParseException;
import util.ParserGenerator;
import util.Rules;

public class Main {
	private static void genGrammarParser() {
		try {
			new GrammarParserGenerator().generate("Gen_GrammarParser");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private static Node getGrammarTree(String fileName) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			StringBuilder sb = new StringBuilder();
			while (br.ready()) {
				sb.append(br.readLine() + '\n');
			}
			Node grammarTree = null;
			try {
				grammarTree = new Gen_GrammarParser(sb.toString(),
						GrammarParserGenerator.delims()).getTree();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return grammarTree;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private static Node getTree(String fileName) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			StringBuilder sb = new StringBuilder();
			while (br.ready()) {
				sb.append(br.readLine() + '\n');
			}
			Node tree = null;
			try {
				tree = new Gen_Math(sb.toString(), GrammarParserGenerator
						.delims()).getTree();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return tree;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private static void genParser(Node grammarTree) {
		Map<NonTerminal, Rules> rules = grammarTree.toRules();
		NonTerminal first = grammarTree.getFirst();
		ParserGenerator gen = new ParserGenerator(rules, first);
		gen.printFirst();
		gen.printFollow();
		gen.writeFile("Gen_Math");
	}

	public static void main(String[] args) {
		// genGrammarParser();
		// Node grammarTree = getGrammarTree("math.g");
		// try {
		// grammarTree.printAsDot(new PrintWriter("math_grammar.dot"));
		// } catch (FileNotFoundException e) {
		// e.printStackTrace();
		// }
		// genParser(grammarTree);
		Node tree = getTree("math.in");
		try {
			tree.printAsDot(new PrintWriter("math.dot"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
