public class TG2 {
	private Lexeme[] lex;
	private int curLex;

	public TG2(Lexeme[] lex) {
		this.lex = lex;
		this.curLex = 0;
	}

	public void assertEquals(String s) throws ParseException {
		if (lex[curLex].text.compareTo(s) != 0) {
			throw new ParseException(lex[curLex]);
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
			case '￿':
			case '*':
			case '+':
			case ')':
				assertEquals("");
				cur.addChild(new Node(""));
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
			case '￿':
			case '+':
			case ')':
				assertEquals("");
				cur.addChild(new Node(""));
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
			case '(':
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
