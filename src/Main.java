import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import util.Node;
import util.NonTerminal;
import util.ParseException;
import util.ParserGenerator;
import util.Rules;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("math.g"));
		StringBuilder sb = new StringBuilder();
		while (br.ready()) {
			sb.append(br.readLine() + '\n');
		}
		Node grammarTree;
		try {
			grammarTree = new GrammarParser(sb.toString(),
					GrammarParserGenerator.delims()).getTree();
		} catch (ParseException e) {
			e.printStackTrace();
			return;
		}
		grammarTree.printAsDot(new PrintWriter("grammar.dot"));
		Map<NonTerminal, Rules> rules = grammarTree.toRules();
		NonTerminal first = grammarTree.getFirst();
		ParserGenerator gen = new ParserGenerator(rules, first);
		gen.printFirst();
		gen.printFollow();
		gen.writeFile("Gen_Math");
	}
}
