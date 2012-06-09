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
	private static void step1() {
		// generates grammar file parser
		try {
			new GrammarParserGenerator().generate("Gen_GrammarParser");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private static void genParser(Node grammarTree, String fileName) {
		Map<NonTerminal, Rules> rules = grammarTree.toRules();
		NonTerminal first = grammarTree.getFirst();
		ParserGenerator gen = new ParserGenerator(rules, first);
		gen.printFirst();
		gen.printFollow();
		gen.writeFile(fileName);
	}

	private static void step2() {
		// generates grammar parser
		try {
			BufferedReader br = new BufferedReader(new FileReader("pascal.g"));
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
				return;
			}
			try {
				grammarTree.printAsDot(new PrintWriter("pascal_grammar.dot"));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			genParser(grammarTree, "Gen_Pascal");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void step3() {
		// generates tree
		final String file = "pascal";
		Node tree = null;
		try {
			BufferedReader br = new BufferedReader(new FileReader(file + ".in"));
			StringBuilder sb = new StringBuilder();
			while (br.ready()) {
				sb.append(br.readLine() + '\n');
			}
			try {
				tree = new Gen_Pascal(sb.toString(), GrammarParserGenerator
						.delims()).getTree();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (tree == null) {
			return;
		}
		try {
			tree.printAsDot(new PrintWriter(file + ".dot"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		step3();
	}
}
