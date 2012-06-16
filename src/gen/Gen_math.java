package gen;

import java.io.FileNotFoundException;
import java.util.Set;

import util.FileScanner;
import util.Node;
import util.ParseException;
import util.StringUtils;

@SuppressWarnings("unused")
public class Gen_math {
	private FileScanner fs;
	private char curChar;

	public Gen_math(String fileName, Set<Character> ignore)
			throws FileNotFoundException {
		this.fs = new FileScanner(fileName, ignore);
		curChar = fs.read();
	}

	private Node f_Digit(C_Digit arg_0) throws ParseException {
		Node cur = new Node("Digit", (char) -1);
		for (char c : new char[] { '3', '2', '1', '0', '7', '6', '5', '4', '9',
				'8' }) {
			if (c == curChar) {
				while (curChar < (char) 48 || curChar > (char) 57) {
					if (fs.ignore(curChar)) {
						curChar = fs.read();
					} else {
						throw new ParseException(fs.getPosition(),
								"" + curChar, "0 — 9");
					}
				}
				C__Terminal arg_1 = new C__Terminal("" + curChar);
				cur.addChild(new Node(
						"" + StringUtils.bestView(curChar, false), curChar));
				curChar = fs.read();

				arg_0.value = arg_1.text.charAt(0) - '0';

				return cur;
			}
		}
		if (fs.ignore(curChar)) {
			curChar = fs.read();
			return f_Digit(arg_0);
		}
		throw new ParseException(fs.getPosition(), "" + curChar);
	}

	private Node f_E(C_E arg_0) throws ParseException {
		Node cur = new Node("E", (char) -1);
		for (char c : new char[] { '3', '2', '1', '0', '7', '6', '5', '4', '(',
				'9', '8' }) {
			if (c == curChar) {
				C_T arg_1 = new C_T();
				cur.addChild(f_T(arg_1));
				C_E2 arg_2 = new C_E2();
				cur.addChild(f_E2(arg_2));

				arg_0.sum = arg_1.prod + arg_2.sum;

				return cur;
			}
		}
		if (fs.ignore(curChar)) {
			curChar = fs.read();
			return f_E(arg_0);
		}
		throw new ParseException(fs.getPosition(), "" + curChar);
	}

	private Node f_E2(C_E2 arg_0) throws ParseException {
		Node cur = new Node("E2", (char) -1);
		for (char c : new char[] { '+' }) {
			if (c == curChar) {
				while (curChar < (char) 43 || curChar > (char) 43) {
					if (fs.ignore(curChar)) {
						curChar = fs.read();
					} else {
						throw new ParseException(fs.getPosition(),
								"" + curChar, "+");
					}
				}
				C__Terminal arg_1 = new C__Terminal("" + curChar);
				cur.addChild(new Node(
						"" + StringUtils.bestView(curChar, false), curChar));
				curChar = fs.read();
				C_T arg_2 = new C_T();
				cur.addChild(f_T(arg_2));
				C_E2 arg_3 = new C_E2();
				cur.addChild(f_E2(arg_3));

				arg_0.sum = arg_2.prod + arg_3.sum;

				return cur;
			}
		}
		for (char c : new char[] { '\uffff', ')' }) {
			if (c == curChar) {
				C_Eps arg_1 = new C_Eps();
				cur.addChild(f_Eps(arg_1));

				arg_0.sum = 0;

				return cur;
			}
		}
		if (fs.ignore(curChar)) {
			curChar = fs.read();
			return f_E2(arg_0);
		}
		throw new ParseException(fs.getPosition(), "" + curChar);
	}

	private Node f_Eps(C_Eps arg_0) throws ParseException {
		Node cur = new Node("Eps", (char) -1);
		for (char c : new char[] { '\uffff', '*', '+', ')' }) {
			if (c == curChar) {
				C__Terminal arg_1 = new C__Terminal("");
				cur.addChild(new Node("", (char) -1));
				return cur;
			}
		}
		if (fs.ignore(curChar)) {
			curChar = fs.read();
			return f_Eps(arg_0);
		}
		throw new ParseException(fs.getPosition(), "" + curChar);
	}

	private Node f_F(C_F arg_0) throws ParseException {
		Node cur = new Node("F", (char) -1);
		for (char c : new char[] { '(' }) {
			if (c == curChar) {
				while (curChar < (char) 40 || curChar > (char) 40) {
					if (fs.ignore(curChar)) {
						curChar = fs.read();
					} else {
						throw new ParseException(fs.getPosition(),
								"" + curChar, "(");
					}
				}
				C__Terminal arg_1 = new C__Terminal("" + curChar);
				cur.addChild(new Node(
						"" + StringUtils.bestView(curChar, false), curChar));
				curChar = fs.read();
				C_E arg_2 = new C_E();
				cur.addChild(f_E(arg_2));
				while (curChar < (char) 41 || curChar > (char) 41) {
					if (fs.ignore(curChar)) {
						curChar = fs.read();
					} else {
						throw new ParseException(fs.getPosition(),
								"" + curChar, ")");
					}
				}
				C__Terminal arg_3 = new C__Terminal("" + curChar);
				cur.addChild(new Node(
						"" + StringUtils.bestView(curChar, false), curChar));
				curChar = fs.read();

				arg_0.value = arg_2.sum;

				return cur;
			}
		}
		for (char c : new char[] { '3', '2', '1', '0', '7', '6', '5', '4', '9',
				'8' }) {
			if (c == curChar) {
				C_Number arg_1 = new C_Number();
				cur.addChild(f_Number(arg_1));

				arg_0.value = arg_1.value;

				return cur;
			}
		}
		if (fs.ignore(curChar)) {
			curChar = fs.read();
			return f_F(arg_0);
		}
		throw new ParseException(fs.getPosition(), "" + curChar);
	}

	private Node f_MaybeDigits(C_MaybeDigits arg_0) throws ParseException {
		Node cur = new Node("MaybeDigits", (char) -1);
		for (char c : new char[] { '3', '2', '1', '0', '7', '6', '5', '4', '9',
				'8' }) {
			if (c == curChar) {
				C_Digit arg_1 = new C_Digit();
				cur.addChild(f_Digit(arg_1));
				C_MaybeDigits arg_2 = new C_MaybeDigits();
				cur.addChild(f_MaybeDigits(arg_2));

				arg_0.exist = true;
				arg_0.value = arg_2.exist ? (10 * arg_1.value + arg_2.value)
						: arg_1.value;

				return cur;
			}
		}
		for (char c : new char[] { '\uffff', '*', '+', ')' }) {
			if (c == curChar) {
				C_Eps arg_1 = new C_Eps();
				cur.addChild(f_Eps(arg_1));

				arg_0.exist = false;

				return cur;
			}
		}
		if (fs.ignore(curChar)) {
			curChar = fs.read();
			return f_MaybeDigits(arg_0);
		}
		throw new ParseException(fs.getPosition(), "" + curChar);
	}

	private Node f_Number(C_Number arg_0) throws ParseException {
		Node cur = new Node("Number", (char) -1);
		for (char c : new char[] { '3', '2', '1', '0', '7', '6', '5', '4', '9',
				'8' }) {
			if (c == curChar) {
				C_Digit arg_1 = new C_Digit();
				cur.addChild(f_Digit(arg_1));
				C_MaybeDigits arg_2 = new C_MaybeDigits();
				cur.addChild(f_MaybeDigits(arg_2));

				arg_0.value = arg_2.exist ? (10 * arg_1.value + arg_2.value)
						: arg_1.value;

				return cur;
			}
		}
		if (fs.ignore(curChar)) {
			curChar = fs.read();
			return f_Number(arg_0);
		}
		throw new ParseException(fs.getPosition(), "" + curChar);
	}

	private Node f_S(C_S arg_0) throws ParseException {
		Node cur = new Node("S", (char) -1);
		for (char c : new char[] { '3', '2', '1', '0', '7', '6', '5', '4', '(',
				'9', '8' }) {
			if (c == curChar) {
				C_E arg_1 = new C_E();
				cur.addChild(f_E(arg_1));

				System.out.println(arg_1.sum);

				return cur;
			}
		}
		if (fs.ignore(curChar)) {
			curChar = fs.read();
			return f_S(arg_0);
		}
		throw new ParseException(fs.getPosition(), "" + curChar);
	}

	private Node f_T(C_T arg_0) throws ParseException {
		Node cur = new Node("T", (char) -1);
		for (char c : new char[] { '3', '2', '1', '0', '7', '6', '5', '4', '(',
				'9', '8' }) {
			if (c == curChar) {
				C_F arg_1 = new C_F();
				cur.addChild(f_F(arg_1));
				C_T2 arg_2 = new C_T2();
				cur.addChild(f_T2(arg_2));

				arg_0.prod = arg_1.value * arg_2.prod;

				return cur;
			}
		}
		if (fs.ignore(curChar)) {
			curChar = fs.read();
			return f_T(arg_0);
		}
		throw new ParseException(fs.getPosition(), "" + curChar);
	}

	private Node f_T2(C_T2 arg_0) throws ParseException {
		Node cur = new Node("T2", (char) -1);
		for (char c : new char[] { '*' }) {
			if (c == curChar) {
				while (curChar < (char) 42 || curChar > (char) 42) {
					if (fs.ignore(curChar)) {
						curChar = fs.read();
					} else {
						throw new ParseException(fs.getPosition(),
								"" + curChar, "*");
					}
				}
				C__Terminal arg_1 = new C__Terminal("" + curChar);
				cur.addChild(new Node(
						"" + StringUtils.bestView(curChar, false), curChar));
				curChar = fs.read();
				C_F arg_2 = new C_F();
				cur.addChild(f_F(arg_2));
				C_T2 arg_3 = new C_T2();
				cur.addChild(f_T2(arg_3));

				arg_0.prod = arg_2.value * arg_3.prod;

				return cur;
			}
		}
		for (char c : new char[] { '\uffff', '+', ')' }) {
			if (c == curChar) {
				C_Eps arg_1 = new C_Eps();
				cur.addChild(f_Eps(arg_1));

				arg_0.prod = 1;

				return cur;
			}
		}
		if (fs.ignore(curChar)) {
			curChar = fs.read();
			return f_T2(arg_0);
		}
		throw new ParseException(fs.getPosition(), "" + curChar);
	}

	private Node f_WS(C_WS arg_0) throws ParseException {
		Node cur = new Node("WS", (char) -1);
		for (char c : new char[] { ' ' }) {
			if (c == curChar) {
				while (curChar < (char) 32 || curChar > (char) 32) {
					if (fs.ignore(curChar)) {
						curChar = fs.read();
					} else {
						throw new ParseException(fs.getPosition(),
								"" + curChar, " ");
					}
				}
				C__Terminal arg_1 = new C__Terminal("" + curChar);
				cur.addChild(new Node(
						"" + StringUtils.bestView(curChar, false), curChar));
				curChar = fs.read();
				return cur;
			}
		}
		for (char c : new char[] { '\t' }) {
			if (c == curChar) {
				while (curChar < (char) 9 || curChar > (char) 9) {
					if (fs.ignore(curChar)) {
						curChar = fs.read();
					} else {
						throw new ParseException(fs.getPosition(),
								"" + curChar, "\\t");
					}
				}
				C__Terminal arg_1 = new C__Terminal("" + curChar);
				cur.addChild(new Node(
						"" + StringUtils.bestView(curChar, false), curChar));
				curChar = fs.read();
				return cur;
			}
		}
		for (char c : new char[] { '\n' }) {
			if (c == curChar) {
				while (curChar < (char) 10 || curChar > (char) 10) {
					if (fs.ignore(curChar)) {
						curChar = fs.read();
					} else {
						throw new ParseException(fs.getPosition(),
								"" + curChar, "\\n");
					}
				}
				C__Terminal arg_1 = new C__Terminal("" + curChar);
				cur.addChild(new Node(
						"" + StringUtils.bestView(curChar, false), curChar));
				curChar = fs.read();
				return cur;
			}
		}
		for (char c : new char[] { '\r' }) {
			if (c == curChar) {
				while (curChar < (char) 13 || curChar > (char) 13) {
					if (fs.ignore(curChar)) {
						curChar = fs.read();
					} else {
						throw new ParseException(fs.getPosition(),
								"" + curChar, "\\r");
					}
				}
				C__Terminal arg_1 = new C__Terminal("" + curChar);
				cur.addChild(new Node(
						"" + StringUtils.bestView(curChar, false), curChar));
				curChar = fs.read();
				return cur;
			}
		}
		if (fs.ignore(curChar)) {
			curChar = fs.read();
			return f_WS(arg_0);
		}
		throw new ParseException(fs.getPosition(), "" + curChar);
	}

	public Node getTree() throws ParseException {
		C_S stNonTerm = new C_S();
		Node ans = f_S(stNonTerm);
		if (curChar != (char) -1) {
			throw new ParseException(fs.getPosition(), "" + curChar, "eof");
		}
		return ans;
	}

	class C__Terminal {
		public String text;

		public C__Terminal(String text) {
			this.text = text;
		}
	}

	class C_Digit {
		int value;
	}

	class C_E {
		int sum;
	}

	class C_E2 {
		int sum;
	}

	class C_Eps {
	}

	class C_F {
		int value;
	}

	class C_MaybeDigits {
		boolean exist;
		int value;
	}

	class C_Number {
		int value;
	}

	class C_S {
	}

	class C_T {
		int prod;
	}

	class C_T2 {
		int prod;
	}

	class C_WS {
	}
}
