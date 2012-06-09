import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;
import java.util.TreeSet;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("math.g"));
		StringBuilder sb = new StringBuilder();
		while (br.ready()) {
			sb.append(br.readLine() + '\n');
		}
		Set<Character> delims = new TreeSet<Character>();
		delims.add(' ');
		delims.add('\n');
		delims.add('\t');
		try {
			Node grammarTree = new GrammarParser(sb.toString(), delims)
					.getTree();
			grammarTree.printAsDot(new PrintWriter("grammar.dot"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
