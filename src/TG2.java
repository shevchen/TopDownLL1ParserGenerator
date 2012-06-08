public class TG2 {
	private Lexeme[] lex;
	private int curLex;

	public TG2(Lexeme[] lex) {
		this.lex = lex;
		this.curLex = 0;
	}

	public void assertEquals(String s) throws ParseException {
		if (lex[curLex].text.compareTo(s) != 0) {
			throw new ParseException(lex[curLex], s);
		}
		++curLex;
	}

	private Node f_E() throws ParseException {
		Node cur = new Node("E");
		switch (lex[curLex].text.charAt(0)) {
		case 'n':
		case '(':
			cur.addChild(f_T());
			cur.addChild(f_E2());
			return cur;
		default:
			throw new ParseException(lex[curLex]);
		}
	}

	private Node f_T() throws ParseException {
		Node cur = new Node("T");
		switch (lex[curLex].text.charAt(0)) {
		case 'n':
		case '(':
			cur.addChild(f_F());
			cur.addChild(f_T2());
			return cur;
		default:
			throw new ParseException(lex[curLex]);
		}
	}

	private Node f_T2() throws ParseException {
		Node cur = new Node("T2");
		switch (lex[curLex].text.charAt(0)) {
		case '*':
			assertEquals("*");
			cur.addChild(new Node("*"));
			cur.addChild(f_F());
			cur.addChild(f_T2());
			return cur;
		case (char) -1:
		case '+':
		case ')':
			return cur;
		default:
			throw new ParseException(lex[curLex]);
		}
	}

	private Node f_E2() throws ParseException {
		Node cur = new Node("E2");
		switch (lex[curLex].text.charAt(0)) {
		case '+':
			assertEquals("+");
			cur.addChild(new Node("+"));
			cur.addChild(f_T());
			cur.addChild(f_E2());
			return cur;
		case (char) -1:
		case ')':
			return cur;
		default:
			throw new ParseException(lex[curLex]);
		}
	}

	private Node f_F() throws ParseException {
		Node cur = new Node("F");
		switch (lex[curLex].text.charAt(0)) {
		case '(':
			assertEquals("(");
			cur.addChild(new Node("("));
			cur.addChild(f_E());
			assertEquals(")");
			cur.addChild(new Node(")"));
			return cur;
		case 'n':
			assertEquals("n");
			cur.addChild(new Node("n"));
			return cur;
		default:
			throw new ParseException(lex[curLex]);
		}
	}

	public Node getTree() throws ParseException {
		return f_E();
	}
}
